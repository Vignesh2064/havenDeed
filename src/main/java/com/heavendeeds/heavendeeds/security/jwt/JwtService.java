package com.heavendeeds.heavendeeds.security.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtService implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    private static String jwtSecret = "svaantech12312378456456454564434234234245654242";
    private static int jwtExpirationMs = 28800000;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String jwtToken = null;

        try {
            logger.info("Generating token for user {}",userPrincipal.getEmail());
            JWSSigner signer = new MACSigner(jwtSecret.getBytes(StandardCharsets.UTF_8));
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .issuer("NEWMEFORUM")
                    .subject(userPrincipal.getEmail())
                    .expirationTime(new Date((new Date()).getTime() + jwtExpirationMs))
                    .claim("email", userPrincipal.getEmail())
                    .jwtID(UUID.randomUUID().toString())
                    .build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            jwtToken = signedJWT.serialize();
            logger.debug("Jwt token : {}", jwtToken);
        }catch (Exception e)
        {
            logger.error("Error generating token {}",e.getMessage());
        }
        return jwtToken;
    }

    public String getUserNameFromJwtToken(String token) {
        try {
            JWTClaimsSet jwtClaimsSet = validateAndGetJWTClaims(token);
            return jwtClaimsSet.getSubject();
        } catch (BadJOSEException | ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateJwtToken(String authToken) {
        try {
            validateAndGetJWTClaims(authToken);
            return true;
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        } catch (BadJOSEException | ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private JWTClaimsSet validateAndGetJWTClaims(String jwtToken) throws BadJOSEException, ParseException, JOSEException {
        ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        JWKSource<SimpleSecurityContext> jweKeySource = new ImmutableSecret<>(jwtSecret.getBytes(StandardCharsets.UTF_8));
        JWSKeySelector<SimpleSecurityContext> jweKeySelector =
                new JWSVerificationKeySelector<>(JWSAlgorithm.HS256, jweKeySource);
        jwtProcessor.setJWSKeySelector(jweKeySelector);
        return jwtProcessor.process(jwtToken, null);
    }
}