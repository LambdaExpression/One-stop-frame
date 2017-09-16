package org.tcat.frame.controller.error;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.tcat.frame.controller.BaseController;

@Api(value = "/error", description = "访问异常控制器", hidden = true)
@RestController
@RequestMapping("/error")
public class ErrorController extends BaseController {

    @ApiOperation(value = "404")
    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public ModelAndView error_aberrant() {
        return new ModelAndView("error/404");
    }

    @ApiOperation(value = "403")
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView error_noPermission() {
        return new ModelAndView("error/403");
    }

}
