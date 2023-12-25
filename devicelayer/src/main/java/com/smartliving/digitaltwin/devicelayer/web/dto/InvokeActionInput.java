package com.smartliving.digitaltwin.devicelayer.web.dto;

import com.smartliving.digitaltwin.devicelayer.domain.wot.core.ActionAffordance;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.Document;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvokeActionInput {
    String id;
    String action;
    Document parameters;
}
