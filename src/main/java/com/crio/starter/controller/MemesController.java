package com.crio.starter.controller;

import com.crio.starter.dto.Meme;
import com.crio.starter.exchange.GetMemesRequest;
import com.crio.starter.exchange.GetMemesResponse;
import com.crio.starter.exchange.PostMemesRequest;
import com.crio.starter.exchange.PostMemesResponse;
import com.crio.starter.service.MemesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memes")
public class MemesController {
  @Autowired
  MemesService memesService;

  @Autowired
  ObjectMapper objectMapper;

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Meme> getMemeById(GetMemesRequest getMemesRequest) {
    GetMemesResponse getMemesResponse = memesService.getMemeById(getMemesRequest);

    if (getMemesResponse == null || getMemesResponse.getMemes().size() == 0) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    return ResponseEntity.ok().body(getMemesResponse.getMemes().get(0));
  }
  
  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Meme>> getMemes(GetMemesRequest getMemesRequest) {
    GetMemesResponse getMemesResponse = memesService.getMemes();

    if (getMemesResponse == null) {
      return ResponseEntity.badRequest().body(null);
    }
    
    return ResponseEntity.ok().body(getMemesResponse.getMemes());
  }

  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PostMemesResponse> postMeme(
      @RequestBody PostMemesRequest postMemesRequest) {
    
    if (
        postMemesRequest.getName().trim().isEmpty()
        || postMemesRequest.getUrl().trim().isEmpty()
        || postMemesRequest.getCaption().trim().isEmpty()
    ) {
      throw new IllegalArgumentException("Name, Url and Caption cannot be empty");
    }

    PostMemesResponse postMemesResponse = memesService.postMeme(postMemesRequest);

    if (postMemesResponse == null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    return ResponseEntity.ok().body(postMemesResponse);
  }

  @ExceptionHandler(ValueInstantiationException.class)
  private ResponseEntity<Object> mappingException(ValueInstantiationException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body("Name, Url and Caption are required");
  }

  @ExceptionHandler(NullPointerException.class)
  private ResponseEntity<Object> mappingException(NullPointerException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body("Name, Url and Caption are required");
  }

  @ExceptionHandler(IllegalArgumentException.class)
  private ResponseEntity<Object> emptyArguementsException(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }
}
