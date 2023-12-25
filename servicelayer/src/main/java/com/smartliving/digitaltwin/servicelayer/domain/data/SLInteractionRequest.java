package com.smartliving.digitaltwin.servicelayer.domain.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.bson.Document;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SLInteractionRequest {
    String id;
    String action;
    Document parameters;
}
