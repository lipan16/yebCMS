package com.lx.yeb.api;

import com.lx.yeb.bean.Navigation;
import com.lx.yeb.bean.User;
import com.lx.yeb.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class VueService{

    @Resource
    private LoginService loginService;

    @RequestMapping(path="/login",method = RequestMethod.GET)
    public Object login(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        log.info("[前端接口调用]: /api/login");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        log.info(username, password);
        boolean verifying = loginService.verifyLogin(username, password);
        if(verifying){
            return new User("lipan", "lipan");
        }
        return null;

    }

    @RequestMapping("/nav")
    public Object navigationBar(){
        log.error("[前端接口调用]: /api/nav");
        List<Navigation> nav = new ArrayList<>();

        nav.add(new Navigation("/", "登录", "() => import('../views/login/login.vue')", true, "", null));

        List<Navigation> children = new ArrayList<>();
        Navigation
                nav1 =
                new Navigation("/home/test1", "选项1", "() => import('../views/test/test1.vue')", false, "", null);
        Navigation
                nav2 =
                new Navigation("/home/test1", "选项2", "() => import('../views/test/test1.vue')", false, "", null);
        children.add(nav1);
        children.add(nav2);

        nav.add(new Navigation("/home", "导航1", "() => import('../views/home.vue')", false, "el-icon-location", children));

        nav.add(new Navigation("/home", "导航2", "() => import('../views/home.vue')", false, "el-icon-menu", null));
        return nav;
    }
}
