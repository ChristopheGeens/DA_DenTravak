package be.ucll.da.dentravak.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum  BreadTypeEnum {
    @JsonProperty("turksBrood")
    TURKISHBREAD("Turks brood"),
    @JsonProperty("wrap")
    WRAP("Wrap"),
    @JsonProperty("boterhammekes")
    BOTERHAMMEKES("Boterhammekes");

    private String type;

    BreadTypeEnum(String type){
        this.type=type;
    }

    public String getType(){
        return type;
    }
}
