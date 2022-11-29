package org.cloud.blog.common.utils;

import lombok.extern.slf4j.Slf4j;
import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaUtil {
    private static final String ALGORITHM = "RSA";

    private static final String CHAR_SET = "utf-8";

    private RsaUtil(){}

    public static String rsaEncode(String text) {
        try {
            byte[] publicBytes = baseStrToByte("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQClwwxhJKwStDnu7c0yCRkwTW2VKuLWwyVtFC6Zx9bYdF1qwqSP26CkDwaF6GHayIvv9b8BHlAaQH4SLIPzir062yzNukqspmthUw4gPJhbx1AQrWRoQJSv3/1Sk+tWyJRHXSiCZJZ3216LDhtx42LQ0HItDP8U9BLtsxA+5LEZzQIDAQAB");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] cipherBytes = cipher.doFinal(text.getBytes(CHAR_SET));
            return baseByteToStr(cipherBytes);
        } catch (Exception e) {

        }
        return null;
    }


    public static String rsaDecode(String text) {
        try {
            byte[] privateBytes = baseStrToByte("MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKXDDGEkrBK0Oe7tzTIJGTBNbZUq4tbDJW0ULpnH1th0XWrCpI/boKQPBoXoYdrIi+/1vwEeUBpAfhIsg/OKvTrbLM26Sqyma2FTDiA8mFvHUBCtZGhAlK/f/VKT61bIlEddKIJklnfbXosOG3HjYtDQci0M/xT0Eu2zED7ksRnNAgMBAAECgYEAlCuz5yn2volnt9HNuEo1v92WdN5vAnZSAB0oQsJFpBrwXjw7CXTTNZNQy2YcAot9uzO6Vu+Xvr+jce9ky9BasM7ehz0gnwJWAO79IqUnmu3RRq7HllDwp72qysXIypJZCF4HX5jAzUGlNzlTSUb1H4LtavKc6a//YqPfQ0jTLsECQQDZuGKGAYq6rBCX0+T8qlQpCPc41wsl4Gi9lLD21ks9PMx44JdhsUrqLWItZiGynDzq1LJ3M1hr3gbSsPQcI9HJAkEAwugDFCiRLOqOBRRGlYbzgGdmXbR4SrMNIpcFTFhU+MsEqaMueVPiNtRSIK6W8pS28ZN0aiZDTBAT84fOIENp5QJBAJaVgQ9OYbVa7N8WH3riE/ONz+/wTDWWUNtOzFbtQHzKYGH6dLmM9lOhsBXWXdg7V6bUFdt8F9wDZJS07yHHZIECQG4rHrJiS80Lt8L/NvaGFVVbHO2SePwgQShwHLqOo1kNyFDqv/YsiA1d7h4zEXeEv/PE2WS2xAtWezCIbualtFECQQDPUkYhs3vZoZgsltdeFnv/WoXaXNRIzunMTmksIlh8JP7C1xQHrwdCpUkffgSVphxGJGHkxooMpki7oTC1Mdjx");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyFactory.generatePrivate(keySpec);
            byte[] cipherText;
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // encrypt the plain text using the public key
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] textbyte = baseStrToByte(text);
            cipherText = cipher.doFinal(textbyte);
            return new String(cipherText, CHAR_SET);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     *   pwdKey:
     *     publicKey: MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQClwwxhJKwStDnu7c0yCRkwTW2VKuLWwyVtFC6Zx9bYdF1qwqSP26CkDwaF6GHayIvv9b8BHlAaQH4SLIPzir062yzNukqspmthUw4gPJhbx1AQrWRoQJSv3/1Sk+tWyJRHXSiCZJZ3216LDhtx42LQ0HItDP8U9BLtsxA+5LEZzQIDAQAB
     *     privateKey: MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKXDDGEkrBK0Oe7tzTIJGTBNbZUq4tbDJW0ULpnH1th0XWrCpI/boKQPBoXoYdrIi+/1vwEeUBpAfhIsg/OKvTrbLM26Sqyma2FTDiA8mFvHUBCtZGhAlK/f/VKT61bIlEddKIJklnfbXosOG3HjYtDQci0M/xT0Eu2zED7ksRnNAgMBAAECgYEAlCuz5yn2volnt9HNuEo1v92WdN5vAnZSAB0oQsJFpBrwXjw7CXTTNZNQy2YcAot9uzO6Vu+Xvr+jce9ky9BasM7ehz0gnwJWAO79IqUnmu3RRq7HllDwp72qysXIypJZCF4HX5jAzUGlNzlTSUb1H4LtavKc6a//YqPfQ0jTLsECQQDZuGKGAYq6rBCX0+T8qlQpCPc41wsl4Gi9lLD21ks9PMx44JdhsUrqLWItZiGynDzq1LJ3M1hr3gbSsPQcI9HJAkEAwugDFCiRLOqOBRRGlYbzgGdmXbR4SrMNIpcFTFhU+MsEqaMueVPiNtRSIK6W8pS28ZN0aiZDTBAT84fOIENp5QJBAJaVgQ9OYbVa7N8WH3riE/ONz+/wTDWWUNtOzFbtQHzKYGH6dLmM9lOhsBXWXdg7V6bUFdt8F9wDZJS07yHHZIECQG4rHrJiS80Lt8L/NvaGFVVbHO2SePwgQShwHLqOo1kNyFDqv/YsiA1d7h4zEXeEv/PE2WS2xAtWezCIbualtFECQQDPUkYhs3vZoZgsltdeFnv/WoXaXNRIzunMTmksIlh8JP7C1xQHrwdCpUkffgSVphxGJGHkxooMpki7oTC1Mdjx
     */
    private static byte[] baseStrToByte(String str) {
        return Base64.getDecoder().decode(str);
    }

    private static String baseByteToStr(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
