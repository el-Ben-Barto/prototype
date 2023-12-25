package com.smartliving.digitaltwin.integrationlayer.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.Document;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvokeActionInput {
    String id;
    String action;
    Document parameters;
}
