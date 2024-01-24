//package com.web.ecomm.app.security;
//
//import com.web.ecomm.app.pojo.User;
//import com.web.ecomm.app.utils.Constants;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.JwtParser;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class JwtUtil {
//
//
//    private final String secret_key = "mysecretkey";
//    private long accessTokenValidity = 60*60*1000;
//
//    private final JwtParser jwtParser;
//
//    public JwtUtil(){
//        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
//    }
//
//    public String createToken(User user) {
//        Claims claims = Jwts.claims().setSubject(user.getUserEmail());
//        claims.put("username",user.getUserName());
////        claims.put("lastName",user.getLastName());
//        Date tokenCreateTime = new Date();
//        Date tokenValidity = new Date(tokenCreateTime.getTime() +
//                TimeUnit.MINUTES.toMillis(Constants.ACCESS_TOKEN_VALIDITY));
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setExpiration(tokenValidity)
//                .signWith(SignatureAlgorithm.HS256, secret_key)
//                .compact();
//    }
//
//    private Claims parseJwtClaims(String token) {
//        return jwtParser.parseClaimsJws(token).getBody();
//    }
//
//    public Claims resolveClaims(HttpServletRequest req) {
//        try {
//            String token = resolveToken(req);
//            if (token != null) {
//                return parseJwtClaims(token);
//            }
//            return null;
//        } catch (ExpiredJwtException ex) {
//            req.setAttribute("expired", ex.getMessage());
//            throw ex;
//        } catch (Exception ex) {
//            req.setAttribute("invalid", ex.getMessage());
//            throw ex;
//        }
//    }
//
//    public String resolveToken(HttpServletRequest request) {
//
//        String bearerToken = request.getHeader(Constants.TOKEN_HEADER);
//        if (bearerToken != null && bearerToken.startsWith(Constants.TOKEN_PREFIX)) {
//            return bearerToken.substring(Constants.TOKEN_PREFIX.length());
//        }
//        return null;
//    }
//
//    public boolean validateClaims(Claims claims) throws AuthenticationException {
//        try {
//            return claims.getExpiration().after(new Date());
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//
//    public String getEmail(Claims claims) {
//        return claims.getSubject();
//    }
//
//    private List<String> getRoles(Claims claims) {
//        return (List<String>) claims.get("roles");
//    }
//
//
//}