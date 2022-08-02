package com.crio.starter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.crio.starter.App;
import com.crio.starter.dto.Meme;
import com.crio.starter.exchange.GetMemesRequest;
import com.crio.starter.exchange.GetMemesResponse;
import com.crio.starter.exchange.PostMemesRequest;
import com.crio.starter.exchange.PostMemesResponse;
import com.crio.starter.repositoryservices.MemesRepositoryService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {App.class})
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@DirtiesContext
@ActiveProfiles("test")
public class MemesServiceTest {

  private static final String TEST_NAME = "Test-name";
  private static final String TEST_URL = "Test-url";
  private static final String TEST_CAPTION = "Test-caption";
  private static final String TEST_ID = "Test-id";
  private static final Integer BULK_SIZE = 100;

  @InjectMocks
  private MemesServiceImpl memesServiceImpl;

  @Mock
  private MemesRepositoryService memesRepositoryService;

  @Mock
  private ModelMapper modelMapper;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  // test post meme 
  @Test
  public void postMeme() {
    // given
    Meme meme = new Meme(null, TEST_NAME, TEST_URL, TEST_CAPTION);
    PostMemesRequest postMemesRequest = new PostMemesRequest(TEST_NAME, TEST_URL, TEST_CAPTION);
    when(modelMapper.map(postMemesRequest, Meme.class)).thenReturn(meme);
    when(memesRepositoryService.postMeme(meme)).thenReturn(TEST_ID);

    // when
    PostMemesResponse postMemesResponse = memesServiceImpl.postMeme(postMemesRequest);

    // then
    assertEquals(TEST_ID, postMemesResponse.getId());
    verify(memesRepositoryService, times(1)).postMeme(meme);
  }

  @Test
  public void getMemeByNonNullId() {
    //given
    GetMemesRequest getMemesRequest = new GetMemesRequest(TEST_ID);
    Meme meme = new Meme(TEST_ID, TEST_NAME, TEST_URL, TEST_CAPTION);
    when(memesRepositoryService.getMemeById(TEST_ID)).thenReturn(meme);

    //when
    GetMemesResponse getMemesResponse = memesServiceImpl.getMemeById(getMemesRequest);

    // then
    assertEquals(1, getMemesResponse.getMemes().size());
    assertEquals(meme, getMemesResponse.getMemes().get(0));
  }

  @Test
  public void getMemeByNullIdReturnsEmptyList() {
    // given
    GetMemesRequest getMemesRequest = new GetMemesRequest(null);

    // when
    GetMemesResponse getMemesResponse = memesServiceImpl.getMemeById(getMemesRequest);

    // then
    assertEquals(0, getMemesResponse.getMemes().size());
  }

  @Test
  public void getMemesReturnsList() {
    //given
    List<Meme> memes = new ArrayList<Meme>();
    for (int cnt = 1; cnt <= 5; cnt++) {
      String cntStr = Integer.toString(cnt);
      String id = TEST_ID + "-" + cntStr;
      String name = TEST_NAME + "-" + cntStr;
      String url = TEST_URL + "-" + cntStr;
      String caption = TEST_CAPTION + '-' + cntStr;
      memes.add(new Meme(id, name, url, caption));
    }
    when(memesRepositoryService.getMemes(BULK_SIZE)).thenReturn(memes);

    //when
    GetMemesResponse getMemesResponse = memesServiceImpl.getMemes();

    //then
    assertEquals(5, getMemesResponse.getMemes().size());
    assertEquals(memes, getMemesResponse.getMemes());
  }

  @Test
  public void getMemesReturnsEmptyList() {
    // given
    List<Meme> memes = new ArrayList<Meme>();
    when(memesRepositoryService.getMemes(BULK_SIZE)).thenReturn(memes);

    // when
    GetMemesResponse getMemesResponse = memesServiceImpl.getMemes();

    // then
    assertEquals(0, getMemesResponse.getMemes().size());
    assertEquals(memes, getMemesResponse.getMemes());
  }
}
