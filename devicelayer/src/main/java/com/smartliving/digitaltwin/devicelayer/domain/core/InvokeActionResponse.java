package com.smartliving.digitaltwin.devicelayer.domain.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.Document;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include. NON_NULL)
public class InvokeActionResponse {
    String id;
    Document status;
}
