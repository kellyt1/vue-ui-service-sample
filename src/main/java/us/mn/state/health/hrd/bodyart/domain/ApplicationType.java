package us.mn.state.health.hrd.bodyart.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApplicationType {
    @JsonProperty("I")
    INITIAL("I", "Initial"),

    @JsonProperty("R")
    RENEWAL("R", "Renewal");

    private String code;
    private final String description;

    private ApplicationType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ApplicationType getByCode(String value) {
        List<ApplicationType> list = Arrays.asList(ApplicationType.values());
        return list.stream().filter(m -> m.code.equalsIgnoreCase(value)).findAny().orElse(null);
    }

    @JsonCreator
    public static ApplicationType forValues(@JsonProperty("code") String code) {
        return getByCode(code);
    }

}