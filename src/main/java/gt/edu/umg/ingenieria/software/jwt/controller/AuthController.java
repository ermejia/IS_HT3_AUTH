package gt.edu.umg.ingenieria.software.jwt.controller;

import gt.edu.umg.ingenieria.software.jwt.service.AuthService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @RequestMapping("auth")
  public void helloWorld(
    @RequestParam(value = "name", defaultValue = "Friend") String name,
    @RequestParam String password
  ) {
    String token;
    AuthService auth = new AuthService();
    token = auth.createJWT(name, password);
    System.out.println();
    System.out.println(token);
    auth.parseJWT(token);
  }
}
