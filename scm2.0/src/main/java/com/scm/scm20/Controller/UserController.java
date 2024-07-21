package com.scm.scm20.Controller;

import com.scm.scm20.Helper.Helper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

  // private static Logger logger = LoggerFactory.getLogger(UserController.class);

  @RequestMapping(value = "/dashboard")
  public String userDashboard() {
    System.out.println("User dashboard");
    return "user/dashboard";
  }

  @RequestMapping(value = "/profile")
  public String userProfile(Authentication authentication) {
    String username = Helper.getEmailOfLoggedInUser(authentication);
    System.out.println(username);
    return "user/profile";
  }
}
