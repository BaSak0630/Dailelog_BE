package com.dailelog.config;

import com.dailelog.config.data.UserSession;
import com.dailelog.domain.Session;
import com.dailelog.exception.Unauthorized;
import com.dailelog.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    private static final String KEY= "s5ZweJAp9NjHKslNcN1cTlYJoTTl7dEpE3Cem4mF3aE=";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String jws = webRequest.getHeader("Authorization");
        if(jws == null  || jws.isEmpty()) {
            throw new Unauthorized();
        }

        byte[] decodeedKey = Base64.getDecoder().decode(KEY);

        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(decodeedKey)
                    .build()
                    .parseSignedClaims(jws);

            String userid = claims.getBody().getSubject();

            return new UserSession(Long.parseLong(userid));
        } catch (JwtException e) {
            throw new Unauthorized();
        }
    }
}
