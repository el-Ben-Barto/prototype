package com.smartliving.digitaltwin.devicelayer.domain.wot.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include. NON_NULL)
public class DataSchema {
    String at_type;
    String title;
    Map<String,String> titles;
    String description;
    Map<String,String> descriptions;
    String type;
    Object cons;
    String unit;
    List<DataSchema> oneOf;
    List<String> enumeration;
    Boolean readOnly;
    Boolean writeOnly;
    String format;
    Map<String, DataSchema> properties;
}
