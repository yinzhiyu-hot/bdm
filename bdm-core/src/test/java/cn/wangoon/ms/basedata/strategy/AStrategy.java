package cn.wangoon.ms.basedata.strategy;

public class AStrategy implements Strategy {
    @Override
    public void say(String message) {
        System.out.println("A说" + message);
    }
}