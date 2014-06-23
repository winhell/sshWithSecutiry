<%@page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
userOptions = {func:"resource",height:210,width:800};
</script>

<div class="easyui-layout" data-options="fit : true,border : false">

<div data-options="region:'center',border:false">
    <table id="resource_gridTable" title="资源管理" class="easyui-datagrid"
    	data-options="fit:true, fitColumns:true,pagination:true,toolbar:'#resourcetoolbar',idfield:'id',url:'getAllResources.action',singleSelect:false,striped:true,
			pageSize : 10,	pageList : [ 10, 20, 30, 40, 50 ]">
    <thead>
    <tr>
    	<th data-options="field:'ck',checkbox:true"></th>
    	<th field="name" width="100px">资源名</th>
    	<th field="url" width="100px">URL</th>
        <th data-options="field:'isMenu',width:100">是否菜单</th>
        <th data-options="field:'showOrder',width:100">显示顺序</th>
   	</tr>
     </thead>
    </table>
    	<div id="resourcetoolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRecord(userOptions)">增加项目</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRecord(userOptions)">修改项目</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteRecord(userOptions)">删除项目</a>
	</div>
    
</div>
</div>