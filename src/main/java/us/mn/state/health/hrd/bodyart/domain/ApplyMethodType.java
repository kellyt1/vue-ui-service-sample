package us.mn.state.health.hrd.bodyart.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApplyMethodType {

    @JsonProperty("S")
    SUPERVISION("S", "Supervision"),

    @JsonProperty("R")
    RECIPROCITY("R", "Reciprocity");

    private String code;
    private final String description;

    private ApplyMethodType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ApplyMethodType getByCode(String value) {
        List<ApplyMethodType> list = Arrays.asList(ApplyMethodType.values());
        return list.stream().filter(m -> m.code.equalsIgnoreCase(value)).findAny().orElse(null);
    }

}