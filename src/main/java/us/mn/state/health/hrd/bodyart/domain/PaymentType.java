package us.mn.state.health.hrd.bodyart.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PaymentType {
    @JsonProperty("CK")
    CHECK("CK", "Check"),

    @JsonProperty("CC")
    CC("CC", "Credit Card");

    private String code;
    private final String description;

    private PaymentType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PaymentType getByCode(String value) {
        List<PaymentType> list = Arrays.asList(PaymentType.values());
        return list.stream().filter(m -> m.code.equalsIgnoreCase(value)).findAny().orElse(null);
    }

}