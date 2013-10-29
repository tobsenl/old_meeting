<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.Hashtable"%>
<%
 String userId = (String) session.getAttribute("userId"); //用户id  
 String meetingId = (String)request.getAttribute("id");
 String roomID = (String)request.getAttribute("roomID");
 Hashtable unavailableRoom =(Hashtable) request.getAttribute("unavailableRoom");   
 String[][] allRoom = (String[][])request.getAttribute("results");
  String roomId = null;
  String building = null;
  String room = null;
  String capacity = null;
  String remark = null;
  String disable = null;
  String checked = null;
 
%>
<!-- 
	Author: houyy
	Name:  roomReAllot.jsp
	Description: 会议室调配
-->
<script>                    
    
function allot(){  
	var flag = 0;				
	var x = "";
	for (var i = 0; i < allotForm.elements.length; i++){				
		if (allotForm.elements[i].name=="radio") {
			if (allotForm.elements[i].checked) {//取得选定的会议室
					x =  allotForm.elements[i].value;
					flag = 1;
			}						
		}						
	}
	if (flag == 0){   	
		alert("提示：请选择要分配会议室！");
		return false;				    		
	} else{                    
    allotForm.roomID.value = x;       
		allotForm.ctrl.value = "reAllotSubmit";                    
    allotForm.action = "MeetingServlet";                    
	  allotForm.submit();                     
  }                   
}      
                   
</script> 
<html>
<head>
<title>会议通知管理系统</title>
<meta Name="generator" Content="Microsoft FrontPage 4.0">
<meta Name="author" Content="">
<meta Name="keywords" Content="">
<meta Name="description" Content="">
<link Href="new.css" Rel="stylesheet" Type="text/css">
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8"></head>
<body Leftmargin="0" Topmargin="0" Marginwidth="0" Marginheight="0">
<table Width="870" Border=0 Align=center Cellpadding=0 Cellspacing=0 Background="images/topbg.gif" Bgcolor=#ffffff>
<tr> 
  <td Width="31%" Rowspan="2"><img Src="images/logo.gif" width="210" height="110"></td>  
  <td Width="69%" Height="80">&nbsp;</td>
</tr>
</table>
<div align="center">
  <center>
<table Width="870" Height="77%" Border="0" Cellpadding="0" Cellspacing="0" Bgcolor="#ffffff" Style="table-layout: Fixed">
  <tr> 
    <td Valign="top" >

<FORM name ="allotForm" METHOD=POST action="">
		 <p align="center" class="tabletitle"><font size="2"><b>会议室调配</b></font></p>
        <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="698cc3">
          <tr> 
            <td>
<table width="100%"  border="0" cellpadding="1" cellspacing="1">
      <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="10%" align="center">选中</TD>
		<TD width="20%" align="center">所在楼会议时</TD>
		<TD width="20%" align="center">房间号</TD>
		<TD width="20%" align="center">容纳人数</TD>
		<TD width="30%" align="center">说明</TD>
	 </TR>
 <%
	 for(int i=0;i<allRoom.length;i++){ 
	   roomId = allRoom[i][0];
       building = allRoom[i][1];
       room = allRoom[i][2];
       capacity = allRoom[i][3];
       remark = allRoom[i][4];
       if(unavailableRoom.contains(roomId.trim())){
      	 disable = "disabled";
       } else {
      	 disable = "";
       }
       if(roomId.equals(roomID)){
      	 checked = "checked";
       } else {
      	 checked = "";
       }
	 
 %>
     <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="10%" valign="middle" align="center"><input type="radio" <%=disable%> <%=checked%> name="radio" value="<%=roomId%>" ></TD>
		<TD width="20%"><%=(building ==null || building.equals("null"))?"":building%></TD>
		<TD width="20%"><%=(room ==null || room.equals("null"))?"":room%></TD>
		<TD width="20%"><%=(capacity ==null || capacity.equals("null"))?"":capacity%></TD>
		<TD width="30%"><%=(remark ==null || remark.equals("null"))?"":remark%></TD>
	</TR>
<% } %>
    <tr bgcolor="#FFFFFF"> 
		<TD align="center" colspan="5" width="490">
          <p align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="#000000">&nbsp;   
          <input type="button" value="确认" name="agrea" onclick= "allot()">&nbsp;    
          &nbsp; &nbsp;&nbsp; <input type="reset" value="重置" name="renew"></font></p> 
        </TD>
	</tr>
			</table>
		</td>
	</tr>
</table>
<font color="#FFFFFF"> 
  <input type="hidden" name="roomID" value="">
  <input type="hidden" name="ctrl" value="">   
  <input type="hidden" name="userId" value="<%=userId%>"> 
  <input type="hidden" name="meetingID" value="<%=meetingId%>">   
</font>       
	
</FORM>

</td>
</tr>
</table>
<center>
<p>会议通知管理系统 江苏核电 2004</p>   
</center>
</body>
</html>