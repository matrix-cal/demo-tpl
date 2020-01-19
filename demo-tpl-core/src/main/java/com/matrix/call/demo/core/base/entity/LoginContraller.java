package com.matrix.call.demo.core.base.entity;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;

/**
 *
 */
//@RestController
@Controller
@RequestMapping("/test")
public class LoginContraller {


    @RequestMapping("/loginStr")
    public String sayHello() {
        return "Hello,World! " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
    }



    @RequestMapping(value = "/html",method = RequestMethod.GET)
    public ModelAndView showlist(){
        ModelAndView mv =  new ModelAndView("index");
        return mv;
    }

    @RequestMapping(value = "/heheda",method = RequestMethod.GET)
//@ResponseBody
    public String showString(){
        return "index";
    }

    @RequestMapping("/hello")
    public String helloHtml(HashMap<String, Object> map) {
        map.put("hello", "欢迎进入HTML页面");
        return "/index";
    }



    @RequestMapping("/hello2")
    public String hello2Html(HashMap<String, Object> map) {
        map.put("hello", "欢迎进入HTML页面");
        return "/index";
    }




}
