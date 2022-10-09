package cn.wangoon.ms.bdm.core.common.enums;

/**
 * @Description 数字枚举
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-10-09 14:37:31
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public enum NumberEnum {
    ZERO("0", 0, 0.00),
    ONE("1", 1, 1.00),
    TWO("2", 2, 2.00),
    THREE("3", 3, 3.00),
    FOUR("4", 4, 4.00),
    FIVE("5", 5, 5.00),
    SIX("6", 6, 6.00),
    SEVEN("7", 7, 7.00),
    EIGHT("8", 8, 8.00),
    NINE("9", 9, 9.00),
    TEN("10", 10, 10.00),
    ELEVEN("11", 11, 11.00),
    TWELVE("12", 12, 12.00),
    THIRTEEN("13", 13, 13.00),
    FOURTEEN("14", 14, 14.00),
    FIFTEEN("15", 15, 15.00),
    SIXTEEN("16", 16, 16.00),
    SEVENTEEN("17", 17, 17.00),
    EIGHTTEEN("18", 18, 18.00),
    NINETEEN("19", 19, 19.00),
    TWENTY("20", 20, 20.00),
    TWENTY_FIVE("25", 25, 25.00),
    TWENTY_EIGHT("28", 28, 28.00),
    TWENTY_NINE("29", 29, 29.00),
    THIRTY("30", 30, 30.00),
    ONE_HUNDRED("100", 100, 100.00),
    TWO_HUNDRED("200", 200, 200.00),
    FIVE_HUNDRED("500", 500, 500.00),
    ONE_THOUSAND("1000", 1000, 1000.00),
    TWO_THOUSAND("2000", 2000, 2000.00),
    THREE_THOUSAND("3000", 3000, 3000.00),
    FIVE_THOUSAND("5000", 5000, 5000.00),
    TEN_THOUSAND("10000", 10000, 10000.00),
    TWENTY_THOUSAND("20000", 20000, 20000.00),
    ONE_HUNDRED_THOUSAND("100000", 100000, 100000.00);

    // 成员变量
    private String numberVal;

    // 删除标记
    private Integer numberInt;

    private Double numberDouble;

    // 构造方法
    NumberEnum(String numberVal, Integer numberInt, Double numberDouble) {
        this.numberVal = numberVal;
        this.numberInt = numberInt;
        this.numberDouble = numberDouble;
    }

    public String getNumberVal() {
        return numberVal;
    }

    public Integer getNumberInt() {
        return numberInt;
    }

    public Double getNumberDouble() {
        return numberDouble;
    }
}
