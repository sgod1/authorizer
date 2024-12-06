package io.issuer.authorizer.apis;

public class RiskResponse {
    private int score;

    public RiskResponse() {
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
