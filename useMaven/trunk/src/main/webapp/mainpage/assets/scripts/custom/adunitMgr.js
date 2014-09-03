/**
 * Created by Administrator on 2014/8/21.
 */
var adunitMgr = function(){
    var loaded = false;
    var filterHandler = function(){

        $.getJSON("admgr/getcolList.action",function(jsonData){
            var list = "<li><a href='javascript:adunitMgr.filter(\"all\");'>全部</a> </li>";
            list += "<li><a href='javascript:adunitMgr.filter(\"\");'>滚动广告</a></li>"
            $.each(jsonData,function(index,item){
                list += "<li><a href='javascript:adunitMgr.filter(\""+ item.id +"\");'>"+ item.name +"</a></li>";
            });
            $('#filterList').append(list);
        })
    };
    return{
        init:function(){
            filterHandler();
            if(loaded==false) {
                $('.fancybox-btn').fancybox({
                    maxHeight: 400
                });
                loaded = true;
            }
        },
        filter:function(index){
            var opts = {};
            opts.action = "admgr/listadcontent.action?col="+index;
            $('#gridtable').data('tableManager').initData(opts,false);
        }
    }
}();