package com.smartliving.digitaltwin.devicelayer.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.smartliving.digitaltwin.devicelayer.domain.wot.core.ActionAffordance;
import com.smartliving.digitaltwin.devicelayer.domain.wot.core.Thing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.Document;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SLThingInput {
    Thing thingDefinition;
    Boolean isVendorProduct;
    Document initialStatus;
}
