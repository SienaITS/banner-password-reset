--create_grails_user.sql
--Written by Mark Stanco for Siena College
--09/02/2015
--This script will create a generic oracle user called "GRAILS_USER".
--The GRAILS_USER should be used by all Grails web applications written by Siena College (This does not include Ellucian's Banner XE applications).  
--This script should be run one-time only to create the user and should be run as BANINST1.


-- drop user if it already exists
DROP USER GRAILS_USER CASCADE;

--create formfusion user
CREATE USER GRAILS_USER
IDENTIFIED BY password_removed
DEFAULT TABLESPACE DEVELOPMENT
PROFILE DEFAULT
TEMPORARY TABLESPACE TEMP;

--grant system privleges
GRANT CREATE SESSION TO GRAILS_USER;
--GRANT ALTER USER TO GRAILS_USER;
--alter user permission not required, however BANINST1 does require this permission
GRANT ALTER USER TO BANINST1; 
--grant table privleges that GRAILS_USER currently requires
GRANT SELECT ON saturn.spbpers TO GRAILS_USER;
GRANT SELECT ON saturn.spriden TO GRAILS_USER;
GRANT SELECT ON general.goremal TO GRAILS_USER;
GRANT SELECT ON general.gobeacc TO GRAILS_USER;
GRANT SELECT ON general.gobtpac TO GRAILS_USER;
GRANT SELECT ON PAYROLL.PEBEMPL TO GRAILS_USER;
GRANT SELECT, INSERT, UPDATE, DELETE ON GENERAL.GZRPWDRESET TO GRAILS_USER; 
GRANT SELECT ON GENERAL.GZRPWDRESET_SEQ TO GRAILS_USER;
GRANT EXECUTE ON BANINST1.GZKPWDRESET to GRAILS_USER;
GRANT EXECUTE ON BANINST1.GZK_SC_COMMON to GRAILS_USER;
GRANT EXECUTE ON BANINST1.gb_third_party_access to GRAILS_USER;
GRANT EXECUTE ON BANINST1.gb_pin_answer TO GRAILS_USER;
