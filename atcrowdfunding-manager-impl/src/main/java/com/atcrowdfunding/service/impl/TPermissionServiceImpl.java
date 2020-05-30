package com.atcrowdfunding.service.impl;

import com.atcrowdfunding.bean.TPermission;
import com.atcrowdfunding.mapper.TPermissionMapper;
import com.atcrowdfunding.service.TPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther:shisheng
 * @creat 2020-05-14-17:35
 */
@Service
public class TPermissionServiceImpl implements TPermissionService {
    @Autowired
    TPermissionMapper tPermissionMapper;


    @Override
    public List<TPermission> getTPermissionList() {
        List<TPermission> tPermissions = tPermissionMapper.selectByExample(null);
        return tPermissions;
    }

    @Override
    public int saveTPermission(TPermission tPermission) {
        int i = tPermissionMapper.insertSelective(tPermission);
        return i;
    }

    @Override
    public TPermission getTPermissionById(Integer id) {
        TPermission tPermission = tPermissionMapper.selectByPrimaryKey(id);
        return tPermission;
    }

    @Override
    public int doUpdate(TPermission tPermission) {
        int i = tPermissionMapper.updateByPrimaryKeySelective(tPermission);
        return i;
    }

    @Override
    public int doDelete(Integer id) {
        int i = tPermissionMapper.deleteByPrimaryKey(id);
        return i;
    }


}
