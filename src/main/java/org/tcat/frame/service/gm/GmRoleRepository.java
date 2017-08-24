package org.tcat.frame.service.gm;

import org.tcat.frame.service.BaseRepository;
import org.tcat.frame.service.gm.dto.GmRoleDto;

import java.util.List;

/**
 * Created by Lin on 2017/8/24.
 */
public interface GmRoleRepository extends BaseRepository<GmRoleDto, Long> {

    List<GmRoleDto> findAll();

}
