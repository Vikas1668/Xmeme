package com.crio.starter.service;

import com.crio.starter.dto.Meme;
import com.crio.starter.exchange.GetMemesRequest;
import com.crio.starter.exchange.GetMemesResponse;
import com.crio.starter.exchange.PostMemesRequest;
import com.crio.starter.exchange.PostMemesResponse;
import com.crio.starter.repositoryservices.MemesRepositoryService;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemesServiceImpl implements MemesService {
  private static Integer DEFAULT_MEME_BATCH_CNT = 100;

  @Autowired
  MemesRepositoryService memesRepositoryService;

  @Autowired
  ModelMapper modelMapper;

  public GetMemesResponse getMemeById(GetMemesRequest getMemesRequest) {
    String memeId = getMemesRequest.getId();

    if (memeId == null) {
      return new GetMemesResponse(new ArrayList<Meme>());
    }

    Meme meme = memesRepositoryService.getMemeById(memeId);
    List<Meme> memeList = new ArrayList<Meme>();
    if (meme != null) {
      memeList.add(meme);
    }
    
    return new GetMemesResponse(memeList);
  }
  
  public GetMemesResponse getMemes() {
    return getMemes(MemesServiceImpl.DEFAULT_MEME_BATCH_CNT);
  }

  public GetMemesResponse getMemes(Integer top) {
    List<Meme> memeList = memesRepositoryService.getMemes(top);
    if (memeList == null) {
      memeList = new ArrayList<Meme>();
    }

    return new GetMemesResponse(memeList);
  }

  public PostMemesResponse postMeme(PostMemesRequest postMemesRequest) {

    Meme memeToPost = modelMapper.map(postMemesRequest, Meme.class);
    String postId = memesRepositoryService.postMeme(memeToPost);

    if (postId == null) {
      return null;
    }

    return new PostMemesResponse(postId);
  }
}
