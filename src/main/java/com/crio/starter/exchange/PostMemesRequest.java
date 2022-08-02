package com.crio.starter.exchange;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class PostMemesRequest {

  @NonNull
  private String name;

  @NonNull
  private String url;

  @NonNull
  private String caption;
}
