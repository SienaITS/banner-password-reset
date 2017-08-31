# banner-password-reset
Grails Application for resetting Banner Passwords
This repository contains the source code for a Grails web application written by Siena college.
The web application provides a portal for INB and SSB users to reset their passwords and/or unlock their accounts.
DISCLAIMER: Siena College is not providing any support for this application and is not responsible for any outcomes that may occur if you choose to run the application in your environment. 



SETUP to deploy system

The following config files need to be setup:

grails-app/conf/Config.groovy  - contains your LDAP server connection info for LDAP authenication

grails-app/conf/DataSource.groovy -  contains your oracle DB connection info

The following database objects need to be present in your Banner Database:
(Check out the /database/ directory for scripts)

USER: GRAILS_USER - or whatever other user you decide to run the application as.

PACKAGES: BANINST1.GZKPWDRESET

TABLES: GENERAL.GZRPWDRESET, GENERAL.GZRPWDLOCK


