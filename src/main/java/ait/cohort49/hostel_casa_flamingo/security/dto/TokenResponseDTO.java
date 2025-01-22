package ait.cohort49.hostel_casa_flamingo.security.dto;

import java.util.Objects;

public class TokenResponseDTO {

    private String refreshToken;
    private String accessToken;

    public TokenResponseDTO() {

    }

    public TokenResponseDTO(String accessToken, String refreshToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return String.format("TokenResponse: access_token: %s, refresh_token: %s", accessToken, refreshToken);
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenResponseDTO that)) return false;
        return Objects.equals(refreshToken, that.refreshToken) && Objects.equals(accessToken, that.accessToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refreshToken, accessToken);
    }
}
