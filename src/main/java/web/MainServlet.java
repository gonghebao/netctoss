package web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDao;
import dao.CostDao;
import entity.Admin;
import entity.Cost;
import util.ImageUtil;

public class MainServlet extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//获取当前的访问路径
		String path = req.getServletPath();
		
		if("/findcost.do".equals(path)){
			findCost(req,res);
		}else if("/addcost.do".equals(path)){
			addCost(req,res);
		}else if("/deletecost.do".equals(path)){
			deleteCost(req,res);
		}else if("/findmodi.do".equals(path)){   //显示修改页面
			findModiCost(req,res);
		}else if("/modi.do".equals(path)){       //保存修改 重定向到查询页面
			modiCost(req,res);
		}else if("/tologin.do".equals(path)){
			toLogin(req,res);
		}else if("/toIndex.do".equals(path)){
			toIndex(req,res);
		}else if("/login.do".equals(path)){
			login(req,res);
		}else if("/kaitong.do".equals(path)){
			kaitong(req,res);
		}else if("/findadd.do".equals(path)){
			findadd(req,res);
		}else if("/createimg.do".equals(path)){
			createImg(req,res);
		}
		else{
			throw new RuntimeException("没有这个页面");
		}
	}
	protected void findCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//查询所有的资费
		CostDao dao = new CostDao();
		List<Cost> list = dao.findAll();
		//转发到find.jsp
		req.setAttribute("costs",list);
		//当前:/netctoss/findcost.do
		//目标:/netctoss/WEB-INF/cost/find.jsp
		req.getRequestDispatcher("WEB-INF/cost/find.jsp").forward(req,res);
		
	}
	protected void addCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//查询所有的资费
		CostDao dao = new CostDao();
		Cost c = new Cost();
		String name = req.getParameter("name");
		String costType = req.getParameter("costType");
		String baseDuration = req.getParameter("baseDuration");
		String baseCost = req.getParameter("baseCost");
		String unitCost = req.getParameter("unitCost");
		if(!"".equals(baseDuration)&&baseDuration!=null){
			c.setBaseDuration(Integer.parseInt(baseDuration));
		}
		if(!"".equals(baseCost)&&baseCost!=null){
			c.setBaseCost(Double.parseDouble(baseCost));
		}
		if(!"".equals(unitCost)&&unitCost!=null){
			c.setUnitCost(Double.parseDouble(unitCost));
		}
		c.setName(name);
		c.setCostType(costType);
		c.setDescr(req.getParameter("descr"));
		//转发到find.jsp
		//当前:/netctoss/findcost.do
		//目标:/netctoss/WEB-INF/cost/find.jsp
		dao.addCost(c);
		res.sendRedirect("findcost.do");
	}
	protected void deleteCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		CostDao dao = new CostDao();
		int id = Integer.parseInt(req.getParameter("id"));
		dao.deleteCost(id);
		res.sendRedirect("findcost.do");
	}
	//用来显示修改页面
	protected void findModiCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		CostDao dao = new CostDao();
		String id   = req.getParameter("costId");
		Cost cost = dao.findById(new Integer(id));
		req.setAttribute("cost",cost);
		req.getRequestDispatcher("WEB-INF/cost/fee_modi.jsp").forward(req,res);
	}
	protected void modiCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		CostDao dao = new CostDao();
		Cost c = new Cost();
		String costId=req.getParameter("costId");
		String name = req.getParameter("name");
		String costType = req.getParameter("costType");
		String baseDuration = req.getParameter("baseDuration");
		String baseCost = req.getParameter("baseCost");
		String unitCost = req.getParameter("unitCost");
		if(!"".equals(baseDuration)){
			c.setBaseDuration(Integer.parseInt(baseDuration));
		}
		if(!"".equals(baseCost)){
			c.setBaseCost(Double.parseDouble(baseCost));
		}
		if(!"".equals(unitCost)){
			c.setUnitCost(Double.parseDouble(unitCost));
		}
		if(!"".equals(costId)){
			c.setCostId(new Integer(costId));
		}
		c.setName(name);
		c.setCostType(costType);
		c.setDescr(req.getParameter("descr"));
		System.out.println("c"+c);
		dao.modi(c);
		res.sendRedirect("findcost.do");
	}
	protected void toLogin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req,res);
	}
	protected void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		AdminDao dao = new AdminDao();
		String adminCode = req.getParameter("user");
		String pwd = req.getParameter("pwd");
		Admin admin = dao.findByCode(adminCode);
		//接收验证码
		String code = req.getParameter("code");
		HttpSession session = req.getSession();
		String imgcode = (String) session.getAttribute("imgcode");
		System.out.println(adminCode);
		System.out.println(pwd);
		System.out.println(admin);
		if(code==null || !imgcode.equalsIgnoreCase(code)){
			String err = "验证码不对";
			req.setAttribute("err",err);
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req,res);
			return;
		}
		if(admin==null){
			String err = "帐号不对";
			req.setAttribute("err",err);
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req,res);
			return;
		}
		if(adminCode.equals(admin.getAdminCode())&&pwd.equals(admin.getPassword())){
			Cookie c = new Cookie("user",adminCode);
			res.addCookie(c);
			//也可以将帐号存入session
			session.setAttribute("user",adminCode);
			res.sendRedirect("toIndex.do");
		}else{
			String err = "密码不对";
			req.setAttribute("err",err);
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req,res);
		}
	}
	protected void kaitong(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		AdminDao dao = new AdminDao();
		int id = new Integer(req.getParameter("id"));
		dao.kaitong(id);
		res.sendRedirect("findcost.do");
	}
	protected void toIndex(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/index.jsp").forward(req,res);
	}
	protected void findadd(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/cost/fee_add.jsp").forward(req,res);
	}
	protected void createImg(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//生成图片及验证码
		Object[] objs = ImageUtil.createImage();
		//将验证码存入session
		HttpSession session = req.getSession();
		session.setAttribute("imgcode",objs[0]);
		//将图片输出给浏览器
		BufferedImage img = (BufferedImage) objs[1];
		res.setContentType("image/png");
		OutputStream os = res.getOutputStream();
		ImageIO.write(img,"png", os);
	}
}
