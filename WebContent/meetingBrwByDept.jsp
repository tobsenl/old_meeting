<%@page contentType="text/html; charset=UTF-8"%>
<!-- 
	Author: liqs
	Name:  meetingBrwByDept.jsp
	Description: 部门会议申请信息浏览
-->
<%  String userId = (String)session.getAttribute("userid");
    String[][] allMeeting = (String[][])request.getAttribute("results");	    
    String flowID = "";	 
    String startTime = "";
    String endTime = "";
	String week = "";
    String content = "";
    String leader = "";
    String depart = "";
    String remark = "";
    String commiterId = "";
    String commitTime = "";
    String status = "";
	String state = "";
	String presider = "";	
    String disabled = "";
	String color ="";
	
%>
   
<SCRIPT LANGUAGE="JavaScript">
<!--
function add(){ 
	visitForm.ctrl.value="toMeetingApply";
  visitForm.action = "MeetingServlet";                
	visitForm.submit();                                           
}

function modify(){        		             
 	var i = 0;
	for (var j = 0;j < visitForm.elements.length;j++) {
		if (visitForm.elements[j].name == "ID") {
			if (visitForm.elements[j].checked) { 
			  i++;
			}
		}
	}
	if(i == 0) {
	  alert("提示：请选择修改项！");
		return false;
	} else if(i > 1){
	  alert("提示：每次只能修改一条记录！");
		return false;
	}else{
    visitForm.ctrl.value="deptMod";
    visitForm.action = "MeetingServlet"; 
    visitForm.submit();
	}                                           
} 
                      
function del(){
  if (confirm("提示：将删除选中的信息？")) {
		var i = 0;
		for (var j=0;j<visitForm.elements.length;j++) {
			if (visitForm.elements[j].name=="ID") {
				if (visitForm.elements[j].checked) { 
				  i++;
				}
			}
		}
		if (i==0) {
			alert("提示：请选择删除项！");
			return false;
		} else{
			visitForm.ctrl.value="deptDel";
			visitForm.action = "MeetingServlet"; 
			visitForm.submit();
		}
  }
}
//-->
</SCRIPT>
   

<html>
<head>
<title>会议通知管理系统</title>
<meta Name="generator" Content="Microsoft FrontPage 4.0">
<meta Name="author" Content="">
<meta Name="keywords" Content="">
<meta Name="description" Content="">
<link Href="new.css" Rel="stylesheet" Type="text/css">
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8"></head>
<body Leftmargin="0" Topmargin="0" Marginwidth="0" Marginheight="0" >
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

<FORM name ="visitForm" METHOD=POST action="">
         <p align="center" class="tabletitle"><font size="2"><b>会议申请浏览</b></font></p>
        <table width="96%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="698cc3">
          <tr> 
            <td>

<table width="829"  border="0" cellpadding="1" cellspacing="1">
      <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="28" align="center">选中</TD>
		<TD width="85" align="center">日期</TD>
		<TD width="162" align="center">会议名称</TD>
		<TD width="83" align="center">主持人</TD>
		<TD width="142" align="center"> <p align="center">公司领导</TD>
		<TD width="125" align="center">参加单位/人员</TD>
		<TD width="120" align="center">有关说明</TD>
		<TD width="75" align="center">发布人/日期</TD>
		<TD width="60" align="center">审批结果</TD>
	 </TR>
<%
for (int k=0;k<allMeeting.length;k++){
     flowID = allMeeting[k][0];     
     startTime = allMeeting[k][1].substring(0,16);
     endTime = allMeeting[k][2].substring(0,16);     
     content = allMeeting[k][3];
     leader = allMeeting[k][4];
     depart = allMeeting[k][5];
     remark = allMeeting[k][6];
     commitTime = allMeeting[k][7].substring(0,10);
     status = allMeeting[k][8];
     commiterId = allMeeting[k][9];   
     presider = allMeeting[k][11];    
     if (status.trim().equals("0")||status.trim().equals("2")||status.trim().equals("4")){
         disabled = "";
     }else{ 
         disabled = "disabled";
     }
%>

   <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="28" valign="middle" align="center"><input type="checkbox" name="ID"   value="<%=flowID%>" <%=disabled%>></TD>
		<TD width="85"><%=(startTime ==null || startTime.equals("null"))?"":startTime%>
          <%=(endTime ==null || endTime.equals("null"))?"":endTime%></TD>
		<TD width="162"><%=(content ==null || content.equals("null"))?"":content%></TD>
		<TD width="83"><%=(presider ==null || presider.equals("null"))?"":presider%></TD>
		<TD width="142"><%=(leader ==null || leader.equals("null"))?"":leader%></TD>
		<TD width="125"><%=(depart ==null || depart.equals("null"))?"":depart%></TD>
		<TD width="120"><%=(remark ==null || remark.equals("null"))?"":remark%></TD>
		<TD width="75"><%=(commiterId ==null || commiterId.equals("null"))?"":commiterId%><BR>
		<%=(commitTime ==null || commitTime.equals("null"))?"":commitTime%></TD>
	<%if(status.equals("2")){
		  state = "拒批";
			color = "red";
		}else if(status.equals("0")){
		  state = "未处理";
			color = "";
		}else if(status.equals("4")){
			state = "领导/主持人/会议室冲突，请修改";
			color = "red";
		}else{
		  state = "已审批";
			color = "green";
		}
	 %>
		<TD align="center" width="60">
       <font color="<%=color%>"> <%=state%></font>
		</TD>
	</TR>
<% } %>
    <tr bgcolor="#FFFFFF"> 
		<TD align="center" colspan="9" width="821">
          <p align="center"><input type="button" value="添加" name="addBot" onclick="add();">&nbsp;                          
          &nbsp; <input type="button" value="修改" name="modifyBot" onclick="modify();">&nbsp;                            
          &nbsp; <INPUT TYPE="button" value=" 删除 " name="deleteBot" onclick="del();" >&nbsp;&nbsp;&nbsp;                            
          <input type="reset" value="重置" name="renew"></p> 
        </TD> 
	</tr> 
			</table> 
</table> 
  <input type="hidden" name="userId" value="<%= userId%>"> 
	<input type="hidden" name="ctrl" value=""> 
	 
</FORM> 
</td> 
  </tr> 
</table> 
<center> 
<p>会议通知管理系统 江苏核电 2004</p>      
</center> 
</body> 
