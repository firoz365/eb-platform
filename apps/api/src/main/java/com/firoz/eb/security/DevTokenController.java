package com.firoz.eb.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/dev")
public class DevTokenController {

    private final String secret;
    private final String issuer;
    private final String audience;

    public DevTokenController(
            @Value("${app.security.jwt.hmac-secret}") String secret,
            @Value("${app.security.jwt.issuer}") String issuer,
            @Value("${app.security.jwt.audience}") String audience
    ) {
        this.secret = secret;
        this.issuer = issuer;
        this.audience = audience;
    }

    @PostMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> token(@RequestBody(required = false) TokenRequest req) throws Exception {
        String subject = (req != null && req.subject != null && !req.subject.isBlank()) ? req.subject : "firoz";
        List<String> scopes = (req != null && req.scopes != null && !req.scopes.isEmpty())
                ? req.scopes
                : List.of("services:read");

        int expiresInSeconds = (req != null && req.expiresInSeconds != null) ? req.expiresInSeconds : 3600;

        Instant now = Instant.now();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .audience(audience)
                .subject(subject)
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plusSeconds(expiresInSeconds)))
                .claim("scope", String.join(" ", scopes)) // Spring-style: space-separated
                .build();

        SignedJWT jwt = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256),
                claims
        );

        byte[] key = secret.getBytes(StandardCharsets.UTF_8);
        if (key.length < 32) {
            throw new IllegalStateException("JWT_HMAC_SECRET must be at least 32 characters");
        }

        jwt.sign(new MACSigner(key));

        String token = jwt.serialize();

        return new LinkedHashMap<>() {{
            put("access_token", token);
            put("token_type", "Bearer");
            put("expires_in", expiresInSeconds);
            put("scope", String.join(" ", scopes));
        }};
    }

    public static class TokenRequest {
        public String subject;
        public List<String> scopes;
        public Integer expiresInSeconds;
    }
}