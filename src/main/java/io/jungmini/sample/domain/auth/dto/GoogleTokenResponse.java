package io.jungmini.sample.domain.auth.dto;

public record GoogleTokenResponse(
    String access_token,
    String refresh_token,
    String token_type,
    int expires_in,
    String id_token,
    String scope
) {
}