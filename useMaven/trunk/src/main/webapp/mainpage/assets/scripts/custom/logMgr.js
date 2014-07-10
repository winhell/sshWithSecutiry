/**
 * Created by Administrator on 14-7-10.
 */
var logMgr = function(){

    var theForm = $('#searchForm');
    var toggleSearch = function(){
        $('#searchPanel').slideToggle();
        $('#gridtable').slideToggle();
    };
    var handlerButton = function(){
        $('#searchButton').on('click',toggleSearch);
        $('#closeSearchForm').on('click',toggleSearch);
        $('#searchSubmit').on('click',function(){
            var params = theForm.formSerialize();
//            console.log(params);
            var url = "system/logSearch.action?"+params;
            $('#gridtable').data('tableManager').initData({action:url},false);
            theForm.resetForm();
            toggleSearch();
        });
    };
    return {
        init:function(){
            $('.date-picker').datepicker({ orientation: "left",
                autoclose: true,language:"zh-CN"});
            handlerButton();
        }
    }
}();

logMgr.init();