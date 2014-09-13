/**
 * Created by Administrator on 2014/9/13.
 */
/**
 * Created by Administrator on 2014/9/4.
 */
/**
 * Created by Administrator on 2014/7/22.
 */
var logMgr = function(){

    var theForm = $('#searchForm');
    var toggleSearch = function(){
        $('#searchPanel').slideToggle();
        $('#calllogtable').slideToggle();
    };
    var handlerButton = function(){
        $('#searchButton').on('click',toggleSearch);
        $('#closeSearchForm').on('click',toggleSearch);
        $('#searchSubmit').on('click',function(){
            var params = theForm.formSerialize();
//            console.log(params);
            var url = "estate/callLogSearch.action?"+params;
            $('#calllogtable').data('tableManager').initData({action:url},false);
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