/**
 * Created by Administrator on 14-7-2.
 */
var userMgr = function () {

    var userID;
    return {
        init:function(){
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
                $.getJSON("system/setUserRoles.action",{userID:userID,idList:idList},function(jsonData){
                    bootbox.alert(jsonData.msg);
                    $('#assignRolesDiv').modal('hide');
                    $("#gridtable").data("tableManager").initData();
                })
            });
            $.getJSON('system/getAllRoles.action',function(data){
                $('.checkbox-list').empty();
                $.each(data.rows,function(index,item){
                    $('.checkbox-list').append('<label class="col-md-4"><input class="roleList" type="checkbox" name="roleIDS" value="'+item.id+'">'+ item.comment + '</label>');
                });
                App.initUniform();

            });
        },
        assignRoles:function(id){
            userID = id;
            $('.roleList').each(function(){
                $(this).attr('checked',false).parent().removeClass('checked');
            });
            $.post('system/getUserRoles.action',{userID:id},function(resText){
                var ids = resText.split(",");
                $.each(ids,function(i,v){
                    $('.roleList').filter(function(index){
                        return $(this).val()==v;
                    }).attr('checked',true).parent().addClass('checked');
                });
            });
            $('#assignRolesDiv').modal('show');
        },
        assignRender:function(data){
            return '<a href="javascript:userMgr.assignRoles(\''+data+'\');" class="btn btn-xs purple"> 分配角色</a>';
        }
    }
}();
userMgr.init();