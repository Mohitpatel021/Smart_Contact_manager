package com.scm.scm20.Services.Impl;

import com.scm.scm20.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityCustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepo userRepo;

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    //THis method will load the user from the database
    return userRepo
      .findByEmail(username)
      .orElseThrow(() ->
        new UsernameNotFoundException(
          "User Not found with the provided username " + username
        )
      );
  }
}
