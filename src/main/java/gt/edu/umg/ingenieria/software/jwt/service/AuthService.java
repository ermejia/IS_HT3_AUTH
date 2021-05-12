package gt.edu.umg.ingenieria.software.jwt.service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import io.jsonwebtoken.*;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

public class AuthService {
    private String secretKey="qwertyuiopasdfghjklzxcvbnm123456";
    long ttlMillis=30000;

    String issuer="Software Engineer";
    String id="Software Engineer H3";

    /**
     * Sample method to construct a JWT
     * @param subject
     * @param passw
     * @return
     */
    public String createJWT( String subject,String passw) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .claim("pswr", passw)
                .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        /**
         * Builds the JWT and serializes it to a compact, URL-safe string
         */
        return builder.compact();
    }


    /**
     * Sample method to validate and read the JWT
     * @param jwt
     */
    public void parseJWT(String jwt) {

        /**
         * This line will throw an exception if it is not a signed JWS (as expected)
         */
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(jwt).getBody();
        System.out.println("Welcome "+claims.getSubject());

        System.out.println("ID: " + claims.getId());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());

    }
}
