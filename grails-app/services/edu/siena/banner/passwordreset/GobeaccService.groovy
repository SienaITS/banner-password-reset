package edu.siena.banner.passwordreset

import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional
class GobeaccService {

    private static final log = org.apache.commons.logging.LogFactory.getLog(this)

    static public int getPidmFromUsername (def dataSource, def username)
    {
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against dataSource
        Sql sql = Sql.newInstance(dataSource)

        def row = sql.firstRow([username:username], "select gobeacc_pidm from gobeacc where gobeacc_username = :username")
        sql.close()

        return row?.gobeacc_pidm
    }

    static public String getUsernameFromPidm (def dataSource, def pidm)
    {
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against dataSource
        Sql sql = Sql.newInstance(dataSource)

        def row = sql.firstRow([pidm:pidm], "select gobeacc_username from gobeacc where gobeacc_pidm = :pidm")
        sql.close()

        return row?.gobeacc_username
    }

    static public String getIdFromUsername (def dataSource, def username)
    {
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against dataSource
        Sql sql = Sql.newInstance(dataSource)

        def row = sql.firstRow([username:username], "select spriden_id from gobeacc, spriden where gobeacc_username = :username and spriden_pidm = gobeacc_pidm and spriden_change_ind is null")
        sql.close()

        if (row == null)
        {
            try{
                throw new Exception("GOBEACC/SPRIDEN record not found for username: $username")
            }
            catch(exception)
            {
                log.error ("Exception occurred. ${exception?.message}", exception)
            }
        }
        else{
            return row.spriden_id
        }
    }
}
