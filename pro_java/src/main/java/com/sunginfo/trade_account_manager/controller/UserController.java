package com.sunginfo.trade_account_manager.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sunginfo.trade_account_manager.model.User;
import com.sunginfo.trade_account_manager.service.UserService;
import com.sunginfo.trade_account_manager.utils.JsonData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/user/all")
    @GetMapping
    String userAll() {
        String result = JsonData.getInit();
        try {
            List<User> data = userService.getAllUser();
            if (data == null) {
                result = JsonData.getResult(true, "[]");
            } else {
                result = JsonData.getResult(true, data);
            }
        } catch (Exception e) {
            result = JsonData.getResult(false, e);
        }
        return result;
    }

    @RequestMapping("/user/byname")
    @GetMapping
    String getUserByName(String name) {
        String result = JsonData.getInit();
        try {
            User data = userService.getUser(name);
            if (data == null) {
                result = JsonData.getResult(true, "{}");
            } else {
                result = JsonData.getResult(true, data);
            }
        } catch (Exception e) {
            result = JsonData.getResult(false, e);
        }
        return result;
    }

    @RequestMapping("/user/bytime")
    @GetMapping
    String getUserByTime(String create_time) {
        String result = JsonData.getInit();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date ct = sdf.parse(create_time);
            List<User> data = userService.getUsersByGeTime(ct);
            if (data == null) {
                result = JsonData.getResult(true, "[]");
            } else {
                result = JsonData.getResult(true, data);
            }
        } catch (Exception e) {
            result = JsonData.getResult(false, e);
        }
        return result;
    }

    @RequestMapping("/user/add")
    @PostMapping
    void addUser(@RequestBody Map<String, String> payload) {
        Boolean enable = true;
        if (payload.containsKey("enable")) {
            enable = Boolean.parseBoolean(payload.get("enable"));
        }

        Boolean is_biller = true;
        if (payload.containsKey("is_biller")) {
            is_biller = Boolean.parseBoolean(payload.get("is_biller"));
        }

        Boolean is_trader = true;
        if (payload.containsKey("is_trader")) {
            is_trader = Boolean.parseBoolean(payload.get("is_trader"));
        }

        String superior_trader = "";
        if (payload.containsKey("superior_trader")) {
            superior_trader = payload.get("superior_trader");
        }
        userService.addUser(payload.get("name"), payload.get("pwd"), enable, is_biller, is_trader, superior_trader);
    }

    @RequestMapping("/user/del")
    @PostMapping
    void delUser(@RequestBody Map<String, String> payload) {
        userService.delUser(payload.get("name"));
    }
}