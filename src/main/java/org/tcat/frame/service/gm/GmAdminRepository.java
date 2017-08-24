package org.tcat.frame.service.gm;

import org.tcat.frame.service.BaseRepository;
import org.tcat.frame.service.gm.dto.AdminDto;

/**
 * Created by Lin on 2017/8/18.
 */
public interface GmAdminRepository extends BaseRepository<AdminDto,Long> {

    AdminDto save(AdminDto adminDto);

}
