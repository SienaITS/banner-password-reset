package edu.siena.banner.passwordreset

import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional
class SpridenService {
    private static final log = org.apache.commons.logging.LogFactory.getLog(this)

    static public Account getSpridenInfoByPidm (def dataSource, int _spriden_pidm, Account account)
    {
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against dataSource
        Sql sql = Sql.newInstance(dataSource)

        if (account == null){
            try {
                throw new Exception("parameter account is null for pidm: $_spriden_pidm")
            }
            catch(exception)
            {
                log.error ("Exception occurred. ${exception?.message}", exception)
            }
        }

        def row = sql.firstRow([spriden_pidm: _spriden_pidm], 'select spriden_pidm, spriden_id, spriden_last_name, spriden_first_name from spriden where spriden_change_ind is null and spriden_pidm = :spriden_pidm')

        if (row != null)
        {
            account.spridenPidm = row.spriden_pidm
            account.spridenID = row.spriden_id
            account.lastName = row.spriden_last_name
            account.firstName = row.spriden_first_name
        }
        else if (row == null)
        {
            try{
                throw new Exception("spriden record not found for pidm: $_spriden_pidm")
            }
            catch(exception)
            {
                log.error ("Exception occurred. ${exception?.message}", exception)
            }
        }

        sql.close()
        return account;
    }

    static public Account getSpridenInfoById (def dataSource, String _spriden_id, Account account)
    {
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against dataSource
        Sql sql = Sql.newInstance(dataSource)

        if (account == null){
            try {
                throw new Exception("parameter account is null for id: $_spriden_id")
            }
            catch(exception)
            {
                log.error ("Exception occurred. ${exception?.message}", exception)
            }
        }

        def row = sql.firstRow([spriden_id: _spriden_id], 'select spriden_pidm, spriden_id, spriden_last_name, spriden_first_name from spriden where spriden_change_ind is null and spriden_id = :spriden_id')

        if (row != null)
        {
            account.spridenPidm = row.spriden_pidm
            account.spridenID = row.spriden_id
            account.lastName = row.spriden_last_name
            account.firstName = row.spriden_first_name
        }
        else if (row == null)
        {
            try{
                throw new Exception("spriden record not found for id: $_spriden_id")
            }
            catch(exception)
            {
                log.error ("Exception occurred. ${exception?.message}", exception)
            }
        }

        sql.close()
        return account;
    }

    static public Boolean isValidID(def dataSource, String _spriden_id)
    {
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against
        Sql sql = Sql.newInstance(dataSource)
        def row = sql.firstRow([spriden_id: _spriden_id], 'select 1 from spriden where spriden_change_ind is null and spriden_id = :spriden_id')
        sql.close()
        if (row == null)
        {
            return false;
        }
        else {
            return true;
        }
    }

    static public int getPidmFromID(def dataSource, String spridenId)
    {
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against
        Sql sql = Sql.newInstance(dataSource)
        def row = sql.firstRow([spriden_id: spridenId], 'select spriden_pidm from spriden where spriden_change_ind is null and spriden_id = :spriden_id')
        sql.close()
        return row?.spriden_pidm
    }
}
