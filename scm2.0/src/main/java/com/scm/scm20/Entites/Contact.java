package com.scm.scm20.Entites;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contact")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {

  @Id
  private String id;

  private String name;
  private String email;
  private String phoneNumber;
  private String address;
  private String picture;

  @Column(length = 1000)
  private String description;

  @Builder.Default
  private boolean favorite = false;

  private String websiteLink;
  private String linkedInLink;

  @OneToMany(
    mappedBy = "contact",
    cascade = CascadeType.ALL,
    fetch = FetchType.EAGER,
    orphanRemoval = true
  )
  @Builder.Default
  private List<SocialLink> links = new ArrayList<>();

  @ManyToOne
  private User user;
}
