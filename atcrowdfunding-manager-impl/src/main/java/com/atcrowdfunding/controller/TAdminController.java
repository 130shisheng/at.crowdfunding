package com.atcrowdfunding.controller;

import com.atcrowdfunding.bean.TAdmin;
import com.atcrowdfunding.bean.TMenu;
import com.atcrowdfunding.bean.TRole;
import com.atcrowdfunding.bean.TRolePermission;
import com.atcrowdfunding.service.TAdminService;
import com.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.rmi.runtime.Log;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @auther:shisheng
 * @creat 2020-05-12-9:47
 */
@Controller()
public class TAdminController {
    Logger log = LoggerFactory.getLogger(TAdminController.class);
    @Autowired
    TAdminService adminService;
    @Autowired
    TRoleService tRoleService;


    @ResponseBody
    @RequestMapping("/admin/doRemoveAssign")
    public int doRemoveAssign(Integer adminId,Integer[] roleId ){
        int i = tRoleService.doRemoveAssign(adminId,roleId);
        return i;
    }


    @ResponseBody
    @RequestMapping("/admin/doAssign")
    public int doAssign(Integer adminId,Integer[] roleId ){

       int i = tRoleService.doAssign(adminId,roleId);

        return i;
    }


    @RequestMapping(value = "/admin/toAssignRole")
    public String assignRole(String id, Model model) {

        //获取所有的角色集合
        List<TRole> tRoles = tRoleService.getAllRole();
        //获取用户已拥有的角色id集合
        List<Integer> roleIdList = tRoleService.getRoleIdByAdminId(id);

//        获取用户没有的角色的集合
        List<TRole> assignList = new ArrayList<>();
        List<TRole> unAssignList = new ArrayList<>();

        //遍历添加！
        for (TRole trole : tRoles) {

                if (roleIdList.contains(trole.getId())) {
                    assignList.add(trole);

            }else {
                unAssignList.add(trole);
            }
        }
        model.addAttribute("assignList", assignList);
        model.addAttribute("unAssignList", unAssignList);

        System.out.println("断点1");

        return "/admin/assignRole";
    }


    //    分页
    @RequestMapping("/admin/index")
    public String index(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "2") Integer pageSize,
                        Model model,
                        @RequestParam(value = "condition", required = false, defaultValue = "") String condition) {

//        线程绑定，设置分页的数据
        PageHelper.startPage(pageNum, pageSize);

        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("condition", condition);

        PageInfo<TAdmin> page = adminService.listAdminPage(paramMap);

        model.addAttribute("page", page);


        return "/admin/index";
    }


    @PreAuthorize("hasRole('超级管理员')")
    @RequestMapping("/admin/toAdd")
    public String toAdd() {

        return "/admin/add";
    }

    @RequestMapping("/admin/doAdd")
    public String doAdd(TAdmin admin) {

        adminService.saveTadmin(admin);


        return "redirect:/admin/index?pageNum=" + Integer.MAX_VALUE;
    }


    @RequestMapping("/admin/toUpdate")
    public String toUpdate(Integer id, Model model) {
        TAdmin admin = adminService.getTAdminByid(id);
        model.addAttribute("admin", admin);

        return "/admin/Update";
    }

    @RequestMapping("/admin/doUpdate")
    public String doUpdate(TAdmin admin, Integer pageNum) {

        adminService.updateTadmin(admin);


        return "redirect:/admin/index?pageNum=" + pageNum;
    }


    @RequestMapping("/admin/doDelete")
    public String daDelete(Integer id, Integer pageNum) {

        adminService.deleteTadmin(id);
        return "redirect:/admin/index?pageNum=" + pageNum;
    }

    @RequestMapping("/admin/doDeleteBatch")
    public String doDeleteBatch(String ids, Integer pageNum) {
        adminService.deleteTadminMore(ids);
        return "redirect:/admin/index?pageNum=" + pageNum;
    }


}
