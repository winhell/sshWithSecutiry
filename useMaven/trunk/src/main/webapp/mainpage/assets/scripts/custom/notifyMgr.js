/**
 * Created by Administrator on 2014/8/12.
 */
var notifyMgr = function(){

    var buildingID;
    var buildingTree = $('#buildingTree').jstree({
        'core' : {
            'data' : {
                'url' : 'estate/getBuildingTree.action?id=-1'
            }
        },'types':{
            'default':{
                "icon" : "glyphicon glyphicon-home"
            }
        },
        "plugins" : [ "types" ]
    });

    var handleButton = function(){
        $('#type').on('change',function(){          //更改按钮状态
            var n_type = $(this).val();
            if(n_type!="specical"){
                $('#buildingName').val("");
                $('#buildingSelection').addClass("disabled");
            }
            if(n_type=="specical"){
                $('#buildingSelection').removeClass("disabled");
            }
        });

        //显示地址对话框
        $('#buildingSelection').on('click',function(){
            $('#address').modal('show');
        });

        //显示房间全名
        $('#selectAddress').on('click',function(){
            var buildingSelect = buildingTree.jstree('get_selected',true);
            if(buildingSelect.length<1||!buildingTree.jstree('is_leaf',buildingSelect[0])){
                bootbox.alert('请选择用户房间号');
            }else{
                buildingID = buildingSelect[0].id;
                $.get('estate/getUserAddress.action',{id:buildingID},function(resText){
                    $('#buildingName').val(resText);
                    $('#to').val(buildingID);
                    $('#address').modal('hide');
                });

            }

        });
    };
    return{
        init:function(){
            handleButton();
            $('.date').datetimepicker({ orientation: "left",
                autoclose: true,language:"zh-CN"});
            $('#type').select2({
                placeholder:"请选择通告对象"
            });
        }
    }
}();
notifyMgr.init();