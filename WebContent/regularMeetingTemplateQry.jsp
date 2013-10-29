<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.Calendar"%>

<script LANGUAGE="javascript">
function deleteTemplate(templateId){
	visitForm.id.value=templateId;
	visitForm.action = "RegularMeetingTemplateServlet?ctrl=delete";                                 
	visitForm.submit();
}
function modifyTemplate(templateId){
	visitForm.id.value=templateId;
	visitForm.action = "RegularMeetingTemplateServlet?ctrl=qrySingle";                                 
	visitForm.submit();
}
function triggerTemplate(templateId){
	visitForm.id.value=templateId;
	visitForm.action = "RegularMeetingTemplateServlet?ctrl=triggerSingle";                                 
	visitForm.submit();
}
</script>

<%
	String[][] templates = (String[][])request.getAttribute("templates");
	String id = "";	
	String week = ""; 
	String startTime = "";
	String endTime = "";
	String content = "";
	String leader = "";
	String depart = "";
	String remark = "";
	String applyDept = "";
	String commitTime = "";
	
	String commitYear = ""; 
	String commitMonth = ""; 
	String commitDay = ""; 
	String commitHour = ""; 
	String commitMinute = ""; 
	//会议室信息
	String building = ""; 
	String room = ""; 
	String capacity = ""; 
	String presider = "";
	
	String scheduledPeriod = "";
	String daysBeforeTrigger = "";
	String lastTriggerDate = "";
	String isAutoTrigger = "";
%>
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


<form name="visitForm" method=post action="">
         <p align="center" class="tabletitle"><font size="2"><b>模板浏览</b></font></p>
        <table width="96%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="698cc3">
          <tr> 
            <td>
<table width="100%"  border="0" cellpadding="1" cellspacing="1">
  <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="12%" align="center">日期</TD>
		<TD width="10%" align="center">会议名称</TD>
		<TD width="10%" align="center">主持人</TD>
		<TD width="13%" align="center"><p align="center">公司领导</TD>
		<TD width="10%" align="center">参加单位/人员</TD>
		<TD width="8%" align="center">会议地点</TD>
		<TD width="10%" align="center">申请部门/日期</TD>
		<td width="6%" align="center">例会周期</td>
		<td width="5%" align="center">提前触发天数</td>
		<td width="5%" align="center">最后触发日期</td>
		<td width="5%" align="center">自动触发</td>
		<td width="5%" align="center">操作</td>
	</tr>
<%
if(templates!=null){
  for ( int k = 0; k < templates.length; k++ ){
     id = templates[k][0]; 
     building =templates[k][1]; 
     room = templates[k][2]; 
     capacity =templates[k][3]; 
     startTime = templates[k][4].substring(0,16);
     endTime = templates[k][5].substring(0,16);     
     content = templates[k][6];
     leader = templates[k][7];
     depart = templates[k][8];
     remark = templates[k][9];
     commitTime = templates[k][10].substring(0,10);
     applyDept=templates[k][12];
     presider =templates[k][15];
     
     scheduledPeriod = templates[k][17];
     daysBeforeTrigger = templates[k][18];
     lastTriggerDate = templates[k][19];
     isAutoTrigger = templates[k][20];
	 
     Calendar calendar=Calendar.getInstance(); 
     commitYear=startTime.substring(0,4); 
     commitMonth=startTime.substring(5,7); 
     commitDay=startTime.substring(8,10); 
     commitHour=startTime.substring(11,13); 
     commitMinute=startTime.substring(14,16);  
     calendar.set((new Integer(commitYear)).intValue(),(new Integer(commitMonth)).intValue()-1,(new Integer(commitDay)).intValue(),(new Integer(commitHour)).intValue(),(new Integer(commitMinute)).intValue());  
     int intWeek=calendar.get(7);
		 
     switch (intWeek){ 
				case 1: 
				week="日"; 
				break; 
				case 2: 
				week="一"; 
				break; 
				case 3: 
				week="二"; 
				break; 
				case 4: 
				week="三"; 
				break; 
				case 5: 
				week="四"; 
				break; 
				case 6: 
				week="五"; 
				break; 
				case 7: 
				week="六"; 
				break; 
		}   
 %> 
   <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="12%">
          <p align="center">星期<%=(week ==null || week.equals("null"))?"":week%><BR>
		      <%=(startTime ==null || startTime.equals("null"))?"":startTime%>
          <%=(endTime ==null || endTime.equals("null"))?"":endTime%></p>  
        </TD>
		<TD width="10%"><%=(content ==null || content.equals("null"))?"":content%></TD>
		<TD width="10%" align="center"><%=(presider ==null || presider.equals("null"))?"":presider%></TD>
		<TD width="13%" align="center"><%=(leader ==null || leader.equals("null"))?"":leader%></TD>
		<TD width="10%"><%=(depart ==null || depart.equals("null"))?"":depart%></TD>
		<TD width="8%" align="center"><%=(building ==null || building.equals("null"))?"":building%><%=(room ==null || room.equals("null"))?"":room%><%=(capacity ==null || capacity.equals("null"))?"":"("+capacity+"人)"%></TD>
		<TD width="10%" align="center"><%=(applyDept ==null || applyDept.equals("null"))?"":applyDept%><BR><%=(commitTime ==null || commitTime.equals("null"))?"":commitTime%></TD>
		<TD width="6%" align="center">
			<%if(scheduledPeriod ==null || scheduledPeriod.equals("null") || scheduledPeriod.equals("")){%>
				<%}else{%>
				<%=scheduledPeriod %>&nbsp;&nbsp;天
			<%}%>
		</TD>
		<TD width="5%" align="center">
			<%if(daysBeforeTrigger ==null || daysBeforeTrigger.equals("null") || daysBeforeTrigger.equals("")){%>
				<%}else{%>
				<%=daysBeforeTrigger %>&nbsp;&nbsp;天
			<%}%>
		</TD>
		<TD width="5%" align="center"><%=(lastTriggerDate ==null || lastTriggerDate.equals("null"))?"":lastTriggerDate%></TD>
		<TD width="5%" align="center">
			<%if(isAutoTrigger ==null || isAutoTrigger.equals("null")){%>
				<%}else if(isAutoTrigger.equals("0")){ %>否
					<%}else if(isAutoTrigger.equals("1")){ %>是
			<%} %>
		</TD>
		<TD width="5%" align="center">
			<u onmouseover="this.style.cursor='hand'" onclick="modifyTemplate('<%=id %>')">修改</u> 
			<u onmouseover="this.style.cursor='hand'" onclick="deleteTemplate('<%=id %>')">删除</u>
			<u onmouseover="this.style.cursor='hand'" onclick="triggerTemplate('<%=id %>')">触发</u>
		</TD>
  </tr>
<%
  }
}%>

			</table>
		</td>
	</tr>
<input type="hidden" name="id" value=""/>
</form>

</table>
<center>
<form name="triggerAll" method=post action="RegularMeetingTemplateServlet?ctrl=triggerAll">
<input type="button" value="手动触发所有例会" onclick="trigger()"/>
</form>
<script LANGUAGE="javascript">
function trigger(){
	triggerAll.submit();
}
</script>
<p></p>   
</center>
  </table>
  <p align="center">会议通知管理系统 江苏核电 2004</p>        
</body>   
