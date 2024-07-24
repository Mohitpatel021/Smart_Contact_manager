package com.scm.scm20.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
  public String userProfile(Model model, Authentication authentication) {
    return "user/profile";
  }
}
