package org.monkey.ebill.entity;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class EBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //id
    private String content;  //账单内容
    private String operator;  //操作账号
    private Double fee;  //费用
    private Date createTime;  //插入时间
    private byte status;  //记录状态

    public EBill() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getOperator() {
        return operator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public byte getStatus() {
        return status;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getFee() {
        return fee;
    }

    @Override
    public String toString() {
        return "EBill{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", operator='" + operator + '\'' +
                ", fee=" + fee +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }
}
