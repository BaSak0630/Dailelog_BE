package com.dailelog.config;

import com.dailelog.config.data.UserSession;
import com.dailelog.domain.Session;
import com.dailelog.exception.Unauthorized;
import com.dailelog.repository.SessionRepository;
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

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest serveletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if(serveletRequest == null) {
            log.error("servlet request is null");
            throw new Unauthorized();
        }

        Cookie[] cookies = serveletRequest.getCookies();
        if(cookies.length==0){
            log.error("cookie is empty");
            throw new Unauthorized();
        }

        String accessToken = cookies[0].getValue();

        Session session = sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(Unauthorized::new);

        return new UserSession(session.getUser().getId());
    }
}
