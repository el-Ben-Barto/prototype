package com.smartliving.digitaltwin.devicelayer.domain.wot.hypermedia;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartliving.digitaltwin.devicelayer.domain.wot.security.SecuritySchemaType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include. NON_NULL)
public class Form {
    FormOperation op;
    String href;
    String contentType;
    String contentEncoding;
    String subprotocoll;
    SecuritySchemaType security;
    List<String> scopes;
    ExpectedResponse response;
    String htv_methodName;

    public enum FormOperation {
        readproperty, writeproperty, observeproperty, unobserveproperty, invokeaktion, subscribeevent, unsubscribeevent, readallproperties, writeallproperties, readmultipleproperties, writemultipleproperties;
    }
}
