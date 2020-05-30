package com.atcrowdfunding.service.impl;

import com.atcrowdfunding.bean.TMenu;
import com.atcrowdfunding.bean.TMenuExample;
import com.atcrowdfunding.mapper.TMenuMapper;
import com.atcrowdfunding.service.TMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @auther:shisheng
 * @creat 2020-05-12-8:01
 */
@Service
public class TMenuServiceImpl implements TMenuService {
    @Autowired
    TMenuMapper tMenuMapper;

    @Override
    public List<TMenu> listMenuAll() {


        List<TMenu> tMenus = tMenuMapper.selectByExample(null);

        //创建父菜单list
        List<TMenu> parentMenus = new ArrayList<>();

        // 创建父亲菜单map 方便查找
        HashMap<Integer, TMenu> parentMap = new HashMap<>();
        //装配父亲菜单map
        for (TMenu menu : tMenus) {
            if (menu.getPid().equals(0)) {
                parentMap.put(menu.getId(), menu);
                parentMenus.add(menu);
            }
        }
        //给每个父菜单配置child菜单
        for (TMenu menu : tMenus) {
            if (!menu.getPid().equals(0)) {
                Integer pid = menu.getPid();
                TMenu parentMenu = parentMap.get(pid);
                parentMenu.getChildMenus().add(menu);
            }
        }

        System.out.println(parentMenus + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return parentMenus;
    }

    @Override
    public List<TMenu> selectAll() {
        List<TMenu> tMenus = tMenuMapper.selectByExample(null);
        return tMenus;
    }

    @Override
    public int savetMenu(TMenu tMenu) {
        int i = tMenuMapper.insertSelective(tMenu);
        return i;
    }

    @Override
    public TMenu gettMenuById(Integer id) {
        TMenu tMenu = tMenuMapper.selectByPrimaryKey(id);
        return tMenu;
    }

    @Override
    public int doUpdateTmenu(TMenu tMenu) {
        int i = tMenuMapper.updateByPrimaryKeySelective(tMenu);
        return i;
    }

    @Override
    public int dodeleteTmenuById(Integer id) {
        int i = tMenuMapper.deleteByPrimaryKey(id);
        return i;
    }
}
