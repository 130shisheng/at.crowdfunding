package com.atcrowdfunding.service;

import com.atcrowdfunding.bean.TPermission;

import java.util.List;

/**
 * @auther:shisheng
 * @creat 2020-05-14-17:35
 */
public interface TPermissionService {
    List<TPermission> getTPermissionList();

    int saveTPermission(TPermission tPermission);

    TPermission getTPermissionById(Integer id);



    int doUpdate(TPermission tPermission);

    int doDelete(Integer id);
}
