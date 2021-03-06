DROP TABLE GENERAL.GZRPWDLOCK;

CREATE TABLE GENERAL.GZRPWDLOCK
(
    gzrpwdlock_id NUMBER(19) NOT NULL,
    gzrpwdlock_ldap_username VARCHAR2(20) NOT NULL,
    gzrpwdlock_ip_address VARCHAR2(45),
    gzrpwdlock_date_locked DATE NOT NULL,
    gzrpwdlock_date_unlocked DATE, 
    CONSTRAINT gzrpwdlock_PK PRIMARY KEY (gzrpwdlock_id)
);

CREATE OR REPLACE TRIGGER GENERAL.GZRPWDLOCK_DATE_LOCKED_TRG
BEFORE INSERT ON GENERAL.GZRPWDLOCK FOR EACH ROW
DECLARE
BEGIN
    :new.gzrpwdlock_date_locked := sysdate;
END GZRPWDLOCK_DATE_LOCKED_TRG;




CREATE OR REPLACE PUBLIC SYNONYM gzrpwdlock FOR GENERAL.GZRPWDLOCK;  

DROP SEQUENCE GENERAL.GZRPWDLOCK_SEQ;

CREATE SEQUENCE GENERAL.GZRPWDLOCK_SEQ
    MINVALUE 1
    MAXVALUE 9999999999999999999
    START WITH  1
    INCREMENT BY 1
    NOCYCLE
    ORDER
    CACHE 20;
    
CREATE OR REPLACE PUBLIC SYNONYM gzrpwdlock_SEQ FOR GENERAL.GZRPWDLOCK_SEQ;

COMMENT ON TABLE GENERAL.GZRPWDLOCK IS 'Table of Locked Users whom are no longer allowed to use Banner Password Reset Grails Application';
COMMENT ON COLUMN GENERAL.GZRPWDLOCK.gzrpwdlock_id IS 'Primary (Surrogate) Key for this table.  A unique one-up number.';
COMMENT ON COLUMN GENERAL.GZRPWDLOCK.gzrpwdlock_ldap_username IS 'The username of the Siena account pulled from LDAP/AD.';
COMMENT ON COLUMN GENERAL.GZRPWDLOCK.gzrpwdlock_ip_address IS 'The IP address of the web user';
COMMENT ON COLUMN GENERAL.GZRPWDLOCK.gzrpwdlock_date_locked IS 'The date and time when the account became locked.'; 
COMMENT ON COLUMN GENERAL.GZRPWDLOCK.gzrpwdlock_date_unlocked IS 'The date and time when ITS unlocked the account.';

GRANT SELECT, INSERT, UPDATE, DELETE ON GENERAL.GZRPWDLOCK TO GRAILS_USER; 
GRANT SELECT ON GENERAL.GZRPWDLOCK_SEQ TO GRAILS_USER;
 
    
