package edu.siena.banner.passwordreset

//poorly named class but this class represents a banner password reset/unlock action.
class Account implements Serializable {

    String ldapUsername
    String bannerUsername
    int spridenPidm
    String spridenID
    String lastName
    String firstName
    String email
    String action
    String system
    //String userType
    Date actionDate
    String ipAddress

    Account(String _system, String _ldapUsername, String _ipAddress)
    {
        system = _system
        ldapUsername = _ldapUsername
        ipAddress = _ipAddress
        actionDate = new Date()
    }

    static constraints = {
        ldapUsername (nullable:false)
        bannerUsername (nullable: true)
        spridenPidm (nullablle:true)
        spridenID (nullable: true)
        firstName (nullable: true)
        lastName (nullable: true)
        action (inList: ['unlock', 'reset'])
        system (inlist: ['INB', 'SSB'])
        //userType (inList: ['EMPLOYEE', 'FACULTY', 'STUDENT'])
        ipAddress (nullable: true)
    }

    static mapping = {
        table name: 'GZRPWDRESET', schema: 'GENERAL'
        id column:'gzrpwdreset_id', generator:'sequence', params: [sequence:'GZRPWDRESET_SEQ']
        version false
        ldapUsername column: 'gzrpwdreset_ldap_username', sqlType: 'VARCHAR2', length: 20
        bannerUsername column: 'gzrpwdreset_inb_username', sqlType: 'VARCHAR2', length: 30
        spridenPidm column: 'gzrpwdreset_spriden_pidm', sqlType: 'NUMBER', length: 8
        spridenID column: 'gzrpwdreset_spriden_id', sqlType: 'VARCHAR2', length: 9
        lastName column: 'gzrpwdreset_last_name', sqlType: 'VARCHAR2', length: 60
        firstName column: 'gzrpwdreset_first_name', sqlType: 'VARCHAR2', length: 60
        email column: 'gzrpwdreset_email', sqlType: 'VARCHAR2', length: 128
        action column: 'gzrpwdreset_action', sqlType: 'VARCHAR2', length: 6
        system column: 'gzrpwdreset_system', sqlType: 'VARCHAR2', length: 3
        actionDate column: 'gzrpwdreset_action_date', sqlType: 'DATE'
        ipAddress column: 'gzrpwdreset_ip_address', sqlType: 'VARCHAR2', length: 45
    }
}
