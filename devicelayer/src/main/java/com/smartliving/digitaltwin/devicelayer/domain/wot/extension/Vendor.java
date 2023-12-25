package com.smartliving.digitaltwin.devicelayer.domain.wot.extension;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include. NON_NULL)
public class Vendor {
    String id;
    String title;
    String documentation;
    Map<String, String> attributes;
}
