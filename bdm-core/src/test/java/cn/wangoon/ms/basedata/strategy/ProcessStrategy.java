package cn.wangoon.ms.basedata.strategy;

import cn.hutool.core.util.ObjectUtil;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessStrategy {
    //private static final List<StrategyContext> CONTEXTS = new ArrayList();
    private static final Map<StrategyEnum, Strategy> CONTEXTS_MAP = new ConcurrentHashMap<>();

    //静态代码块,先加载所有的策略
    static {
        //CONTEXTS.add(new StrategyContext(StrategyEnum.A, new AStrategy()));
        //CONTEXTS.add(new StrategyContext(StrategyEnum.B, new BStrategy()));
        //CONTEXTS.add(new StrategyContext(StrategyEnum.C, new CStrategy()));
        // TODO 待扩展策略

        //备注
        CONTEXTS_MAP.put(StrategyEnum.A, new AStrategy());
        CONTEXTS_MAP.put(StrategyEnum.B, new BStrategy());
        //CONTEXTS_MAP.put(StrategyEnum.C, new StrategyContext(new CStrategy()));
        //TODO 待扩展策略
    }

    public void say(StrategyEnum strategyEnum, String message) {

        //Strategy strategy = null;
        //for (StrategyContext context : CONTEXTS) {
        //    if (context.options(strategyEnum)) {
        //        strategy = context.getStrategy();
        //        break;
        //    }
        //}
        //if (ObjectUtil.isEmpty(strategy)) {
        //    System.out.println("缺乏ProcessStrategy策略");
        //    return;
        //}

        Strategy strategy = Optional.ofNullable(CONTEXTS_MAP.get(strategyEnum)).orElse(null);
        if (ObjectUtil.isEmpty(strategy)) {
            System.out.println(strategyEnum.name() + " 缺乏 ProcessStrategy 策略");
            return;
        }

        strategy.say(message);
    }
}