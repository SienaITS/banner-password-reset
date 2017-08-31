DROP TABLE GENERAL.GZRPWDRESET;

CREATE TABLE GENERAL.GZRPWDRESET
(
    gzrpwdreset_id NUMBER(19) NOT NULL,
    gzrpwdreset_ldap_username VARCHAR2(20),
    gzrpwdreset_inb_username VARCHAR2(30),
    gzrpwdreset_spriden_pidm NUMBER(8),
    gzrpwdreset_spriden_id VARCHAR2(9),
    gzrpwdreset_last_name VARCHAR2(60),
    gzrpwdreset_first_name VARCHAR2(60),
    gzrpwdreset_email VARCHAR2(128) NOT NULL,
    gzrpwdreset_action VARCHAR2(6) NOT NULL,
    gzrpwdreset_system VARCHAR2(3) NOT NULL,
    gzrpwdreset_action_date DATE NOT NULL,
    CONSTRAINT GZRPWDRESET_PK PRIMARY KEY (gzrpwdreset_id),
    CONSTRAINT gzbpwdreset_action_ck CHECK (gzrpwdreset_action IN ('unlock', 'reset')),
    CONSTRAINT gzbpwdreset_system_ck CHECK (gzrpwdreset_system IN ('INB', 'SSB'))
);

CREATE OR REPLACE TRIGGER GENERAL.GZRPWDRESET_ACTION_DATE_TRG
BEFORE INSERT ON GENERAL.GZRPWDRESET FOR EACH ROW
DECLARE
BEGIN
    :new.gzrpwdreset_action_date := sysdate;
END GZRPWDRESET_ACTION_DATE_TRG;




CREATE OR REPLACE PUBLIC SYNONYM GZRPWDRESET FOR GENERAL.GZRPWDRESET;  

DROP SEQUENCE GENERAL.GZRPWDRESET_SEQ;

CREATE SEQUENCE GENERAL.GZRPWDRESET_SEQ
    MINVALUE 1
    MAXVALUE 9999999999999999999
    START WITH  1
    INCREMENT BY 1
    NOCYCLE
    ORDER
    CACHE 20;
    
CREATE OR REPLACE PUBLIC SYNONYM GZRPWDRESET_SEQ FOR GENERAL.GZRPWDRESET_SEQ;

COMMENT ON TABLE GENERAL.GZRPWDRESET IS 'Tracking table for Banner Password Resets';
COMMENT ON COLUMN GENERAL.GZRPWDRESET.gzrpwdreset_id IS 'Primary (Surrogate) Key for this table.  A unique one-up number.';
COMMENT ON COLUMN GENERAL.GZRPWDRESET.gzrpwdreset_ldap_username IS 'The username of the Siena account pulled from LDAP/AD.';
COMMENT ON COLUMN GENERAL.GZRPWDRESET.gzrpwdreset_inb_username IS 'The Oracle (INB) username of the user whose password was reset';
COMMENT ON COLUMN GENERAL.GZRPWDRESET.gzrpwdreset_spriden_pidm IS 'The spriden_pidm of the person: A unique 8 digit number.  For SSB, this tells us which account to reset.  For INB, this is additional optional information.';  
COMMENT ON COLUMN GENERAL.GZRPWDRESET.gzrpwdreset_spriden_id IS 'The spriden_id of the person: A unique 9 digit ID number.  For SSB, this tells us which account to reset.  For INB, this is additional optional information.';
COMMENT ON COLUMN GENERAL.GZRPWDRESET.gzrpwdreset_last_name IS 'The last name of the person whose account is being reset or unlock.  Data is pulled from spriden and stored here.';
COMMENT ON COLUMN GENERAL.GZRPWDRESET.gzrpwdreset_first_name IS 'The first name of the person whose account is being reset or unlock.  Data is pulled from spriden and stored here.';
COMMENT ON COLUMN GENERAL.GZRPWDRESET.gzrpwdreset_email IS 'The email address to notify the user at.  New passwords will be sent to this email address Data is pulled from goremal at time of action and stored here for historical purposes.';
COMMENT ON COLUMN GENERAL.GZRPWDRESET.gzrpwdreset_action IS 'The action that was requested by the user using the password reset application.  Valid actions include: unlocking an account or reseting a password.';
COMMENT ON COLUMN GENERAL.GZRPWDRESET.gzrpwdreset_system IS 'The system to perform the action on.  Valid choices are either Internet Native Banner (INB) or Self-Service Banner (SSB).';
COMMENT ON COLUMN GENERAL.GZRPWDRESET.gzrpwdreset_action_date IS 'The date and time when the action occurred.'; 

GRANT SELECT, INSERT, UPDATE, DELETE ON GENERAL.GZRPWDRESET TO GRAILS_USER; 
GRANT SELECT ON GENERAL.GZRPWDRESET_SEQ TO GRAILS_USER;
 
    
