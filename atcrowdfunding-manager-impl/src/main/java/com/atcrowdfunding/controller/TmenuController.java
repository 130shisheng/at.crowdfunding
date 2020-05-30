package com.atcrowdfunding.controller;

import com.atcrowdfunding.bean.TMenu;
import com.atcrowdfunding.service.TMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @auther:shisheng
 * @creat 2020-05-14-15:12
 */
@Controller
public class TmenuController {
    @Autowired
    TMenuService tMenuService;

    @RequestMapping(value = "/menu/index")
    public String index(){


        return "/menu/index";
    }

    @ResponseBody
    @RequestMapping("menu/loadTree")
    public  List<TMenu> loadTree(){
      List<TMenu> tMenus = tMenuService.selectAll();
      return  tMenus;
    }

    @ResponseBody
    @RequestMapping("menu/doADD")
    public int doADD(TMenu tMenu){
        int i = tMenuService.savetMenu(tMenu);
        return i;
    }

    @ResponseBody
    @RequestMapping("/menu/getTmenuById")
    public TMenu getTmenuById(Integer id){
      TMenu tMenu= tMenuService.gettMenuById(id);
      return tMenu;
    }

    @ResponseBody
    @RequestMapping("/menu/doUpdate")
    public int doUpdate(TMenu tMenu){
       int i = tMenuService.doUpdateTmenu(tMenu);
        return i;
    }

    @ResponseBody
    @RequestMapping("/menu/doDelete")
    public int doDelete(Integer id){
        int i = tMenuService.dodeleteTmenuById(id);
        return i;
    }

}
