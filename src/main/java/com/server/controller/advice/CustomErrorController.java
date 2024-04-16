package com.server.controller.advice;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String errorPage(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
        
            switch (statusCode) {
                case 404:
                    return "error/bad-request";
                case 403:
                    return "error/forbidden";
                case 500:
                    return "error/server";
                default:
                    break;
            }
        }

        return "error/base";
    }
}
