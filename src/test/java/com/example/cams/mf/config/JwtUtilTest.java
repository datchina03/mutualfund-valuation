package com.example.cams.mf.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

public class JwtUtilTest {

    private static void setField(Object target, String name, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        f.set(target, value);
    }

    @Test
    void generateValidateAndExtract() throws Exception {
        JwtUtil util = new JwtUtil();
        // 32 char secret (256 bits) for HMAC-SHA
        setField(util, "jwtSecret", "01234567890123456789012345678901");
        setField(util, "jwtExpirationMs", 60_000L);

        String token = util.generateToken("alice", "ROLE_USER");

        assertTrue(util.validateToken(token), "generated token should validate");
        assertEquals("alice", util.extractuserName(token));
    }

    @Test
    void tamperedTokenIsInvalid() throws Exception {
        JwtUtil util = new JwtUtil();
        setField(util, "jwtSecret", "01234567890123456789012345678901");
        setField(util, "jwtExpirationMs", 60_000L);

        String token = util.generateToken("bob", "ROLE_USER");
        String tampered = token + "x";

        assertFalse(util.validateToken(tampered), "tampered token should be invalid");
    }

    @Test
    void expiredTokenIsInvalid() throws Exception {
        JwtUtil util = new JwtUtil();
        setField(util, "jwtSecret", "01234567890123456789012345678901");
        // negative expiration -> token already expired
        setField(util, "jwtExpirationMs", -1000L);

        String token = util.generateToken("carol", "ROLE_USER");

        assertFalse(util.validateToken(token), "expired token should be invalid");
    }

}
