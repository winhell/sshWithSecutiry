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
        var options="<option value='-1'>---请选择---</option> ";
        var opts = eval("({"+$(this).data('options')+"})");
        $.getJSON(opts.url,function(jsondata){
            $.each(jsondata,function(index,item){
//                options.push("<option value='",item[opts.valueField],"'>",item[opts.textField],"</option>");
                options += "<option value='"+item[opts.valueField]+"'>"+item[opts.textField]+"</option>";
            });
            ele.append(options);
        });
    };
})(jQuery);