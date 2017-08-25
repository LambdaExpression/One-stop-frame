package org.tcat.frame.service.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tcat.frame.JunitBaseTest;
import org.tcat.frame.service.user.dto.UserDto;

/**
 * Created by Lin on 2017/8/18.
 */
public class TestUserRepository extends JunitBaseTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test_save() {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setAccount("account")
                .setPassword("password");
//                .setType(1);
        show(userRepository.save(userDto));
    }

    @Test
    public void test_findById() {
        show(userRepository.findByAccountAndPassword("account", "password"));
    }

}
