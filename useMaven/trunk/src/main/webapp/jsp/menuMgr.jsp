<%@page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">

opts = {func:"menu",prefix:"ma",height:210,width:800};
function add(grid_options) {
    $("<div/>").dialog({
        href : grid_options.func+"_add.html",
        title : "添加记录",
        height : grid_options.height,
        width : grid_options.width,
        modal : true,
        iconCls : "icon-add",
        buttons : [ {
            text : '确定添加',
            iconCls : 'icon-add',
            handler : function() {
                var d = $(this).closest('.window-body');
                $("#addForm").form("submit", {
                    url : "addmenu.action",
                    onSubmit : function(param) {
                        param.action = 'add';
                        return $(this).form('validate');
                    },
                    success : function(result) {
                        result = eval('(' + result + ')');
                        dealAjaxResult(result, function(r) {
                            d.dialog('destroy');
                            $.show_warning("提示", "操作成功");
                            $("#"+grid_options.prefix+"_gridTable").treegrid("reload");
                        });
                    }
                });
            }
        }, {
            text : '取消添加',
            iconCls : 'icon-cancel',
            handler : function() {
                $(this).closest('.window-body').dialog('destroy');
            }
        } ],
        onClose : function() {
            $(this).dialog('destroy');
        }
    });
}

function edit(grid_options) {
    var r = $("#"+grid_options.prefix+"_gridTable").treegrid("getChecked");
    if (r.length < 1) {
        $.show_warning("错误", "请选择一个要修改的记录");
        return;
    }
    if (r.length > 1) {
        $.show_warning("错误", "一次只能修改一条记录");
        $("#"+grid_options.prefix+"_gridTable").treegrid('clearSelections').treegrid(
                'clearChecked');
        return;

    }
    $("<div/>")
            .dialog(
            {
                href : grid_options.func+"_edit.html",
                title : "修改",
                height : grid_options.height,
                width : grid_options.width,
                modal : true,
                iconCls : "icon-edit",
                buttons : [
                    {
                        text : '确定修改',
                        iconCls : 'icon-edit',
                        handler : function() {
                            var d = $(this).closest(
                                    '.window-body');
                            $("#editForm")
                                    .form("submit",
                                    {	url : "updatemenu.action",
                                        onSubmit : function(param) {
                                            param.id = r[0].id;
                                            return $(this).form('validate');
                                        },
                                        success : function(result) {
                                            result = eval('(' + result + ')');
                                            dealAjaxResult(result,function(r) {
                                                d.dialog('destroy');
                                                $.show_warning(
                                                        "提示",
                                                        "操作成功");
                                                $("#"+grid_options.prefix+"_gridTable")
                                                        .treegrid("reload").treegrid('clearSelections')
                                                        .treegrid('clearChecked');
                                            });
                                        }
                                    });
                        }
                    },
                    {
                        text : '取消修改',
                        iconCls : 'icon-cancel',
                        handler : function() {
                            $(this).closest('.window-body')
                                    .dialog('destroy');
                        }
                    } ],
                onClose : function() {
                    $(this).dialog('destroy');
                },
                onLoad : function() {

                    loadForm("#editForm",r[0]);
                }
            });

}
$(function(){
    $('#roleId').combobox({
        onSelect:function(item){
            $('input[name=idList]').each(function(){
                $(this).removeAttr("checked");
            });
            var role = $('#roleId').combobox('getValue');
            var parents = $('#ma_gridTable').treegrid('getRoots');
            $.each(parents,function(index,item){
                $('#ma_gridTable').treegrid('expand',item.id);
            });
            $.ajax({
                url:'getMenusByRole.action?roleId='+role,
                type:'POST',
                dataType:'text',
                success:function(data){
                    $('input[name=idList]').each(function(){
                        var thisId = $(this).val();
                        if(data.indexOf(thisId+",")!=-1){
                            $(this).attr("checked","checked");
                        }
                    });
                }
            });
        }
    });
    $("#assignPriv").click(function(){
        var role = $('#roleId').combobox('getValue');
        if(role==""){
            $.messager.alert('错误！','请选择所要权限的角色！');
            return;
        }
        var rows = $('input:checked');
        if(rows.length<1){
            $.messager.alert("Error","请选好所要分配给该角色的菜单项！");
            return;
        }
        $.messager.confirm('提示','确定要把这些菜单项分配给该角色吗？',function(r){
            if(r){
                var idList = "";
                $.each(rows,function(){
                    idList += $(this).val()+",";
                });
                $.post('setRoleMenu.action',{roleId:role,idList:idList},afterAssign);
            }
        });
    });
});

function showCheck(value,rowData,rowIndex){
    return "<input name='idList' type='checkbox' value='"+rowData.id+"'/>"+rowData.name;
}

function afterAssign(data){
    $.messager.alert("Success","权限分配成功！");
    refreshTab();
}

function deleteItem(grid_options) {
    var rows = $("#"+grid_options.prefix+"_gridTable").treegrid("getChecked");
    if (rows.length < 1) {
        $.show_warning("错误", "请选择要删除的记录");
        return;
    }
    $.messager.confirm('提示！', '确定删除这' + rows.length + '条记录?', function(r) {
        if (r) {
            var para = {};
            para.action = "dele";
            para.timespan = new Date().getTime();
            para.idList = "";
            $.each(rows, function(i, row) {
                para.idList = para.idList + row.id + ",";
            });
            $.ajax({
                url : "deletemenu.action",
                data : para,
                type : "POST",
                dataType : "json",
                success : function(result) {
                    dealAjaxResult(result,
                            function(r) {
                                $.show_warning("提示", "操作成功");
                                $("#"+grid_options.prefix+"_gridTable").treegrid("reload")
                                        .treegrid('clearSelections')
                                        .treegrid('clearChecked');
                                ;
                            });
                }
            });
        }
    });
}
</script>

<div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'north',title:'选择角色',border:false,iconCls:'icon-columns'"
         style="height: 100px; background: #f4f4f4;padding:15px;">
        请选择需要分配权限的角色：
        <input id="roleId" name="roleId" class="easyui-combobox"
               data-options="valueField:'id',textField:'comment',url:'getAllRoles.action'"/>
        <a id="assignPriv" class="easyui-linkbutton" data-options="iconCls:'icon-search'">分配权限</a>
    </div>
    <div data-options="region:'center',border:false" id="menuPanel">
        <table id="ma_gridTable" title="菜单管理"  class="easyui-treegrid"
               url="getMenuTree.action"
               fit="true"
               fitColumns="true"
               rownumbers="true"
               toolbar='#menutoolbar'
               idField="id" treeField="name"
               singleSelect="true">
            <thead>
            <tr>
                <th field="name"
                    formatter="showCheck" width="100px">菜单名称</th>
                <th field="url" width="100px">url</th>
                <th field="icon" width="100px">图标</th>
                <th data-options="field:'showOrder',width:20">显示顺序</th>
            </tr>
            </thead>
        </table>
        <div id="menutoolbar">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add(opts)">增加记录</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit(opts)">修改记录</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteItem(opts)">删除记录</a>
        </div>

    </div>
</div>