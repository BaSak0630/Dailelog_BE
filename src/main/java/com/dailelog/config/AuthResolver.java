package com.dailelog.config;

import com.dailelog.config.data.UserSession;
import com.dailelog.exception.Unauthorized;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {
    private final AppConfig appConfig;

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

        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(appConfig.getJwtKey())
                    .build()
                    .parseSignedClaims(jws);//복호화

            String userid = claims.getBody().getSubject();

            return new UserSession(Long.parseLong(userid));
        } catch (JwtException e) {
            throw new Unauthorized();
        }
    }
}
