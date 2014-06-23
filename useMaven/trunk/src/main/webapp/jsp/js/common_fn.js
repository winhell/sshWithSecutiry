/**


 * panel关闭时回收内存，主要用于layout使用iframe嵌入网页时的内存泄漏问题
 */
$.fn.panel.defaults.onBeforeDestroy = function() {

	var frame = $('iframe', this);
	try {
		// alert('销毁，清理内存');
		if (frame.length > 0) {
			for ( var i = 0; i < frame.length; i++) {
				frame[i].contentWindow.document.write('');
				frame[i].contentWindow.close();
			}
			frame.remove();
			if ($.browser.msie) {
				CollectGarbage();
			}
		}
	} catch (e) {
	}
};

/**
 * 在iframe中调用，在父窗口中出提示框(herf方式不用调父窗口)
 */
$.extend({
	show_warning : function(strTitle, strMsg) {
		$.messager.show({
			title : strTitle,
			msg : strMsg,
			showType : 'slide',
			style : {
				right : '',
				top : document.body.scrollTop
						+ document.documentElement.scrollTop,
				bottom : ''
			}
		});
	}
});

$.extend({
	show_alert : function(strTitle, strMsg) {
		$.messager.alert(strTitle, strMsg);
	}
});

/**
 * 
 * 
 * @requires jQuery,EasyUI
 * 
 * 防止panel/window/dialog组件超出浏览器边界
 * @param left
 * @param top
 */

var easyuiPanelOnMove = function(left, top) {
	var l = left;
	var t = top;
	if (l < 1) {
		l = 1;
	}
	if (t < 1) {
		t = 1;
	}
	var width = parseInt($(this).parent().css('width')) + 14;
	var height = parseInt($(this).parent().css('height')) + 14;
	var right = l + width;
	var buttom = t + height;
	var browserWidth = $(window).width();
	var browserHeight = $(window).height();
	if (right > browserWidth) {
		l = browserWidth - width;
	}
	if (buttom > browserHeight) {
		t = browserHeight - height;
	}
	$(this).parent().css({/* 修正面板位置 */
		left : l,
		top : t
	});
};
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.panel.defaults.onMove = easyuiPanelOnMove;

/**
 * 
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展tree，使其支持平滑数据格式
 */
$.fn.tree.defaults.loadFilter = function(data, parent) {
	var opt = $(this).data().tree.options;
	var idFiled, textFiled, parentField;
	// alert(opt.parentField);
	if (opt.parentField) {
		idFiled = opt.idFiled || 'id';
		textFiled = opt.textFiled || 'text';
		parentField = opt.parentField;
		var i, l, treeData = [], tmpMap = [];
		for (i = 0, l = data.length; i < l; i++) {
			tmpMap[data[i][idFiled]] = data[i];
		}
		for (i = 0, l = data.length; i < l; i++) {
			if (tmpMap[data[i][parentField]]
					&& data[i][idFiled] != data[i][parentField]) {
				if (!tmpMap[data[i][parentField]]['children'])
					tmpMap[data[i][parentField]]['children'] = [];
				data[i]['text'] = data[i][textFiled];
				tmpMap[data[i][parentField]]['children'].push(data[i]);
			} else {
				data[i]['text'] = data[i][textFiled];
				treeData.push(data[i]);
			}
		}
		return treeData;
	}
	return data;
};

/**
 * 
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展combotree，使其支持平滑数据格式
 */
$.fn.combotree.defaults.loadFilter = $.fn.tree.defaults.loadFilter;

/**
 * 
 * 
 * @requires jQuery,EasyUI
 * 
 * 通用错误提示
 * 
 * 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作/combotree
 */
var easyuiErrorFunction = function(XMLHttpRequest) {
	$.messager.progress('close');
	// $.messager.alert('错误', XMLHttpRequest.responseText);
	if (XMLHttpRequest.responseText == "timeout") { // 未登录
		$.relogin();
	} else {
		// window.parent.window.$.messager.alert('错误',
		// XMLHttpRequest.responseText);
		alert("服务器繁忙请稍后再试");
	}
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;
$.fn.combotree.defaults.onLoadError = easyuiErrorFunction;

/**
 * 
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展validatebox，添加验证两次密码功能
 */
$.extend($.fn.validatebox.defaults.rules, {
	eqPwd : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	}
});

/*
 * 
 * 用户未登录 或session超时 弹出登录框重新登录
 */
$.extend({relogin : function() {
				$("<div/>")
						.attr("id", "dialog_relogin")
						.dialog(
								{
									title : " 未登录或登录超时，请重新登录",
									href : "relogin.html",
									width : 300,
									height : 200,
									closable : false,
									iconCls : "icon-lock",
									modal : true,
									buttons : [
											{
												text : "登录",
												iconCls : "icon-key",
												handler : function() {
													var para = {};
													para.account = $(
															"#relogin_account")
															.val();
													para.password = $(
															"#relogin_pwd")
															.val();
													para.timespan = new Date()
															.getTime();
													$
															.ajax({
																url : "login.action",
																type : "POST",
																data : para,
																dataType : "text",
																success : function(
																		result) {
																	var mapresult = eval("(" + result + ")");
																    var lastlogintime=mapresult.lastlogintime;
																	dealAjaxResult(
																			result,
																			function(
																					r) {
																				$
																						.show_warning(
																								"提示",
																								"登录成功,上次登入时间"+lastlogintime);
																				$(
																						"#dialog_relogin")
																						.dialog(
																								'destroy');
																				InitLeftMenu();
																				getSession(function f(
																						r) {
																					try {
																						var user = eval("("
																								+ r
																								+ ")");
																						if (user.status ==0)
																						{
																						$.activate("activate.html",
																								"oa_OperActivate.action",350,250);
																						}
																						var role=getRoleName(user.roleName);
																						if(user.roleName!="admin")
																						{
																						  $("#changebonusparamdisplay").remove();
																						  $("#switchdisplay").remove();
																						  $("#switchdisplay2").remove();
																						  $("#changebonusparamdisplay2").remove();
																						  $("#changeversion2").remove();
																						  $("#changeversion").remove();
																						}
																						$("#div_welcom").html("<b>" + role + ":" + user.account + "，欢迎您！");
																						$(
																								"#hd_account")
																								.val(
																										user.account);
																					} catch (e) {
																					}

																				})
																			});

																}
															});
												}

											},
											{
												text : "退出系统",
												iconCls : "icon-cancel",
												handler : function() {
													window.location.href = "../login.html";
												}
											} ]
								})
			}
		});

/*
 * 
 * 用户激活
 */
$.extend({activate : function(url, activateAction, width, height) {

		$("<div/>").attr("id", "dialog_activate").dialog({
			title : "修改初始密码",
			href : url,
			width : width,
			height : height,
			closable : false,
			iconCls : "icon-lock",
			modal : true,
			buttons : [ {
				text : "提交",
				iconCls : "icon-key",
				handler : function() {
					var d = $(this).closest('.window-body');
					$("#f_activate").form("submit", {
						url : activateAction,
						onSubmit : function() {
							return $(this).form('validate');
						},
						success : function(result) {
							dealAjaxResult(result, function(r) {
								d.dialog('destroy');
									$.show_warning("提示", "修改成功");
							});
						}
					});
				}

			}, {
				text : "下次修改",
				iconCls : "icon-cancel",
				handler : function() {
					$(this).closest('.window-body').dialog('destroy');
					
				}
			} ]
		})
	}
});

/*
 * zf 获取session
 */
function getSession(deal) {
	// alert(12);
	$.ajax({
		url : "getSession.action?_"+new Date().getTime(),
		type : "POST",
		success : function(r) {
			deal(r);
		}
	});
}
/*
 * 判断后台开关的状态
 */

/*
 * zf 处理ajax返回值通用处理方法
 * 
 */
function dealAjaxResult(data, okFun) {
	var result = data.result;
	var reason = data.msg;
	if (result == "timeout") {
		$.relogin();
	}

	else if (result == "SUCCESS") {
		okFun(reason);
	} else if (result == "FAIL") {
		// $.show_alert("错误",reason);
		alert(reason);
	}
}

/**
 * 
 * 
 * @requires jQuery
 * 
 * 改变jQuery的AJAX默认属性和方法
 */
$.ajaxSetup({
	// type: 'POST',
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		$.messager.progress('close');
		$.messager.alert('错误', XMLHttpRequest.responseText);
	}
});

/*
 * 
 */
function loadForm(formid, data) {
	for ( var item in data) {
		var loca = formid + " #" + item;
		var classname = $(loca).attr("class");
		if (classname == "easyui-datebox datebox-f combo-f"
				&& data[item] != null && data[item].length > 8) {
			$(loca).datetimebox({
				value : data[item],
				formatter : formaterdate,
				parser : parserdate
			});
		}
		else if(classname=="easyui-combobox combobox-f combo-f")
		{
		$(loca).combobox(
				'select', data[item]);
		}
		else {
			$(loca).val(data[item]);
		}
	}
}
function formaterDateBox(dateBoxId, stringDate) {
	$(dateBoxId).datetimebox({
		value : stringDate,
		formatter : formaterdate,
		parser : parserdate
	});
}
function formaterdate(date) {
	return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
			+ date.getDate();
}
function parserdate(date) {
	return new Date(Date.parse(date.replace(/-/g, "/")));
}
function formatterstringdate(val) {
	if (val == null)
		return "";
	if (val.length > 10)
		return val.substring(0, 10);
	return val;
}
function formatterstatus(val) {
	if (val == null)
		return "";
	else if (val == '0')
		return '等待核审';
	else if (val == '1')
		return '已审核';
	else if (val == '2')
		return '审核未通过';
}

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}


function changepwds() {
	$("<div/>").attr("id", "dialog_changepassword").dialog({
		title : " 修改密码",
		href : "changepassword.html",
		width : 280,
		height : 220,
		closable : false,
		iconCls : "icon-lock",
		modal : true,
		buttons : [ {
			text : "确定修改",
			iconCls : "icon-key",
			handler : function() {
				var para = {};
				para.oldPassword = $("#oldPassword").val();
				para.newPassword = $("#newPassword").val();
				para.verifyPassword = $("#verifyPassword").val();
				para.action = "changepassword";
				if (para.oldPassword == null || para.oldPassword == "") {
					alert("请输入原密码");
					return;
				}
				if (para.newPassword == null && para.newPassword == "") {
					alert("新密码不能为空");
					return;
				}
				if (param.newPassword.length < 6) {
					alert("密码不能少于6位");
					return;
				}
				if (para.verifyPassword == null && para.verifyPassword == "") {
					alert("确认密码不能为空");
					return;
				}
				if (para.verifyPassword != para.newPassword) {
					alert("两次密码输入不一致");
					return;
				}
				$.ajax({
					url : "changePassword.action",
					type : "POST",
					data : para,
					dataType : "json",
					success : function(result) {
						dealAjaxResult(result, function(r) {
							$.show_warning("提示", "修改成功");
							$("#dialog_changepassword").dialog('destroy');
						});
					}
				});
			}

		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#dialog_changepassword").dialog('destroy');
			}
		} ]
	})
}
function logout() {
	$.messager.confirm('提示！', '确定退出系统？', function(r) {
		if (r) {
			var para = {
				"action" : "logout"
			};
			$.ajax({
				url : "/auth/logout",
				type : "post",
				data : para,
				success : function(r) {
					window.location.href = "index.jsp";
				}
			});
		}
	});
}

function detailinfo(hrefurl,title,height,width,datagridid) {
	//alert(1);
	var r = $("#"+datagridid).datagrid("getChecked");
	if (r.length < 1) {
		$.show_warning("错误", "请选择一条记录");
		return;
	}
	if (r.length > 1) {
		$.show_warning("错误", "一次只能查看一条记录");
		$("#"+datagridid).datagrid('clearSelections').datagrid(
				'clearChecked');
		return;

	}

	$("<div/>")
			.dialog(
					{
						href : hrefurl,
						title :title,
						height : height,
						width : width,
						modal : true,
						iconCls : "icon-edit",
						buttons : [
								{
									text : '',
									iconCls : 'icon-undo',
									handler : function() {
										$(this).closest('.window-body')
												.dialog('destroy');
									}
								} ],
						onClose : function() {
							$(this).dialog('destroy');
						},
						onLoad : function() {
							loadForm("#form_detailinfo",r[0]);

						}
					});

}



function getCurDateyy_mm_dd()
{
 var today = new Date();    
 var day   = today.getDate();         //获取当前日(1-31)  
 var month = today.getMonth() + 1;     //显示月份比实际月份小1,所以要加1
 var year  = today.getFullYear();           //获取完整的年份(4位,1970-????)  \
 month     = month<10?"0"+month:month;          //数字<10，实际显示为，如5，要改成05  
 day       = day<10?"0"+day:day;   
 var date  = year + "-" + month + "-" + day;
 return date;
}
function getNextDateyy_mm_dd()
{
 var today = new Date();                   
 var next=new Date(today.getTime()+86400000);
 var day   = next.getDate();         //获取当前日(1-31)  
 var month = next.getMonth() + 1;     //显示月份比实际月份小1,所以要加1
 var year  = next.getFullYear();           //获取完整的年份(4位,1970-????)  \
 month     = month<10?"0"+month:month;          //数字<10，实际显示为，如5，要改成05  
 day       = day<10?"0"+day:day;   
 var date  = year + "-" + month + "-" + day;
 return date;
}

