package cn.wangoon.ms.bdm.web.controller.biz.demo;

import cn.wangoon.ms.bdm.core.domain.dto.biz.demo.req.DemoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description Demo管理
 * @Remark
 * @Author YINZHIYU
 * @Date 2022-10-01 03:08:11
 * @Version 1.0.0.0
 * @Postscript 人生得意须尽欢
 **/
@Api(tags = "demo")
@RestController
@RequestMapping(value = "/demo")
public class DemoController {

    @ApiOperation(value = "Demo信息", notes = "Demo信息", httpMethod = "GET")
    @RequestMapping(value = "/demoInfo")
    public String demoInfo() {
        return "demoInfo";
    }

    @ApiOperation(value = "Demo信息", notes = "Demo信息", httpMethod = "POST")
    @RequestMapping(value = "/addDemoInfo")
    public String addDemoInfo(@Validated DemoDto demoDto) {
        return "addDemoInfo";
    }
}
