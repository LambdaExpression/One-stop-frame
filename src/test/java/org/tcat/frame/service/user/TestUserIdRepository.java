package org.tcat.frame.service.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tcat.frame.JunitBaseTest;
import org.tcat.frame.enums.MultiLanguage;
import org.tcat.frame.exception.code.CodeMsg;
import org.tcat.frame.service.user.dto.UserIdDto;
import org.tcat.frame.service.user.enums.UserIdType;

/**
 * Created by Lin on 2017/8/18.
 */
public class TestUserIdRepository extends JunitBaseTest {

    @Autowired
    private UserIdRepository userIdRepository;

    @Test
    public void test_save() {
        UserIdDto userIdDto = new UserIdDto()
                .setType(UserIdType.USER);
        show(userIdRepository.save(userIdDto));
    }

    @Test
    public void test_findById() {
        show(userIdRepository.findById(1L));
    }

    @Test
    public void test_enums() {
        show(UserIdType.STAFF.getMsg(MultiLanguage.cn));
        show(UserIdType.STAFF.codeList());

        show(CodeMsg.getMsg(MultiLanguage.en, "0"));
    }

}
