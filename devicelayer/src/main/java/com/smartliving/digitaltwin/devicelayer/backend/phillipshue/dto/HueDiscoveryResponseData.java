package com.smartliving.digitaltwin.devicelayer.backend.phillipshue.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
@AllArgsConstructor
@JsonInclude(JsonInclude.Include. NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HueDiscoveryResponseData {
    String id;
    String id_v1;
    Map<String, String> owner;
    Boolean enabled;
    Map<String, String> contact_report;
    Map<String, String> product_data;
    Map<String, String> metadata;
    Map<String, String> identify;
    Map<String, Object> on;
    Map<String, String> dimming;
    Map<String, String> dimming_delta;
    ColorTemperature color_temperature;
    Map<String, String> color_temperature_delta;
    Color color;
    Dynamic dynamics;
    Map<String, List<String>> alert;
    Map<String, List<String>> signaling;
    String mode;
    Effect effects;
    String type;
    List<HueDiscoveryResponseDataServices> services;

    @Value
    @AllArgsConstructor
    public static class ColorTemperature {
        String mirek;
        Boolean mirek_valid;
        Map<String, Integer> mirek_schema;
    }
    @Value
    @AllArgsConstructor
    public static class Color {
        Coordinate xy;
        Map<String, Coordinate> gamut;
        String gamut_type;
    }

    @Value
    @AllArgsConstructor
    public static class Coordinate{
        Double x;
        Double y;
    }

    @Value
    @AllArgsConstructor
    public static class Dynamic {
        String status;
        List<String> status_values;
        Double speed;
        Boolean speed_value;
    }

    @Value
    @AllArgsConstructor
    public static class Effect {
        List<String> status_values;
        String status;
        List<String> effect_values;
    }
}
