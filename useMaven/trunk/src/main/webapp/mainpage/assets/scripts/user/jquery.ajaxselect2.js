/**
 * Created by Administrator on 14-7-3.
 */
;
(function($){
    var defaultSetting = {
        showText:'name',
        defaultText:''
    };
    var setting;
    var methods={
        init:function(options){
            return this.each(function(){
                var $this = $(this);
                if($this.data('options')===undefined)
                    setting =  $.extend({},defaultSetting,options);
                else {
                    var htmlsetting = eval("({" + $this.data('options') + "})");
                    setting = $.extend(defaultSetting, htmlsetting, options);
                }
                if(setting.url===undefined){
                    console.error('ajaxselect2 must has url option!');
                    return;
                }
                $this.select2({
                    placeholder: "请选择"+setting.defaultText,
                    ajax: {
                        url:setting.url,
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
                        return data[setting.showText];//markup.join("");
                    },
                    formatSelection: function (data) {
                        return data[setting.showText];
                    },
                    initSelection:function(element,callback){
                        return $.getJSON(setting.url,function(jsonData){
                            $.each(jsonData,function(index,item){
                                if(item.id===element.val())
                                    callback(item);
                            });
                        });
                    }
                })
            })
        }
    };

    $.fn.ajaxselect2 = function(){
        var method = arguments[0];
        var args;
        if (methods[method]) {
            method = methods[method];
            args = Array.prototype.slice.call(arguments, 1);
        } else if (typeof method === "object" || !method ) {
            method = methods.init;
            args = Array.prototype.slice.call(arguments, 0);
        } else {
            $.error("Method" + method + "does not exist on jQuery.ajaxselect");
            return this;
        }
        return method.apply(this, args);
    };
})(jQuery);