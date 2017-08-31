package edu.siena.banner.passwordreset

import grails.transaction.Transactional

import javax.servlet.http.HttpServletRequest

//design inspired from https://stackoverflow.com/questions/2140859/how-do-you-get-client-ip-address-in-a-grails-controller

@Transactional
class IpService {

    static public String getIPAddress(HttpServletRequest request) {
        println "CLIENTIP: " + request.getHeader("Client-IP")
        println "XFORWARDEDFOR: " + request.getHeader("X-Forwarded-For")
        println "REMOTEADDR: " + request.getRemoteAddr()
        def ipAddress = request.getHeader("Client-IP")
        if (ipAddress == null) {
            ipAddress = request.getHeader("X-Forwarded-For")
        }
        if(ipAddress == null) {
            ipAddress = request.getRemoteAddr()
        }
        if (ipAddress != null) {
            def index = ipAddress.indexOf(',')
            if (index != -1){
                ipAddress = ipAddress.take(index)
            }
        }

        return ipAddress
    }
}
