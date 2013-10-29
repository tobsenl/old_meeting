<%@page contentType="text/html; charset=UTF-8"%>

<%
 String userId = (String) session.getAttribute("userId"); //用户id 
 String[] result = (String[])request.getAttribute("result");
 String Id = result[0];
 String building = result[1];
 String room = result[2];
 String capacity = result[3];
 String remark = result[4];
    
%>
<!-- 
	Author: 李其胜
	Name:  meetingRoomModify.jsp
	Description: 修改会议室
-->
<script>  
function renew(){
meetingRoomForm.remark.value=meetingRoomForm.remarkValue.value;
} 
                 
function setFocus(){
  meetingRoomForm.remark.focus();
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
		 <p align="center" class="tabletitle"><b><font size="2">修改</font></b><b><font size="2">会议室</font></b></p>
        <table width="40%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="698cc3">
          <tr> 
            <td>
<table width="100%"  border="0" cellpadding="1" cellspacing="1">
      <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="40%" align="left">所在楼</TD>
		<TD width="60%" align="left"><font size="2"><input type="text" name="building" value="<%=(building==null || building.equals("null"))?"": building%>" disabled size="27"></font></TD>
	 </TR>
 
     <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="40%">房间号</TD>
		<TD width="60%"><font size="2"><input type="text" name="room" value="<%=(room==null || room.equals("null"))?"": room%>" disabled size="27"></font></TD>
	</TR>
     <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="40%">容纳人数</TD>
		<TD width="60%"><font size="2"><input type="text" name="capacity" value="<%=(capacity==null || capacity.equals("null"))?"": capacity%>" disabled size="27"></font></TD>
	</TR>
     <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="40%">说明</TD>
		<TD width="60%"><font size="2"><input type="text" name="remark" value="<%=(remark==null || remark.equals("null"))?"": remark%>" size="50"></font></TD>
		
	</TR>
	

    <tr bgcolor="#FFFFFF"> 
		<TD align="center" colspan="2" width="490">
          <p align="center"><input type="submit" value="提交" >&nbsp;&nbsp;          
          &nbsp;&nbsp; <input type="button" value="重置" onClick="renew()"></p>        
        </TD>
	</tr>
			</table>
		</td>
	</tr>
</table>
<font color="#FFFFFF">   
  <input type="hidden" name="ctrl" value="modify_submit">    
  <input type="hidden" name="userId" value="<%=userId%>"> 
  <input type="hidden" name="Id" value="<%=Id%>"> 
  <input type="hidden" name="remarkValue" value="<%=(remark==null || remark.equals("null"))?"": remark%>"> 

  
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
