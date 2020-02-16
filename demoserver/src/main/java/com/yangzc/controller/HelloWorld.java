package com.yangzc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.yangzc.entity.Student;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping(value = "/login")
    @ResponseBody
    public Object login(@RequestParam(name = "username") String userName,
                        @RequestParam(name = "password") String password,
                        @RequestParam(name = "g-recaptcha-response") String recaptchaResponse,
                        @RequestParam(required = false) String callback){
        Map<String,Object> map = new HashMap();
        if(recaptchaResponse==null||recaptchaResponse.equals("")){
            map.put("code",11);
            map.put("msg","验证码为空！");
            return new JSONPObject(callback,map);
        }
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("https://www.recaptcha.net/recaptcha/api/siteverify");
        request.setHeader("Content-type", "application/x-www-form-urlencoded");
        StringEntity body = null;
        HttpResponse response = null;
        JSONObject obj = null;
        //System.out.println("secret=6LeQUNkUAAAAACv1Ei_26r0k7P_361Rx591vLfsx&response=" + recaptchaResponse);
        try {
            body = new StringEntity("secret=6LeQUNkUAAAAACv1Ei_26r0k7P_361Rx591vLfsx&response=" + recaptchaResponse);
            request.setEntity(body);
            response = client.execute(request);
            String data = EntityUtils.toString(response.getEntity());
            obj = JSON.parseObject(data);
            if(!obj.getBoolean("success")){
                map.put("code",17);
                map.put("msg","验证码错误！");
                map.put("recaptcha",obj);
                return new JSONPObject(callback,map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",16);
            map.put("msg","出现异常！");
            return new JSONPObject(callback,map);
        }

        if(userName.equals("admin")&&password.equals("123456")){
            map.put("code",0);
            map.put("msg","登录成功！");
            map.put("recaptcha",obj);
        }else{
            map.put("code",15);
            map.put("msg","账号或密码错误！");
        }
        return new JSONPObject(callback,map);
    }
}
