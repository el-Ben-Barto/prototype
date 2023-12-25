package com.smartliving.digitaltwin.devicelayer.backend.phillipshue.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HueDiscoveryResponse {
    List<Document> errors;
    LocalDate creationtime;
    List<Document> data;
    String id;
    String type;
}
