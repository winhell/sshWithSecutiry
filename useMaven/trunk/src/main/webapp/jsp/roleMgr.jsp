<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">

userOptions = {func:"role",height:210,width:800};

</script>

<div class="easyui-layout" data-options="fit : true,border : false">

<div data-options="region:'center',border:false">
    <table id="role_gridTable" title="角色管理" class="easyui-datagrid"
    	data-options="fit:true, fitColumns:true,pagination:true,toolbar:'#roletoolbar',idfield:'id',url:'getAllRoles.action',singleSelect:false,striped:true,
			pageSize : 10,	pageList : [ 10, 20, 30, 40, 50 ]">
    <thead>
    <tr>
    	<th data-options="field:'ck',checkbox:true"></th>
    	<th field="name" width="100px">角色名</th>
    	<th field="createtime" width="100px">创建时间</th>
    	<th data-options="field:'comment',width:100">说明</th>
   	</tr>
     </thead>
    </table>
    	<div id="roletoolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRecord(userOptions)">增加项目</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRecord(userOptions)">修改项目</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteRecord(userOptions)">删除项目</a>
	</div>
    
</div>
</div>