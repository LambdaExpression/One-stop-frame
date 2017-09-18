package org.tcat.frame.initialization;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tcat.frame.controller.system.AdminController;
import org.tcat.frame.service.gm.GmAdminRepository;
import org.tcat.frame.service.gm.dto.AdminDto;
import org.tcat.frame.service.gm.enums.AdminGrade;
import org.tcat.frame.service.user.UserIdRepository;
import org.tcat.frame.service.user.enums.UserGender;
import org.tcat.frame.util.MD5Utils;

@Component
public class initAdmin implements InitializingBean {

    @Autowired
    private UserIdRepository userIdRepository;
    @Autowired
    private GmAdminRepository gmAdminRepository;
    @Autowired
    private AdminController adminController;

    private final static String DEFAULT_ACCOUNT = "admin";
    private final static String DEFAULT_PASSWORD = "admin";

    @Override
    public void afterPropertiesSet() throws Exception {
        if (gmAdminRepository.findByAccount(DEFAULT_ACCOUNT) == null) {
            adminController.create(new AdminDto()
                    .setAccount(DEFAULT_PASSWORD)
                    .setPassword(MD5Utils.md5(DEFAULT_PASSWORD))
                    .setNameCn(DEFAULT_ACCOUNT)
                    .setNameEn(DEFAULT_ACCOUNT)
                    .setGender(UserGender.FEMALE)
                    .setGrade(AdminGrade.SUPER));
        }
    }

}
