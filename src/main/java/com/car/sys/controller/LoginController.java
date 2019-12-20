package com.car.sys.controller;

import com.car.sys.constast.SysConstast;
import com.car.sys.domain.User;
import com.car.sys.service.UserService;
import com.car.sys.utils.WebUtils;
import com.car.sys.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("login")
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("toLogin")
    public String toLogin(){
        return "system/main/login";
    }

    @RequestMapping("login")
    public String login(UserVo userVo, Model model){
        User user = userService.login(userVo);
        if(null!=user){
            WebUtils.getHttpSession().setAttribute("user",user);

            return "system/main/index";
        }else{
            model.addAttribute("error", SysConstast.USER_LOGIN_ERROR_MSG);
            return "system/main/login";
        }
    }
}
