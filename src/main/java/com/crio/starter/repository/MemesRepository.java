package com.crio.starter.repository;

import com.crio.starter.data.MemeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MemesRepository extends MongoRepository<MemeEntity, String> {
  @Query("{ id : ?0 }")
  MemeEntity findMemeById(String memeId);
}
