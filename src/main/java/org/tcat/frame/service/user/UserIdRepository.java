package org.tcat.frame.service.user;

import org.tcat.frame.service.BaseRepository;
import org.tcat.frame.service.user.dto.UserIdDto;

/**
 * Created by Lin on 2017/8/18.
 */
public interface UserIdRepository extends BaseRepository<UserIdDto, Long> {

    UserIdDto findById(Long id);

}
