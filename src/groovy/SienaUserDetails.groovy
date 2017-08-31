/**
 * Created by mstanco on 6/29/2017.
 */
package edu.siena.banner.passwordreset

import groovy.transform.CompileStatic
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

//https://grails-plugins.github.io/grails-spring-security-ldap/v3/index.html#custom-userdetailscontextmapper

@CompileStatic
class SienaUserDetails extends User implements Serializable {

    // extra instance variables
    final String email
    final String displayName
    final String employeeID

    SienaUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
                  boolean credentialsNonExpired, boolean accountNonLocked,
                  Collection<GrantedAuthority> authorities, String email, String displayName, String employeeID) {

        super(username, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities)

        this.email = email
        this.displayName = displayName
        this.employeeID = employeeID
    }
}
