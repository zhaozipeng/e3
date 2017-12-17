package com.e3.web.exception;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class GlobalException implements HandlerExceptionResolver {

    private static Logger logger=Logger.getLogger(GlobalException.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("exception");
        modelAndView.addObject("message",e.getMessage());
        logger.error("========"+e.getMessage());
        return modelAndView;
    }
}
