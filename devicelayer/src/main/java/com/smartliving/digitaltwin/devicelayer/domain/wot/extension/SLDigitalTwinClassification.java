package com.smartliving.digitaltwin.devicelayer.domain.wot.extension;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SLDigitalTwinClassification {

    Digital_Twin(true,true, true),
    Digital_Shadow(true,true, true),
    Digital_Shadow_Virtual(false,true, true),
    Digital_Model(true,true, false),
    Digital_Model_Virtual(true,false, false);

    Boolean physicalEntity;
    Boolean virtualEntity;
    Boolean connectivityModule;
}
