package edu.siena.banner.passwordreset

import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional
class AccountService {

    static public Boolean isSystemAccessBlocked(def dataSource, String ldapUsername) {
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against dataSource
        Sql sql = Sql.newInstance(dataSource)

        def row = sql.firstRow([ldap_username:ldapUsername], "select 1 from gzrpwdlock where gzrpwdlock_ldap_username = :ldap_username and gzrpwdlock_date_locked < SYSDATE and (gzrpwdlock_date_unlocked IS NULL or gzrpwdlock_date_unlocked > SYSDATE)");
        Boolean returnValue

        if (row == null)
        {
            returnValue = false;
        }
        else{
            returnValue = true;
        }

        sql.close()
        return returnValue;
    }
}
