package org.tcat.frame.service.user;


import org.tcat.frame.service.BaseRepository;
import org.tcat.frame.service.user.dto.UserDto;

/**
 * Created by Lin on 2017/8/3.
 */
public interface UserRepository extends BaseRepository<UserDto, Long> {
//
//    UserDto findById(@Param("id") Long id);
//
//    @Query("from user$user where name=:name")
//    UserDto findUser(@Param("name") String name);

    UserDto findByAccountAndPassword(String account, String password);

    UserDto findByAccount(String account);

}
