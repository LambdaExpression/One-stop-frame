package org.tcat.frame.service.gm;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.tcat.frame.service.BaseRepository;
import org.tcat.frame.service.gm.dto.AdminDto;

import java.util.List;

/**
 * Created by Lin on 2017/8/18.
 */
public interface GmAdminRepository extends BaseRepository<AdminDto, Long> {

    AdminDto save(AdminDto adminDto);

    AdminDto findByAccount(String account);

    AdminDto findByAccountAndPassword(String account, String password);

    @Modifying
    @Query(value = " UPDATE gm$admin SET disable = ?1 WHERE user_id IN ?2 ", nativeQuery = true)
    int updateDisable(Integer disable, List<Long> userIds);

}
