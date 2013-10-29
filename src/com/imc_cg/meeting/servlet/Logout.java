package com.imc_cg.meeting.servlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class Logout extends HttpServlet {
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    res.setLocale(java.util.Locale.CHINESE);
    HttpSession session = req.getSession(true);
    String userid = (String) session.getAttribute("userid");
    String passwd = (String) session.getAttribute("passwd");
    session.removeAttribute("userid");
    session.removeAttribute("passwd");
    session.removeAttribute("vec");    
    PrintWriter out = res.getWriter();
    out.println("<html><head></head><body>");
    out.println("<FORM METHOD=POST ACTION=\"http://login.jnpc.com.cn/login/all_index.jsp\" name=form1>");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"userid\" value=" + userid + ">");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"passwd\" value="+passwd+">");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"return\" value=\"1\">");
    out.println("</FORM></body></html>");
    out.println("<SCRIPT LANGUAGE=\"JavaScript\">form1.target=\"_top\";form1.submit();</SCRIPT>");
    out.flush();
    out.close();
  }
}