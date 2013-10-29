<%@page contentType="text/html; charset=UTF-8"%>

<%
 String userId = (String) session.getAttribute("userId"); //用户id    
 String[][] allRoom = (String[][])request.getAttribute("results");
  String roomId = null;
  String building = null;
  String room = null;
  String capacity = null;
  String remark = null;
 
%>
<!-- 
	Author: 李其胜
	Name:  meetingRoomManage.jsp
	Description: 会议室管理，包括添加、修改和删除
-->
<script>                    
    
function checkDelete(){
   
    var i = 0;
	for (var j=0;j<meetingRoomForm.elements.length;j++) {
		if (meetingRoomForm.elements[j].name=="checkbox") {
		    if (meetingRoomForm.elements[j].checked) {
			    i++;
		    }
		}
	}
	if (i==0) {
	  alert("提示：请选择删除项！");
	  return false;
	} else{                    
        if (confirm("提示：将删除选中的信息？")) {       
		meetingRoomForm.ctrl.value = "delete";                    
        meetingRoomForm.action = "MeetingRoomServlet";                    
	    meetingRoomForm.submit();                    
	  } 
    }                    
}

function checkModify(){
   
    var i = 0;
	for (var j=0;j<meetingRoomForm.elements.length;j++) {
		if (meetingRoomForm.elements[j].name=="checkbox") {
		    if (meetingRoomForm.elements[j].checked) {
			    i++;
		    }
		}
	}
	if (i==0) {
	  alert("提示：请选择修改项！");
	  return false;
	}else if(i>1){                    
        alert("提示：一次只能修改一项！");       
	    return false;
                  
	}else{                    
        if (confirm("提示：只能修改会议室说明,要修改其它项请重新添加会议室？")) {       
		    meetingRoomForm.ctrl.value = "modify";                    
            meetingRoomForm.action = "MeetingRoomServlet";                    
	        meetingRoomForm.submit();                    
	    } 
   }                    
}

function checkAdd(){      
    meetingRoomForm.action = "meetingRoomAdd.jsp";                    
	meetingRoomForm.submit();       
} 

function setFocus(){
  meetingRoomForm.add.focus();
}      
      
                   
</script>      <html>
<head>
<title>会议通知管理系统</title>
<meta Name="generator" Content="Microsoft FrontPage 4.0">
<meta Name="author" Content="">
<meta Name="keywords" Content="">
<meta Name="description" Content="">
<link Href="new.css" Rel="stylesheet" Type="text/css">
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8"></head>
<body Leftmargin="0" Topmargin="0" Marginwidth="0" Marginheight="0" onload="setFocus()">
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

<FORM name ="meetingRoomForm" METHOD=POST action="">
		 <p align="center" class="tabletitle"><font size="2"><b>会议室管理</b></font></p>
        <table width="75%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="698cc3">
          <tr> 
            <td>
<table width="100%"  border="0" cellpadding="1" cellspacing="1">
      <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="10%" align="center">选中</TD>
		<TD width="20%" align="center">会议室所在楼</TD>
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
	 
 %>
     <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="10%" valign="middle" align="center"><input type="checkbox" name="checkbox" value="<%=roomId%>"></TD>
		<TD width="20%" valign="middle" align="center"><%=(building ==null || building.equals("null"))?"":building%></TD>
		<TD width="20%" valign="middle" align="center"><%=(room ==null || room.equals("null"))?"":room%></TD>
		<TD width="20%" valign="middle" align="center"><%=(capacity ==null || capacity.equals("null"))?"":capacity%></TD>
		<TD width="30%" valign="middle" align="center"><%=(remark ==null || remark.equals("null"))?"":remark%></TD>
	</TR>
<% } %>
    <tr bgcolor="#FFFFFF"> 
		<TD align="center" colspan="5" width="490">
          <p align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="#000000">&nbsp; 
          <input type="button" value="添加" name="add" onclick= "checkAdd()">&nbsp;   
          &nbsp;<INPUT TYPE="button" value=" 修改 " onclick= "checkModify()" name="modify">&nbsp;&nbsp; 
          <INPUT TYPE="button" value=" 删除 " onclick= "checkDelete()" name="delete">&nbsp;&nbsp; 
          <input type="reset" value="重置" name="renew"></font></p>
        </TD>
	</tr>
			</table>
		</td>
	</tr>
</table>
<font color="#FFFFFF">  
  <input type="hidden" name="ctrl" value="">   
  <input type="hidden" name="userId" value="<%= userId%>">   
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
