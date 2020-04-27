package cn.imhtb.antlive.service;

import io.jsonwebtoken.Claims;

public interface ITokenService {

    Claims get();

    void set();

    Integer getUserId();
}
