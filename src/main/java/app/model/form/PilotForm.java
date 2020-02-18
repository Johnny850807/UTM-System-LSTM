package app.model.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import static app.model.form.FormStatus.MAY_NOT_BE_EMPTY;

public class PilotForm {

    @JsonProperty("pilot-id")
    @NotBlank(message = MAY_NOT_BE_EMPTY)
    private String pilotId;

    @NotBlank(message = MAY_NOT_BE_EMPTY)
    private String password;

    @NotBlank(message = MAY_NOT_BE_EMPTY)
    @JsonProperty("retype-password")
    private String retypePassword;

    public PilotForm(){}

    public String getPilotId() {
        return pilotId;
    }

    public String getPassword() {
        return password;
    }

    public String getRetypePassword() {
        return retypePassword;
    }
}
