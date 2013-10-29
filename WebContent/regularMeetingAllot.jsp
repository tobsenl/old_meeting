<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.Hashtable"%>
<%
 String userId = (String) session.getAttribute("userId");
 Hashtable unavailableRoom =(Hashtable) request.getAttribute("unavailableRoom");   
 String[][] allRoom = (String[][])request.getAttribute("allRoom");
 String allotedRoomId = request.getParameter("roomID");
 
  String roomId = null;
  String building = null;
  String room = null;
  String capacity = null;
  String remark = null;
  String disable = null;
  String checked = "";
 
%>
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
		allotForm.ctrl.value = "alloted";                    
		allotForm.action = "RegularMeetingTemplateServlet";                    
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
      <tr bordercolor="gray" bgcolor="#FFFFFF" class="P1"> 
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
       }else {
      	 disable = "";
       }
       if(roomId.equals(allotedRoomId)){
      	 checked = "checked";
       } else {
      	 checked = "";
       }
 %>
     <tr bordercolor="gray" bgcolor="#FFFFFF" class="P1"> 
		<TD width="10%" valign="middle" align="center"><input type="radio" <%=disable%> <%=checked %> name="radio" value="<%=roomId%>" ></TD>
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

	<input type="hidden" name="userId" value="<%=request.getAttribute("userId") %>">
	<input type="hidden" name="meetingID" value="<%=request.getAttribute("meetingID") %>">
	<input type="hidden" name="building" value="<%=request.getAttribute("building") %>">
	<input type="hidden" name="room" value="<%=request.getAttribute("room") %>">
	<input type="hidden" name="capacity" value="<%=request.getAttribute("capacity") %>">

	<input type="hidden" name="startYear" value="<%=request.getAttribute("startYear") %>">
	<input type="hidden" name="startMonth" value="<%=request.getAttribute("startMonth") %>">
	<input type="hidden" name="startDay" value="<%=request.getAttribute("startDay") %>">
	<input type="hidden" name="startHour" value="<%=request.getAttribute("startHour") %>">
	<input type="hidden" name="startMinute" value="<%=request.getAttribute("startMinute") %>">
	<input type="hidden" name="endYear" value="<%=request.getAttribute("endYear") %>">
	<input type="hidden" name="endMonth" value="<%=request.getAttribute("endMonth") %>">
	<input type="hidden" name="endDay" value="<%=request.getAttribute("endDay") %>">
	<input type="hidden" name="endHour" value="<%=request.getAttribute("endHour") %>">
	<input type="hidden" name="endMinute" value="<%=request.getAttribute("endMinute") %>">

	<input type="hidden" name="content" value="<%=request.getAttribute("content") %>">
	<input type="hidden" name="depart" value="<%=request.getAttribute("depart") %>">
	<input type="hidden" name="remark" value="<%=request.getAttribute("remark") %>">
	<input type="hidden" name="commiterId" value="<%=request.getAttribute("commiterId") %>">
	<input type="hidden" name="approverId" value="<%=request.getAttribute("approverId") %>">
	<input type="hidden" name="alloterId" value="<%=request.getAttribute("alloterId") %>">
	<input type="hidden" name="presider" value="<%=request.getAttribute("presider") %>">
	<input type="hidden" name="grade" value="<%=request.getAttribute("grade") %>">
	<input type="hidden" name="scheduledPeriod" value="<%=request.getAttribute("scheduledPeriod") %>">
	<input type="hidden" name="daysBeforeTrigger" value="<%=request.getAttribute("daysBeforeTrigger") %>">
	<input type="hidden" name="lastTriggerDate" value="<%=request.getAttribute("lastTriggerDate") %>">
	<input type="hidden" name="isAutoTrigger" value="<%=request.getAttribute("isAutoTrigger") %>">

<% String[] selectedLeaders = (String[])(request.getAttribute("selectedLeaders"));
	if(selectedLeaders!=null){
for(int i=0;i<selectedLeaders.length;i++){%>
  <TR>
    <TD>
       <INPUT type=hidden name="selectedLeaders" value=<%=selectedLeaders[i]%> >
    </TD>
  </TR>
<%}}%>
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