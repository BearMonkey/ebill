package org.monkey.ebill.token;

import java.util.UUID;

/**
 * Token 处理器
 */
public class TokenHandler {

    public static Token createToken(String userId) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Token token = new Token(uuid, userId);
        return token;
    }

}
