package com.atcrowdfunding.service;

import com.atcrowdfunding.bean.TAdmin;
import com.atcrowdfunding.bean.TMenu;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;

/**
 * @auther:shisheng
 * @creat 2020-05-11-21:53
 */
public interface TAdminService {
    TAdmin getTAdminByLogin(HashMap<String, Object> paramMap);


    PageInfo<TAdmin> listAdminPage(HashMap<String, Object> paramMap);

    void saveTadmin(TAdmin admin);

    TAdmin getTAdminByid(Integer id);

    void updateTadmin(TAdmin admin);

    void deleteTadmin(Integer id);

    void deleteTadminMore(String ids);
}
