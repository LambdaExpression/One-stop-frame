package org.tcat.frame.service.gm;


import org.tcat.frame.service.BaseRepository;
import org.tcat.frame.service.gm.dto.GmRelAdminRoleDto;

import java.util.List;

/**
 * Created by Lin on 2017/8/24.
 */
public interface GmRelAdminRoleRepository extends BaseRepository<GmRelAdminRoleDto, Long> {

    List<GmRelAdminRoleDto> findByAdminId(Long adminId);

}
