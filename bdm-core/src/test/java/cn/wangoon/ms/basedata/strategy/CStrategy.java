package cn.wangoon.ms.basedata.strategy;

public class CStrategy implements Strategy {
    @Override
    public void say(String message) {
        System.out.println("C说" + message);
    }
}