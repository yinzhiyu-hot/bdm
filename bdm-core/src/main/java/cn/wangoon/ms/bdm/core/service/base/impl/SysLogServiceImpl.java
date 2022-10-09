package cn.wangoon.ms.bdm.core.service.base.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.wangoon.ms.bdm.core.common.constants.SysBaseConfigConstants;
import cn.wangoon.ms.bdm.core.dao.mapper.base.SysLogMapper;
import cn.wangoon.ms.bdm.core.domain.entity.base.SysLog;
import cn.wangoon.ms.bdm.core.service.base.SysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 日志记录
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-09-27 13:52:38
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Service
@Slf4j
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public void recordLog(SysLog sysLog) {

        ThreadUtil.execAsync(() -> {
            try {
                baseMapper.insert(sysLog);
            } catch (Exception ex) {
                log.error("记录日志异常,{}", sysLog, ex);
            }
        });
    }

    @Override
    public boolean batchInsert(List<SysLog> list) {
        return SqlHelper.retBool(baseMapper.batchInsert(list));
    }

    @Override
    public void dataCarryForward() {
        String recordDate = DateUtil.format(DateUtil.offsetDay(DateUtil.date(), SysBaseConfigConstants.LOG_CARRY_FORWARD_DAY_OFFSET), DatePattern.NORM_DATE_PATTERN);
        int count = baseMapper.dataCarryForward(recordDate);
        recordLog(new SysLog("dataCarryForward", String.format("本次结转sys_log日志表数据记录 %s 条", count)));
    }
}
