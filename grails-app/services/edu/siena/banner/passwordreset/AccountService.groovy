package edu.siena.banner.passwordreset

import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional
class AccountService {

    static public Boolean isSystemAccessBlocked(def dataSource, String ldapUsername) {
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against dataSource
        Sql sql = Sql.newInstance(dataSource)

        def row = sql.firstRow([ldap_username:ldapUsername], "select 1 from gzrpwdlock where gzrpwdlock_ldap_username = :ldap_username");
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
