package cn.wangoon.ms.bdm.core.common.enums;

/**
 * @Description 字母枚举
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-10-09 14:37:46
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
public enum LetterEnum {
    A("A", 'A', "a", 'a'),
    B("B", 'B', "b", 'b'),
    C("C", 'C', "c", 'c'),
    D("D", 'D', "d", 'd'),
    E("E", 'E', "e", 'e'),
    F("F", 'F', "f", 'f'),
    G("G", 'G', "g", 'g'),
    H("H", 'H', "h", 'h'),
    I("I", 'I', "i", 'i'),
    J("J", 'J', "j", 'j'),
    K("K", 'K', "k", 'k'),
    L("L", 'L', "l", 'l'),
    M("M", 'M', "m", 'm'),
    N("N", 'N', "n", 'n'),
    O("O", 'O', "o", 'o'),
    P("P", 'P', "p", 'p'),
    Q("Q", 'Q', "q", 'q'),
    R("R", 'R', "r", 'r'),
    S("S", 'S', "s", 's'),
    T("T", 'T', "t", 't'),
    U("U", 'U', "u", 'u'),
    V("V", 'V', "v", 'v'),
    W("W", 'W', "w", 'w'),
    X("X", 'X', "x", 'x'),
    Y("Y", 'Y', "y", 'y'),
    Z("Z", 'Z', "z", 'z');

    private String letterUpper;

    private String letterLower;

    private Character letterCharUpper;

    private Character letterCharLower;


    // 构造方法
    LetterEnum(String letterUpper, Character letterCharUpper, String letterLower, Character letterCharLower) {
        this.letterUpper = letterUpper;
        this.letterCharUpper = letterCharUpper;
        this.letterUpper = letterUpper;
        this.letterCharLower = letterCharLower;
    }

    public String getLetterUpper() {
        return letterUpper;
    }

    public String getLetterLower() {
        return letterLower;
    }

    public Character getLetterCharUpper() {
        return letterCharUpper;
    }

    public Character getLetterCharLower() {
        return letterCharLower;
    }
}
