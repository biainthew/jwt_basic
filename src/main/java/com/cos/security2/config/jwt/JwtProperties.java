package com.cos.security2.config.jwt;

public interface JwtProperties {
    String SECRET_KEY = "Bia";
    int EXPIRATION_TIME = 60000 * 10;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
