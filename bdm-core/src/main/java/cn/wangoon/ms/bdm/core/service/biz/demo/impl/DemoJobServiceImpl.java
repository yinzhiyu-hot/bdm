package cn.wangoon.ms.bdm.core.service.biz.demo.impl;

import cn.hutool.json.JSONUtil;
import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTask;
import cn.wangoon.ms.bdm.core.service.biz.demo.DemoJobService;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Remark
 * @Author YINZHIYU
 * @Date 2022/9/27 10:10
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Service
public class DemoJobServiceImpl implements DemoJobService {
    public boolean demoDo(SyncTask syncTask) {
        System.out.println(JSONUtil.toJsonStr(syncTask));
        return true;
    }
}
