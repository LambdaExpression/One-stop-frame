package org.tcat.frame.service.gm;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.tcat.frame.service.BaseRepository;
import org.tcat.frame.service.gm.dto.GmRelAdminRoleDto;

import java.util.List;

/**
 * Created by Lin on 2017/8/24.
 */
public interface GmRelAdminRoleRepository extends BaseRepository<GmRelAdminRoleDto, Long> {

    List<GmRelAdminRoleDto> findByAdminId(Long adminId);

    int deleteByAdminId(Long adminId);

    @Modifying
    @Query(value = " DELETE FROM gm$rel_admin_role where user_id in ?1 ", nativeQuery = true)
    int deleteByAdminIds(List<Long> adminIds);

}
