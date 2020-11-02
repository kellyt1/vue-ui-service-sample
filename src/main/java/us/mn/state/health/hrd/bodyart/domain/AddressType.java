package us.mn.state.health.hrd.bodyart.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AddressType {

    @JsonProperty("H")
    HOME("H", "Home"),

    @JsonProperty("E")
    EMPLOYER("E", "Employer");

    private String code;
    private final String description;

    private AddressType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static AddressType getByCode(String value) {
        List<AddressType> list = Arrays.asList(AddressType.values());
        return list.stream().filter(m -> m.code.equalsIgnoreCase(value)).findAny().orElse(null);
    }

    @JsonCreator
    public static AddressType forValues(@JsonProperty("code") String code) {
        return getByCode(code);
    }
}