package com.smartliving.digitaltwin.devicelayer.domain.wot.extension;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include. NON_NULL)
public class Location {
    String id;
    String title;
    GeoJsonPoint coordinates;
}
