package edu.siena.banner.passwordreset

class LockedAccount {
    String ldapUsername
    String ipAddress
    Date dateLocked

    LockedAccount (String _ldapUsername, String _ipAddress)
    {
        ldapUsername = _ldapUsername
        ipAddress = _ipAddress
        dateLocked = new Date()
    }

    static constraints = {
        ldapUsername (nullable: false)
        dateLocked (nullable: false)
    }

    static mapping = {
        table name: 'GZRPWDLOCK', schema: 'GENERAL'
        id column:'gzrpwdlock_id', generator:'sequence', params: [sequence:'GZRPWDLOCK_SEQ']
        version false
        ldapUsername column: 'gzrpwdlock_ldap_username', sqlType: 'VARCHAR2', length: 20
        ipAddress column: 'gzrpwdlock_ip_address', sqlType: 'VARCHAR2', length: 45
        dateLocked column: 'gzrpwdlock_date_locked', sqlType: 'DATE'
    }
}
