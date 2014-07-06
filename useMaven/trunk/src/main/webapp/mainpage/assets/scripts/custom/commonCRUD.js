/**
 * Created by Administrator on 14-6-26.
 */
var commonCRUD = function(){

    var reloadForm = function(data){

        if(data.status=='SUCCESS'){
            bootbox.alert(data.msg);
            $('#additemForm').resetForm();
            $("#gridtable").data("tableManager").initData();
            $('#additemFormDiv').modal('hide');
        }else
            bootbox.alert(data.msg);
    }

    var formOptions = {
        target:'#additemForm',
        dataType:'json',
        success:reloadForm
    };

    var funcPrefix = $('#additemForm').data('func');

    return {
        init:function(){

            $('#additemForm').ajaxForm(formOptions);

//添加记录
            $('#addLink').click(function(){
                var funcPrefix = $('#additemForm').data('func');
                formOptions.url='add'+funcPrefix+'.action';
                $('#formTitle').html('增加项目');
                $('#additemFormDiv').modal('show');
            });

//编辑记录
            $('#editLink').click(function(){

                var rows = $('#gridtable').data('tableManager').getCheckedRows();
                if(rows.length!=1){
                    bootbox.alert('请选取一条记录进行修改！');
                    return;
                }

                $('#formTitle').html('项目修改');
                //装入表单数据
                var rowData = rows[0];
                for(var item in rowData)
                    $('#'+item).val(rowData[item]).trigger('change');
                formOptions.url='update'+funcPrefix+'.action?id='+rowData.id;
                $('#additemFormDiv').modal('show');
            });

//删除记录
            $('#deleteLink').click(function(){
                var delUrl = 'delete'+funcPrefix+'.action';
                var rows = $('#gridtable').data('tableManager').getCheckedRows();
                if(rows.length<1){
                    bootbox.alert('请选取所要删除的记录！');
                    return;
                }
                var idList = "";
                $.each(rows,function(index,row){
                    idList += row.id + ",";
                })
                bootbox.confirm("你确定要删除所选记录吗？",function(result){
                    if(result){
                        $.getJSON(delUrl,{idList:idList},reloadForm);
                        console.log('record delete...');
                    }
                });
            });
        }
    };
}();