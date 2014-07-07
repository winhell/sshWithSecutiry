/**
 * Created by Administrator on 14-7-2.
 */
var userMgr = function () {
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
                    return '<a href="javascript:userMgr.assignRoles(\''+data+'\');" class="btn btn-xs purple"> 分配角色</a>';
                }
            }
        ],
        dataProp: "rows",
        pageProp: "total",
        bootpag: {
            maxVisible: 5
        }
    };
    var userID;
    return {
        init:function(){
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
            $('#assignButton').click(function(){
                var roleids = [];
                $('.roleList').each(function(){
                    if($(this).attr('checked')==="checked")
                        roleids.push($(this).val());
                });
                var idList = roleids.join(",");
                $.getJSON("setUserRoles.action",{userID:userID,idList:idList},function(jsonData){
                    bootbox.alert(jsonData.msg);
                    $('#assignRolesDiv').modal('hide');
                    $("#gridtable").data("tableManager").initData();
                })
            });
            $.getJSON('getAllRoles.action',function(data){
                $('.checkbox-list').empty();
                $.each(data.rows,function(index,item){
                    $('.checkbox-list').append('<label class="checkbox-inline"><input class="roleList" type="checkbox" name="roleIDS" value="'+item.id+'">'+ item.comment + '</label>');
                });
                App.initUniform();

            });
            commonCRUD.init();
        },
        assignRoles:function(id){
            userID = id;
            $('.roleList').each(function(){
                $(this).attr('checked',false).parent().removeClass('checked');
            });
            $.post('getUserRoles.action',{userID:id},function(resText){
                var ids = resText.split(",");
                $.each(ids,function(i,v){
                    $('.roleList').filter(function(index){
                        return $(this).val()==v;
                    }).attr('checked',true).parent().addClass('checked');
                });
            });
            $('#assignRolesDiv').modal('show');
        }
    }
}();
userMgr.init();