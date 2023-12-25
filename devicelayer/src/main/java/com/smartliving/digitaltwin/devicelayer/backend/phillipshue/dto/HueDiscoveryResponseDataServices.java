package com.smartliving.digitaltwin.devicelayer.backend.phillipshue.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class HueDiscoveryResponseDataServices {
    String rid;
    String rtype;
}
