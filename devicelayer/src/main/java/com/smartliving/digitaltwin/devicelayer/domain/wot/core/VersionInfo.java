package com.smartliving.digitaltwin.devicelayer.domain.wot.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include. NON_NULL)
public class VersionInfo {
    String instance;
}
