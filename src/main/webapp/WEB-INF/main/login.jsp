<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>达内－NetCTOSS</title>
<link type="text/css" rel="stylesheet" media="all"
	href="styles/global.css" />
<link type="text/css" rel="stylesheet" media="all"
	href="styles/global_color.css" />
</head>
<body class="index">
	<div class="login_box">
		<form action="login.do" method="post">
			<table>
				<tr>
					<td class="login_info">账号：</td>
					<td colspan="2"><input name="user" type="text" class="width150" value="${param.user}" /></td>
					<td class="login_error_info"><span class="required">30长度的字母、数字和下划线</span></td>
				</tr>
				<tr>
					<td class="login_info">密码：</td>
					<td colspan="2"><input name="pwd" type="password"
						class="width150" /></td>
					<td><span class="required">30长度的字母、数字和下划线</span></td>
				</tr>
				<tr>
					<td class="login_info">验证码：</td>
					<td class="width70"><input name="code" type="text" class="width70" /></td>
					<!-- 在后面增加随机参数的目的,是为了
						 欺骗浏览器，让它误以为路径发生了变化.
					 -->
					<td><img src="createimg.do" onclick="this.src='createimg.do?x='+Math.random()" alt="验证码" title="点击更换" /></td>
					<td><span class="required"></span></td>
				</tr>
				<tr>
					<td></td>
					<td class="login_button" colspan="2">
					<!-- 表单提交有2中方式:
						1.点击submit按钮,从而触发表单的onsubmit
					 -->
					  <a href="javascript:document.forms[0].submit()">
						<img src="images/login_btn.png" />
					  </a>
					</td>
					<td><span class="required">${err}</span></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
