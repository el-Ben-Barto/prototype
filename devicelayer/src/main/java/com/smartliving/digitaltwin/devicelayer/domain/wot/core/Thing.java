package com.smartliving.digitaltwin.devicelayer.domain.wot.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.Human;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.Location;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.Vendor;
import com.smartliving.digitaltwin.devicelayer.domain.wot.hypermedia.Form;
import com.smartliving.digitaltwin.devicelayer.domain.wot.hypermedia.Link;
import com.smartliving.digitaltwin.devicelayer.domain.wot.security.SecuritySchemaType;
import com.smartliving.digitaltwin.devicelayer.domain.wot.security.SecurityScheme;
import com.smartliving.digitaltwin.devicelayer.domain.wot.extension.SLDigitalTwinClassification;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include. NON_NULL)
public class Thing {

    List<Object> at_context;
    List<String> at_type;
    @Indexed(unique = true)
    String id;
    String title;
    String titles;
    String description;
    String descriptions;
    VersionInfo versionInfo;
    LocalDate created;
    LocalDate modified;
    String support;
    String base;
    Map<String, PropertyAffordance> properties;
    Map<String, ActionAffordance> actions;
    Map<String, EventAffordance> events;
    List<Link> links;
    List<Form> forms;
    List<SecuritySchemaType> security;
    Map<String, SecurityScheme> securityDefinitions;
    SLDigitalTwinClassification classification;
    Vendor vendor;
    Location location;
    Human human;
}
