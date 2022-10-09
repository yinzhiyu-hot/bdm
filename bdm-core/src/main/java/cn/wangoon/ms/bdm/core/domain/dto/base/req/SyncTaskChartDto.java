package cn.wangoon.ms.bdm.core.domain.dto.base.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 任务处理报表传输对象
 * @PackagePath cn.wangoon.domain.dto.SyncTaskChartDto
 * @Author YINZHIYU
 * @Date 2020/6/12 14:56
 * @Version 1.0.0.0
 **/
@Data
public class SyncTaskChartDto implements Serializable {
    public String name;
    public List<Integer> value;
    public String color;
    @JsonProperty(value = "line_width")
    public Integer lineWidth;
    public int maxValue;
}
