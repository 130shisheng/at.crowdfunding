package com.atcrowdfunding.component;

import com.atcrowdfunding.bean.TAdmin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

/**
 * @auther:shisheng
 * @creat 2020-05-18-19:06
 */
public class TSecurityAdmin extends User {

    TAdmin admin ;

    public TSecurityAdmin(TAdmin admin,Set<GrantedAuthority> authorities) {
        //super(admin.getLoginacct(),admin.getUserpswd(),authorities);
        super(admin.getLoginacct(),admin.getUserpswd(),true,true,true,true,authorities);
        this.admin = admin;
    }

}
