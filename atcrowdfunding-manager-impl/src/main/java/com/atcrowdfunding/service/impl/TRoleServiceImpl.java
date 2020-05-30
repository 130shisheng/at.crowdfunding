package com.atcrowdfunding.service.impl;

import com.atcrowdfunding.bean.TRole;
import com.atcrowdfunding.bean.TRoleExample;
import com.atcrowdfunding.bean.TRolePermissionExample;
import com.atcrowdfunding.mapper.TAdminRoleMapper;
import com.atcrowdfunding.mapper.TRoleMapper;
import com.atcrowdfunding.mapper.TRolePermissionMapper;
import com.atcrowdfunding.service.TAdminService;
import com.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.HashMap;
import java.util.List;

/**
 * @auther:shisheng
 * @creat 2020-05-12-21:26
 */
@Service
public class TRoleServiceImpl implements TRoleService {
    @Autowired
    TRoleMapper tRoleMapper;
    @Autowired
    TAdminRoleMapper tAdminRoleMapper;
    @Autowired
    TRolePermissionMapper tRolePermissionMapper;


    @Override
    public void saveTRole(TRole role) {

        tRoleMapper.insertSelective(role);
    }

    @Override
    public TRole getRoleById(Integer roleId) {
        TRole tRole = tRoleMapper.selectByPrimaryKey(roleId);
        return tRole;
    }

    @Override
    public void update(TRole role) {
        int i = tRoleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int deleteById(Integer id) {
      return tRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<TRole> getAllRole() {
        List<TRole> tRoles = tRoleMapper.selectByExample(null);
        return tRoles;
    }

    @Override
    public List<Integer> getRoleIdByAdminId(String id) {
        List<Integer> roleIdByAdminId = tAdminRoleMapper.getRoleIdByAdminId(id);
        return roleIdByAdminId;
    }

    @Override
    public int doAssign(Integer adminId, Integer[] roleId) {

        int i = tAdminRoleMapper.insertMore(adminId, roleId);
        return i;
    }

    @Override
    public int doRemoveAssign(Integer adminId, Integer[] roleId) {


        int i = tAdminRoleMapper.deleteByadminIdAndRoleId(adminId,roleId);
        return i;
    }

    @Override
    public int doAssignPermissionToRole(Integer roleId, List<Integer> ids) {
int i =tRolePermissionMapper.saveAssignAndPermission(roleId,ids);
        return i;
    }

    @Override
    public void deleteAssignPermissionToRoleByRoleId(Integer roleId) {
        TRolePermissionExample tRolePermissionExample = new TRolePermissionExample();
        tRolePermissionExample.createCriteria().andRoleidEqualTo(roleId);
        tRolePermissionMapper.deleteByExample(tRolePermissionExample);
    }

    @Override
    public List<Integer> listPermissionIdByRoleId(Integer roleId) {
        List<Integer> pIds = tRolePermissionMapper.listPermissionIdByRoleId(roleId);
        return pIds;
    }

    @Override
    public PageInfo<TRole> listRolePage(HashMap<String, Object> paramMap) {

        TRoleExample example = new TRoleExample();

        String condition =(String)paramMap.get("condition");

        example.createCriteria().andNameLike("%"+condition+"%");

        List<TRole> tRoles = tRoleMapper.selectByExample(example);
        PageInfo<TRole> page = new PageInfo<>(tRoles,5);
        return page;

    }
}
