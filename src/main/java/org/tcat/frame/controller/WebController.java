package org.tcat.frame.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.tcat.frame.bean.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lin on 2017/8/3.
 */
@RestController
@RequestMapping("/")
public class WebController extends BaseController {

    @ApiOperation(value = "首页")
    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("index_bak");
        model.addObject("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return model;
    }


    @RequestMapping(value = "/exception", method = {RequestMethod.GET})
    public ModelAndView exceptionGet() {
        ModelAndView model = new ModelAndView("index");
        //throw Exception
        Integer.valueOf("aa");
        model.addObject("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return model;
    }

    @RequestMapping(value = "/exception", method = RequestMethod.POST)
    public JsonObject exceptionPost() {
        //throw Exception
        Integer.valueOf("aa");
        return JsonObject.ok();
    }

}
