package com.lx.yeb.api;

import com.lx.yeb.bean.Navigation;
import com.lx.yeb.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class VueService{
    Logger logger = LoggerFactory.getLogger(VueService.class);

    @RequestMapping("/login")
    public Object login(){
        logger.info("[前端接口调用]: /api/login");
        return new User("lp", "lipan", "1234");
    }

    @RequestMapping("/nav")
    public Object navigationBar(){

        logger.error("[前端接口调用]: /api/nav");
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
