$(function () {

    $("#jqGrid").jqGrid({
        url: 'users/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, hidden: true, key: true},
            {label: '登录名', name: 'userName', index: 'userName', sortable: false, width: 80},
            {label: '添加时间', name: 'createTime', index: 'createTime', sortable: false, width: 80}
        ],
        height: 485,
        rowNum: 10,
        rowList: [10, 30, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: true,
        rownumWidth: 35,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
});

function userAdd() {
    reset();
    $('#modalAddTitle').html('用户添加');
    $('#modalAdd').modal('show');
}
function userEdit() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    $('#userId').val(id);
    $('#modalEditTitle').html('密码编辑');
    $('#modalEdit').modal('show');
}

function userDel() {
    var id = getSelectedRows();
    if (id == null) {
        return;
    }
    $.ajax({
        type: 'DELETE',
        url: 'users/delete',
        contentType: "application/json",
        data: JSON.stringify(id),
        beforeSend: function (request) {
            //设置header值
            request.setRequestHeader("token", getCookie("token"));
        },
        success: function (r) {
            $("#jqGrid").trigger("reloadGrid");
        }
    })
}

/**
 *
 * 新增按钮
 * */
$('#saveButton').click(function () {
    if (validObjectForAdd()) {
        var userName = $('#userName').val();
        var password = $('#password').val();
        $.ajax({
            url: "users/save",
            type: 'POST',
            dataType: 'json',
            data: {
                userName: userName,
                password: password
            },
            success: function (result) {
                $('#modalAdd').modal('hide');
                reload()
            },
            error: function () {
                reset()
            }
        })
    }
})

//绑定modal上的编辑按钮
$('#editButton').click(function () {
    //验证数据
    if (validObjectForEdit()) {
        //一切正常后发送网络请求
        var password = $("#passwordEdit").val();
        var id = $("#userId").val();
        var data = {"id": id, "password": password};
        $.ajax({
            type: 'PUT',//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: 'users/updatePassword',//url
            data: data,
            success: function (result) {
                if (result.resultCode == 200) {
                    $('#modalEdit').modal('hide');
                    reload();
                }
            },
            error: function () {
                reset();
            }
        });

    }
});



/**
 * 数据验证
 */
function validObjectForAdd() {
    var userName = $('#userName').val();
    if (isNull(userName)) {
        showErrorInfo("用户名不能为空!");
        return false;
    }
    if (!validUserName(userName)) {
        showErrorInfo("请输入符合规范的用户名!");
        return false;
    }
    var password = $('#password').val();
    if (isNull(password)) {
        showErrorInfo("密码不能为空!");
        return false;
    }
    if (!validPassword(password)) {
        showErrorInfo("请输入符合规范的密码!");
        return false;
    }
    return true;
}

/**
 * 数据验证
 */
function validObjectForEdit() {
    var userId = $('#userId').val();
    if (isNull(userId) || userId < 1) {
        showErrorInfo("数据错误！");
        return false;
    }
    var password = $('#passwordEdit').val();
    if (isNull(password)) {
        showErrorInfo("密码不能为空!");
        return false;
    }
    if (!validPassword(password)) {
        showErrorInfo("请输入符合规范的密码!");
        return false;
    }
    return true;
}

// 重置
function reset() {
    // 隐藏错误提示框
    $('.alert-danger').css("display", "none");
    //清空数据
    $('#password').val('');
    $('#passwordEdit').val('');
    $('#userName').val('');
    $('#userId').val(0);
}

// jqGrid重新加载
function reload() {
    reset();
    var page = $("#jqGrid").jqGrid("getGridParam", "page")
    $('#jqGrid').jqGrid('setGridParam', {
        page: page
    }).trigger('reloadGrid');
}




