package com.scm.scm20.Helper;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

  private String content;

  @Builder.Default
  private MessageType type = MessageType.blue;
}
