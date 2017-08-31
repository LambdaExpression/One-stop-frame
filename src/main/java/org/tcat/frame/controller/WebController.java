package org.tcat.frame.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.tcat.frame.bean.JsonObject;
import org.tcat.frame.component.CacheDate;
import org.tcat.frame.component.DynamicTimer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lin on 2017/8/3.
 */
@Api(value = "/", description = "web基础管理", hidden = true)
@RestController
@RequestMapping("/")
public class WebController extends BaseController {

    @ApiOperation(value = "首页", hidden = true)
    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("index_bak");
        model.addObject("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return model;
    }

    @Autowired
    private CacheDate cacheDate;
    @Autowired
    private DynamicTimer dynamicTimer;

    @RequestMapping(value = "test")
    public JsonObject test() {
        cacheDate.putMinutes("123", "456", 1);
        return JsonObject.ok();
    }

    @RequestMapping(value = "test2")
    public JsonObject<String> test2() {
        return JsonObject.ok(cacheDate.show());
    }

}
