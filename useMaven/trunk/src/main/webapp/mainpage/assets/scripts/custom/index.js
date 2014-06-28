var Index = function () {

    var initMenu = function(){
        $.ajax({
            url : "getUserMenus.action",
            type : "POST",
            dataType : "json",
            success : function(_menus) {
                var menulist = "";
                $.each(_menus,function(i, n) {
                    if(n.parentId=="0"){
                        menulist += '<li>' + '<a href="javascript:;"><i class="fa fa-cogs"></i> '
                            +'<span class="title">'+ n.name + '</span> '
                            +'<span class="selected"></span> '
                            +'<span class="arrow"></span> '
                            +'</a> ';
                        menulist += '<ul class="sub-menu">';
                        $.each(_menus,function(index,item){
                            if(item.parentId==n.id){
                                menulist += '<li><a class="ajaxify" href="'+ item.url + '">'
                                    + item.name + '</a> </li>'
                            }
                        });
                        menulist += '</ul></li>';
                    }
                });
                $(".page-sidebar-menu").append(menulist);
            }
        });
    }
    return {

        //main function
        init: function () {
            initMenu();
        }
    }
}();