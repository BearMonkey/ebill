package org.monkey.ebill.controller;

import org.apache.commons.lang3.time.DateUtils;
import org.monkey.ebill.common.DateUtil;
import org.monkey.ebill.common.FeeCheckUtil;
import org.monkey.ebill.entity.EBill;
import org.monkey.ebill.enumerate.EBillStatusEnum;
import org.monkey.ebill.exception.EBillException;
import org.monkey.ebill.service.IEBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ebill")
public class EBillController {

    @Autowired
    private IEBillService ebillService;

    @PostMapping(value = "/accounting")
    public void accounting(@RequestParam("content") String content, @RequestParam("fee") Double fee) {
        System.out.println("Accounting for expenses");
        if (null == content || content.isEmpty()) {
            System.out.println("Accounting failed, content is null!");
            return;
        }

        // 费用校验
        if (!FeeCheckUtil.feeCheck(fee)) {
            System.out.println("Value of fee if valid!");
            return;
        }

        EBill ebill = new EBill();
        ebill.setContent(content);
        ebill.setFee(fee);
        ebill.setCreateTime(new Date());
        ebill.setStatus(EBillStatusEnum.getIndex("active"));

        try{
            ebillService.accounting(ebill);
            System.out.println("Accounting success!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public List<EBill> queryEBill() {
        System.out.println("Query all ebills!");
        return ebillService.queryEBill();
    }

    @PostMapping("/queryByTime")
    public List<EBill> queryByCreateTime(@RequestParam("startTime") Date startTime,
                                         @RequestParam("endTime") Date endTime) {
        // 参数校验
        if (null == startTime || null == endTime) {
            System.out.println("Time parameter cannot be empty.");
            return null;
        }

        Date curTime = new Date();
        long startMillis = startTime.getTime();
        long endMillis = endTime.getTime();
        long curMillis = curTime.getTime();

        if ((startMillis - endMillis) > 0) {
            System.out.println("Start time cannot be greater than end time. Start Time is " + startMillis + ", end time is " + endMillis);
            return null;
        }
        if ((startMillis - curMillis) > DateUtils.MILLIS_PER_DAY) {
            System.out.println("Start time cannot be greater than current time. Start Time is " + startMillis + ", current time is " + curMillis);
            return null;
        }
        if ((endMillis - curMillis) > DateUtils.MILLIS_PER_DAY) {
            System.out.println("End time cannot be less than current time. End Time is " + endMillis + ", current time is " + curMillis);
            return null;
        }


        // 时间校正
        /*
        * curMillis，
        * startMillis，设为当前日期0时，
        * curMillis，设置为当前时间curMillis；
        * */
        if (Math.abs(curMillis - startMillis) < DateUtils.MILLIS_PER_DAY) {
            //开始时间，当日0时
            startTime = DateUtil.dateValue(startTime);
        }
        if (true) {
            Calendar a = Calendar.getInstance();
            a.setTime(startTime);
        }

        try {
            return ebillService.queryByCreateTime(startTime, endTime);
        } catch (EBillException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
}
