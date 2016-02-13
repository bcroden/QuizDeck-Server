package com.quizdeck.util;

import com.quizdeck.filters.AuthenticationFilter;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Exposes the claims of the auth token contained in the Authorization header as a ModelAttribute.
 *
 * Created by Brandon on 2/12/2016.
 */
@ControllerAdvice
public class GlobalModuleAttributesConfigurer {
    @ModelAttribute("claims")
    public Claims getClaims(HttpServletRequest request) {
        return (Claims)request.getAttribute(AuthenticationFilter.CLAIMS_ATTRIBUTE);
    }
}
