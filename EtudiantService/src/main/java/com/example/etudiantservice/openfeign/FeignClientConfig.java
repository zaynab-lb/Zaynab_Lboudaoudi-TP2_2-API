package com.example.etudiantservice.openfeign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignClientConfig
{

    @Bean
    public RequestInterceptor requestInterceptor()
    {
        return new RequestInterceptor()
        {
            @Override
            public void apply(RequestTemplate template)
            {
                try
                {

                    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    if (attrs != null)
                    {
                        HttpServletRequest request = attrs.getRequest();
                        String authHeader = request.getHeader("Authorization");

                        if (authHeader != null && authHeader.startsWith("Bearer "))
                        {
                            template.header("Authorization", authHeader);
                        }
                    } else
                    {
                        if (SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken jwtAuth)
                        {
                            Jwt jwt = jwtAuth.getToken();
                            template.header("Authorization", "Bearer " + jwt.getTokenValue());
                        }
                    }
                } catch (Exception e)
                {
                    System.err.println("Erreur lors du transfert du token : " + e.getMessage());
                }
            }
        };
    }
}
