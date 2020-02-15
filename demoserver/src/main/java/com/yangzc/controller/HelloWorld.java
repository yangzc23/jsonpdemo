package com.yangzc.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.yangzc.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/hello")
public class HelloWorld {

    @RequestMapping("/welcome")
    public String sayHi(Model model){
        model.addAttribute("message", "欢迎使用Spring MVC!");
        return "hello";
    }

    @RequestMapping("/detail/{sno}")
    @ResponseBody
    public Object detail(@PathVariable Integer sno, @RequestParam(required = false) String callback){
        Student stu = new Student(sno,"刘亦菲",new Date(),false,null);
        if(callback==null||callback.equals("")){
            return stu;
        }
        //
        //MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(stu);
        //mappingJacksonValue.setValue(callback);
        return new JSONPObject(callback,stu);
    }
}
