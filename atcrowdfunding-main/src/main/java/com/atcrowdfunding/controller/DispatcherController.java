package com.atcrowdfunding.controller;

import com.atcrowdfunding.bean.TAdmin;
import com.atcrowdfunding.bean.TMenu;
import com.atcrowdfunding.service.TAdminService;
import com.atcrowdfunding.service.TMenuService;
import com.atcrowdfunding.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther:shisheng
 * @creat 2020-05-11-10:04
 */
@Controller
public class DispatcherController {
    Logger log = LoggerFactory.getLogger(DispatcherController.class);
    @Autowired
    TAdminService adminService;
    @Autowired
    TMenuService tMenuservice;


    @RequestMapping("/index")
    public String index() {
        log.debug("跳转到主页面...");

        return "index";
    }

    @RequestMapping("/toLogin")
    public String login() {
        log.debug("跳转到登录页面...");

        return "login";
    }


//    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
//    public String dologin(String loginacct, String userpswd, HttpSession session, Model model) {
//
//        log.debug("开始登录验证...");
//        System.out.println(loginacct);
//        System.out.println(userpswd);
//
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("loginacct", loginacct);
//        paramMap.put("userpswd", userpswd);
//
//        TAdmin admin = null;
//        try {
//            admin = adminService.getTAdminByLogin(paramMap);
//            session.setAttribute(Const.LOGIN_ADMIN, admin);
//            log.debug("登录成功...");
//            return "redirect:/main";
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.debug("登陆失败：{}", e.getMessage());
//            model.addAttribute("message", e.getMessage());
//            return "login";
//        }
//    }

    @RequestMapping("/main")
    public String main(HttpSession session) {
        log.debug("跳转到后台系统main页面...");

        //存放父菜单
        if (session.getAttribute("menuList")==null){
            log.debug("被执行了！！！！！！！！！！！！！！！！！！！！！！！");
            List<TMenu> menuList = tMenuservice.listMenuAll();
            session.setAttribute("menuList", menuList);
        }


        return "main";
    }



    //    退出登录
//    @RequestMapping("/logout")
//    public String logout(HttpSession session) {
//        log.debug("注销系统");
//        if (session != null) {
////            销毁数据
//            session.removeAttribute(Const.LOGIN_ADMIN);
//            //销毁session
//            session.invalidate();
//        }
//
//        return "redirect:/index";
//    }
}
