<%@ page import="java.util.*,java.util.Vector"%>
<%@ page import="com.imc_cg.meeting.bean.business.Right"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
	Right right = new Right();

	String userid = request.getParameter("userid");
	String passwd = request.getParameter("passwd");

	/* String userid = "20021272";
	String passwd = "8888"; */
	String fromWhere = request.getParameter("return");

	if (fromWhere != null && fromWhere.equals("1")) {
		//密码加密
		passwd = right.computeDigest(passwd);
	}
	//String flag = "";
	String flagEncrypt = "";
	if (userid == null || passwd == null || userid.equals("")
			|| passwd.equals("")) {
		response.sendRedirect("http://www0.jnpc.com.cn/login/login.jsp");
	} else {
		//验证用户

		//flag = right.VerifyUserLogin(userid,passwd);
		flagEncrypt = right.VerifyUser(userid, passwd);

		if(!flagEncrypt.equals("0")){
			out.println("<p>用户名或密码不正确，请重新输入！</p>");
			out.flush();
			return;
		} else {
		// 设置用户所有权限（排除相同的权限）
		Vector vec = right.getRights(userid, "38");
		//add by houyy
		/* vec.addElement("3801");
		vec.addElement("380101");
		vec.addElement("3802");
		vec.addElement("380201");
		vec.addElement("3803");
		vec.addElement("380301");
		vec.addElement("3804");
		vec.addElement("380401"); */

		if (vec == null) {
			out.println("数据库问题，请稍后再试！");
			out.flush();
			return;
		}

		//存储用户和用户权限
		session.setAttribute("userid", userid);
		session.setAttribute("passwd", passwd);
		session.setAttribute("vec", vec);
	}
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会议通知管理系统</title>
</head>

<frameset rows="*" cols="128,*" framespacing="0" frameborder="no"
	border="0">
	<frame src="left.jsp" name="left" scrolling="NO" noresize>
	<frame src="main.jsp" name="main">
</frameset>
<noframes>
	<body>

	</body>
</noframes>
</html>
