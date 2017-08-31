package edu.siena.banner.passwordreset

import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional
class EmailService {

    static String getEmailAddress(def dataSource, def spridenPidm)
    {
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against datasource
        Sql sql = Sql.newInstance(dataSource)

        def row = sql.firstRow([spriden_pidm:spridenPidm], "select gzk_sc_common.f_get_email(:spriden_pidm, 'EMBEMO') from dual")
        def email = row[0]

        if (email == null)
        {
            throw new Exception("No email address found in Banner")
        }
        sql.close()
        return email

    }
}
