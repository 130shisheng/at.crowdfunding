package com.atcrowdfunding.controller;

import com.atcrowdfunding.bean.TAdmin;
import com.atcrowdfunding.bean.TRole;
import com.atcrowdfunding.service.TRoleService;
import com.atcrowdfunding.util.Datas;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.relation.Role;
import java.util.HashMap;
import java.util.List;

/**
 * @auther:shisheng
 * @creat 2020-05-12-21:19
 */
@Controller
public class TRoleController {
    Logger log = LoggerFactory.getLogger(TRoleController.class);

    @Autowired
    TRoleService tRoleService;


    @ResponseBody
    @RequestMapping("/role/listPermissionIdByRoleId")
    public  List<Integer> listPermissionIdByRoleId(Integer roleId){
        List<Integer> pIds = tRoleService.listPermissionIdByRoleId(roleId);
        return pIds;

    }

    @ResponseBody
    @RequestMapping("/role/doAssignPermissionToRole")
    public int doAssignPermissionToRole(Integer roleId, Datas datas){
        tRoleService.deleteAssignPermissionToRoleByRoleId(roleId);
        List<Integer> ids = datas.getIds();
        int i = tRoleService.doAssignPermissionToRole(roleId,ids);
        return i;
    }


    @RequestMapping("/role/index")
    public String index() {

        return "/role/index";
    }


    @ResponseBody
    @RequestMapping("/role/loadData")
    public PageInfo<TRole> index(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "pageSize", required = false, defaultValue = "2") Integer pageSize,
                                 @RequestParam(value = "condition", required = false, defaultValue = "") String condition) {

        PageHelper.startPage(pageNum, pageSize);

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("condition", condition);

        PageInfo<TRole> page = tRoleService.listRolePage(paramMap);

        return page;
    }

    @PreAuthorize("hasRole('超级管理员')")
    @ResponseBody
    @RequestMapping("/role/doAdd")
    public String doAdd(TRole role) {
        tRoleService.saveTRole(role);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/role/getRoleById")
    public TRole getRoleById(Integer id){
        TRole role =  tRoleService.getRoleById(id);
      return role;
    };


    @ResponseBody
    @RequestMapping("/role/doUpdate")
    public String update(TRole role){
        tRoleService.update(role);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("role/doDelete")
    public int doDelete(Integer id){
        int i = tRoleService.deleteById(id);
        return i;

    }


}
