$(function ($) {
    initTable();
    queryTable();

    //关闭按钮
    Utils.get('closeBtn').click(function () {
        Utils.get("taskData").val("");
        hideTaskDataInfo();
    });
    //关闭按钮
    Utils.get('closeLogsBtn').click(function () {
        hideLogsInfo();
    });

    Utils.get("queryBtn").bind("click", queryTable);

    //基本信息-异步提交表单
    Utils.get('submitBtn').click(function () {
        //组装数据
        var data = {
            "id": Utils.get("updateTaskId").val(),
            "taskData": Utils.get("taskData").val()
        };
        submitUpdate(data);
    });
});

function submitUpdate(value) {
    var d = dialog({
        title: '消息',
        width: 200,
        zIndex: 999999999,
        content: '确定提交更新吗？',
        ok: function () {
            var da = dialog({
                title: '消息',
                width: 300,
                content: '此操作是极有风险的呐！！！<br>你真的确定要提交报文更新吗？<br>你确定吗？',
                ok: function () {
                    $.ajax({
                        //请求方式
                        type: "POST",
                        //请求的媒体类型
                        contentType: "application/json;charset=UTF-8",
                        //请求地址
                        url: "/bdm-web/tasks/updateTaskData",
                        //数据，json字符串
                        data: JSON.stringify(value),
                        //请求成功
                        success: function (result) {
                            if (result.success) {
                                var s = dialog({
                                    title: '消息',
                                    width: 200,
                                    zIndex: 999999995,
                                    content: result.data,
                                    cancel: false,
                                    okValue: '确定',
                                    ok: function () {
                                        Utils.get("taskData").val("");
                                        hideTaskDataInfo();
                                        queryTable();
                                    }
                                });
                                s.showModal();
                            } else {
                                var f = dialog({
                                    title: '消息',
                                    width: 200,
                                    zIndex: 999999999,
                                    content: result.responseDesc
                                });
                                f.showModal();
                            }
                        },
                        //请求失败，包含具体的错误信息
                        error: function (e) {

                        }
                    });
                    return true;
                },
                okValue: "确定",
                cancel: function () {
                    return true;
                },
                cancelValue: "取消"
            });
            da.showModal();

            return true;
        },
        okValue: "确定",
        cancel: function () {
            return true;
        },
        cancelValue: "取消"
    });
    d.showModal();
}

function queryTable() {
    Utils.get("table").bootstrapTable('refresh');
}

function initTable() {
    /*boostrap table*/
    Utils.get('table').bootstrapTable({
        columns: [
            {
                // field: 'Number',//可不加
                title: '序号',//标题  可不加
                width: 50,
                align: "center",
                formatter: function (value, row, index) {
                    return index + 1;
                }
            },
            {
                field: 'id',
                title: '任务id',
                width: 70,
                align: "left"
            }, {
                field: 'taskType',
                title: '任务类型',
                align: "left",
                width: 280,
                formatter: paramsMatter
            }, {
                field: 'taskDesc',
                title: '任务描述',
                align: "left",
                formatter: paramsMatter
            }, {
                field: 'taskStatus',
                title: '任务状态',
                align: "center",
                width: 80,
                formatter: taskStatusFormatter
            }, {
                field: 'processCount',
                title: '执行次数',
                align: "center",
                width: 80
            }, {
                field: 'createDate',
                title: '创建时间',
                width: 180,
                align: "center",
                formatter: dataFormatter
            }, {
                field: 'finishDate',
                title: '完成时间',
                width: 180,
                align: "center",
                formatter: dataFormatter
            }, {
                field: 'taskStatus',
                title: '操作',
                width: 140,
                align: "center",
                formatter: operateFormatter
            }],

        method: 'get',
        contentType: "application/x-www-form-urlencoded",//必须要有！因为bootstap table使用的是ajax方式获取数据，这时会将请求的content type默认设置为 text/plain，这样在服务端直接通过 @RequestParam参数映射是获取不到的。
        url: "/bdm-web/tasks/listPage",//要请求数据的文件路径,加时间戳，防止读取缓存数据
        pagination: true,//是否开启分页（*）启动分页，必须设为true
        queryParamsType: '',//注意:查询参数组织方式，默认值为 'limit',在默认情况下 传给服务端的参数为：offset,limit,sort 。 设置为 '' 在这种情况下传给服务器的参数为：pageSize,pageNumber
        queryParams: queryParams,//请求服务器时所传的参数
        pageNumber: 1,//初始化加载第一页，默认第一页
        pageSize: 10,//每页的记录行数（*）
        pageList: [10, 15, 20, 25, 30],//可供选择的每页的行数（*）
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        responseHandler: function (res) {
            drawChar(res.labels, res.chartDatas, res.end_scale, res.scale_space);
            return {
                "total": res.total,
                "rows": res.rows
            }
        }
    });

    //得到查询的参数
    function queryParams(params) {
        return {  //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.pageSize,  //页面大小
            pageNumber: params.pageNumber, //页码
            id: Utils.get("taskId").val(),
            taskType: Utils.get("taskType").val(),
            taskDesc: Utils.get("taskDesc").val(),
            taskStatus: Utils.get("taskStatus").val()
        };
    }

    //表格加载完毕后，处理动作
    Utils.get('table').on('load-success.bs.table', function (data) {
        $("[data-toggle='tooltip']").tooltip();
    })
}

function reset(value) {
    var d = dialog({
        title: '消息',
        width: 200,
        content: '确定重置吗？',
        ok: function () {
            $.ajax({
                //请求方式
                type: "POST",
                //请求的媒体类型
                contentType: "application/json;charset=UTF-8",
                //请求地址
                url: "/bdm-web/tasks/reset",
                //数据，json字符串
                data: JSON.stringify({"id": value}),
                //请求成功
                success: function (result) {
                    if (result.success) {
                        var s = dialog({
                            title: '消息',
                            width: 200,
                            content: result.data,
                            cancel: false,
                            okValue: '确定',
                            ok: function () {
                                queryTable();
                            }
                        });
                        s.showModal();
                    } else {
                        var f = dialog({
                            title: '消息',
                            width: 200,
                            content: result.responseDesc
                        });
                        f.showModal();
                    }
                },
                //请求失败，包含具体的错误信息
                error: function (e) {

                }
            });
            return true;
        },
        okValue: "确定",
        cancel: function () {
            return true;
        },
        cancelValue: "取消"
    });
    d.showModal();
}

function del(value) {
    var d = dialog({
        title: '消息',
        width: 200,
        content: '确定删除吗？',
        ok: function () {
            $.ajax({
                //请求方式
                type: "POST",
                //请求的媒体类型
                contentType: "application/json;charset=UTF-8",
                //请求地址
                url: "/bdm-web/tasks/delete",
                //数据，json字符串
                data: JSON.stringify({"id": value}),
                //请求成功
                success: function (result) {
                    if (result.success) {
                        var s = dialog({
                            title: '消息',
                            width: 200,
                            content: result.data,
                            cancel: false,
                            okValue: '确定',
                            ok: function () {
                                queryTable();
                            }
                        });
                        s.showModal();
                    } else {
                        var f = dialog({
                            title: '消息',
                            width: 200,
                            content: result.responseDesc
                        });
                        f.showModal();
                    }
                },
                //请求失败，包含具体的错误信息
                error: function (e) {

                }
            });
            return true;
        },
        okValue: "确定",
        cancel: function () {
            return true;
        },
        cancelValue: "取消"
    });
    d.showModal();
}

function taskLogsDetails(row) {
    //解压成syncTask 的 json字符串
    var jsonStr = uncompileStr(row);

    try {
        //转syncTask任务json对象
        var syncTaskJson = JSON.parse(jsonStr);

        //businessKey
        var businessKey = syncTaskJson.id;

        //queryDate
        // var queryDate = dateFormat("YYYY-mm-dd", new Date(syncTaskJson.ts));

        queryLogsTable(businessKey, queryDate);
    } catch (e) {
        Utils.get("taskData").val(JSON.parse(jsonStr).taskData);
    }
}

function taskDataDetail(row) {
    //解压成syncTask 的 json字符串
    var jsonStr = uncompileStr(row);

    try {
        //转syncTask任务json对象
        var syncTaskJson = JSON.parse(jsonStr);

        //将任务数据转成json对象
        var taskDataJson = JSON.parse(syncTaskJson.taskData);

        var taskStatus = syncTaskJson.taskStatus;

        switch (taskStatus) {
            case 3:
            case 99:
                Utils.get('submitBtn').show();
                break;
            default:
                Utils.get('submitBtn').hide();
                break;
        }

        Utils.get("taskData").val(JSON.stringify(taskDataJson, null, 4));
        Utils.get("updateTaskId").val(syncTaskJson.id);
    } catch (e) {
        Utils.get("taskData").val(JSON.parse(jsonStr).taskData);
        Utils.get("updateTaskId").val(JSON.parse(jsonStr).id);
    }
    showTaskDataInfo();
}

function taskStatusFormatter(value, row, index) {
    switch (value) {
        case 0:
            return "待处理";
        case 1:
            return "处理中";
        case 2:
            return "已处理";
        case 3:
            var jsonStr = "\"" + compileStr(JSON.stringify(row)) + "\"";
            return [
                "<div class=’form-inline‘ onclick='taskLogsDetails(" + jsonStr + ")'>",
                "<a href='#'>",
                "处理失败",
                "</a>",
                "</div>"
            ].join('');
        case 4:
            var excuteWaitJsonStr = "\"" + compileStr(JSON.stringify(row)) + "\"";
            return [
                "<div class=’form-inline‘ onclick='taskLogsDetails(" + excuteWaitJsonStr + ")'>",
                "<a href='#'>",
                "处理等待",
                "</a>",
                "</div>"
            ].join('');
        case 99:
            var jsonStrEx = "\"" + compileStr(JSON.stringify(row)) + "\"";
            return [
                "<div class=’form-inline‘ onclick='taskLogsDetails(" + jsonStrEx + ")'>",
                "<a href='#'>",
                "处理异常",
                "</a>",
                "</div>"
            ].join('');
        case 100:
            var jsonStrStop = "\"" + compileStr(JSON.stringify(row)) + "\"";
            return [
                "<div class=’form-inline‘ onclick='taskLogsDetails(" + jsonStrStop + ")'>",
                "<a href='#'>",
                "终止",
                "</a>",
                "</div>"
            ].join('');
        default:
            return "-";
    }
}

function operateFormatter(value, row, index) {
    var jsonStr = "\"" + compileStr(JSON.stringify(row)) + "\"";
    switch (value) {
        case 1:
            return [
                "<div class=’form-inline‘>",
                "<button type='button' class='btn btn-primary' onclick='taskDataDetail(" + jsonStr + ")'>报文</button>",
                "<button style='visibility: hidden'/>",
                "<button type='button' class='btn btn-primary' onclick='reset(" + row.id + ")'>重置</button>",
                "</div>"
            ].join('');
        case 3:
            return [
                "<div class=’form-inline‘>",
                "<button type='button' class='btn btn-primary' onclick='taskDataDetail(" + jsonStr + ")'>报文</button>",
                "<button style='visibility: hidden'/>",
                "<button type='button' class='btn btn-primary' onclick='reset(" + row.id + ")'>重置</button>",
                "</div>"
            ].join('');
        case 99:
            return [
                "<div class=’form-inline‘>",
                "<button type='button' class='btn btn-primary' onclick='taskDataDetail(" + jsonStr + ")'>报文</button>",
                "<button style='visibility: hidden'/>",
                "<button type='button' class='btn btn-primary' onclick='reset(" + row.id + ")'>重置</button>",
                "</div>"
            ].join('');
        case 100:
            return [
                "<div class=’form-inline‘>",
                "<button type='button' class='btn btn-primary' onclick='taskDataDetail(" + jsonStr + ")'>报文</button>",
                "<button style='visibility: hidden'/>",
                "<button type='button' class='btn btn-primary' onclick='del(" + row.id + ")'>删除</button>",
                "</div>"
            ].join('');
        default:
            return [
                "<div class=’form-inline‘>",
                "<button type='button' class='btn btn-primary' onclick='taskDataDetail(" + jsonStr + ")'>报文</button>",
                "</div>"
            ].join('');
    }
}

//展示
function showTaskDataInfo() {
    Utils.get('taskDataInfo').modal({backdrop: 'static', keyboard: false});
}

//隐藏
function hideTaskDataInfo() {
    Utils.get('taskDataInfo').modal('hide');
}

function queryLogsTable(businessKey, queryDate) {

    Utils.get("businessKey").val(businessKey);
    // Utils.get("queryDate").val(queryDate);

    initLogsTable();

    Utils.get("logsTable").bootstrapTable("removeAll");
    Utils.get("logsTable").bootstrapTable('refresh');

    showLogsInfo();
}

function initLogsTable() {
    /*boostrap table*/
    Utils.get('logsTable').bootstrapTable({
        columns: [
            {
                // field: 'Number',//可不加
                title: '序号',//标题  可不加
                width: 50,
                align: "center",
                formatter: function (value, row, index) {
                    return index + 1;
                }
            }, {
                field: 'message',
                title: 'Message',
                align: "left",
                formatter: paramsMatter
            }, {
                field: 'ts',
                title: '日志时间',
                width: 160,
                align: "center",
                formatter: dataFormatter
            }],
        method: 'get',
        contentType: "application/x-www-form-urlencoded",//必须要有！因为bootstap table使用的是ajax方式获取数据，这时会将请求的content type默认设置为 text/plain，这样在服务端直接通过 @RequestParam参数映射是获取不到的。
        url: "/bdm-web/logs/listPage",//要请求数据的文件路径,加时间戳，防止读取缓存数据
        pagination: true,//是否开启分页（*）启动分页，必须设为true
        queryParamsType: '',//注意:查询参数组织方式，默认值为 'limit',在默认情况下 传给服务端的参数为：offset,limit,sort 。 设置为 '' 在这种情况下传给服务器的参数为：pageSize,pageNumber
        queryParams: queryParams,//请求服务器时所传的参数
        pageNumber: 1,//初始化加载第一页，默认第一页
        pageSize: 10,//每页的记录行数（*）
        pageList: [10, 15, 20, 25, 30],//可供选择的每页的行数（*）
        sidePagination: "server" //分页方式：client客户端分页，server服务端分页（*）
    });

    //表格加载完毕后，处理动作
    Utils.get('logsTable').on('load-success.bs.table', function (data) {
        $("[data-toggle='tooltip']").tooltip();
        // $("[data-toggle='tooltip']").bind("click", showToolTip);
    })
}

//得到查询的参数
function queryParams(params) {
    return {  //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        pageSize: params.pageSize,  //页面大小
        pageNumber: params.pageNumber, //页码
        businessKey: Utils.get("businessKey").val(),
        queryDate: Utils.get("queryDate").val()
    };
}

//展示
function showLogsInfo() {
    Utils.get('logsInfo').modal({backdrop: 'static', keyboard: false});
}

//隐藏
function hideLogsInfo() {
    Utils.get('logsInfo').modal('hide');
}

function drawChar(labels, data, end_scale, scale_space) {
    if (data == undefined || data.length <= 0) {
        return;
    }
    var chart = new iChart.ColumnMulti3D({
        render: 'canvasDiv',
        data: data,
        labels: labels,
        title: {
            text: 'JOB近7天任务处理情况',
            color: '#3e576f'
        },
        footnote: {
            text: '数据来源：Erp系统',
            color: '#909090',
            fontsize: 11,
            padding: '0 44'
        },
        width: 1000,
        height: 400,
        background_color: '#ffffff',
        legend: {
            enable: true,
            background_color: null,
            align: 'center',
            valign: 'bottom',
            row: 1,
            column: 'max',
            border: {
                enable: false
            }
        },
        column_width: 15,//柱形宽度
        zScale: 8,//z轴深度倍数
        xAngle: 50,
        bottom_scale: 1.1,
        label: {
            color: '#4c4f48'
        },
        sub_option: {
            label: false
        },
        tip: {
            enable: true
        },
        text_space: 20,//坐标系下方的label距离坐标系的距离。
        coordinate: {
            background_color: '#ffffff',
            grid_color: '#a4a4a2',
            color_factor: 0.24,
            board_deep: 10,
            offsety: -10,
            pedestal_height: 10,
            left_board: false,//取消左侧面板
            width: 900,
            height: 240,
            scale: [{
                position: 'left',
                start_scale: 0,
                end_scale: end_scale,
                scale_space: scale_space,
                scale_enable: false,
                label: {
                    color: '#4c4f48'
                }
            }]
        }
    });

    //利用自定义组件构造左侧说明文本
    chart.plugin(new iChart.Custom({
        drawFn: function () {
            //计算位置
            var coo = chart.getCoordinate(),
                x = coo.get('originx'),
                y = coo.get('originy');
            //在左上侧的位置，渲染一个单位的文字
            chart.target.textAlign('start')
                .textBaseline('bottom')
                .textFont('600 11px Verdana')
                .fillText('任务数(条)', x - 40, y - 40, false, '#6d869f');

        }
    }));

    chart.draw();

}
