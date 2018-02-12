import edu.siena.banner.passwordreset.DESCodec

dataSource {
    pooled = true
    //jmxExport = true
    dialect = org.hibernate.dialect.Oracle10gDialect
    driverClassName = "oracle.jdbc.OracleDriver"
    username = "grails_user"
    password = DESCodec.decode("your_encrypted_password") //you can encrypt password with DESCodec.encode(string password)
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
//    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
    flush.mode = 'manual' // OSIV session flush mode outside of transactional context
}

// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "validate" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:oracle:thin:@bannerDBhostname:port:ORACLE_SID"


        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:oracle:thin:@bannerDBhostname:port:ORACLE_SID"
        }
    }
    production {
        dataSource {
            dbCreate = "validate"
            url = "jdbc:oracle:thin:@bannerDBhostname:port:ORACLE_SID"

        }
    }
}
