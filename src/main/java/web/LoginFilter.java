package web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {
	private String[] paths;
	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		//将无需过滤的请求排除掉
//		String[] paths = new String[]{
//				"/tologin.do",
//				"/createimg.do",
//				"/login.do"
//				
//		};
		String sp = request.getServletPath();
		for(String p : paths){
			//若当前路径是排除的路径之一,
			//则无需检查是否登录,请求继续.
			if(p.equals(sp)){
				chain.doFilter(request, response);
				return;
			}
		}
		//尝试从session中获取帐号
		HttpSession session = request.getSession();
		String user = (String)session.getAttribute("user");
		//判断用户是否已登录
		if(user==null){
			//未登录,重定向到登录页面
			response.sendRedirect(request.getContextPath()+"/tologin.do");
		}else{
			//已登录,请求继续执行
			chain.doFilter(request,response);
		}
	}
	
	public void init(FilterConfig config) throws ServletException {
			String excludePath  = config.getInitParameter("excludePath");
			paths = excludePath.split(",");
	}

}
