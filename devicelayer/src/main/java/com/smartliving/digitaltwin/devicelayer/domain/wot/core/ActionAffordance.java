package com.smartliving.digitaltwin.devicelayer.domain.wot.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartliving.digitaltwin.devicelayer.domain.wot.hypermedia.Form;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include. NON_NULL)
public class ActionAffordance extends DataSchema {
    List<Form> forms;
    DataSchema input;
    DataSchema output;
    Boolean safe;
    Boolean idempotent;
    Map<String, DataSchema> actions;
}
