package org.monkey.ebill.interceptor;


import com.alibaba.fastjson.JSONObject;
import org.monkey.ebill.Constents.ConstantKit;
import org.monkey.ebill.annotation.AuthToken;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * Token验证拦截器，添加@AuthToken注解，会进行Token验证
 * @author monkey
 */
public class TokenInterceptor implements HandlerInterceptor {
    //存放鉴权信息的Header名称，默认是Authorization
    private String httpHeaderName = "Authorization";

    //鉴权失败后返回的错误信息，默认为401 unauthorized
    private String unauthorizedErrorMessage = "401 unauthorized";

    //鉴权失败后返回的HTTP错误码，默认为401
    private int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

    /**
     * 存放登录用户模型Key的Request Key
     */
    public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle.");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method        = handlerMethod.getMethod();
        // 如果打上了AuthToken注解则需要验证token
        if (method.getAnnotation(AuthToken.class) != null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {

            String token = request.getHeader(httpHeaderName);
            System.out.println("token is " + token);
            String username = "";
            Jedis jedis = new Jedis("localhost", 6379);
            if (token != null && token.length() != 0) {

                username = jedis.get(token);
                System.out.println("username is " + username);
            }
            if (username != null && !username.trim().equals("")) {
                //log.info("token birth time is: {}",jedis.get(username+token));
                System.out.println("token generate time is " + jedis.get(username + token));
                Long tokeBirthTime = Long.valueOf(jedis.get(token + username));
                //log.info("token Birth time is: {}", tokeBirthTime);
                System.out.println("token generate time is " + tokeBirthTime);
                Long diff = System.currentTimeMillis() - tokeBirthTime;
                //log.info("token is exist : {} ms", diff);
                System.out.println("token is exist: " + diff + "ms.");
                if (diff > ConstantKit.TOKEN_RESET_TIME) {
                    jedis.expire(username, ConstantKit.TOKEN_EXPIRE_TIME);
                    jedis.expire(token, ConstantKit.TOKEN_EXPIRE_TIME);
                    //log.info("Reset expire time success!");
                    System.out.println("Reset expire time success!");
                    Long newBirthTime = System.currentTimeMillis();
                    jedis.set(token + username, newBirthTime.toString());
                }

                //用完关闭
                jedis.close();
                request.setAttribute(REQUEST_CURRENT_KEY, username);
                return true;


            } else {
                JSONObject jsonObject = new JSONObject();

                PrintWriter out = null;
                try {
                    response.setStatus(unauthorizedErrorCode);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                    jsonObject.put("code", ((HttpServletResponse) response).getStatus());
                    jsonObject.put("message", HttpStatus.UNAUTHORIZED);
                    out = response.getWriter();
                    out.println(jsonObject);

                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != out) {
                        out.flush();
                        out.close();
                    }
                }

            }

        }

        request.setAttribute(REQUEST_CURRENT_KEY, null);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
