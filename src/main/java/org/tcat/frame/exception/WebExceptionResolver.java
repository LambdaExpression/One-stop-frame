package org.tcat.frame.exception;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.tcat.frame.bean.JsonObject;
import org.tcat.frame.exception.code.ErrorCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 * Created by Lin on 2017/8/5.
 */
@ControllerAdvice
public class WebExceptionResolver {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final static String DEFAULT_ERROR_VIEW = "error/404";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {

        logger.error("控制器抛出异常", ex);

        String exMessage = ex.toString() + Arrays.asList(ex.getStackTrace()).stream().map(i -> i.toString()).collect(Collectors.joining("\n\t", "\n\t", ""));
        if (HttpMethod.GET.name().equals(request.getMethod())) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("exception", exMessage);
            mav.addObject("url", request.getRequestURL());
            mav.setViewName(DEFAULT_ERROR_VIEW);
            return mav;
        } else {
            JsonObject json = new JsonObject();
            json.setCode(ErrorCode.error);
            json.setData(exMessage);
            try (PrintWriter writer = response.getWriter()) {
                writer.write(new Gson().toJson(json));
                writer.flush();
            } catch (IOException e) {
            }
            return null;
        }
    }

}
