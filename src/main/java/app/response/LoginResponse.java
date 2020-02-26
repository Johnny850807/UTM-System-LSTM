package app.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

public class LoginResponse {

    private String token;

    @JsonProperty("pilot-id")
    private String pilotId;

    public LoginResponse(String token, String pilotId) {
        this.token = token;
        this.pilotId = pilotId;
    }

    public String getToken() {
        return token;
    }

    public String getPilotId() {
        return pilotId;
    }
}
