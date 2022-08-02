package com.crio.starter.repositoryservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.crio.starter.App;
import com.crio.starter.data.MemeEntity;
import com.crio.starter.dto.Meme;
import com.crio.starter.repository.MemesRepository;
import com.crio.starter.repositoryservices.MemesRepositoryServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {App.class})
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@AutoConfigureMockMvc
@DirtiesContext
@ActiveProfiles("test")
public class MemesRepositoryServiceTest {

  private static final String TEST_NAME = "Test-name";
  private static final String TEST_URL = "Test-url";
  private static final String TEST_CAPTION = "Test-caption";
  private static final String TEST_ID = "Test-id";
  
  @InjectMocks
  private MemesRepositoryServicesImpl memesRepositoryServicesImpl;

  @Mock
  private MemesRepository memesRepository;

  @Mock
  private MongoTemplate mongoTemplate;

  @Mock
  private ModelMapper modelMapper;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void getMemeByValidIdReturnsMeme() {
    //given
    MemeEntity memeEntity = new MemeEntity(TEST_ID, TEST_NAME, TEST_URL, TEST_CAPTION);
    Meme expectedMeme = new Meme(TEST_ID, TEST_NAME, TEST_URL, TEST_CAPTION);

    when(memesRepository.findMemeById(TEST_ID)).thenReturn(memeEntity);
    when(modelMapper.map(memeEntity, Meme.class)).thenReturn(expectedMeme);

    //when
    Meme meme = memesRepositoryServicesImpl.getMemeById(TEST_ID);
    
    //then
    assertEquals(expectedMeme, meme);
  }
 
  @Test
  public void getMemeByAbsentIdReturnsNull() {
    // given
    when(memesRepository.findMemeById(TEST_ID)).thenReturn(null);

    // when
    Meme meme = memesRepositoryServicesImpl.getMemeById(TEST_ID);

    // then
    assertNull(meme);
  }

  @Test
  public void getMemeByNullIdReturnsNull() {
    // given

    // when
    Meme meme = memesRepositoryServicesImpl.getMemeById(null);

    // then
    assertNull(meme);
    verify(memesRepository, times(0)).findMemeById(null);
  }
}
