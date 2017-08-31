package edu.siena.banner.passwordreset

import grails.transaction.Transactional
import groovy.sql.Sql

import java.sql.SQLException

@Transactional
class InbService {

    static public def unlockAccount(def dataSource, def username){
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against dataSource
        Sql sql = Sql.newInstance(dataSource)

        def rowsChanged = sql.call("DECLARE BEGIN gzkpwdreset.p_unlock_account(?); END;", [username]); //execute stored procedure
        assert rowsChanged == 1 //confirm stored procedure executed successfully

        sql.close()
    }

    static public String resetPassword(def dataSource, def username) {
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against dataSource
        Sql sql = Sql.newInstance(dataSource)

        def password

        def rowsChanged = sql.call("DECLARE BEGIN gzkpwdreset.p_reset_password(?, ?); END;", [username, Sql.VARCHAR], { pwd ->
            password = pwd
        })

        sql.close()

        //println password
        return password
    }

    static public Boolean isValidUsername(def dataSource, def username)
    {
        Boolean returnValue
        assert dataSource != null, "dataSource is null.   Check DataSource.groovy file."
        //open up database session against dataSource
        Sql sql = Sql.newInstance(dataSource)

        try
        {
            def row = sql.firstRow([username:username], "SELECT DBMS_ASSERT.SCHEMA_NAME(:username) FROM dual")
            if(row != null)
                returnValue = true
        }
        catch(SQLException e){
            returnValue = false
        }
        finally {
            sql.close()
        }


        return returnValue
    }
}
