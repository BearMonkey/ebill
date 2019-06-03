package org.monkey.ebill.common;

/**
 * 费用校验工具类
 */
public class FeeCheckUtil {

    private static final Double MIN_FEE = 0.0; // 费用最小值
    private static final Double MAX_FEE = Double.MAX_VALUE; //费用最大值

    /**
     * 费用范围校验
     * @param fee 费用
     * @return boolean
     */
    public static boolean feeRangeCheck(Double fee) {
        if (MIN_FEE > fee) {
            return false;
        }
        return true;
    }

    /**
     * 费用完整校验
     * @param fee 费用
     * @return boolean
     */
    public static boolean feeCheck(Double fee) {
        // 费用范围校验
        if (!feeRangeCheck(fee)) {
            return false;
        }
        return true;
    }
}
