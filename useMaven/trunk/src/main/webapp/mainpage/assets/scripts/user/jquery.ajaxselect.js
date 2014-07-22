/**
 * Created by Administrator on 14-7-2.
 */
;
(function($){
    $.fn.ajaxselect = function(){
        if($(this).data('options')===undefined)
            return;
        var ele = $(this);
        ele.empty();
        var options="<option value='0'>---请选择---</option> ";
        var opts = eval("({"+$(this).data('options')+"})");
        $.getJSON(opts.url,function(jsondata){
            if(opts.valueField===undefined){       //如果未定义valueField，则视为以Map方式传输数据
                for(var p in jsondata){
                    options += "<option value='"+p+"'>"+jsondata[p]+"</option>";
                }
            }else{
                $.each(jsondata,function(index,item){
//                options.push("<option value='",item[opts.valueField],"'>",item[opts.textField],"</option>");
                    options += "<option value='"+item[opts.valueField]+"'>"+item[opts.textField]+"</option>";
                });
            }
            ele.append(options);
        });
    };
})(jQuery);