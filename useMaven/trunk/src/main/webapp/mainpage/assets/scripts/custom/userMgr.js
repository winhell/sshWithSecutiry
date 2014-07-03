/**
 * Created by Administrator on 14-7-2.
 */
$(function () {
    var opts = {
        action: "listuser.action",
        param: {
            page: 1,
            rows: 10
        },
        aoColumns: [
            {
                "mData": "id",
                "bSortable": false,
                "mRender": function (data, type, full) {
                    return "<input class='checkboxes' type='checkbox' value='" + data + "'/>";
                }
            },
            {
                "mData": "name",
                "bSortable": false
            },
            {
                "mData":"rolesName",
                "bSortable":false
            },
            {
                "mData": "createtime",
                "bSortable": false
            },{
                "mData":"departId",
                "bSortable":false
            },{
                "mData":"id",
                "bSortable":false,
                "mRender":function(data){
                    return '<a href="javascript:assignRoles(\''+data+'\');" class="btn btn-xs purple"> 分配角色</a>';
                }
            }
        ],
        dataProp: "rows",
        pageProp: "total",
        bootpag: {
            maxVisible: 5
        }
    }
    $("#gridtable").tableManager(opts);
    $('#additemForm').validate({
        rules:{name:'required',
            password:'required',
            repassword:{
                equalTo:'#password',
                required:true
            }
        }
    });
    commonCRUD.init();
});
function assignRoles(id){
    $('#userID').val(id);
    $.getJSON('getAllRoles.action',function(data){
        $('.checkbox-list').empty();
        $.each(data.rows,function(index,item){
            $('.checkbox-list').append('<label class="checkbox-inline"><input type="checkbox" name="roleIDS" value="'+item.id+'">'+ item.comment + '</label>');
        });
        App.initUniform();
        $('#assignRolesDiv').modal('show');
    });
}