package com.crio.starter.data;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "memes")
@NoArgsConstructor
@AllArgsConstructor
public class MemeEntity {
  @Id
  private String id;
  
  @NonNull
  private String name;

  @NonNull
  private String url;

  @NonNull
  private String caption;
}
