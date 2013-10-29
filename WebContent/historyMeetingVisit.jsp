
<%@page contentType="text/html; charset=gb2312"%>
<%@page import = "java.util.Calendar"%>
<%
 String userId = (String) session.getAttribute("userId"); //用户id  
 String[][] results = (String[][])request.getAttribute("results"); 

%>

<!-- 
	Author: liqs
	Name:  historyMeetingVisit.jsp
	Description: 浏览历史会议记录
-->
<%  //会议记录信息
    String Id = "";	
    String week = ""; 
    String startTime = "";
    String endTime = "";
    String content = "";
    String leader = "";
    String depart = "";
    String remark = "";
    String applyDept = "";
    String commitTime = "";
	String presider = "";

	String commitYear = ""; 
	String commitMonth = ""; 
	String commitDay = ""; 
	String commitHour = ""; 
    String commitMinute = ""; 
    //会议室信息
    String building = ""; 
    String room = ""; 
    String capacity = ""; 

%>
<script> 
function delCheck(){
  
	var i = 0;
	for (var j=0;j<visitForm.elements.length;j++) {
		if (visitForm.elements[j].name=="checkbox") {
		    if (visitForm.elements[j].checked)
			i++;
		}
	}
	if (i==0) {
	  alert("提示：请选择删除项！");
	  return false;
	} else{
	    if (confirm("提示：将删除选中的信息？")) {
        visitForm.ctrl.value="historyDel";
        visitForm.action = "MeetingServlet"; 
        visitForm.submit();
	    }
	}
}    
/*function delAll(){	
	
	    if (confirm("提示：将删除所有历史会议记录？")) {
        visitForm.ctrl.value="deleteAll";
        visitForm.action = "MeetingServlet"; 
        visitForm.submit();
	    }
	
} */                               
                            
                                 
</script>                                 
<html>
<head>
<title>会议通知管理系统</title>
<meta Name="generator" Content="Microsoft FrontPage 4.0">
<meta Name="author" Content="">
<meta Name="keywords" Content="">
<meta Name="description" Content="">
<link Href="new.css" Rel="stylesheet" Type="text/css">
<meta Http-equiv="content-type" Content="text/html; Charset=gb2312"></head>
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
         <p align="center" class="tabletitle"><font size="2"><b>历史会议浏览</b></font></p>
        <table width="96%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="698cc3">
          <tr> 
            <td>
<table width="836"  border="0" cellpadding="1" cellspacing="1">
  <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="116" align="center">日期</TD>
		<TD width="97" align="center">会议名称</TD>
		<TD width="66" align="center">
          主持人</TD>
		<TD width="98" align="center">
          <p align="center">公司领导</TD>
		<TD width="128" align="center">参加单位/人员</TD>
		<TD width="104" align="center">有关说明</TD>
		<TD width="62" align="center">会议地点</TD>
		<TD width="81" align="center">申请部门/日期</TD>
		<TD width="28" align="center">选中</TD>
	</tr><%
  for ( int k = 0; k < results.length; k++ ){
     Id = results[k][0];     
     startTime = results[k][4].substring(0,16);
     endTime = results[k][5].substring(0,16);     
     content = results[k][6];
     leader = results[k][7];
     depart = results[k][8];
     remark = results[k][9];
     commitTime = results[k][10].substring(0,10);
     applyDept=results[k][12];
     presider = results[k][17];
     
     building =results[k][1]; 
     room = results[k][2]; 
     capacity =results[k][3]; 
		 
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
		<TD width="116">
          <p align="center">星期<%=(week ==null || week.equals("null"))?"":week%><BR>
		      <%=(startTime ==null || startTime.equals("null"))?"":startTime%>
          <%=(endTime ==null || endTime.equals("null"))?"":endTime%></p> 
        </TD>
		<TD width="97"><%=(content ==null || content.equals("null"))?"":content%></TD>
		<TD width="66"><%=(presider ==null || presider.equals("null"))?"":presider%></TD>
		<TD width="98"><%=(leader ==null || leader.equals("null"))?"":leader%></TD>
		<TD width="128"><%=(depart ==null || depart.equals("null"))?"":depart%></TD>
		<TD width="104"><%=(remark ==null || remark.equals("null"))?"":remark%></TD>
		<TD width="62"><%=(building ==null || building.equals("null"))?"":building%>
		<%=(room ==null || room.equals("null"))?"":room%>(<%=(capacity ==null || capacity.equals("null"))?"":capacity%>人)</TD>
		<TD width="81"><%=(applyDept ==null || applyDept.equals("null"))?"":applyDept%><BR>
		<%=(commitTime ==null || commitTime.equals("null"))?"":commitTime%></TD>
		<TD width="28" valign="middle" align="center"><input type="checkbox" name="checkbox" value="<%=Id%>"></TD>
  </tr>
<%}%>

    <tr bgcolor="#FFFFFF"> 
	  <TD colspan="9" align="center" width="794">
        &nbsp;<font color="#000000"> <INPUT style="color: #000000; background-color: #FFFFFF" type="button" name="delete" value="删除" onclick="delCheck()"> 
        &nbsp;&nbsp; <INPUT style="color: #000000; background-color: #FFFFFF" type="reset" name="renew" value=" 重选">
        </font>
					</TD>
				</tr>
			</table>
		</td>
	</tr>
<input type="hidden" name="ctrl" value="">
</form>

</table>
  </table>
<center>
<p>会议通知管理系统 江苏核电 2004</p>         
</center>  
</body>  
