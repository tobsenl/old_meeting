<%@page contentType="text/html; charset=UTF-8"%>

<%
 String userId = (String) session.getAttribute("userId"); //用户id     
%>
<!-- 
	Author: 李其胜
	Name:  meetingRoomManage.jsp
	Description: 添加会议室
-->
<script>                    
    
function check(){                    
  if (meetingRoomForm.building.value==""){                    
	  alert("请输入会议室地点！");         
      return false;                   
	
	}else{       
	    meetingRoomForm.action = "MeetingRoomServlet";                    
	    meetingRoomForm.submit();                    
	   
    }                    
}

function ifNumber(){
	var num,i,j,strTemp;
	num = meetingRoomForm.capacity.value;
	if(num != ""){
		for (i=0;i<num.length;i++) {
		  strTemp = "0123456789";
		  j=strTemp.indexOf(num.charAt(i));	
			if (j==-1) {
				alert("会议室容纳人数应该为数字，请重新填写!");
				meetingRoomForm.capacity.value="";
				meetingRoomForm.capacity.focus();
			  return false;
			}
    }
	}
}


function setFocus(){
  meetingRoomForm.building.focus();
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
		 <p align="center" class="tabletitle"><b><font size="2">添加会议室</font></b></p>
        <table width="40%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="698cc3">
          <tr> 
            <td>
<table width="100%"  border="0" cellpadding="1" cellspacing="1">
      <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="40%" align="left">地点</TD>
		<TD width="60%" align="left"><font size="2"><input type="text" name="building" size="27"></font></TD>
	 </TR>
 
     <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="40%">房间号</TD>
		<TD width="60%"><font size="2"><input type="text" name="room" size="27"></font></TD>
	</TR>
     <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="40%">容纳人数</TD>
		<TD width="60%"><font size="2"><input type="text" name="capacity" size="27" onchange="ifNumber()"></font></TD>
	</TR>
     <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="40%">说明</TD>
		<TD width="60%"><font size="2"><input type="text" name="remark" size="50"></font></TD>
	</TR>
    <tr bgcolor="#FFFFFF"> 
		<TD align="center" colspan="2" width="490">
          <p align="center"><input type="button" value="添加" onClick="check()">&nbsp;&nbsp;      
          &nbsp;&nbsp; <input type="reset" value="重置" name="renew"></p>     
        </TD>
	</tr>
			</table>
		</td>
	</tr>
</table>
<font color="#FFFFFF">   
  <input type="hidden" name="ctrl" value="add_submit">    
  <input type="hidden" name="userId" value="<%=userId%>">   
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
