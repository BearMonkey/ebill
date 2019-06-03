package org.monkey.ebill.service;

import org.monkey.ebill.entity.EBill;
import org.monkey.ebill.exception.EBillException;

import java.util.Date;
import java.util.List;

public interface IEBillService {

    /**
     * 记账
     * @param ebill
     * @throws Exception
     */
    public void accounting(EBill ebill) throws Exception;

    /**
     * 查看所有账目
     * @return List<EBill>
     */
    public List<EBill> queryEBill();

    /**
     * 按时间范围查询
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @return List<EBill>
     */
    public List<EBill> queryByCreateTime(Date startTime, Date endTime) throws EBillException;

}
