package edu.siena.banner.passwordreset

import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional
class PebemplService {

    //returns true if the person is an active employee, otherwise returns false
    static public Boolean isActiveEmployee(def dataSource, def pidm){
        return getEmployeeByStatus(dataSource, pidm, 'A')
    }
    //returns true if the employee is terminated, otherwise returns false
    static public Boolean isTerminatedEmployee(def dataSource, def pidm){
        return getEmployeeByStatus(dataSource, pidm, 'T')
    }

    static private Boolean getEmployeeByStatus(def dataSource, def pidm, def status){
        Boolean returnValue
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against
        Sql sql = Sql.newInstance(dataSource)
        def row = sql.firstRow([pidm:pidm, status:status], 'select 1 from pebempl where pebempl_pidm = :pidm and pebempl_empl_status = :status')
        if (row == null)
        {
            returnValue = false
        }
        else {
            returnValue = true;
        }
        sql.close()

        return returnValue

    }
}
