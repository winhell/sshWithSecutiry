/**
 * Created by Administrator on 2014/7/28.
 */
var buildingMgr = function(){

    var buildingTree = $('#buildingTree');

    var idSelect;
    var formAction;

    var getSelectNode = function(){
        var id = buildingTree.jstree('get_selected',true);
        if(id===undefined||id.length===0){
            bootbox.alert("请选择一个节点进行操作！");
            return false;
        }
        idSelect = id[0];
        return true;
    };

    var formOptions = {
        url:'estate/autoCreate.action',
        target:'#createForm',
        dataType:'json',
        success:function(jsonData){
            buildingTree.jstree('refresh');
        }
    };

    var handlerButton=function(){

        $('#hideForm').on('click',function(){
            $('#addFormDiv').slideUp();
        });
        $('#addLink').on('click',function(){
            if(getSelectNode()){
                formAction = 'estate/addbuilding.action';
                $('#name').val("");
                $('#addFormDiv').slideDown();
            }
        });

        $('#editLink').on('click',function(){
            if(getSelectNode()){
                formAction = 'estate/updatebuilding.action';
                $('#name').val(idSelect.text);
                $('#addFormDiv').slideDown();
            }
        });

        $('#deleteLink').on('click',function() {
            if(getSelectNode()){
                bootbox.confirm('确定要删除节点'+idSelect.text+'及其下属节点吗？',function(result){
                    if(result){
                        $.getJSON('estate/deletebuilding.action',{id:idSelect.id},function(jsonData){
                            bootbox.alert(jsonData.msg);
                            buildingTree.jstree('refresh');
                        })
                    }
                });
            }
        });

        $('#formSubmit').on('click',function(){
            $.ajax({
                url:formAction,
                dataType:'json',
                type:'POST',
                data:{name:$('#name').val(),parent:idSelect.id,isGate:$('#isGate').attr("checked")?$('#isGate').val():""},
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success:function(jsonData){
                    if(jsonData.status=="SUCCESS"){
                        buildingTree.jstree('refresh');
                        $('#addFormDiv').slideUp();
                    }
                    bootbox.alert(jsonData.msg);
                    $('#name').val("");
                    $('#isGate').removeAttr("checked");
                }
            });

        });

        $('#createButton').on('click',function(){
            if(getSelectNode()) {
                $('#parentID').val(idSelect.id);
                $('#createRoomDiv').modal('show');
            }
        });
    };
    return {
        init:function(){
            $('#createForm').ajaxForm(formOptions);
            $('#isGate').bootstrapSwitch();
            buildingTree.jstree({
                'core' : {
                    'data' : {
                        'url' : 'estate/getBuildingTree.action?id=-1&='+Math.floor(Math.random() * (new Date()).getTime())
                    }
                },'types':{
                    'default':{
                        "icon" : "glyphicon glyphicon-home"
                    }
                },
                "plugins" : [ "types" ,"sort"]
            });
            handlerButton();

        }
    }
}();

buildingMgr.init();