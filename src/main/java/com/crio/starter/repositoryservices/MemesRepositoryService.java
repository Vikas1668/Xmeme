package com.crio.starter.repositoryservices;

import com.crio.starter.dto.Meme;
import java.util.List;

public interface MemesRepositoryService {
  Meme getMemeById(String memeId);

  List<Meme> getMemes(Integer top);

  String postMeme(Meme meme);
}
