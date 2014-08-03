/**
 * Created by Administrator on 2014/7/22.
 */
var communityMgr = function(){

    var cityName,provinceName,wholeName;
    var handleSelector = function(){
        $('#province').ajaxselect().on('change',function(){
            var val = $(this).val();
            if(val!='-1'){
                $('#city').ajaxselect({url:'estate/listCity.action?city='+val,defaultText:'城市'});
                provinceName = $("option:selected",this).text();
                $('#district').empty().select2('destroy');
            }
        });

        $('#city').on('change',function(){

            var val = $(this).val();
            if(val!='-1'){
                $('#district').ajaxselect({url:'estate/listCity.action?city='+val,defaultText:'地区'});
                cityName = $("option:selected",this).text();
            }
        });

        $('#district').on('change',function(){
            var val = $(this).val();
            if(val!='-1'){
                wholeName = provinceName+cityName+$("option:selected",this).text();
                $('#cityName').val(wholeName);
            }
        });

        $('#editLink').on('click',function(){
            $('#estateuser').attr('readonly','true');
        });
    };


    return {
        init:function(){
            handleSelector();
        }
    }

}();
communityMgr.init();