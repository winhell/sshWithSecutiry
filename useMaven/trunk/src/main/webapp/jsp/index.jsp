<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="SHORTCUT ICON" href="images/favicon.ico" />
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>科技项目管理系统</title>

    <link id="accordin" rel="stylesheet" type="text/css" href="css/default.css" />
    <link id="easyuiTheme" rel="stylesheet" type="text/css"	href="../easyui/themes/bootstrap/easyui.css" />
    <link id="easyuiicon"  rel="stylesheet" type="text/css" href="../easyui/themes/icon.css" />
    <script type="text/javascript" src="../easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
    <script src="../easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <script src="js/common_fn.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/init.js"></script>
    <script type="text/javascript" src="js/commonCRUD.js"></script>
</head>


<body class="easyui-layout">
<div data-options="region:'north',border:false"
     style="height: 100px; font-size: xx-large;overflow:hidden; background: #2eb0de url('images/top.jpg') no-repeat;">
    <h1 style="font-size:24px;padding-left:300px;padding-top:20px;">江西省卫生厅科技项目管理系统</h1>
    <div style="position: absolute; right: 5px; top: 10px;"
         id="div_welcom">
       欢迎你： <sec:authentication property="principal.username"/>
    </div>
    <div style="position: absolute; right: 0px; bottom: 0px;">

        <a href="javascript:void(0);" class="easyui-menubutton"
           data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">
            控制面板</a>
        <a href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'icon-back'" onclick="logout()">退出系统</a>
    </div>
</div>
<div data-options="region:'west',split:true,title:'导航菜单'"
     style="width: 180px;" id="westreg">
</div>
<div data-options="region:'south',border:true,split:false" style="height: 25px; background: #e0ecff;
        text-align: center; line-height: 23px">
    研发与技术支持 @南昌大学网络中心</div>
<div data-options="region:'center'">
    <div id="tabs" class="easyui-tabs" fit="true" border="false"
         data-options="
                    tools:[{iconCls : 'icon-reload',handler:refreshTab},
                    {iconCls : 'icon-cancel',handler:closeTab}]
                    ">
        <!--
    <div title="欢迎使用" style="padding: 20px; overflow: hidden;" id="home">
    </div>
    -->
    </div>
</div>
<div id="mm" class="easyui-menu" style="width: 150px;">
    <div id="mm-tabclose">
        关闭
    </div>
    <div id="mm-tabcloseall">
        全部关闭
    </div>
    <div id="mm-tabcloseother">
        除此之外全部关闭
    </div>
    <div class="menu-sep">
    </div>
    <div id="mm-tabcloseright">
        当前页右侧全部关闭
    </div>
    <div id="mm-tabcloseleft">
        当前页左侧全部关闭
    </div>
    <div class="menu-sep">
    </div>
    <div id="mm-exit">
        退出
    </div>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
    <div onclick="changepwds();">
        修改密码
    </div>
    <div class="menu-sep" id="switchdisplay2">
    </div>
    <div onclick="swititchcontrol();" id="switchdisplay">
    </div>

    <div class="menu-sep" id="changeversion2">
    </div>

    <div class="menu-sep" id="changebonusparamdisplay2">
    </div>
    <div>
        <span>更换主题</span>
        <div style="width: 120px;">
            <div onclick="changeTheme('default');">
                默认
            </div>
            <div onclick="changeTheme('gray');">
                灰色
            </div>
            <div onclick="changeTheme('metro');">
                美俏
            </div>
            <div onclick="changeTheme('black');">
                暗黑
            </div>
            <div onclick="changeTheme('bootstrap');">
                Bootstrap
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="hd_account" />
</body>
</html>
