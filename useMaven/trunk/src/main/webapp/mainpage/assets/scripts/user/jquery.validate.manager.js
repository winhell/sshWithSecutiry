/**
 * 验证插件的二次封装，将统一规则验证
 */

;
(function ($) {
    $.fn.validation = function (options) {
        var o = $.extend($.fn.validation.options, options);
        return this.each(function () {
            var that = $(this);
            /*o.validate.submitHandler=function(form){
                var val = $(form).find("[data-role='param']");
                var param = {};
                val.each(function (index, control) {
                    if ($(control).is("select")) {
                        param[control.name] = $(control).find("option:selected").val();
                    }
                    else {
                        param[control.name] = control.value;
                    }
                });
                $.post(form.action, param, function (data) {
                    o.callback(data,form)
                }, "json");
            }*/
            that.validate(o);
        })
    };
    $.fn.validation.options={
        /*invalidHandler: function (event, validator) { //display error alert on form submit
            var errors = validator.numberOfInvalids();
            if(errors){

            }
        },*/
        meta: "validate",
        highlight: function (element) { // hightlight error inputs
            $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
        },
        errorPlacement: function (error, element) {
            var tips=$(element).closest("form").find(".form-tips");
            tips.slideUp("slow",function(){
                tips.remove();
            });
        },
        success: function (label, element) {
            $(element).closest('.form-group').removeClass('has-error');
        }
    }
    /*$.fn.validation.options = {
        validate: {
            highlight: function (element) { // hightlight error inputs
                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
            },
            errorPlacement: function (error, element) {
                $(element).closest("form").find(".form-tips").remove();
            },
            success: function (label, element) {
                $(element).closest('.form-group').removeClass('has-error');
            }
        },
        callback:function(data,from){
            var html = [];
            switch (data.status) {
                case "failed":
                    html.push("<div class='alert alert-danger form-tips'><i class='fa fa-exclamation-circle'></i> 失败：,", data.reason, "</div>");
                    break;
                case "success":
                    html.push("<div class='alert alert-success form-tips'><i class='fa fa-exclamation-circle'></i> 成功</div>");
                    break;
            }
            $(html.join("")).insertBefore($(from).find(".form-actions"));//$(".buy-form .form-actions"));
        }
    }*/
})
(jQuery);