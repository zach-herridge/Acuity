package com.acuitybotting.common.utils;

import com.rockaport.alice.Alice;
import com.rockaport.alice.AliceContext;
import com.rockaport.alice.AliceContextBuilder;
import org.mindrot.jbcrypt.BCrypt;

import java.security.GeneralSecurityException;
import java.util.Base64;

public class EncryptionUtil {

    private static Alice getAlice() {
        return new Alice(new AliceContextBuilder().setKeyLength(AliceContext.KeyLength.BITS_128).build());
    }

    public static String encrypt(String key, String value) throws GeneralSecurityException {
        return Base64.getEncoder().encodeToString(getAlice().encrypt(value.getBytes(), key.toCharArray()));
    }

    public static String decrypt(String key, String value) throws GeneralSecurityException {
        return new String(getAlice().decrypt(Base64.getDecoder().decode(value), key.toCharArray()));
    }

    public static String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean comparePassword(String hash, String password) {
        return BCrypt.checkpw(password, hash);
    }
}
