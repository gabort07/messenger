package org.progmatic.messenger.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class MyControllerAdvice {

    private Logger logger = LoggerFactory.getLogger(MyControllerAdvice.class);

    @ExceptionHandler
    public String exception(Exception ex, Model model){
        logger.error("Error", ex);
        String traceMsg = Arrays.toString(Thread.currentThread().getStackTrace());
        model.addAttribute("exceptionMessage", ex.getMessage());
        model.addAttribute("stacktrace", traceMsg);
        return "error";
    }


}
