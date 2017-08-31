package edu.siena.banner.passwordreset

import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional
class SsbService {

    static public def unlockAccount (def dataSource, def spridenPidm) {
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against dataSource
        Sql sql = Sql.newInstance(dataSource)

        def rowsChanged = sql.call("DECLARE BEGIN gb_third_party_access.p_update(p_pidm=> ?, p_pin_disabled_ind=>'N'); END;", [spridenPidm]); //execute stored procedure
        assert rowsChanged == 1 //confirm stored procedure executed successfully
    }


    static public String resetPin (def dataSource, def spridenPidm) {
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against dataSource
        Sql sql = Sql.newInstance(dataSource)

        def row = sql.firstRow([pidm:spridenPidm], "select gzkpwdreset.f_reset_ssb_pin(:pidm, 10) from dual")

        if (row == null)
        {
            throw new Exception("Self-Service PIN Reset Failed")
        }

        def rowsChanged = sql.call(
                """DECLARE
                BEGIN
                gb_third_party_access.p_update(p_pidm=> ?, p_pin_disabled_ind=>'N', p_usage_accept_ind=>'N', p_pin_exp_date=>TRUNC(SYSDATE));
                IF gb_pin_answer.f_exists(p_pidm=> ?, p_num=> 1) = 'Y'
                THEN
                gb_pin_answer.p_delete(p_pidm=> ?, p_num=> 1);
                END IF;
                IF gb_pin_answer.f_exists(p_pidm=> ?, p_num=> 2) = 'Y'
                THEN
                gb_pin_answer.p_delete(p_pidm=> ?, p_num=> 2);
                END IF;
                END;""",
                [spridenPidm, spridenPidm, spridenPidm, spridenPidm, spridenPidm]); //execute stored procedure
        assert rowsChanged == 1 //confirm stored procedure executed successfully
        sql.close()

        return  row[0]

    }

    static public Boolean isValidUser(def dataSource, def pidm){
        Boolean returnValue
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against dataSource
        Sql sql = Sql.newInstance(dataSource)

        def row = sql.firstRow([pidm:pidm], "select 1 from gobtpac where gobtpac_pidm = :pidm")

        if (row == null){
            returnValue = false
        }
        else {
            returnValue = true
        }

        sql.close()
        return returnValue


    }
}
