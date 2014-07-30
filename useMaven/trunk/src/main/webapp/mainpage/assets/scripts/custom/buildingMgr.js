/**
 * Created by Administrator on 2014/7/28.
 */
var buildingMgr = function(){

    var buildingTree = $('#buildingTree').jstree({
        'core' : {
            'data' : {
                'url' : 'estate/getBuildingTree.action?id=-1'
            }
        }
    });

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

    var handlerButton=function(){
        $('#addLink').on('click',function(){
            if(getSelectNode()){
                formAction = 'estate/addbuilding.action';
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
                data:{name:$('#name').val(),parent:idSelect.id},
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success:function(jsonData){
                    if(jsonData.status=="SUCCESS"){
                        buildingTree.jstree('refresh');
                        $('#addFormDiv').slideUp();
                    }
                    bootbox.alert(jsonData.msg);
                    $('#name').val("");
                }
            });

        });
    };
    return {
        init:function(){
            handlerButton();
        }
    }
}();

buildingMgr.init();