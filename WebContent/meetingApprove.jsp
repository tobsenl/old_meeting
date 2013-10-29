
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.Calendar"%>
<%
 String userId = (String) session.getAttribute("userId"); //用户id  
 String[][] results = (String[][])request.getAttribute("results");
%>

<!-- 
	Author: houyy
	Name:  meetingApprove.jsp
	Description: 显示所有未审核的会议记录
-->
<%
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

%>
<script>                                 
function check(){   
	var i = 0;
	var k = 0;
	var x = 1;
	var y = 1;
	for (var j = 0;j < form1.elements.length;j++) { 
		if (form1.elements[j].name=="approve") { 
			if (form1.elements[j].checked) { 
				if(form1.elements[j+1].checked){ 
					alert("不能对同一条记录同时选择批准和拒批！"); 
					return false; 
				} 
			 i++; 
			} 
		} 
		else if (form1.elements[j].name=="disapprove"){
			if (form1.elements[j].checked) { 
        k++;
			}
		}
	}
	 
	if (i==0 && k==0 ) { 
	  alert("提示：请选择审核项或者拒批项！"); 
		return false; 
	} else { 
		//如果审核没有被选中的项
		if(i==0) { x = 0; } 
		//如果拒批没有被选中的项
    if(k==0) { y = 0; }
		form1.agree.value = x;
		form1.disagree.value = y;
    form1.ctrl.value = "approved";                          
    form1.action = "MeetingServlet";                          
	  form1.submit(); 	
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


<form name="form1" method=post action="">
         <p align="center" class="tabletitle"><font size="2"><b>会议审核</b></font></p>
        <table width="96%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="698cc3">
          <tr> 
            <td>
<table width="829"  border="0" cellpadding="1" cellspacing="1">
  <tr bordercolor="gray" bordercolordark="white" bgcolor="#FFFFFF" class="P1"> 
		<TD width="111" align="center">日期</TD>
		<TD width="131" align="center">会议名称</TD>
		<TD width="96" align="center">
          主持人</TD>
		<TD width="191" align="center">
          <p align="center">公司领导</TD>
		<TD width="175" align="center">参加单位/人员</TD>
		<TD width="146" align="center">有关说明</TD>
		<TD width="76" align="center">申请部门/日期</TD>
		<TD width="27" align="center">批准</TD>
		<TD width="27" align="center">拒批</TD>
	</tr>
<%
  for ( int k = 0; k < results.length; k++ ){
     Id = results[k][0];     
     startTime = results[k][2].substring(0,16);
     endTime = results[k][3].substring(0,16);     
     content = results[k][4];
     leader = results[k][5];
     depart = results[k][6];
     remark = results[k][7];
     commitTime = results[k][8].substring(0,10);
     applyDept=results[k][10];
     presider=results[k][15];
     
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
		<TD width="111" valign="middle" align="center">星期<%=(week ==null || week.equals("null"))?"":week%><BR>
		      <%=(startTime ==null || startTime.equals("null"))?"":startTime%>
          <%=(endTime ==null || endTime.equals("null"))?"":endTime%></TD>
		<TD width="131"><%=(content==null || content.equals("null"))?"":content%></TD>
		<TD width="96"><%=(presider==null || presider.equals("null"))?"":presider%></TD>
		<TD width="191"><%=(leader==null || leader.equals("null"))?"":leader%></TD>
		<TD width="175"><%=(depart==null || depart.equals("null"))?"":depart%></TD>
		<TD width="146"><%=(remark==null || remark.equals("null"))?"":remark%></TD>
		<TD width="76"><%=(applyDept==null || applyDept.equals("null"))?"":applyDept%>
		   <BR><%=(commitTime==null || commitTime.equals("null"))?"":commitTime%></TD>
		<TD width="27" valign="middle" align="center"><input type="checkbox" name="approve" value="<%=Id%>"></TD>
		<TD width="27" valign="middle" align="center"><input type="checkbox" name="disapprove" value="<%=Id%>"></TD>
  </tr>
<%}%>

    <tr bgcolor="#FFFFFF"> 
	  <TD colspan="9" align="center" width="829">
        <INPUT style="COLOR:#44606B;background-color:#FFFFFF" type="button" name="" value=" 提交 " onclick="check()"> 
        &nbsp;&nbsp; &nbsp; <INPUT style="COLOR:#44606B;background-color:#FFFFFF" type="reset" name="" value=" 重置 ">     
					</TD> 
				</tr> 
			</table> 
		</td> 
	</tr> 
	</table> 
<input type="hidden" name="agree" value=""> 
<input type="hidden" name="disagree" value=""> 
<input type="hidden" name="ctrl" value=""> 
</form> 
 
    </td> 
  </tr> 
</table> 
<center> 
<p>会议通知管理系统 江苏核电 2004</p>     
</center> 
</body> 
</html> 
 




