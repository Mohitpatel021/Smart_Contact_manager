package com.scm.scm20.Controller;

import com.scm.scm20.Entites.User;
import com.scm.scm20.Helper.Message;
import com.scm.scm20.Helper.MessageType;
import com.scm.scm20.Services.UserService;
import com.scm.scm20.forms.UserForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

  @Autowired
  private UserService userService;

  @GetMapping("/")
  public String index() {
    return "redirect:/home";
  }

  @RequestMapping("/home")
  public String home(Model model) {
    System.out.println("Home Page Handler");
    return "home";
  }

  @RequestMapping("/about")
  public String aboutPage() {
    System.out.println("About page Loading");
    return "about";
  }

  @RequestMapping("/services")
  public String servicePage() {
    return "service";
  }

  @GetMapping("/contact")
  public String contactPage() {
    return new String("contact");
  }

  @GetMapping("/login")
  public String loginPage() {
    return new String("login");
  }

  @GetMapping("/register")
  public String registerPage(Model model) {
    UserForm userForm = new UserForm();
    model.addAttribute("userForm", userForm);
    return new String("register");
  }

  @RequestMapping(value = "/do-register", method = RequestMethod.POST)
  public String processRegister(
    @Valid @ModelAttribute UserForm userForm,
    BindingResult rBindingResult,
    HttpSession session
  ) {
    if (rBindingResult.hasErrors()) {
      return "register";
    }
    User user = new User();
    user.setName(userForm.getName());
    user.setEmail(userForm.getEmail());
    user.setAbout(userForm.getAbout());
    user.setPassword(userForm.getPassword());
    user.setPhoneNumber(userForm.getPhoneNumber());
    user.setProfilePic(
      "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pngitem.com%2Fmiddle%2Fmmhwxw_transparent-user-png-default-user-image-png-png%2F&psig=AOvVaw2YnzZmMiXifJhV7FfZ1Us2&ust=1720461447339000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCODBp8LAlYcDFQAAAAAdAAAAABAE"
    );
    User savedUser = userService.saveUser(user);
    System.out.println(savedUser);
    Message message = Message
      .builder()
      .content("Registration Successfull")
      .type(MessageType.green)
      .build();
    session.setAttribute("message", message);
    return "redirect:/register";
  }
}
