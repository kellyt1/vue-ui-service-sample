package us.mn.state.health.hrd.bodyart.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LicenseType {
    @JsonProperty("T")
    TATTOOIST("T", "Tattooist"),

    @JsonProperty("P")
    PIERCER("P", "Piercer"),

    @JsonProperty("D")
    DUAL("D", "Dual");

    private String code;
    private final String description;

    private LicenseType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static LicenseType getByCode(String value) {
        List<LicenseType> list = Arrays.asList(LicenseType.values());
        return list.stream().filter(m -> m.code.equalsIgnoreCase(value)).findAny().orElse(null);
    }

}