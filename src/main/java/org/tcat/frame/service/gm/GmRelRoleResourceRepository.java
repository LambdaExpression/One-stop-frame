package org.tcat.frame.service.gm;

import org.tcat.frame.service.BaseRepository;
import org.tcat.frame.service.gm.dto.GmRelRoleResourceDto;

import java.util.List;


/**
 * Created by Lin on 2017/8/24.
 */
public interface GmRelRoleResourceRepository extends BaseRepository<GmRelRoleResourceDto, Long> {

    List<GmRelRoleResourceDto> findByRoleId(Long roleId);

    int deleteByRoleId(Long roleId);

}
