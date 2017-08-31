/**
 * Created by mstanco on 6/29/2017.
 */
package edu.siena.banner.passwordreset

import groovy.transform.CompileStatic

import org.springframework.ldap.core.DirContextAdapter
import org.springframework.ldap.core.DirContextOperations
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper

//https://grails-plugins.github.io/grails-spring-security-ldap/v3/index.html#custom-userdetailscontextmapper

@CompileStatic
class SienaUserDetailsContextMapper implements UserDetailsContextMapper {

    @Override
    UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
        String email = ctx.attributes.get('mail')?.get() as String
        String displayName = ctx.attributes.get('displayName')?.get() as String
        String employeeID = ctx.attributes.get('employeeID')?.get() as String

        new SienaUserDetails(username,
                '',
                true,
                true,
                true,
                true,
                authorities,
                email,
                displayName,
                employeeID)
    }

    @Override
    void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
        throw new IllegalStateException("Only retrieving data from AD is currently supported")
    }
}