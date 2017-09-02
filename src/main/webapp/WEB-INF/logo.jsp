<%@page pageEncoding="utf-8"%>
<img src="images/logo.png" alt="logo" class="left" />
<!-- 
	EL默认的取值范围:
	pageContext,request,session,application
	也可以从cookie中取值,语法如下:
	cookie.NAME.value
 -->
<%-- <span>
${cookie.user.value}
</span> --%>
<span>${sessionScope.user}</span>
<a href="#">[退出]</a>
