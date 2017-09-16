package org.tcat.frame.service.gm;

import org.tcat.frame.service.BaseRepository;
import org.tcat.frame.service.gm.dto.GmRelAdminGroupDto;

import java.util.List;

/**
 * Created by Lin on 2017/8/24.
 */
public interface GmRelAdminGroupRepository extends BaseRepository<GmRelAdminGroupDto, Long> {

    List<GmRelAdminGroupDto> findByAdminId(Long adminId);

}
