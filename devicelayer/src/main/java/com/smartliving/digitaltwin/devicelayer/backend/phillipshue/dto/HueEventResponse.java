package com.smartliving.digitaltwin.devicelayer.backend.phillipshue.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.bson.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HueEventResponse {
    String creationtime;
    List<Document> data;
    String id;
    String type;

}
