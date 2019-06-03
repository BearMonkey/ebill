package org.monkey.ebill.controller;

import com.alibaba.fastjson.JSONObject;
import org.monkey.ebill.Constents.ConstantKit;
import org.monkey.ebill.Constents.RedisConstants;
import org.monkey.ebill.entity.User;
import org.monkey.ebill.exception.UserException;
import org.monkey.ebill.service.IUserService;
import org.monkey.ebill.token.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private TokenGenerator md5TokenGenerator; // Token生成器

    @PostMapping(value = "/add")
    public void addUser(@RequestParam("account") String account, @RequestParam("password") String password) {
        System.out.println("Add a new user!");
        if (null == account || account.isEmpty() || null == password || password.isEmpty()) {
            System.out.println("Add faild, the account or password is empty!");
            return;
        }
        try {
            User user = new User();
            user.setAccount(account);
            user.setPassword(password);
            user.setCreateTime(new Date());
            user.setStatus((byte) 0);
            userService.addUser(user);
        } catch (UserException e) {
            System.out.println("Add faild, " + e.getMessage());
        }
    }

    @GetMapping("/allUser")
    public List<User> queryUsers() {
        return userService.queryAll();
    }

    @PostMapping(value = "/login")
    public JSONObject login(@RequestParam("account") String account, @RequestParam("password") String password) {
        JSONObject result = new JSONObject();
        if (null == account || account.isEmpty() || null == password || password.isEmpty()) {
            System.out.println("Login faild, the account or password is empty!");
            result.put("message", "the account or password is empty");
            result.put("status", "failed");
            result.put("code", 401);

        } else {
            List<User> users = userService.queryUser(account, password);
            if (null == users || 1 != users.size()) {
                result.put("message", "account or password is error.");
                result.put("status", "failed");
                result.put("code", 401);
            } else {
                User existUser = users.get(0);
                if (null != existUser) {
                    //生成token，存入Redis
                    String uuid = UUID.randomUUID().toString();
                    String token = md5TokenGenerator.generate(account, uuid); // token: account+uuid
                    Jedis jedis = new Jedis(RedisConstants.REDIS_HOST, RedisConstants.REDIS_POST);
                    jedis.set(account, token);
                    jedis.expire(account, ConstantKit.TOKEN_EXPIRE_TIME);
                    jedis.set(token, account);
                    jedis.expire(token, ConstantKit.TOKEN_EXPIRE_TIME);
                    long currentTimeMillis = System.currentTimeMillis();
                    jedis.set(token + account, String.valueOf(currentTimeMillis));
                    jedis.close(); // 关闭
                    result.put("message", "login passed!");
                    result.put("status", "success");
                    result.put("code", 200);
                } else {
                    result.put("message", "account or password is error.");
                    result.put("status", "failed");
                    result.put("code", 401);
                }
            }
        }
        return result;
    }
}
