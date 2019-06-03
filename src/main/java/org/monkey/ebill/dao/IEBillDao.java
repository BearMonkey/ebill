package org.monkey.ebill.dao;

import org.monkey.ebill.entity.EBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * 电子账单数据库操作接口
 */
public interface IEBillDao extends JpaRepository<EBill, Integer> {

    /**
     * 按时间范围查询
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @return List<EBill>
     */
    public List<EBill> findByCreateTimeBetween(Date startTime, Date endTime);
}
