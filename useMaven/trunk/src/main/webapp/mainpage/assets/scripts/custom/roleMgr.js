/**
 * Created by Administrator on 14-7-4.
 */
var roleMgr = function(){
    var opts = {
        action: "getAllRoles.action",
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
                "mData": "createtime",
                "bSortable": false
            },{
                "mData":"comment",
                "bSortable":false
            },{
                "mData":"id",
                "bSortable":false,
                "mRender":function(data){
                    return '<a href="javascript:roleMgr.showPriv(\''+data+'\');" class="btn btn-xs purple"> 分配权限</a>';
                }
            }
        ],
        dataProp: "rows",
        pageProp: "total",
        bootpag: {
            maxVisible: 5
        }
    }
    var menutree = $('#menutree').jstree({
        'core' : {
            'data' : {
                'url' : 'getMenuTree.action'
            }
        },
        'plugins':['checkbox']
    });

    var roleId;

    return {
        init: function(){
            $("#gridtable").tableManager(opts);
            commonCRUD.init();

            $('#assignButton').click(function(){
                var selectedIds = menutree.jstree('get_selected',false);
                console.log(selectedIds);
                $('.jstree-undetermined').each(function(index,item){
                    var undeterminedId = $(this).parents('.jstree-node').attr('id');
                    selectedIds.push(undeterminedId);
                });
                console.log(selectedIds);
                var ids = selectedIds.join(",");
                $.getJSON('setRoleMenu.action',{idList:ids,roleId:roleId},function(resJSON){
                    if(resJSON.status==='SUCCESS'){
                        bootbox.alert(resJSON.msg);
                        $('#assignMenuDiv').modal('hide');
                    }else
                        bootbox.alert(resJSON.msg);

                });

            });
        },
        showPriv : function(id){
            menutree.jstree('deselect_all');
            $.post('getMenusByRole.action',{roleId:id},function(res){
                var ids = res.split(",");
                $.each(ids,function(index,item){
                    menutree.jstree('select_node',item);
                });
                roleId = id;
                $('#assignMenuDiv').modal('show');
            });

        }
    }
}();

roleMgr.init();