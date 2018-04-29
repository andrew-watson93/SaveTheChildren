/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Service;

/**
 *
 * @author andre
 */
@Service
public class Crypto {

    //http://www.adeveloperdiary.com/java/how-to-easily-encrypt-and-decrypt-text-in-java/
    private static final String KEY = "DUMMYKEY";

    public String encrypt(String strClearText) throws Exception {
        String strData = "";

        try {
            SecretKeySpec skeyspec = new SecretKeySpec(KEY.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
            byte[] encrypted = cipher.doFinal(strClearText.getBytes());
            strData = new String(encrypted);

        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new Exception(e);
        }
        return strData;
    }

    public String decrypt(String strEncrypted) throws Exception {
        String strData = "";

        try {
            SecretKeySpec skeyspec = new SecretKeySpec(KEY.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, skeyspec);
            byte[] decrypted = cipher.doFinal(strEncrypted.getBytes());
            strData = new String(decrypted);

        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new Exception(e);
        }
        return strData;
    }

}
