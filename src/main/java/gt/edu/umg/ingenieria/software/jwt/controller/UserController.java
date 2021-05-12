package gt.edu.umg.ingenieria.software.jwt.controller;

import gt.edu.umg.ingenieria.software.jwt.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class UserController {

  @PostMapping("user")
  public UserEntity login(
    @RequestParam("user") String username,
    @RequestParam("password") String pwd
  ) {
    String token = getJWTToken(username);
    UserEntity user = new UserEntity();
    user.setUser(username);
    user.setToken(token);
    return user;
  }

  private String getJWTToken(String username) {
    String secretKey = "mySecretKey";
    List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
      "ROLE_USER"
    );

    String token = Jwts
      .builder()
      .setId("softtekJWT")
      .setSubject(username)
      .claim(
        "authorities",
        grantedAuthorities
          .stream()
          .map(GrantedAuthority::getAuthority)
          .collect(Collectors.toList())
      )
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + 600000))
      .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
      .compact();

    return "Bearer " + token;
  }
}
