package org.tcat.frame.controller.gm;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tcat.frame.controller.BaseController;
import org.tcat.frame.service.user.UserIdRepository;

/**
 * Created by Lin on 2017/8/30.
 */
@Api(value = "/gmAdmin", description = "管理员帐户管理")
@RestController
@RequestMapping("gmAdmin")
public class GmAdminController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserIdRepository userIdRepository;



}
