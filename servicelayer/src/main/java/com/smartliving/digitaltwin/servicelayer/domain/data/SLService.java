package com.smartliving.digitaltwin.servicelayer.domain.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;
import java.util.List;


@Value
@Builder
@JsonInclude(JsonInclude.Include. NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SLService {
    ObjectId _id;
    @Indexed
    String serviceId;
    @Indexed
    String readableName;
    String owner;
    String shortDescription;
    String longDescription;
    List<String> allowedUsers;
    @Indexed
    Boolean active;
    LocalDate creationTime;
    List<SLInteraction> registeredInteractions;
}
