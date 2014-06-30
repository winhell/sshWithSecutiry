/**
 * Created by Administrator on 14-6-26.
 */

var formOptions = {
    target:'#additemForm',
    dataType:'json',
    success:reloadForm
};

function reloadForm(data){
    if(data.result=='SUCCESS'){
        $('#additemFormDiv').modal('hide');
        bootbox.alert(data.msg);
        $('#additemForm').resetForm();
        $("#gridtable").data("tableManager").initData();
    }else
        bootbox.alert(data.msg);

}

$('#formClose').click(function(){
    $('#additemFormDiv').modal('hide');
});

//提交表单
$('#formSubmit').click(function(){
    $('#additemForm').ajaxSubmit(formOptions);
    $("#additemFormDiv").modal('hide');
});

//添加记录
$('#addLink').click(function(){
    var funcPrefix = $('#additemForm').data('func');
    formOptions.url='add'+funcPrefix+'.action';
    $('#formTitle').html('增加项目');
    $('#additemFormDiv').modal('show');
});

//编辑记录
$('#editLink').click(function(){
    var funcPrefix = $('#additemForm').data('func');
    var rows = $('#gridtable').data('tableManager').getCheckedRows();
    if(rows.length!=1){
        bootbox.alert('请选取一条记录进行修改！');
        return;
    }

    $('#formTitle').html('项目修改');
    //装入表单数据
    var rowData = rows[0];
    for(var item in rowData)
        $('#'+item).val(rowData[item]);
    formOptions.url='update'+funcPrefix+'.action?id='+rowData.id;
    $('#additemFormDiv').modal('show');
});

//删除记录
$('#deleteLink').click(function(){
    bootbox.confirm("你确定要删除所选记录吗？",function(result){
        if(result){
            console.log('record delete...');
        }
    });
});