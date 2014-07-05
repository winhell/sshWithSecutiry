/**
 * Created by Administrator on 14-7-4.
 */
var menuMgr = function(){
    var opts = {
        action: "listmenus.action",
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
                "mData":"url",
                "bSortable":false
            },
            {
                "mData": "icon",
                "bSortable": false
            },{
                "mData":"showOrder",
                "bSortable":false
            },{
                "mData":"comment",
                "bSortable":false
            }
        ],
        dataProp: "rows",
        pageProp: "total",
        bootpag: {
            maxVisible: 5
        }
    };


    return {
        init: function(){
            $("#gridtable").tableManager(opts);
            $('#parentId').ajaxselect();
            commonCRUD.init();
        }
    }
}();

menuMgr.init();