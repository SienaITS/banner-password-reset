package edu.siena.banner.passwordreset

import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional
class EmailService {
    private static final log = org.apache.commons.logging.LogFactory.getLog(this)

    static String getEmailAddress(def dataSource, def spridenPidm)
    {
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against datasource
        Sql sql = Sql.newInstance(dataSource)

        def row = sql.firstRow([spriden_pidm:spridenPidm], "select gzk_sc_common.f_get_email(:spriden_pidm, 'EMEEMO') from dual")
        def email = row[0]

        if (email == null)
        {
            try{
                throw new Exception("No email address found in Banner for pidm: $spridenPidm")
            }
            catch(exception)
            {
                log.error ("Exception occurred. ${exception?.message}", exception)
            }
        }
        sql.close()
        return email

    }
}
