package com.smartliving.digitaltwin.servicelayer.web.dto;

import com.smartliving.digitaltwin.servicelayer.domain.data.SLService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.bson.Document;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SLServiceInput {
    SLService serviceDefinition;
}
