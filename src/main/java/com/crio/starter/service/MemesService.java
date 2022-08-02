package com.crio.starter.service;

import com.crio.starter.exchange.GetMemesRequest;
import com.crio.starter.exchange.GetMemesResponse;
import com.crio.starter.exchange.PostMemesRequest;
import com.crio.starter.exchange.PostMemesResponse;

public interface MemesService {
  GetMemesResponse getMemeById(GetMemesRequest getMemesRequest);

  GetMemesResponse getMemes();

  PostMemesResponse postMeme(PostMemesRequest postMemesRequest);
}
