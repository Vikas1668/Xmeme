package com.crio.starter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.crio.starter.App;
import com.crio.starter.exchange.PostMemesRequest;
import com.crio.starter.service.MemesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest(classes = {App.class})
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@AutoConfigureMockMvc
@DirtiesContext
@ActiveProfiles("test")
public class MemesControllerTest {
  
  private static final String MEMES_ENDPOINT = "/memes";
  private static final String TEST_NAME = "Test-name";
  private static final String TEST_URL = "Test-url";
  private static final String TEST_CAPTION = "Test-caption";

  private ObjectMapper objectMapper;

  private MockMvc mvc;

  @InjectMocks
  private MemesController memesController;

  @Mock
  private MemesService memesService;


  @BeforeEach
  public void setup() {
    objectMapper = new ObjectMapper();

    MockitoAnnotations.openMocks(this);

    mvc = MockMvcBuilders.standaloneSetup(memesController).build();
  }

  @Test
  public void absentIdInDatabaseResultsInBadHttpRequest() throws Exception {
    MockHttpServletResponse response =
        mvc.perform(get(MEMES_ENDPOINT + "/absentId").accept(APPLICATION_JSON_VALUE)).andReturn()
            .getResponse();

    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
  }
  
  @Test
  public void emptyNameResultsInHttpConflict() throws Exception {
    PostMemesRequest postMemesRequest = new PostMemesRequest("", TEST_URL, TEST_CAPTION);
    String body = objectMapper.writeValueAsString(postMemesRequest);

    MockHttpServletResponse response = mvc.perform(
        post(MEMES_ENDPOINT).contentType(APPLICATION_JSON_VALUE).content(body)
    ).andReturn().getResponse();

    assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
  }

  @Test
  public void emptyUrlResultsInHttpConflict() throws Exception {
    PostMemesRequest postMemesRequest = new PostMemesRequest(TEST_NAME, "", TEST_CAPTION);
    String body = objectMapper.writeValueAsString(postMemesRequest);

    MockHttpServletResponse response =
        mvc.perform(post(MEMES_ENDPOINT).contentType(APPLICATION_JSON_VALUE).content(body))
            .andReturn().getResponse();

    assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
  }

  @Test
  public void emptyCaptionResultsInHttpConflict() throws Exception {
    PostMemesRequest postMemesRequest = new PostMemesRequest(TEST_NAME, TEST_URL, "");
    String body = objectMapper.writeValueAsString(postMemesRequest);

    MockHttpServletResponse response =
        mvc.perform(post(MEMES_ENDPOINT).contentType(APPLICATION_JSON_VALUE).content(body))
            .andReturn().getResponse();

    assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
  }

  @Test
  public void nullNameResultsInHttpConflict() throws Exception {

    String body = "{\"url\": \"" + TEST_URL + "\", \"caption\": \"" + TEST_CAPTION + "\"}";

    MockHttpServletResponse response =
        mvc.perform(post(MEMES_ENDPOINT).contentType(APPLICATION_JSON_VALUE).content(body))
            .andReturn().getResponse();

    assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
  }
  
  @Test
  public void nullUrlResultsInHttpConflict() throws Exception {

    String body = "{\"name\": \"" + TEST_NAME + "\", \"caption\": \"" + TEST_CAPTION + "\"}";

    MockHttpServletResponse response =
        mvc.perform(post(MEMES_ENDPOINT).contentType(APPLICATION_JSON_VALUE).content(body))
            .andReturn().getResponse();

    assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
  }

  @Test
  public void nullCaptionResultsInHttpConflict() throws Exception {

    String body = "{\"name\": \"" + TEST_NAME + "\", \"url\": \"" + TEST_URL + "\"}";

    MockHttpServletResponse response =
        mvc.perform(post(MEMES_ENDPOINT).contentType(APPLICATION_JSON_VALUE).content(body))
            .andReturn().getResponse();

    assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
  }

  @Test
  public void incorrectlySpelledNameResultsInHttpConflict() throws Exception {
    String body = "{\"Nane\": \"" + TEST_NAME + "\", \"url\": \"" + TEST_URL + "\", \"caption\": \""
        + TEST_CAPTION + "\"}";

    MockHttpServletResponse response =
        mvc.perform(post(MEMES_ENDPOINT).contentType(APPLICATION_JSON_VALUE).content(body))
            .andReturn().getResponse();

    assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
  }
  
  @Test
  public void incorrectlySpelledUrlResultsInHttpConflict() throws Exception {
    String body = "{\"Nane\": \"" + TEST_NAME + "\", \"ural\": \""
        + TEST_URL + "\", \"caption\": \"" + TEST_CAPTION + "\"}";

    MockHttpServletResponse response =
        mvc.perform(post(MEMES_ENDPOINT).contentType(APPLICATION_JSON_VALUE).content(body))
            .andReturn().getResponse();

    assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
  }

  @Test
  public void incorrectlySpelledCaptionResultsInHttpConflict() throws Exception {
    String body = "{\"Nane\": \"" + TEST_NAME + "\", \"url\": \"" + TEST_URL + "\", \"captain\": \""
        + TEST_CAPTION + "\"}";

    MockHttpServletResponse response =
        mvc.perform(post(MEMES_ENDPOINT).contentType(APPLICATION_JSON_VALUE).content(body))
            .andReturn().getResponse();

    assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
  }

}
