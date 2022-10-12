package cn.wangoon.ms.basedata.strategy;

public class BStrategy implements Strategy {
    @Override
    public void say(String message) {
        System.out.println("B说" + message);
    }
}