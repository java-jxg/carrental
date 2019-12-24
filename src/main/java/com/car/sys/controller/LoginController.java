package com.car.sys.controller;

import com.car.sys.constast.SysConstast;
import com.car.sys.domain.User;
import com.car.sys.service.LogInfoService;
import com.car.sys.service.UserService;
import com.car.sys.utils.WebUtils;
import com.car.sys.vo.LogInfoVo;
import com.car.sys.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("login")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private LogInfoService logInfoService;

    @RequestMapping("toLogin")
    public String toLogin(){
        return "system/main/login";
    }

    @RequestMapping("login")
    public String login(UserVo userVo, Model model){
        User user = userService.login(userVo);
        if(null!=user){
            WebUtils.getHttpSession().setAttribute("user",user);

            //记录登录日志
            LogInfoVo logInfoVo = new LogInfoVo();
            logInfoVo.setLoginname(user.getRealname()+"-"+user.getLoginname());
            logInfoVo.setLogintime(new Date());
            logInfoVo.setLoginip(WebUtils.getHttpServletRequest().getRemoteAddr());
            logInfoService.addLogInfo(logInfoVo);
            return "system/main/index";
        }else{
            model.addAttribute("error", SysConstast.USER_LOGIN_ERROR_MSG);
            return "system/main/login";
        }
    }
}
