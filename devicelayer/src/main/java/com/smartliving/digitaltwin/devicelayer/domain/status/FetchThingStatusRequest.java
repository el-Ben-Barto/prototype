package com.smartliving.digitaltwin.devicelayer.domain.status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartliving.digitaltwin.devicelayer.domain.wot.core.DataSchema;
import com.smartliving.digitaltwin.devicelayer.domain.wot.hypermedia.Form;
import com.smartliving.digitaltwin.devicelayer.domain.wot.security.SecuritySchemaType;
import com.smartliving.digitaltwin.devicelayer.domain.wot.security.SecurityScheme;
import lombok.*;

import java.util.Map;

@Value
@Builder
@JsonInclude(JsonInclude.Include. NON_NULL)
public class FetchThingStatusRequest {
    String vendorId;
    Form form;
    SecuritySchemaType securitySchemaType;
    Map<String, SecurityScheme> securityDefinitions;
    DataSchema input;
}
