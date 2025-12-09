package com.example.cams.mf.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import javax.naming.AuthenticationException;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

public class GlobalExceptionHandlerTest {

    @Test
    void makeBody_containsExpectedEntries() {
        GlobalExceptionHandler geh = new GlobalExceptionHandler();
        Map<String, Object> body = geh.makeBody(HttpStatus.BAD_REQUEST, "bad", "/x");

        assertEquals("bad", body.get("message"));
        assertEquals("Bad Request", body.get("error"));
        assertEquals(400, body.get("status"));
    }

    @Test
    void handleGeneric_returnsInternalServerErrorAndDetail() {
        GlobalExceptionHandler geh = new GlobalExceptionHandler();
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI("/some");
        Exception ex = new Exception("boom");

        ResponseEntity<Map<String, Object>> resp = geh.handleGeneric(ex, req);

        assertEquals(500, resp.getStatusCodeValue());
        assertEquals("An unexpected error occurred", resp.getBody().get("message"));
        assertEquals("boom", resp.getBody().get("detail"));
    }

    @Test
    void handleAuthenticationException_returnsUnauthorized() throws AuthenticationException {
        GlobalExceptionHandler geh = new GlobalExceptionHandler();
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI("/login");
        AuthenticationException authEx = new AuthenticationException("bad auth");

        ResponseEntity<Map<String, Object>> resp = geh.handleAuthenticationException(authEx, req);

        assertEquals(401, resp.getStatusCodeValue());
        assertEquals("bad auth", resp.getBody().get("message"));
    }

}
