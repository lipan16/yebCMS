package com.lx.yeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Student{

    @RequestMapping("/select")
    public String SelectStudent(){
        return "select student";
    }
}
