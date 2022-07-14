package com.mykhailo_pavliuk.smart_cookie.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Genre {

  private Long id;

  private String name;
}
