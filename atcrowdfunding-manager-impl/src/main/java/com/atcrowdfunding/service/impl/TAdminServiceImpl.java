package com.atcrowdfunding.service.impl;

import com.atcrowdfunding.bean.TAdmin;
import com.atcrowdfunding.bean.TAdminExample;
import com.atcrowdfunding.bean.TMenu;
import com.atcrowdfunding.exception.LoginException;
import com.atcrowdfunding.mapper.TAdminMapper;
import com.atcrowdfunding.service.TAdminService;
import com.atcrowdfunding.util.AppDateUtils;
import com.atcrowdfunding.util.Const;
import com.atcrowdfunding.util.MD5Util;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.javassist.runtime.Desc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @auther:shisheng
 * @creat 2020-05-11-21:54
 */
@Service
public class TAdminServiceImpl implements TAdminService {
    @Autowired
    TAdminMapper adminMapper;

    @Override
    public TAdmin getTAdminByLogin(HashMap<String, Object> paramMap) {
//1.密码加密

        //2.查询用户
        String loginacct = (String) paramMap.get("loginacct");
        String userpswd = (String) paramMap.get("userpswd");

        TAdminExample tAdminExample = new TAdminExample();
        //给tAdminExample增加条件 根据用户名来查询
        tAdminExample.createCriteria().andLoginacctEqualTo(loginacct);

        List<TAdmin> tAdmins = adminMapper.selectByExample(tAdminExample);
        //3.用户不存在
        if (tAdmins.size() == 0 || tAdmins == null) {
            throw new LoginException(Const.LOGIN_LOGINACCT_ERROR);
        }
        //4.密码不正确


        TAdmin tAdmin = tAdmins.get(0);
        if (!tAdmin.getUserpswd().equals(MD5Util.digest(userpswd))) {
            throw new LoginException(Const.LOGIN_USERPSWD_ERROR);
        }
        //5.返回用户信息
        return tAdmin;
    }

    //    分页
    @Override
    public PageInfo<TAdmin> listAdminPage(HashMap<String, Object> paramMap) {

        TAdminExample example = new TAdminExample();
        //根据创建时间进行降序排列asc
        example.setOrderByClause("createtime asc");

        String condition = (String) paramMap.get("condition");

        example.createCriteria().andLoginacctLike("%"+condition+"%");

        TAdminExample.Criteria criteria2 = example.createCriteria();
        TAdminExample.Criteria criteria3 = example.createCriteria();

        criteria2.andUsernameLike("%"+condition+"%");
        criteria3.andEmailLike("%"+condition+"%");

        example.or(criteria2);
        example.or(criteria3);


        List<TAdmin> Admins = adminMapper.selectByExample(example);

        PageInfo<TAdmin> page = new PageInfo<>(Admins, 5);


        return page;
    }

    @Override
    public void saveTadmin(TAdmin admin) {
        //设置初始化密码
        admin.setUserpswd(MD5Util.digest(Const.DEFAULT_USERPSWD));
        //设置创建时间
        admin.setCreatetime(AppDateUtils.getFormatTime());

        adminMapper.insertSelective(admin);


    }

    @Override
    public TAdmin getTAdminByid(Integer id) {
        TAdmin admin = adminMapper.selectByPrimaryKey(id);
        return admin;

    }

    @Override
    public void updateTadmin(TAdmin admin) {
//        根据主键选择性更新数据
        adminMapper.updateByPrimaryKeySelective(admin);

    }

    @Override
    public void deleteTadmin(Integer id) {
        adminMapper.deleteByPrimaryKey(id);


    }

    @Override
    public void deleteTadminMore(String ids) {
        adminMapper.deleteTadminMore(ids);

    }
}
