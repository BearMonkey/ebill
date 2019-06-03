package org.monkey.ebill.token;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class Md5TokenGenerator implements  TokenGenerator{
    @Override
    public String generate(String... strings) {
        long timeMillis = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer("");
        for(String str: strings) {
            sb.append(sb);
        }
        sb.append(timeMillis);
        String token = DigestUtils.md5DigestAsHex(sb.toString().getBytes());
        return token;
    }
}
