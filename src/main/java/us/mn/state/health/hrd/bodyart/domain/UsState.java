package us.mn.state.health.hrd.bodyart.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public enum UsState implements Serializable {
    @JsonProperty("AK") AK("AK","Alaska"),
    @JsonProperty("AL") AL("AL","Alabama"),
    @JsonProperty("AR") AR("AR","Arkansas"),
    @JsonProperty("AZ") AZ("AZ","Arizona"),
    @JsonProperty("CA") CA("CA","California"),
    @JsonProperty("CO") CO("CO","Colorado"),
    @JsonProperty("CT") CT("CT","Connecticut"),
    @JsonProperty("DC") DC("DC","District of Columbia"),
    @JsonProperty("DE") DE("DE","Delaware"),
    @JsonProperty("FL") FL("FL","Florida"),
    @JsonProperty("GA") GA("GA","Georgia"),
    @JsonProperty("HI") HI("HI","Hawaii"),
    @JsonProperty("IA") IA("IA","Iowa"),
    @JsonProperty("ID") ID("ID","Idaho"),
    @JsonProperty("IL") IL("IL","Illinois"),
    @JsonProperty("IN") IN("IN","Indiana"),
    @JsonProperty("KS") KS("KS","Kansas"),
    @JsonProperty("KY") KY("KY","Kentucky"),
    @JsonProperty("LA") LA("LA","Louisiana"),
    @JsonProperty("MA") MA("MA","Massachusetts"),
    @JsonProperty("MD") MD("MD","Maryland"),
    @JsonProperty("ME") ME("ME","Maine"),
    @JsonProperty("MI") MI("MI","Michigan"),
    @JsonProperty("MN") MN("MN","Minnesota"),
    @JsonProperty("MO") MO("MO","Missouri"),
    @JsonProperty("MS") MS("MS","Mississippi"),
    @JsonProperty("MT") MT("MT","Montana"),
    @JsonProperty("NC") NC("NC","North Carolina"),
    @JsonProperty("ND") ND("ND","North Dakota"),
    @JsonProperty("NE") NE("NE","Nebraska"),
    @JsonProperty("NH") NH("NH","New Hampshire"),
    @JsonProperty("NJ") NJ("NJ","New Jersey"),
    @JsonProperty("NM") NM("NM","New Mexico"),
    @JsonProperty("NV") NV("NV","Nevada"),
    @JsonProperty("NY") NY("NY","New York"),
    @JsonProperty("OH") OH("OH","Ohio"),
    @JsonProperty("OK") OK("OK","Oklahoma"),
    @JsonProperty("OR") OR("OR","Oregon"),
    @JsonProperty("PA") PA("PA","Pennsylvania"),
    @JsonProperty("RI") RI("RI","Rhode Island"),
    @JsonProperty("SC") SC("SC","South Carolina"),
    @JsonProperty("SD") SD("SD","South Dakota"),
    @JsonProperty("TN") TN("TN","Tennessee"),
    @JsonProperty("TX") TX("TX","Texas"),
    @JsonProperty("UT") UT("UT","Utah"),
    @JsonProperty("VA") VA("VA","Virginia"),
    @JsonProperty("VT") VT("VT","Vermont"),
    @JsonProperty("WS") WA("WA","Washington"),
    @JsonProperty("WI") WI("WI","Wisconsin"),
    @JsonProperty("WV") WV("WV","West Virginia"),
    @JsonProperty("WY") WY("WY","Wyoming");

    private String code;
    private final String description;

    private UsState(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static UsState getByCode(String value) {
        List<UsState> list = Arrays.asList(UsState.values());
        return list.stream().filter(m -> m.code.equalsIgnoreCase(value)).findAny().orElse(null);
    }
}
