package cn.wangoon.ms.bdm.web.controller.base;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.wangoon.biz.common.Result;
import cn.wangoon.ms.bdm.core.common.builder.SyncTaskBuilder;
import cn.wangoon.ms.bdm.core.common.constants.BdmConstants;
import cn.wangoon.ms.bdm.core.common.enums.SyncTaskStatusEnum;
import cn.wangoon.ms.bdm.core.common.enums.SyncTaskTypeEnum;
import cn.wangoon.ms.bdm.core.domain.dto.base.req.SyncTaskChartDto;
import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTask;
import cn.wangoon.ms.bdm.core.domain.entity.base.SyncTaskData;
import cn.wangoon.ms.bdm.core.domain.vo.base.BasePageVo;
import cn.wangoon.ms.bdm.core.domain.vo.base.SyncTaskChartVo;
import cn.wangoon.ms.bdm.core.service.base.SyncTaskDataService;
import cn.wangoon.ms.bdm.core.service.base.SyncTaskService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description Task 管理中心
 * @PackagePath cn.wangoon.controller.TasksManagerCenterController
 * @Author YINZHIYU
 * @Date 2020/4/27 18:00
 * @Version 1.0.0.0
 **/
@SuppressWarnings("unchecked")
@Api(tags = "Task 管理中心")
@Controller
@RequestMapping(value = "/tasks")
public class TasksManagerCenterController {

    @Resource
    private SyncTaskBuilder syncTaskBuilder;

    @Resource
    private SyncTaskService syncTaskService;

    @Resource
    private SyncTaskDataService syncTaskDataService;

    /**
     * @Description Task任务页面
     * @Remark
     * @Params ==>
     * @Return java.lang.String
     * @Date 2021-12-28 16:27:50
     * @Auther YINZHIYU
     */
    @ApiOperation(value = "Task任务页面", notes = "Task任务页面", httpMethod = "GET")
    @RequestMapping(value = "/pages")
    public String pages(Model model) {
        Set<String> taskTypeList = new HashSet<>();
        for (SyncTaskTypeEnum value : SyncTaskTypeEnum.values()) {
            taskTypeList.add(value.getSyncTaskType());
        }
        model.addAttribute("taskTypeList", new ArrayList<>(taskTypeList));
        return "tasks_manager";
    }

    /**
     * @Description 分页
     * @Remark
     * @Params ==>
     * @Param basePageVO
     * @Param syncTask
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     * @Date 2021-12-28 16:27:55
     * @Auther YINZHIYU
     */
    @ApiOperation(value = "Task任务信息分页", notes = "Task任务信息分页", httpMethod = "GET")
    @RequestMapping(value = "/listPage")
    @ResponseBody
    public Map<String, Object> listPage(BasePageVo basePageVO, SyncTask syncTask) {
        Page<SyncTask> page = new Page<>(basePageVO.getPageNumber(), basePageVO.getPageSize());
        QueryWrapper<SyncTask> queryWrapper = new QueryWrapper<>();

        if (ObjectUtil.isNotEmpty(syncTask.getId())) {
            queryWrapper.eq(SyncTask.COL_ID, syncTask.getId());
        }
        if (ObjectUtil.isNotEmpty(syncTask.getTaskStatus())) {
            queryWrapper.eq(SyncTask.COL_TASK_STATUS, syncTask.getTaskStatus());
        }
        if (ObjectUtil.isNotEmpty(syncTask.getTaskType())) {
            queryWrapper.eq(SyncTask.COL_TASK_TYPE, syncTask.getTaskType());
        }
        if (ObjectUtil.isNotEmpty(syncTask.getTaskDesc())) {
            queryWrapper.like(SyncTask.COL_TASK_DESC, syncTask.getTaskDesc());
        }
        //queryWrapper.orderByDesc(SyncTask.COL_ID);//如果是通过级联查询，无法使用queryWrapper设置的排序规则
        IPage<SyncTask> pages = syncTaskService.listSyncTaskByPage(page, queryWrapper);

        //图表统计
        List<String> labels = Lists.newArrayList();
        for (int i = 6; i >= 0; i--) {
            String queryDate = DateUtil.format(DateUtil.offsetDay(DateUtil.date(), i * -1), DatePattern.NORM_DATE_PATTERN);
            labels.add(queryDate);
        }

        List<SyncTaskChartDto> taskChartDtos = getChartData(labels);

        int endScale = taskChartDtos.stream().map(SyncTaskChartDto::getMaxValue).max(Comparator.comparingInt(Integer::intValue)).map(es -> {
            if (es <= 2) {
                es = 5;
            }
            return es;
        }).orElse(5);

        int scaleSpace = endScale / 5 == 0 ? endScale : endScale / 5;

        //bootstrap-table要求服务器返回的json须包含：total，rows
        Map<String, Object> map = Maps.newHashMap();
        map.put("total", pages.getTotal());
        map.put("rows", pages.getRecords());
        map.put("labels", labels);
        map.put("chartDatas", taskChartDtos);
        map.put("end_scale", endScale);
        map.put("scale_space", scaleSpace);
        return map;
    }

    /**
     * @Description 获取报表数据
     * @Remark
     * @Params ==>
     * @Param labels
     * @Return java.util.List<cn.domain.dto.SyncTaskChartDto>
     * @Date 2021-12-28 16:28:02
     * @Auther YINZHIYU
     */
    private List<SyncTaskChartDto> getChartData(List<String> labels) {

        List<SyncTaskChartDto> taskChartDtos = Lists.newArrayList();

        List<SyncTaskChartVo> chartVOList = syncTaskService.getTaskChartsList(null);

        Map<String, SyncTaskChartVo> maps = convertMap(chartVOList);

        if (ObjectUtil.isEmpty(maps)) {
            return taskChartDtos;
        }

        for (SyncTaskStatusEnum value : SyncTaskStatusEnum.values()) {
            if (Objects.equals(value, SyncTaskStatusEnum.FINISH)) {
                continue;
            }
            SyncTaskChartDto syncTaskChartDto = new SyncTaskChartDto();
            syncTaskChartDto.setName(value.getRemark());
            syncTaskChartDto.setLineWidth(2);
            syncTaskChartDto.setColor(value.getColor());

            List<Integer> integers = Lists.newArrayList();
            labels.forEach(date -> {
                SyncTaskChartVo taskChartVO = maps.get(String.format(BdmConstants.STRING_FORMAT_TWO_CONCAT, date, value.getTaskStatus()));
                if (ObjectUtil.isNotEmpty(taskChartVO)) {
                    integers.add(taskChartVO.getNumbers());
                } else {
                    integers.add(0);
                }
            });
            syncTaskChartDto.setValue(integers);
            syncTaskChartDto.setMaxValue(integers.stream().max(Comparator.comparingInt(Integer::intValue)).orElse(20));

            taskChartDtos.add(syncTaskChartDto);
        }
        return taskChartDtos;
    }

    /**
     * @Description 转换map
     * @Params ==>
     * @Param sysBaseConfigs
     * @Return void
     * @Date 2020/4/26 11:01
     * @Auther YINZHIYU
     */
    private Map<String, SyncTaskChartVo> convertMap(List<SyncTaskChartVo> syncTaskChartVoList) {
        Map<String, SyncTaskChartVo> maps = null;

        if (ObjectUtil.isNotEmpty(syncTaskChartVoList)) {
            maps = syncTaskChartVoList.stream().collect(Collectors.toMap(entity -> new StringBuffer()
                    .append(entity.getDate())
                    .append("_")
                    .append(entity.getTaskStatus())
                    .toString(), Function.identity(), (v1, v2) -> v1));
        }
        return maps;
    }

    /**
     * @Description 重置
     * @Params ==>
     * @Param sysJobConfig
     * @Return cn.wangoon.domain.common.Result
     * @Date 2020/4/27 18:05
     * @Auther YINZHIYU
     */
    @ApiOperation(value = "重置", notes = "重置", httpMethod = "POST")
    @PostMapping(value = "/reset")
    @ResponseBody
    public Result reset(@RequestBody SyncTask syncTask) {

        try {
            syncTask.setTaskStatus(SyncTaskStatusEnum.WAIT.getTaskStatus());
            syncTask.setProcessCount(0);
            boolean result = syncTaskService.updateById(syncTask);

            if (result) {
                return Result.success("处理成功");
            } else {
                return Result.failure("处理失败");
            }
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }

    /**
     * @Description 删除
     * @Params ==>
     * @Param sysJobConfig
     * @Return cn.wangoon.domain.common.Result
     * @Date 2020/4/27 18:05
     * @Auther YINZHIYU
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping(value = "/delete")
    @ResponseBody
    public Result delete(@RequestBody SyncTask syncTask) {
        return syncTaskService.deleteSyncTask(syncTask);
    }

    /**
     * @Description 更新报文
     * @Remark
     * @Params ==>
     * @Param syncTask
     * @Return cn.wangoon.domain.common.Result
     * @Date 2020/10/23 10:19
     * @Auther YINZHIYU
     */
    @ApiOperation(value = "更新报文", notes = "更新报文", httpMethod = "POST")
    @PostMapping(value = "/updateTaskData")
    @ResponseBody
    public Result updateTaskData(@RequestBody SyncTask syncTask) {

        try {
            boolean result = false;

            if (ObjectUtil.isNotEmpty(syncTask.getId()) && ObjectUtil.isNotEmpty(syncTask.getTaskData())) {

                List<SyncTaskData> syncTaskDataList = syncTaskBuilder.convertSyncTaskDatas(syncTask.getTaskData());
                syncTask.setSyncTaskDataList(syncTaskDataList);

                result = syncTaskService.updateSyncTaskData(syncTask);
            }

            if (result) {
                return Result.success("处理成功");
            } else {
                return Result.failure("处理失败");
            }
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }
}
