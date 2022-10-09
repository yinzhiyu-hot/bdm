package cn.wangoon.ms.bdm.web.controller.base;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.wangoon.ms.bdm.core.common.constants.SysBaseConfigConstants;
import cn.wangoon.ms.bdm.core.domain.dto.base.req.SysLogDto;
import cn.wangoon.ms.bdm.core.domain.entity.base.SysLog;
import cn.wangoon.ms.bdm.core.domain.vo.base.BasePageVo;
import cn.wangoon.ms.bdm.core.service.base.SysLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description 日志 管理中心
 * @PackagePath cn.wangoon.controller.LogManagerCenterController
 * @Author YINZHIYU
 * @Date 2020/4/27 18:00
 * @Version 1.0.0.0
 **/
@Api(tags = "日志 管理中心")
@Controller
@RequestMapping(value = "/logs")
public class LogManagerCenterController {

    @Resource
    private SysLogService sysLogService;

    /**
     * @Description 日志页面
     * @Remark
     * @Params ==>
     * @Param model
     * @Return java.lang.String
     * @Date 2020/11/17 14:06
     * @Auther YINZHIYU
     */
    @ApiOperation(value = "日志页面", notes = "日志页面", httpMethod = "GET")
    @RequestMapping(value = "/pages")
    public String pages(Model model) {
        List<String> queryDateList = Lists.newArrayList();
        for (int i = 0; i < Math.abs(SysBaseConfigConstants.LOG_CARRY_FORWARD_DAY_OFFSET); i++) {
            String queryDate = DateUtil.format(DateUtil.offsetDay(DateUtil.date(), i * -1), DatePattern.NORM_DATE_PATTERN);
            queryDateList.add(queryDate);
        }
        model.addAttribute("queryDateList", queryDateList);
        return "logs_manager";
    }

    /**
     * @Description 日志
     * @Remark
     * @Params ==>
     * @Param sysLogDto
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     * @Date 2020/11/17 14:06
     * @Auther YINZHIYU
     */
    @ApiOperation(value = "日志查询", notes = "日志查询", httpMethod = "GET")
    @RequestMapping(value = "/listPage")
    @ResponseBody
    public Map<String, Object> listPage(BasePageVo basePageVO, SysLogDto sysLogDto) {
        Page<SysLog> page = new Page<>(basePageVO.getPageNumber(), basePageVO.getPageSize());
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(sysLogDto.getQueryDate())) {
            queryWrapper.eq(SysLog.COL_RECORD_DATE, sysLogDto.getQueryDate());
        }
        if (ObjectUtil.isNotEmpty(sysLogDto.getBusinessType())) {
            queryWrapper.eq(SysLog.COL_BUSINESS_TYPE, sysLogDto.getBusinessType());
        }
        if (ObjectUtil.isNotEmpty(sysLogDto.getBusinessKey())) {
            queryWrapper.eq(SysLog.COL_BUSINESS_KEY, sysLogDto.getBusinessKey());
        }
        queryWrapper.orderByDesc(SysLog.COL_TS);

        //queryWrapper.orderByDesc(SyncTask.COL_ID);//如果是通过级联查询，无法使用queryWrapper设置的排序规则
        IPage<SysLog> pages = sysLogService.page(page, queryWrapper);

        //bootstrap-table要求服务器返回的json须包含：total，rows,采取客户端分页，服务端提供全部数据
        Map<String, Object> map = Maps.newHashMap();
        map.put("total", pages.getTotal());
        map.put("rows", pages.getRecords());
        return map;
    }
}
