<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">

userOptions = {func:"user",height:210,width:800};

</script>

<div class="easyui-layout" data-options="fit : true,border : false">

<div data-options="region:'center',border:false">
    <table id="user_gridTable" title="用户管理" class="easyui-datagrid"
    	data-options="fit:true, fitColumns:true,pagination:true,toolbar:'#usertoolbar',idfield:'id',url:'listuser.action',singleSelect:false,striped:true,
			pageSize : 10,	pageList : [ 10, 20, 30, 40, 50 ]">
    <thead>
    <tr>
    	<th data-options="field:'ck',checkbox:true"></th>
    	<th field="name" width="100px">用户名</th>
    	<th field="rolesName" width="100px">所拥有角色</th>
    	<th field="createtime" width="100px">创建时间</th>
    	<th data-options="field:'departId',width:100">所在单位</th>
   	</tr>
     </thead>
    </table>
    	<div id="usertoolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRecord(userOptions)">增加项目</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRecord(userOptions)">修改项目</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteRecord(userOptions)">删除项目</a>
	</div>
    
</div>
</div>