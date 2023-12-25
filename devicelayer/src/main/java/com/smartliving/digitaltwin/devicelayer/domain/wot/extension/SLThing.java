package com.smartliving.digitaltwin.devicelayer.domain.wot.extension;

import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SLDigitalTwinClassification;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SLThing {
    String id;
    Document status;
    LocalDate timestamp;
}
