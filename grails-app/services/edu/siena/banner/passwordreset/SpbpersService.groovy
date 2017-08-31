package edu.siena.banner.passwordreset

import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional
class SpbpersService {

    static public Boolean verifyBirthDate (def dataSource, Date birthDate, Account account){
        Boolean returnValue
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against
        Sql sql = Sql.newInstance(dataSource)

        def row = sql.firstRow([birth_date: birthDate.format('MM/dd/yyyy'), pidm: account.spridenPidm], "select 1 from spbpers where spbpers_pidm = :pidm and TRUNC(spbpers_birth_date) = TO_DATE(:birth_date, 'MM/DD/YYYY')");
        if (row == null)
        {
            returnValue = false
        }
        else
        {
            returnValue = true
        }

        sql.close()

        return returnValue

    }

}
