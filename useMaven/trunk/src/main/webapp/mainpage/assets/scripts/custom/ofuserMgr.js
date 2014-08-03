/**
 * Created by Administrator on 2014/7/31.
 */
var ofuserMgr = function(){

    var buildingTree = $('#buildingTree').jstree({
        'core' : {
            'data' : {
                'url' : 'estate/getBuildingTree.action?id=-1'
            }
        }
    });

    var buildingID;

    var handleButtons = function(){
        $('#buildingSelection').on('click',function(){
            $('#address').modal('show');
        });

        $('#selectAddress').on('click',function(){
            var buildingSelect = buildingTree.jstree('get_selected',true);
            if(buildingSelect.length<1||!buildingTree.jstree('is_leaf',buildingSelect[0])){
                bootbox.alert('请选择用户房间号');
            }else{
                buildingID = buildingSelect[0].id;
                $.get('estate/getUserAddress.action',{id:buildingID},function(resText){
                    $('#buildingName').val(resText);
                    $('#buildingID').val(buildingID);
                    $('#address').modal('hide');
                });

            }

        })
    };
    return{
        init:function(){
            handleButtons();
        },
        filter:function(typeName){
            var opts = {};
            opts.action = 'estate/userlist.action?typeName='+typeName;
            $('#gridtable').data('tableManager').initData(opts,false);
        }
    }

}();
ofuserMgr.init();