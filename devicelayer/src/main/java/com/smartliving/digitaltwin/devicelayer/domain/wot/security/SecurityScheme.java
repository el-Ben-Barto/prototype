package com.smartliving.digitaltwin.devicelayer.domain.wot.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include. NON_NULL)
public class SecurityScheme {
    List<String> atType;
    SecuritySchemaType scheme;
    String description;
    Map<String, String> descriptions;
    String proxy;
    In in;
    String name;
}
