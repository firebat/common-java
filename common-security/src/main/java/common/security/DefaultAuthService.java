package common.security;

import common.security.jwt.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DefaultAuthService implements AuthService {

    private final Logger log = LoggerFactory.getLogger(AuthService.class);

    protected JwtConfig config;

    @Override
    public String generateToken(UserDetails details) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", details.getUsername());
        claims.put("created", new Date());

        String token = config.getTokenHead() + generateToken(claims);

        log.debug("uid={}, token={}", details.getUsername(), token);

        return token;
    }

    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, config.getSignature())
                .compact();
    }

    public Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + config.getExpiration());
    }

    public Claims parseClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(config.getSignature())
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String parseUsername(String token) {
        return parseClaimsFromToken(token).getSubject();
    }

    public Date parseExpirationDateFromToken(String token) {
        return parseClaimsFromToken(token).getExpiration();
    }

    @Override
    public boolean validate(String token) {
        Date expiration = parseExpirationDateFromToken(token);
        return expiration.after(new Date());
    }

    public void setConfig(JwtConfig config) {
        this.config = config;
    }
}
