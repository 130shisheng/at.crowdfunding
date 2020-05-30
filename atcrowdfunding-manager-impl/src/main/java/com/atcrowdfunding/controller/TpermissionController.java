package com.atcrowdfunding.controller;

import com.atcrowdfunding.bean.TPermission;
import com.atcrowdfunding.service.TPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @auther:shisheng
 * @creat 2020-05-14-17:34
 */
@Controller
public class TpermissionController {
    @Autowired
    TPermissionService tPermissionService;


   @RequestMapping("/permission/index")
    public String index() {
        return "permission/index";
    }

    @ResponseBody
    @RequestMapping("/permission/loadTree")
    public List<TPermission> loadTree() {
        List<TPermission> list = tPermissionService.getTPermissionList();
        return list;
    }

    @ResponseBody
    @RequestMapping("/permission/doADD")
    public int doADD(TPermission tPermission){
       int i = tPermissionService.saveTPermission(tPermission);
       return i;
    }

    @ResponseBody
    @RequestMapping("/permission/getTPermissionById")
    public TPermission getTPermissionById(Integer id){
       TPermission tPermission = tPermissionService.getTPermissionById(id);
       return tPermission;
    }

    @ResponseBody
    @RequestMapping("/permission/doUpdate")
    public int doUpdate(TPermission tPermission){
       int i = tPermissionService.doUpdate(tPermission);
       return i;
    }
    @ResponseBody
    @RequestMapping("/permission/doDelete")
    public int doDelete(Integer id){
       int i = tPermissionService.doDelete(id);
       return i;
    }

}
