package com.scm.scm20.Controller;

import com.scm.scm20.Entites.User;
import com.scm.scm20.Helper.Helper;
import com.scm.scm20.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

  @Autowired
  private UserService userService;

  @ModelAttribute
  public void addLoggedInUserInformation(
    Model model,
    Authentication authentication
  ) {
    if (authentication == null) {
      return;
    }
    String username = Helper.getEmailOfLoggedInUser(authentication);
    System.out.println(username);
    User user = userService.getUserByEmail(username);
    model.addAttribute("loggedInUser", user);
  }
}
