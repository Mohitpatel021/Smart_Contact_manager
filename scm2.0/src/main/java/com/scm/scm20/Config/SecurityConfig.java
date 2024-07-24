package com.scm.scm20.Config;

import com.scm.scm20.Services.Impl.SecurityCustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Autowired
  @Lazy
  private SecurityCustomUserDetailsService userDetailsService;

  private OAuthAuthenticationSucessHandler handler;

  @Autowired
  @Lazy
  public void setHandler(OAuthAuthenticationSucessHandler handler) {
    this.handler = handler;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    throws Exception {
    httpSecurity.authorizeHttpRequests(authorize -> {
      authorize.requestMatchers("/user/**").authenticated();
      authorize.anyRequest().permitAll();
    });
    //if we want to change anything related to form Login
    httpSecurity.formLogin(formLogin -> {
      formLogin.loginPage("/login");
      formLogin.loginProcessingUrl("/authenticate");
      formLogin.successForwardUrl("/user/profile");
      formLogin.usernameParameter("email");
      formLogin.passwordParameter("password");
    });
    httpSecurity.csrf(AbstractHttpConfigurer::disable);

    // oauth configuration
    httpSecurity.oauth2Login(oauth -> {
      oauth.loginPage("/login");
      oauth.successHandler(handler);
    });
    httpSecurity.logout(logoutForm -> {
      logoutForm.logoutUrl("/logout");
    });

    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }
}
