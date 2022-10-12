package cn.wangoon.ms.basedata.strategy;

import java.util.Objects;

public class StrategyContext {
    private final StrategyEnum strategyEnum;
    private final Strategy strategy;

    public StrategyContext(StrategyEnum strategyEnum, Strategy strategy) {
        this.strategyEnum = strategyEnum;
        this.strategy = strategy;
    }

    public Strategy getStrategy() {
        return this.strategy;
    }

    public boolean options(StrategyEnum strategyEnum) {
        return Objects.equals(this.strategyEnum, strategyEnum);
    }
}
