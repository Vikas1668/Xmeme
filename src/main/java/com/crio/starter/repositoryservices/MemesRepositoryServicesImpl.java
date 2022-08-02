package com.crio.starter.repositoryservices;

import com.crio.starter.data.MemeEntity;
import com.crio.starter.dto.Meme;
import com.crio.starter.repository.MemesRepository;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class MemesRepositoryServicesImpl implements MemesRepositoryService {

  @Autowired
  MemesRepository memesRepository;

  @Autowired
  MongoTemplate mongoTemplate;

  @Autowired
  ModelMapper modelMapper;
  
  public Meme getMemeById(String memeId) {
    if (memeId == null) {
      return null;
    }
    MemeEntity memeEntity = memesRepository.findMemeById(memeId);

    if (memeEntity == null) {
      return null;
    }

    Meme meme = modelMapper.map(memeEntity, Meme.class);
    return meme;
  }
  
  public List<Meme> getMemes(Integer top) {
    Query top100Memes = queryForTopXMemes(100);
    List<MemeEntity> memeEntityList = mongoTemplate.find(top100Memes, MemeEntity.class);
    
    if (memeEntityList == null) {
      return null;
    }

    List<Meme> memes = new ArrayList<Meme>();
    for (MemeEntity memeEntity : memeEntityList) {
      Meme meme = modelMapper.map(memeEntity, Meme.class);
      memes.add(meme);
    }

    return memes;
  }
  
  public String postMeme(Meme memeToPost) {
    // check is post is already present
    Query checkForDuplicates = queryForDuplicate(memeToPost);
    if (mongoTemplate.exists(checkForDuplicates, MemeEntity.class)) {
      return null;
    }

    MemeEntity memeEntity = modelMapper.map(memeToPost, MemeEntity.class);
    memeEntity = memesRepository.save(memeEntity);
    return memeEntity.getId();
  }
  
  private Query queryForTopXMemes(Integer top) {
    Query topXMemes = new Query();
    topXMemes.limit(top);
    topXMemes.with(Sort.by(Direction.DESC, "$natural"));
    return topXMemes;
  }

  private Query queryForDuplicate(Meme meme) {
    Query checkForDuplicates = new Query();    
    checkForDuplicates.addCriteria(Criteria.where("name").is(meme.getName()));
    checkForDuplicates.addCriteria(Criteria.where("url").is(meme.getUrl()));
    checkForDuplicates.addCriteria(Criteria.where("caption").is(meme.getCaption()));
    return checkForDuplicates;
  }
}
