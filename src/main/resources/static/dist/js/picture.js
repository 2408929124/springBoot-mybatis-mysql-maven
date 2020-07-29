$(function () {
    $("#jqGrid").jqGrid({
        url: 'pictures/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, sortable: false, hidden: true, key: true},
            {label: '图片预览', name: 'path', index: 'path', sortable: false, width: 105, formatter: imgFormatter},
            {label: '图片备注', name: 'remark', index: 'remark', sortable: false, width: 105},
            {label: '添加时间', name: 'createTime', index: 'createTime', sortable: true, width: 80}
        ],
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: true,
        rownumWidth: 25,
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


    function imgFormatter(cellvalue) {
        return "<a href='" + cellvalue + "'> <img src='" + cellvalue + "' height=\"120\" width=\"135\" alt='lou.springboot'/></a>";
    }

    new AjaxUpload('#upload', {
        action: 'upload',
        name: 'file',
        autoSubmit: true,
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                console.log('只支持jpg、png、gif格式的文件！')
                return false;
            }
        },
        onComplete: function (file, r) {
            console.log("上传成功")
            $("#picturePath").val(r);
            $("#img").attr("src", r);
            $("#img").attr("style", "width: 100px;height: 100px;display:block;");
        }
    });
    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
    // 绑定modal上的确定按钮
    $('#saveButton').click(function () {
        //验证数据
        //一切正常后发送网络请求
        //ajax
        var id = $("#pictureId").val();
        var picturePath = $("#picturePath").val();
        var pictureRemark = $("#pictureRemark").val();
        var data = {"path": picturePath, "remark": pictureRemark};
        var url = 'pictures/save';
        var method = 'POST';
        //id>0表示编辑操作
        if (id > 0) {
            data = {"id": id, "path": picturePath, "remark": pictureRemark};
            url = 'pictures/update';
            method = 'PUT';
        }
        $.ajax({
            type: method,//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: url, //url
            beforeSend: function (request) {
                //设置header值
                request.setRequestHeader("token", getCookie("token"));
            },
            data: data,
            success: function (result) {
                checkResultCode(result.resultCode);
                if (result.resultCode == 200) {
                    $('#pictureModal').modal('hide');
                    swal("保存成功", {
                        icon: "success",
                    });
                    reload();
                } else {
                    $('#pictureModal').modal('hide');
                    swal("保存失败", {
                        icon: "error",
                    });
                };
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });

    });

})


function pictureAdd() {
    reset();
    $('.modal-title').html('图片添加');
    $('#pictureModal').modal('show');
}

/**
 * 重置
 */
function reset() {
    //隐藏错误提示框
    $('.alert-danger').css("display", "none");
    //清空数据
    $('#pictureId').val(0);
    $('#picturePath').val('');
    $('#pictureRemark').val('');
    $("#img").attr("style", "display:none;");
}

/**
 * jqGrid重新加载
 */
function reload() {
    reset();
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}
/**
 *
 * 删除
 * */
function deletePicture() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true
    }).then(flag => {
        if (flag) {
            $.ajax({
                type: "DELETE",
                url: "pictures/delete",
                beforeSend: function(request) {
                    //设置header值
                    request.setRequestHeader("token", getCookie("token"));
                },
                data: {
                    id: ids.toString()
                },
                success: function(r) {
                    checkResultCode(r.data);
                    if (r.resultCode == 200) {
                        swal("删除成功", {
                            icon: "success"
                        });
                        $("#jqGrid").trigger("reloadGrid");
                    } else {
                        swal(r.message, {
                            icon: "error"
                        });
                    }
                }
            });
        }
    });
}
/**
 * 图片编辑
 * */
function pictureEdit() {
    reset();
    $('.modal-title').html('图片编辑');
    //显示modal
    $('#pictureModal').modal('show');
}

//绑定modal上的保存按钮
$('#saveButton').click(function () {
    console.log($('.modal-title').text())
    //ajax
    var id = getSelectedRow();
    var picturePath = $("#picturePath").val();
    var pictureRemark = $("#pictureRemark").val();
    var data = {"path": picturePath, "remark": pictureRemark};
    var url = 'pictures/save';
    var method = 'POST';
    //id>0表示编辑操作
    if (id > 0) {
        data = {"id": id, "path": picturePath, "remark": pictureRemark};
        url = 'pictures/update';
        method = 'PUT';
    }
    $.ajax({
        type: method,//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: url,//url
        beforeSend: function (request) {
            //设置header值
            request.setRequestHeader("token", getCookie("token"));
        },
        data: data,
        success: function (result) {
            checkResultCode(result.resultCode);
            if (result.resultCode == 200) {
                $('#pictureModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reload();
            }
            else {
                $('#pictureModal').modal('hide');
                swal("保存失败", {
                    icon: "error",
                });
            };
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});
