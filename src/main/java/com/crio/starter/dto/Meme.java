package com.crio.starter.dto;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meme {
  private String id;

  @NonNull
  private String name;

  @NonNull
  private String url;

  @NonNull
  private String caption;
}
