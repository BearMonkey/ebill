package org.monkey.ebill.enumerate;

public enum EBillStatusEnum {
    ACTIVE("active", (byte) 0),
    FREEZE("freeze", (byte) 1);

    /**
     * 成员变量
     */
    private String name;
    private byte index;

    /**
     * 构造方法
     * @param name
     * @param index
     */
    private EBillStatusEnum(String name, byte index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(byte index) {
        for (EBillStatusEnum statuses : EBillStatusEnum.values()) {
            if (statuses.getIndex() == index) {
                return statuses.name;
            }
        }
        return null;
    }

    // 普通方法
    public static byte getIndex(String name) {
        for (EBillStatusEnum statuses : EBillStatusEnum.values()) {
            if (statuses.getName() == name) {
                return statuses.index;
            }
        }
        return -1; // -1: error status
    }

    public String getName() {
        return name;
    }

    public byte getIndex() {
        return index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(byte index) {
        this.index = index;
    }
}
