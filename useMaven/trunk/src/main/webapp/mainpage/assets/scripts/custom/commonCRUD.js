/**
 * Created by Administrator on 14-6-26.
 */

var formOptions = {
    target:'#additemForm',
    success:reloadForm
};

function reloadForm(data){
    $('#additemFormDiv').modal('hide');
    $('#gridtable').tableManager('reload');
}

$('#formClose').click(function(){
    $('#additemFormDiv').modal('hide');
});

$('#formSubmit').click(function(){
    formOptions.url='adduser.action';
    $('#additemForm').ajaxSubmit(formOptions);
});

$('#addLink').click(function(){
    formOptions.url='adduser.action';
    $('#formTitle').html('增加项目');
    $('#additemFormDiv').modal('show');
});

$('#editLink').click(function(){
    formOptions.url='updateuser.action';
    $('#formTitle').html('项目修改');
    $('#additemFormDiv').modal('show');
});