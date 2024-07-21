package com.scm.scm20.Entites;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "user")
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

  @Id
  private String userId;

  @Column(name = "user_name", nullable = false)
  private String name;

  @Column(unique = true, nullable = false)
  private String email;

  @Getter(AccessLevel.NONE)
  private String password;

  @Column(length = 1000)
  private String about;

  @Column(length = 1000)
  private String profilePic;

  private String phoneNumber;

  @Getter(AccessLevel.NONE)
  @Builder.Default
  private boolean enabled = false;

  @Builder.Default
  private boolean emailVarified = false;

  @Builder.Default
  private boolean phoneVerified = false;

  //SELF, GOOGLE,FACEBOOK,TWITTER,LINKDIN,GITHUB
  @Enumerated(value = EnumType.STRING)
  @Builder.Default
  private Providers provider = Providers.SELF;

  private String providerUserId;

  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    orphanRemoval = true
  )
  @Builder.Default
  private List<Contact> contacts = new ArrayList<>();

  @Builder.Default
  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> roleList = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    //List of Roles{USER,ADMIN,OWNER,MANAGER}
    //Collection of SimpleGrantedAuthority [roles{ADMIN,USER,MANAGER,TEAM LEAD}]
    //A user can have Multiple roles that's why we implement like this
    Collection<SimpleGrantedAuthority> roles = roleList
      .stream()
      .map(role -> new SimpleGrantedAuthority(role))
      .collect(Collectors.toList());
    return roles;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  @Override
  public String getPassword() {
    return password;
  }
}
