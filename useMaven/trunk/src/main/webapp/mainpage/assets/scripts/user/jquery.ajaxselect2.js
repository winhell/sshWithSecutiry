/**
 * Created by Administrator on 14-7-3.
 */
;
(function($){
    $.fn.ajaxselect2 = function(){
        var showText = $(this).data('show');
        var url = $(this).data('url');

        $(this).select2({
            placeholder: "请选择",
            ajax: {
                url:url,
                dataType: 'json',
                type: "POST",
                quietMillis: 100,
                data: function (term, page) { // page is the one-based page number tracked by Select2
                    return {
                        name: term
                    };
                },
                results: function (data, page) {
                    return {results: data};
                }
            },
            formatResult: function (data) {
                return data[showText];//markup.join("");
            },
            formatSelection: function (data) {
                return data[showText];
            },
            initSelection:function(element,callback){
                return $.getJSON(url,function(jsonData){
                    $.each(jsonData,function(index,item){
                        if(item.id===element.val())
                            callback(item);
                    });
                });
            }
        })

    };
})(jQuery);