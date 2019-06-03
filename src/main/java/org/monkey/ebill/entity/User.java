package org.monkey.ebill.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class User {
    @Id
    private String account;  //账号
    private String nickName;  //名称
    private String password;  //密码
    private Date createTime;  //创建时间
    private byte status;  //状态

    public User() {
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public byte getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }
}
