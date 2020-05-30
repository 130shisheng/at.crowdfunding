package com.atcrowdfunding.service;

import com.atcrowdfunding.bean.TMenu;

import java.util.List;

/**
 * @auther:shisheng
 * @creat 2020-05-12-8:00
 */
public interface TMenuService {


    /**
     * 查询父菜单里有子菜单
     * @return
     */
    List<TMenu> listMenuAll();

    /**
     * 直接查询全部菜单
     * @return
     */
    List<TMenu> selectAll();

    int savetMenu(TMenu tMenu);

    TMenu gettMenuById(Integer id);

    int doUpdateTmenu(TMenu tMenu);

    int dodeleteTmenuById(Integer id);
}
