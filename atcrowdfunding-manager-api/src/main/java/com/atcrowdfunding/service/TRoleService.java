package com.atcrowdfunding.service;

import com.atcrowdfunding.bean.TRole;
import com.github.pagehelper.PageInfo;

import javax.management.relation.Role;
import java.util.HashMap;
import java.util.List;

/**
 * @auther:shisheng
 * @creat 2020-05-12-21:25
 */
public interface TRoleService {

  

    PageInfo<TRole> listRolePage(HashMap<String, Object> paramMap);

    void saveTRole(TRole role);

    TRole getRoleById(Integer roleId);

    void update(TRole role);

    int deleteById(Integer id);

    List<TRole> getAllRole();

    List<Integer> getRoleIdByAdminId(String id);

    int doAssign(Integer adminId, Integer[] roleId);

    int doRemoveAssign(Integer adminId, Integer[] roleId);

    int doAssignPermissionToRole(Integer roleId, List<Integer> ids);

    void deleteAssignPermissionToRoleByRoleId(Integer roleId);

    List<Integer> listPermissionIdByRoleId(Integer roleId);
}
