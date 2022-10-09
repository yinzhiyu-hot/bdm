$(function ($) {

    Utils.get("username").keydown(function (event) {
        if (event.keyCode == 13) {
            Utils.get('loginInBtn').click();
        }
    });
    Utils.get("password").keydown(function (event) {
        if (event.keyCode == 13) {
            Utils.get('loginInBtn').click();
        }
    });
    //基本信息-异步提交表单
    Utils.get('loginInBtn').click(function () {
        Utils.get('loginForm').bootstrapValidator('validate');

        if (!Utils.get('loginForm').data("bootstrapValidator").isValid()) {
            return;
        }

        //组装数据
        var data = {
            "username": Utils.get("username").val(),
            "password": Utils.get("password").val()
        };
        loginIn(data);
    });

    Utils.get('loginForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {

            username: {
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    }
                }
            }
        }
    });
});

function loginIn(value) {
    $.ajax({
        //请求方式
        type: "POST",
        //请求的媒体类型
        contentType: "application/json;charset=UTF-8",
        //请求地址
        url: "/bdm-web/login/loginIn",
        //数据，json字符串
        data: JSON.stringify(value),
        //请求成功
        success: function (result) {
            if (result.success) {
                window.location.href = "/bdm-web/index/"
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
}