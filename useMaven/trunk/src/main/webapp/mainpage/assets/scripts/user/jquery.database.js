/**
 * Created by Administrator on 14-7-7.
 */
/**
 * Created by Administrator on 14-6-26.
 */
var databaseUtil = function(){

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

    var opts = {
        param: {
            page: 1,
            rows: 10
        },
        dataProp: "rows",
        pageProp: "total",
        bootpag: {
            maxVisible: 5
        }
    };
    var funcPrefix = $('#additemForm').data('func');

    var initTable = function(){
        var datatables = $('.datatable');
        datatables.each(function(){
            var datatable = $(this);
            opts.action = datatable.data('url');
            var fields = datatable.find('th');
            var aoColumns = [];
            fields.each(function(){
                var fieldOpt =  eval("({"+$(this).data('options')+"})");
                aoColumns.push(fieldOpt);
//            console.log($(this).data('options'));
            });
            opts.aoColumns = aoColumns;
            datatable.tableManager(opts);
        })
    };

    var bindButtons = function(){
        $('#addLink').on('click',function(){                 //添加记录
            formOptions.url='add'+funcPrefix+'.action';
            $('#formTitle').html('增加项目');
            $('#additemFormDiv').modal('show');
        });

        $('#editLink').on('click',function(){               //编辑记录
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

        $('#deleteLink').on('click',function(){            //删除记录
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
    };

    return {
        init:function(){

            $('#additemForm').ajaxForm(formOptions);
            initTable();
            bindButtons();
        },
        checkboxid:function (data, type, full) {
            return "<input class='checkboxes' type='checkbox' value='" + data + "'/>";
        }
    };
}();

databaseUtil.init();