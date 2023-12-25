package com.smartliving.digitaltwin.devicelayer.backend.phillipshue.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
@AllArgsConstructor
@JsonInclude(JsonInclude.Include. NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HueEventResponseData {
    String id;
    String id_v1;
    //Map<String, String> contact_report;
    Map<String,String> owner;
    String status;
    String type;
    Map<String, Object> dimming;
    Map<String, Object> on;

     /*
    "creationtime": "2023-12-11T16:59:04Z",
        "data": [
            {
                "contact_report": {
                    "changed": "2023-12-11T16:59:04.747Z",
                    "state": "no_contact"
                },
                "id": "11835319-534c-4dd0-885b-a2c241a9549f",
                "owner": {
                    "rid": "01d64c9c-dac9-49c2-b115-917328397335",
                    "rtype": "device"
                },
                "type": "contact"
            }
        ],
        "id": "2e322049-aa17-4504-9bec-d4fede7281d4",
        "type": "update"
    }
     */
}
