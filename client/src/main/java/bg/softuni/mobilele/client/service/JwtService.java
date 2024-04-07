package bg.softuni.mobilele.client.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final String jwtSecretKey;
  private final long jwtExpirationMs;

  public JwtService(
      @Value("${jwt.secret}") String jwtSecretKey,
      @Value("${jwt.expiration}") long jwtExpirationMs) {
    this.jwtSecretKey = jwtSecretKey;
    this.jwtExpirationMs = jwtExpirationMs;
  }

  public String generateToken(String userName, Map<String, Object> claims) {

    var now = new Date(System.currentTimeMillis());

    return Jwts
        .builder()
        .setClaims(claims)
        .setSubject(userName)
        .setIssuedAt(now)
        .setNotBefore(now)
        .setExpiration(new Date(now.getTime() + jwtExpirationMs))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
  // decode
}
