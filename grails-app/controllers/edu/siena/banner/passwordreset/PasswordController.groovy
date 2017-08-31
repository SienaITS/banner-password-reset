package edu.siena.banner.passwordreset

import org.springframework.security.core.context.SecurityContextHolder

class PasswordController {
    def systemLockedErrorMessage = "Your access to this system has been blocked due to too many invalid unlock/reset attempts.   Please contact helpdesk@siena.edu to re-gain access to this system."

    def index() {
        redirect(action:"passwordReset")
    }

    def passwordResetFlow = {
        initFlow {
            action {
                //set attempts to zero if not initialized
                if (session.attempts == null) {
                    session.attempts = 0
                }
                SienaUserDetails sienaUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal() //get AD attributes and username
                conversation.sienaUserDetails = sienaUserDetails;
                //check if employee is terminated here
                def pidm = SpridenService.getPidmFromID(dataSource, sienaUserDetails.employeeID)
                if(PebemplService.isTerminatedEmployee(dataSource, pidm)) {
                    flash.message = "You are a terminated employee and no longer have access to this system."
                    error()
                }
                else if (AccountService.isSystemAccessBlocked(dataSource, sienaUserDetails.username)) {
                    session.attempts++
                    flash.message = systemLockedErrorMessage
                    error()
                }
            }

            on("error").to "systemError"
            on("success").to"startResetAttempts"
        }

        startResetAttempts {
            subflow(action:"attempts")
            on("proceed").to "startReset"
            on("accountLocked") {
                flash.message = systemLockedErrorMessage
            }.to "systemError"
        }

        startReset {
            on("unlock").to "unlockAccount"
            on("reset").to "resetPassword"
        }

        unlockAccount {
            action {
                //SienaUserDetails sienaUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal() //get AD attributes and username
                conversation?.account = new Account('unlock', conversation.sienaUserDetails.username)
                def pidm = SpridenService.getPidmFromID(dataSource, conversation.sienaUserDetails.employeeID)
                def bannerUsername = GobeaccService.getUsernameFromPidm(dataSource, pidm)
                //check to disable INB reset/unlock
                if (InbService.isValidUsername(dataSource, bannerUsername))
                {
                    flow.disableINB = "false"
                }
                else {
                    flow.disableINB = "true"
                }
                //check to disable SSB reset/unlock
                if (SsbService.isValidUser(dataSource, pidm)){
                    flow.disableSSB = "false"
                }
                else {
                    flow.disableSSB = "true"
                }
            }
            on("success").to "chooseSystemAttempts"
        }

        resetPassword {
            action {
                //SienaUserDetails sienaUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal()  //get AD attributes and username
                conversation?.account = new Account('reset', conversation.sienaUserDetails.username)
                def pidm = SpridenService.getPidmFromID(dataSource, conversation.sienaUserDetails.employeeID)
                def bannerUsername = GobeaccService.getUsernameFromPidm(dataSource, pidm)
                //check to disable INB reset/unlock
                if (InbService.isValidUsername(dataSource, bannerUsername))
                {
                    flow.disableINB = "false"
                }
                else {
                    flow.disableINB = "true"
                }
                //check to disable SSB reset/unlock
                if (SsbService.isValidUser(dataSource, pidm)){
                    flow.disableSSB = "false"
                }
                else {
                    flow.disableSSB = "true"
                }
            }
            on("success").to "chooseSystemAttempts"
        }

        chooseSystemAttempts {
            subflow(action:"attempts")
            on("proceed").to "chooseSystem"
            on("accountLocked") {
                flash.message = systemLockedErrorMessage
            }.to "systemError"
        }

        chooseSystem {
            on("selfServiceBanner"){
              conversation?.account?.system = 'SSB'
            }.to "getIDAttempts"
            on("internetNativeBanner"){
                conversation?.account?.system = 'INB'
            }.to "getUsernameAttempts"
        }

        getIDAttempts {
            subflow(action:"attempts")
            on("proceed").to "getID"
            on("accountLocked") {
                flash.message = systemLockedErrorMessage
            }.to "systemError"
        }

        getID {
            on("submit").to "verifyID"
        }

        verifyID {
            action{
                //SienaUserDetails sienaUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal() //get AD attributes and username
                if(SpridenService.isValidID(dataSource, params.spridenID) == true)
                {
                    if (conversation.sienaUserDetails.employeeID == params.spridenID){
                        conversation.account = SpridenService.getSpridenInfoById(dataSource, params.spridenID, conversation.account)
                        //conversation.account.email = EmailService.getEmailAddress(dataSource, conversation.account.spridenPidm)
                        conversation.account.email = conversation.sienaUserDetails.email
                    }
                    else {
                        flash.message = "The Banner ID you entered is not associated with your Siena account.  Please re-enter your Banner ID."
                        session.attempts++
                        error()
                    }

                }
                else {
                    flash.message = "Invalid Banner ID entered: " + params.spridenID + " . Please re-enter a valid Banner ID."
                    session.attempts++
                    error()
                }
            }
            on("success").to "getBirthDate"
            on("error").to "getIDAttempts"
        }

        getUsernameAttempts {
            subflow(action:"attempts")
            on("proceed").to"getUsername"
            on("accountLocked") {
                flash.message = systemLockedErrorMessage
            }.to "systemError"
        }

        getUsername{
            on("submit").to "verifyUsername"

        }

        verifyUsername {
            action {
                //SienaUserDetails sienaUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal() //get AD attributes and username
                if (InbService.isValidUsername(dataSource, params.username.toUpperCase()) == true) {
                    conversation.account.bannerUsername = params.username.toUpperCase()
                    def spridenPidm = GobeaccService.getPidmFromUsername(dataSource, conversation.account.bannerUsername)
                    conversation.account = SpridenService.getSpridenInfoByPidm(dataSource, spridenPidm, conversation.account)
                    conversation.account.email = EmailService.getEmailAddress(dataSource, conversation.account.spridenPidm)
                    if (conversation.account.spridenID != conversation.sienaUserDetails.employeeID) {
                        flash.message = "The Banner username that you entered is not associated with your Siena account. Please re-enter your Banner username."
                        session.attempts++
                        error()
                    }
                }
                else {
                    flash.message = "Invalid INB username entered: " + params.username + " . Please enter a valid username."
                    session.attempts++
                    error()
                }
            }
            on("success").to "getBirthDate"
            on("error").to "getUsernameAttempts"
        }

        getBirthDateAttempts {
            subflow(action:"attempts")
            on("proceed").to"getBirthDate"
            on("accountLocked") {
                flash.message = systemLockedErrorMessage
            }.to "systemError"
        }

        getBirthDate {
            on("startOver").to "initFlow"
            on("submit").to "verifyBirthDate"

        }

        verifyBirthDate {
            action {
                if (SpbpersService.verifyBirthDate(dataSource, params.birthDate, conversation.account) == true)
                {
                    if (conversation?.account?.system == 'INB')
                    {
                        if (conversation?.account?.action == 'unlock')
                        {
                            unlockINB()
                        }
                        else if (conversation?.account?.action == 'reset')
                        {
                            resetINB()
                        }
                    }
                    else if (conversation?.account?.system == 'SSB')
                    {
                        if (conversation?.account?.action == 'unlock')
                        {
                            unlockSSB()
                        }
                        else if (conversation?.account?.action == 'reset')
                        {
                            resetSSB()
                        }
                    }
                }
                else {
                    flash.message = "The birth date that you provided does not match our records. Please provide your correct birth date."
                    session.attempts++
                    error()
                }
            }
            on("unlockSSB").to "unlockSSB"
            on("resetSSB").to "resetSSB"
            on("unlockINB").to "unlockINB"
            on("resetINB").to "resetINB"
            on("error").to "getBirthDateAttempts"

        }

        unlockSSB {
            action{
                SsbService.unlockAccount(dataSource, conversation.account.spridenPidm)
                mailService.sendMail{
                    to conversation.account.email
                    from "bannerpwdreset@siena.edu"
                    subject "Banner SSB Account Unlocked"
                    html g.render(template: "/mail/unlockSSB", model:[name: conversation.account.firstName + " " + conversation.account.lastName])
                }
                conversation.account.save(flush:true)
                session.attempts = 0
            }
            on("success").to "unlockSSBSuccess"
        }

        unlockSSBSuccess {
            on("submit").to "initFlow"
        }

        resetSSB {
            action{
                def pin = SsbService.resetPin(dataSource, conversation.account.spridenPidm)

                def pwpushLink = 'https://pwpush.siena.edu/p/'

                //create pwpush.siena.edu link by making HTTP POST JSON request against pwpush server
                withHttp(uri: "https://pwpush.siena.edu/p.json") {
                    //%2E is URL encoded for period . character
                    def data = request(groovyx.net.http.Method.POST, groovyx.net.http.ContentType.JSON ) { req ->
                        body = [
                                //json parameters for http request
                                password: [
                                        payload: pin,
                                        expire_after_days: 30,
                                        expire_after_views: 11
                                ]
                        ]

                        response.success = { resp, json ->
                            pwpushLink += json.url_token
                        }
                    }
                }

                //issue a get request after the pwpush creation to avoid first time creation part of interface.
                withHttp(uri: pwpushLink) {
                    def data = request(groovyx.net.http.Method.GET, groovyx.net.http.ContentType.HTML ) { req ->
                        response.failure = { resp ->
                            throw new Exception("pwpush get request failed: " + resp)
                        }
                        response.success = { resp ->
                            println resp
                        }
                    }
                }

                //email password to user.
                mailService.sendMail{
                    to conversation.sienaUserDetails.email
                    from "bannerpwdreset@siena.edu"
                    subject "Banner SSB Password Reset"
                    html g.render(template: "/mail/resetSSB", model:[name: conversation.account.firstName + " " + conversation.account.lastName, password:pwpushLink])
                }
                conversation.account.save(flush:true)
                session.attempts = 0
            }

            on("success").to "resetSSBSuccess"
        }

        resetSSBSuccess {
            on("submit").to "initFlow"
        }

        unlockINB {
            action{
                InbService.unlockAccount(dataSource, conversation.account.bannerUsername)
                mailService.sendMail{
                    to conversation.sienaUserDetails.email
                    from "bannerpwdreset@siena.edu"
                    subject "Banner INB Account Unlocked"
                    html g.render(template: "/mail/unlockINB", model:[name: conversation.account.firstName + " " + conversation.account.lastName])
                }
                conversation.account.save(flush:true)
                session.attempts = 0
            }
            on("success").to "unlockINBSuccess"
        }

        unlockINBSuccess{
            on("submit").to "initFlow"
        }

        resetINB {
            action{
                def password = InbService.resetPassword(dataSource, conversation.account.bannerUsername)

                def pwpushLink = 'https://pwpush.siena.edu/p/'

                //create pwpush.siena.edu link by making HTTP POST JSON request against pwpush server
                withHttp(uri: "https://pwpush.siena.edu/p.json") {
                    //%2E is URL encoded for period . character
                    def data = request(groovyx.net.http.Method.POST, groovyx.net.http.ContentType.JSON ) { req ->
                        body = [
                                //json parameters for http request
                                password: [
                                        payload: password,
                                        expire_after_days: 30,
                                        expire_after_views: 10
                                ]
                        ]

                        response.success = { resp, json ->
                            pwpushLink += json.url_token
                        }
                    }
                }


                //issue a get request after the pwpush creation to avoid first time creation part of interface.
                withHttp(uri: pwpushLink) {
                    def data = request(groovyx.net.http.Method.GET, groovyx.net.http.ContentType.HTML ) { req ->
                        response.failure = { resp ->
                            throw new Exception("pwpush get request failed: " + resp)
                        }
                        response.success = { resp ->
                            println resp
                        }
                    }
                }

                //email password to user.
                mailService.sendMail{
                    to conversation.sienaUserDetails.email
                    from "bannerpwdreset@siena.edu"
                    subject "Banner INB Password Reset"
                    html g.render(template: "/mail/resetINB", model:[name: conversation.account.firstName + " " + conversation.account.lastName, password:pwpushLink])
                }
                conversation.account.save(flush:true)
                session.attempts = 0
            }
            on("success").to "resetINBSuccess"
            //on("error").to "resetINBError"
        }

        resetINBSuccess{
            on("submit").to "initFlow"
        }

        systemError {
            //do nothing just display error message through gsp layer
        }

    }

    def attemptsFlow = {
        checkAttempts{
            action {
                if (session.attempts >= 5){
                    //create LockedAccount object
                    conversation?.lockedAccount = new LockedAccount(conversation.sienaUserDetails.username, IpService.getIPAddress(request))
                    if (conversation?.account?.firstName == null){
                        conversation.account = SpridenService.getSpridenInfoById(dataSource, conversation.sienaUserDetails.employeeID, conversation.account)
                        conversation.account.email = EmailService.getEmailAddress(dataSource, conversation.account.spridenPidm)
                    }
                    //lock account - maybe not needed?
                    //email/notify user
                    mailService.sendMail{
                        to conversation.sienaUserDetails.email
                        from "bannerpwdreset@siena.edu"
                        subject "Banner Password Reset Access Blocked"
                        html g.render(template: "/mail/systemAccountLocked", model:[name: conversation.account.firstName + " " + conversation.account.lastName])

                    }
                    //email/notify ITS?
                    mailService.sendMail{
                        to "mstanco@siena.edu"
                        from "bannerpwdreset@siena.edu"
                        subject "Banner Password Reset Access Blocked"
                        html g.render(template: "/mail/systemAccountLockedITS", model:[ldap_username: conversation.lockedAccount.ldapUsername, ip_address:conversation.lockedAccount.ipAddress])
                    }

                    //save lockedAccount to DB to prevent user from using the application
                    conversation.lockedAccount.save()
                    error()
                }
                else if (AccountService.isSystemAccessBlocked(dataSource, conversation.sienaUserDetails.username)) {
                    error()
                }
            }
            on("success").to "proceed"
            on("error").to "accountLocked"
        }
        proceed()
        accountLocked()
    }
}
