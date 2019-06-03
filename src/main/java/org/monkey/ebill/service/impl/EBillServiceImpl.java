package org.monkey.ebill.service.impl;

import org.monkey.ebill.dao.IEBillDao;
import org.monkey.ebill.entity.EBill;
import org.monkey.ebill.exception.EBillException;
import org.monkey.ebill.service.IEBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service("ebillService")
public class EBillServiceImpl implements IEBillService {

    @Autowired
    private IEBillDao ebillDao;

    @Override
    @Transactional // 事务回滚
    public void accounting(EBill ebill) throws Exception {
        try {
            ebillDao.save(ebill);
        } catch (RuntimeException e) {
            throw new Exception("RuntimeException ocurs! Automatic rollback");
        }catch (Exception e) {
            throw new Exception("Exception ocurs when insert to database!");
        }
    }

    @Override
    public List<EBill> queryEBill() {
        return ebillDao.findAll();
    }

    @Override
    public List<EBill> queryByCreateTime(Date startTime, Date endTime) throws EBillException {
        try {
            return ebillDao.findByCreateTimeBetween(startTime, endTime);
        } catch (Exception e) {
            throw new EBillException("Exception ocurs when query database by create time");
        }
    }
}
