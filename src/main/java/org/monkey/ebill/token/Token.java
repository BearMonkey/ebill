package org.monkey.ebill.token;

public class Token {
    private String uuid;
    private String userId;

    public Token(String uuid, String userId) {
        this.uuid = uuid;
        this.userId = userId;
    }


    public Token getToken(String uuid, String userId) {
        return new Token(uuid, userId);
    }

    @Override
    public String toString() {
        return uuid + "_" + userId;
    }
}
