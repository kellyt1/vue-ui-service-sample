package us.mn.state.health.hrd.bodyart.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Settings {

    @JsonUnwrapped
    @JsonInclude(JsonInclude.Include.ALWAYS)
    Map<String, String> settings;
}
