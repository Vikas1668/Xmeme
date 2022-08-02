package com.crio.starter.exchange;

import com.crio.starter.dto.Meme;
import java.util.List;
import lombok.Data;
import lombok.NonNull;

@Data
public class GetMemesResponse {
  @NonNull
  private List<Meme> memes;
}

