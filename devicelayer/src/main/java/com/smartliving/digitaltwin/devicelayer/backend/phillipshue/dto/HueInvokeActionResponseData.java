package com.smartliving.digitaltwin.devicelayer.backend.phillipshue.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Map;

@Value
@AllArgsConstructor
@JsonInclude(JsonInclude.Include. NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HueInvokeActionResponseData {
    String rid;
    String rtype;
}
