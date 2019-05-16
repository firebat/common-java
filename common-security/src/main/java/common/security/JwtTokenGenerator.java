package common.security;

import common.security.config.AuthorizationConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenGenerator {

    private AuthorizationConfig config;

    public JwtTokenGenerator(AuthorizationConfig config) {
        this.config = config;
    }

    public String generateToken(Claims claims) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + config.getExpiration());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .setIssuedAt(issuedAt)
                .signWith(SignatureAlgorithm.HS512, config.getSignature())
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(config.getSignature())
                .parseClaimsJws(token)
                .getBody();
    }
}
