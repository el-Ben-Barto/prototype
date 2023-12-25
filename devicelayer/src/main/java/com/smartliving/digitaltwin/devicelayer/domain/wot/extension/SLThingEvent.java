package com.smartliving.digitaltwin.devicelayer.domain.wot.extension;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.bson.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SLThingEvent {
    String eventId;
    List<Document> eventChain;
    LocalDate timestamp;
}
