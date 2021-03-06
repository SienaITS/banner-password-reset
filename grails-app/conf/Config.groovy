import edu.siena.banner.passwordreset.DESCodec
// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}


grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
log4j.main = {
    // Example of changing the log pattern for the default console appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}
    //info ('net.sf.ehcache.hibernate') //logging for services
    //info 'grails.app' //services and controllers - siena - MCS

    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'grails.app.services', //services - added by siena- MCS
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
}

grails{
    mail{
        //added mail server configuration to be able to send email through grails application
        host = "YOUR_SMTP_HOST"  //enter smtp host name here
        port = 25
    }
}

//LDAP configuration example
//grails.plugin.springsecurity.ldap.context. managerDn = 'uid=admin,ou=system'
//grails.plugin.springsecurity.ldap.context. managerPassword = 'secret'
//grails.plugin.springsecurity.ldap.context. server = 'ldap://localhost:10389'
//grails.plugin.springsecurity.ldap.authorities. groupSearchBase =
//        'ou=groups,dc=yourcompany,dc=com'
//grails.plugin.springsecurity.ldap.search. base = 'dc=yourcompany,dc=com'

// LDAP config for Active Directory
grails.plugin.springsecurity.ldap.context.managerDn = 'uid=admin,ou=system'
grails.plugin.springsecurity.ldap.context.managerPassword = DESCodec.decode("your_encrypted_password") //you can encrypt password with DESCodec.encode(string password)
grails.plugin.springsecurity.ldap.context.server = 'ldap://ldapserverhostname:port/'
grails.plugin.springsecurity.ldap.authorities.ignorePartialResultException = true // typically needed for Active Directory
grails.plugin.springsecurity.ldap.search.base = 'ou=groups,dc=yourcompany,dc=com' //all the LDAP groups that you want to have access to the application
grails.plugin.springsecurity.ldap.search.filter="sAMAccountName={0}" // for Active Directory you need this
grails.plugin.springsecurity.ldap.search.searchSubtree = true
grails.plugin.springsecurity.ldap.auth.hideUserNotFoundExceptions = false
grails.plugin.springsecurity.ldap.search.attributesToReturn = ['mail',
                                                                'displayName',
                                                                'employeeID'] // extra attributes you want returned; see below for custom classes that access this data
grails.plugin.springsecurity.providerNames = ['ldapAuthProvider',
                                              'anonymousAuthenticationProvider'] // specify this when you want to skip attempting to load from db and only use LDAP
grails.plugin.springsecurity.ldap.useRememberMe = false //disable remember me

//role-specific LDAP config
grails.plugin.springsecurity.ldap.authorities.groupSearchBase = 'YOUR_ITS_LDAP_CN' //enter full CN of ldap group that has all ITS users who you want to access admin application.
grails.plugin.springsecurity.ldap.authorities.retrieveGroupRoles = true
grails.plugin.springsecurity.ldap.authorities.groupSearchFilter = 'member={0}' // Active Directory specific for not supporting group recursion:
//grails.plugin.springsecurity.ldap.authorities.groupSearchFilter = '(member:1.2.840.113556.1.4.1941:={0})' // Active Directory specific for group recursion
grails.plugin.springsecurity.ldap.authorities.retrieveDatabaseRoles=false

//added from https://stackoverflow.com/questions/20696211/access-denied-and-getting-sorry-youre-not-authorized-to-view-this-page-after
grails.plugin.springsecurity.rejectIfNoRule = false
grails.plugin.springsecurity.fii.rejectPublicInvocations = false
grails.plugin.springsecurity.securityConfigType = 'InterceptUrlMap'
grails.plugin.springsecurity.interceptUrlMap = [
        '/':                              ['permitAll'],
//        //'/':               ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/index':                         ['permitAll'],
        '/index.gsp':                     ['permitAll'],
        '/error':                         ['permitAll'],
        '/error.gsp':                     ['permitAll'],
        '/assets/**':                     ['permitAll'],
        //'/**/js/**':                      ['permitAll'],
        //'/**/css/**':                     ['permitAll'],
        '/**/images/**':                  ['permitAll'],
        '/**/favicon.ico':                ['permitAll'],
        '/login/**':                      ['permitAll'],
        '/logout/**':                     ['permitAll'],
        '/auth/**':                   ['permitAll'],
        '/password/**':                   ['IS_AUTHENTICATED_FULLY'],
        '/mail/**':                       ['IS_AUTHENTICATED_FULLY'],
        '/lockedAccount/**':                   ['ROLE_ITS_GLOBAL'],
]

//needed for bruteforce-defender/recaptcha-spring-security
grails.plugin.springsecurity.useSecurityEventListener = true

//config needed for recaptcha-spring-security plugin
bruteforcedefender {
    time = 5
    allowedNumberOfAttempts = 3
}