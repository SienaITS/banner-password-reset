/**
 * Created by mstanco on 9/7/2017.
 */
package edu.siena.banner.passwordreset

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

class DESCodec {
    def static encode = { String target ->
        def cipher = getCipher(Cipher.ENCRYPT_MODE)
        return cipher.doFinal(target.bytes).encodeBase64() as String
    }

    def static decode = { String target ->
        def cipher = getCipher(Cipher.DECRYPT_MODE)
        return new String(cipher.doFinal(target.decodeBase64())) as String
    }

    private static getCipher(mode) {
        def keySpec = new DESKeySpec(getPassword())
        def cipher = Cipher.getInstance("DES")
        def keyFactory = SecretKeyFactory.getInstance("DES")
        cipher.init(mode, keyFactory.generateSecret(keySpec))
        return cipher
    }

    private static getPassword() {
        "testsaltString".getBytes("UTF-8")
    }


    static void main(args) {

        println args
        if(args.length == 1) {
            println encode(args[0])
        } else {

            println decode(args[1])
        }
    }
}
/*class DESCodec {
    static encode(target) {
        def cipher = getCipher(Cipher.ENCRYPT_MODE)
        return cipher.doFinal(target.bytes).encodeBase64()
    }

    static decode(target) {
        def cipher = getCipher(Cipher.DECRYPT_MODE)
        return new String(cipher.doFinal(target.decodeBase64()))
    }

    private static getCipher(mode) {
        def keySpec = new PBEKeySpec(getPassword())
        def cipher = Cipher.getInstance("DES")
        def keyFactory = SecretKeyFactory.getInstance("DES")
        cipher.init(mode, keyFactory.generateSecret(keySpec))
    }

    private static getPassword() { "secret".toCharArray() }

    static void main(args) {
        if(args) {
            println encode(args[0])
        }
    }
}*/