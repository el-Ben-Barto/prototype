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
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include. NON_NULL)
public class PropertyAffordance extends DataSchema {
    List<Form> forms;
    Boolean observable;
}
