package com.scm.scm20.Config;

import com.scm.scm20.Entites.Providers;
import com.scm.scm20.Entites.User;
import com.scm.scm20.Helper.AppConstant;
import com.scm.scm20.Repositories.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuthAuthenticationSucessHandler
  implements AuthenticationSuccessHandler {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  Logger logger = LoggerFactory.getLogger(
    OAuthAuthenticationSucessHandler.class
  );

  @Override
  public void onAuthenticationSuccess(
    HttpServletRequest request,
    HttpServletResponse response,
    Authentication authentication
  ) throws IOException, ServletException {
    logger.info("OAuth Authentication Success Handler");

    //Identify the provider first

    var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
    String authorisedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
    logger.info("Client ID : " + authorisedClientRegistrationId);

    var oAuthUser = (DefaultOAuth2User) authentication.getPrincipal();
    oAuthUser
      .getAttributes()
      .forEach((key, value) -> {
        logger.info(
          " User Object COnsisiting theese things {} ==> {}",
          key,
          value
        );
      });
    User user = new User();
    user.setUserId(UUID.randomUUID().toString());
    user.setRoleList(List.of(AppConstant.ROLE_USER));
    user.setEmailVarified(true);
    user.setEnabled(true);

    if (authorisedClientRegistrationId.equalsIgnoreCase("google")) {
      //for google login
      user.setEmail(oAuthUser.getAttribute("email").toString());
      user.setProfilePic(oAuthUser.getAttribute("picture").toString());
      user.setName(oAuthUser.getAttribute("name").toString());
      user.setProviderUserId(oAuthUser.getName());
      user.setProvider(Providers.GOOGLE);
      user.setPassword(passwordEncoder.encode("User@123"));
      user.setAbout("This account is created by Google");
    } else if (authorisedClientRegistrationId.equalsIgnoreCase("github")) {
      //logic for github Login
      String email = oAuthUser.getAttribute("email") != null
        ? oAuthUser.getAttribute("email")
        : oAuthUser.getAttribute("login").toString() + "@github.com";

      String picture = oAuthUser.getAttribute("avatar_url").toString();
      String name = oAuthUser.getAttribute("login").toString();
      user.setProviderUserId(oAuthUser.getName());
      user.setEmail(email);
      user.setProfilePic(picture);
      user.setName(name);
      user.setPassword(passwordEncoder.encode("User@123"));
      user.setProvider(Providers.GITHUB);
      user.setAbout("This account is created by github");
    } else if (authorisedClientRegistrationId.equalsIgnoreCase("linkdin")) {
      //Login for Linkedin Login
    } else {
      logger.info("OAuthAuthenticationSuccesHandler : Unknown Provider ");
    }

    /* 
    DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
    user
      .getAttributes()
      .forEach((key, value) -> {
        logger.info(
          " User Object COnsisiting theese things {} ==> {}",
          key,
          value
        );
      });
    logger.info("Printing all the user Authorities ", user.getAuthorities());
    //Save data to database

    String email = user.getAttribute("email").toString();
    String name = user.getAttribute("name").toString();
    String picture = user.getAttribute("picture").toString();

    User user1 = new User();
    user1.setEmail(email);
    user1.setName(name);
    user1.setProfilePic(picture);
    user1.setPassword("password");
    user1.setUserId(UUID.randomUUID().toString());
    user1.setProvider(Providers.GOOGLE);
    user1.setEnabled(true);
    user1.setEmailVarified(true);
    user1.setProviderUserId(user.getName());
    user1.setRoleList(List.of(AppConstant.ROLE_USER));
    user1.setAbout("The account is created using google..");
    User user2 = userRepo.findByEmail(email).orElse(null);
    if (user2 == null) {
      userRepo.save(user1);
      logger.info("user Saved Successfully : " + email);
    }
      */
    User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
    if (user2 == null) {
      userRepo.save(user);
      logger.info("user Saved Successfully : " + user.getEmail());
    }

    new DefaultRedirectStrategy()
      .sendRedirect(request, response, "/user/profile");
  }
}
