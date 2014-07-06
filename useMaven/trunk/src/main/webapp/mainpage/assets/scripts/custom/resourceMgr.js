/**
 * Created by Administrator on 14-7-5.
 */
var resourceMgr = function(){
    var opts = {
        action: 'getAllResources.action',
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
            },{
                "mData":"url",
                "bSortable":false
            },
            {
                "mData": "comment",
                "bSortable": false
            },{
                "mData":"id",
                "bSortable":false,
                "mRender":function(data){
                    return '<a href="javascript:resourceMgr.assignRoles(\''+data+'\');" class="btn btn-xs purple"> 分配角色</a>';
                }
            }
        ],
        dataProp: "rows",
        pageProp: "total",
        bootpag: {
            maxVisible: 5
        }
    };


    var resourceId;
    return {
        init:function(){
            $('#gridtable').tableManager(opts);
            $('#assignButton').click(function(){
                var roleids = [];
                $('.roleList').each(function(){
                    if($(this).attr('checked')==="checked")
                        roleids.push($(this).val());
                });
                var idList = roleids.join(",");
                $.getJSON("assignRoles.action",{resourceId:resourceId,idList:idList},function(jsonData){
                    bootbox.alert(jsonData.msg);
                    $('#assignRolesDiv').modal('hide');
                })
            });
            commonCRUD.init();
            $.getJSON('getAllRoles.action',function(data){
                $('.checkbox-list').empty();
                $.each(data.rows,function(index,item){
                    $('.checkbox-list').append('<label class="checkbox-inline"><input class="roleList" type="checkbox" name="roleIDS" value="'+item.id+'">'+ item.comment + '</label>');
                });
                App.initUniform();
            });

        },
        assignRoles:function(id){
            resourceId = id;
            $('.roleList').each(function(){
                $(this).attr('checked',false).parent().removeClass('checked');
            });
            $.post('getResourceRoles.action',{resourceId:resourceId},function(resText){
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
resourceMgr.init();