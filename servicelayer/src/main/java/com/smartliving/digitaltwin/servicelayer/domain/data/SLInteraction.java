package com.smartliving.digitaltwin.servicelayer.domain.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.bson.Document;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include. NON_NULL)
public class SLInteraction {
    SLInteractionType type;
    String urn;
    String readableName;
    SLInteractionAction interaction;
}
