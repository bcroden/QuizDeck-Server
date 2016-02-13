package com.quizdeck.util;

import com.quizdeck.filters.AuthFilter;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Brandon on 2/12/2016.
 */
@ControllerAdvice
public class GlobalModuleAttributesConfigurer {
    @ModelAttribute("claims")
    public Claims getClaims(HttpServletRequest request) {
        return (Claims)request.getAttribute(AuthFilter.CLAIMS_ATTRIBUTE);
    }
}
