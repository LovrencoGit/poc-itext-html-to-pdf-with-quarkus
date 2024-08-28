package org.acme.enumerations;

public enum HTMLMarkerEnum {

    FOR     ("for"),
    IMPORT  ("import");

    public String marker;

    HTMLMarkerEnum(String marker) {
        this.marker = marker;
    }
}
