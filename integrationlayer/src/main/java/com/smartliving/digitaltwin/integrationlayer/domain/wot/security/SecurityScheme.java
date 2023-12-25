package com.smartliving.digitaltwin.integrationlayer.domain.wot.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
