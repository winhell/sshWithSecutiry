function addRecord(grid_options) {
    $("<div id='" + grid_options.func + "addDlg'/>").dialog({
        href: grid_options.func + "_add.html",
        title: "添加记录",
        height: grid_options.height,
        width: grid_options.width,
        modal: true,
        iconCls: "icon-add",
        buttons: [
            {
                text: '确定添加',
                iconCls: 'icon-add',
                handler: function () {
                    var d = $(this).closest('.window-body');
                    $("#addForm").form("submit", {
                        url: "add" + grid_options.func + ".action",
                        onSubmit: function (param) {
                            param.action = 'add';
                            return $(this).form('validate');
                        },
                        success: function (result) {
                            result = eval('(' + result + ')');
                            dealAjaxResult(result, function (r) {
                                d.dialog('destroy');
                                $.messager.alert("提示", "操作成功");
                                $("#" + grid_options.func + "_gridTable").datagrid("reload");
                            });
                        }
                    });
                }
            },
            {
                text: '取消添加',
                iconCls: 'icon-cancel',
                handler: function () {
                    $(this).closest('.window-body').dialog('destroy');
                }
            }
        ],
        onClose: function () {
            $(this).dialog('destroy');
        }
    });
}

function editRecord(grid_options) {
    var r = $("#" + grid_options.func + "_gridTable").datagrid("getChecked");
    if (r.length < 1) {
        $.messager.alert("错误", "请选择一个要修改的记录");
        return;
    }
    if (r.length > 1) {
        $.messager.alert("错误", "一次只能修改一条记录");
        $("#" + grid_options.func + "_gridTable").datagrid('clearSelections').datagrid(
            'clearChecked');
        return;

    }
    $("<div id='" + grid_options.func + "editDlg'/>")
        .dialog(
        {
            href: grid_options.func + "_edit.html",
            title: "修改",
            height: grid_options.height,
            width: grid_options.width,
            modal: true,
            iconCls: "icon-edit",
            buttons: [
                {
                    text: '确定修改',
                    iconCls: 'icon-edit',
                    handler: function () {
                        var d = $(this).closest('.window-body');
                        $("#editForm").form("submit", {
                            url: "update" + grid_options.func + ".action",
                            onSubmit: function (param) {
                                param.id = r[0].id;
                                return $(this).form('validate');
                            },
                            success: function (result) {
                                result = eval('(' + result + ')');
                                dealAjaxResult(result, function (r) {
                                    d.dialog('destroy');
                                    $.messager.alert("提示", "操作成功");
                                    $("#" + grid_options.func + "_gridTable").datagrid("reload")
                                        .datagrid('clearSelections')
                                        .datagrid('clearChecked');
                                });
                            }
                        });
                    }
                },
                {
                    text: '取消修改',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        $(this).closest('.window-body')
                            .dialog('destroy');
                    }
                }
            ],
            onClose: function () {
                $(this).dialog('destroy');
            },
            onLoad: function () {

                loadForm("#editForm", r[0]);
            }
        });

}

function deleteRecord(grid_options) {
    var rows = $("#" + grid_options.func + "_gridTable").datagrid("getChecked");
    if (rows.length < 1) {
        $.messager.alert("错误", "请选择要删除的记录");
        return;
    }
    $.messager.confirm('提示！', '确定删除这' + rows.length + '条记录?', function (r) {
        if (r) {
            para = {};
            para.action = "dele";
            para.timespan = new Date().getTime();
            para.idList = "";
            $.each(rows, function (i, row) {
                para.idList = para.idList + row.id + ",";
            });
            $.ajax({
                url: "delete" + grid_options.func + ".action",
                data: para,
                type: "POST",
                dataType: "json",
                success: function (result) {
                    dealAjaxResult(result,
                        function (r) {
                            $.show_warning("提示", "操作成功");
                            $("#" + grid_options.func + "_gridTable").datagrid("reload")
                                .datagrid('clearSelections')
                                .datagrid('clearChecked');
                        });
                }
            });
        }
    });
}