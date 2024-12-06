package io.issuer.authorizer.apis;

public class AuthorizationResponse {
    private String authorizationDecision = "";

    public AuthorizationResponse() {
    }

    public AuthorizationResponse(String authorizationDecision) {
        this.authorizationDecision = authorizationDecision;
    }

    public String getAuthorizationDecision() {
        return authorizationDecision;
    }

    public void setAuthorizationDecision(String authorizationDecision) {
        this.authorizationDecision = authorizationDecision;
    }
}
