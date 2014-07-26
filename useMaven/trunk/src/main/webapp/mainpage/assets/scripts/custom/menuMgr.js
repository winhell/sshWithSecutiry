/**
 * Created by Administrator on 14-7-4.
 */
var menuMgr = function(){

    var test = function(){
        console.log('I changed!');
    };

    return {
        init: function(){
            $('#parentId').ajaxselect();
        }
    }
}();

menuMgr.init();