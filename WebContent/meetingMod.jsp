<%@page contentType="text/html; charset=UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
%>
<%
 String userId = (String) session.getAttribute("userId"); //用户id 
 String[] myMeeting = (String[])request.getAttribute("result");  //公司领导列表
 String[] leaders = (String[])request.getAttribute("leaders");  //公司领导列表
 String id = (String)request.getAttribute("id");
 String[] selectedLeaders = (String[])request.getAttribute("selectedLeaders");  //参会领导
 
 String startTime = myMeeting[1];
 String endTime = myMeeting[2];
 String content = myMeeting[3];
 String depart = myMeeting[5];
 String remark = myMeeting[6];
 String meetingType = myMeeting[7];
 String ifchecked1 = "";  //判断是否选中例会
 String ifchecked2 = "";  //判断是否选中会议 

 String startYear=startTime.substring(0,4);
 String startMonth=startTime.substring(5,7);
 String startDay=startTime.substring(8,10);
 String startHour=startTime.substring(11,13);
 String startMinute=startTime.substring(14,16); 
 String endYear=endTime.substring(0,4);
 String endMonth=endTime.substring(5,7);
 String endDay=endTime.substring(8,10);
 String endHour=endTime.substring(11,13);
 String endMinute=endTime.substring(14,16);
 String presider = myMeeting[8];
 String grade = myMeeting[9];
%>

<!-- 
	Author: 
	Name:  meetingMod.jsp
	Description: 最新会议修改
-->


<html>
<head>
<title>会议通知管理系统</title>
<meta Name="generator" Content="Microsoft FrontPage 4.0">
<meta Name="author" Content="">
<meta Name="keywords" Content="">
<meta Name="description" Content="">
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8"></head>
<link Href="new.css" Rel="stylesheet" Type="text/css">

<script ID="clientEventHandlersJS" LANGUAGE="javascript">                                 
function check(){                            
  if(timeForm.content.value==""){                                 
		alert("请输入会议名称！");                                 
		timeForm.content.focus();                                 
		return false;                                
	}else{  
    if(timeForm.startMinute.value==""){  
    timeForm.startMinute.value='0';  
    }  
    if(timeForm.endMinute.value==""){  
    timeForm.endMinute.value='0';  
    } 
	//选中所有的参会领导	
  var x=document.timeForm.leaders;
  for(var i =0;i<x.length;i++) {
    x.options[i].selected=true;
  }	
  timeForm.action = "MeetingServlet";                                 
	timeForm.submit();                                 
	}           
	                                 
}

function year(){
	var startYear,i,j,strTemp;
	startYear = timeForm.startYear.value;
	if(startYear != ""){
		for (i=0;i<startYear.length;i++) {
		  strTemp = "0123456789";
		  j=strTemp.indexOf(startYear.charAt(i));	
			if (j==-1) {
				alert("年份应该为数字，请重新填写!");
				timeForm.startYear.value="";
				timeForm.startYear.focus();
			  return false;
			}else if (startYear > 10000 || startYear < 1 || startYear.length != 4){
			  alert("年份格式不正确,请重新填写!");
				timeForm.startYear.value="";
				timeForm.startYear.focus();
				return false;
		  }
	  }
  }
	timeForm.endYear.value = startYear;
}

function month(){
	var startMonth,i,j,strTemp;
	startMonth = timeForm.startMonth.value;
	if(startMonth != ""){
		for (i=0;i<startMonth.length;i++) {
		  strTemp = "0123456789";
		  j=strTemp.indexOf(startMonth.charAt(i));	
			if (j==-1) {
				alert("月份应该为数字，请重新填写!");
        timeForm.startMonth.value="";
				timeForm.startMonth.focus();
			  return false;
			}else if (startMonth > 12 || startMonth < 1 || startMonth.length > 2){
			  alert("月份格式不正确,请重新填写!");
				timeForm.startMonth.value="";
				timeForm.startMonth.focus();
				return false;
		  }
	  }
  }
	timeForm.endMonth.value = startMonth;
}

function day(){
	var startDay,i,j,strTemp;
	startDay = timeForm.startDay.value;
	if(startDay != ""){
		for (i=0;i<startDay.length;i++) {
		  strTemp = "0123456789";
		  j=strTemp.indexOf(startDay.charAt(i));	
			if (j==-1) {
				alert("'日'应该为数字，请重新填写!");
        timeForm.startDay.value="";
				timeForm.startDay.focus();
			  return false;
			}else if (startDay > 31 || startDay < 1){
			  alert("'日'的格式不正确,请重新填写!");
				timeForm.startDay.value="";
				timeForm.startDay.focus();
				return false;
		  }
	  }
  }
	timeForm.endDay.value = startDay;
}

function hour(num){
	var Hour,i,j,strTemp;
	if(num == 1){
	  Hour = timeForm.startHour.value;
	}else if(num == 2){
		Hour = timeForm.endHour.value;
	}
	if(Hour != ""){
		for (i=0;i<Hour.length;i++) {
		  strTemp = "0123456789";
		  j=strTemp.indexOf(Hour.charAt(i));	
			if (j==-1) {
				alert("'小时'应该为数字，请重新填写!");
				if(num == 1){
          timeForm.startHour.value="";
				  timeForm.startHour.focus();
			    return false;
				}else if(num == 2){
          timeForm.endHour.value="";
				  timeForm.endHour.focus();				  
				}
			}else if (Hour > 23 || Hour < 0){
			  alert("'小时'的格式不正确,请重新填写!");
				if (num == 1){
					timeForm.startHour.value="";
					timeForm.startHour.focus();
					return false;
				}else if(num == 2){
					timeForm.endHour.value="";
					timeForm.endHour.focus();
					return false;
				}
		  }
	  }
  }
}

function minute(num){
	var Minute,i,j,strTemp;
	if(num == 1){
	  Minute = timeForm.startMinute.value;
	}else if(num == 2){
		Minute = timeForm.endMinute.value;
	}
	if(Minute != ""){
		for (i=0;i<Minute.length;i++) {
		  strTemp = "0123456789";
		  j=strTemp.indexOf(Minute.charAt(i));	
			if (j==-1) {
				alert("'分钟'应该为数字，请重新填写!");
				if(num == 1){
          timeForm.startMinute.value="";
				  timeForm.startMinute.focus();
			    return false;
				}else if(num == 2){
          timeForm.endMinute.value="";
				  timeForm.endMinute.focus();				  
				}
			}else if (Minute > 60 || Minute < 0){
			  alert("'分钟'的格式不正确,请重新填写!");
				if (num == 1){
					timeForm.startMinute.value="";
					timeForm.startMinute.focus();
					return false;
				}else if(num == 2){
					timeForm.endMinute.value="";
					timeForm.endMinute.focus();
					return false;
				}
		  }
	  }
  }
}


function keyUp(num) {
	    if (num=='1'){
         if(document.timeForm.startYear.value.length==4){
           document.timeForm.startMonth.focus();
				 }
			}else if (num=='2'){
         if(document.timeForm.startMonth.value.length==2){
           document.timeForm.startDay.focus();
				 }
			}else if (num=='3'){
        if(document.timeForm.startDay.value.length==2){
           document.timeForm.startHour.focus();
				 }
			}else if (num=='4'){
        if(document.timeForm.startHour.value.length==2){
           document.timeForm.startMinute.focus();
				 }
			}else if (num=='5'){
        if(document.timeForm.startMinute.value.length==2){
           document.timeForm.endHour.focus();
				 }
			}else if (num=='6'){
        if(document.timeForm.endHour.value.length==2){
           document.timeForm.endMinute.focus();
				 }
      }else if (num=='7'){
        if(document.timeForm.endMinute.value.length==2){
           
				 }
      }		
} 

function add() {  
  var x = document.timeForm.leaders;   //保存已选中的领导
  var y = document.timeForm.selLeaders;  //刚选中的领导    
  var k = x.length;
  var strx = ',';
  for(var i=0;i<x.length;i++) {
    strx=strx+x.options[i].value+',';
  }
  for(var i=0;i<y.length;i++) {
    if (y.options[i].selected) {
      if(strx.indexOf(',' + y.options[i].value + ',') == -1) {
        //如果在已选中的人员中不存在，则加入这条刚选中的人员
        k+=1;
        x.length=k;
        x.options[k-1].text=y.options[i].text;
        x.options[k-1].value=y.options[i].value;
      }
    }
  }

}
function del() {
 
  var x=document.timeForm.leaders;
  var y=document.timeForm.selLeaders; 
  for(var i =x.length-1; i >=0; i--) {
    if (x.options[i].selected) {
      x.options[i]=null;
    }
  }
}
                               
</script> 
</head>
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

 <form name="timeForm"  method="post" action="">                
  <p align="center"><b><font color="#000000" size="2">例 会 修 改</font></b></p>                                                                          
  <table border="0" width="124%" cellspacing="8" height="408">    
    <tr>    
    <% if( meetingType.equals("2")){    
         ifchecked2 = "checked";    
         ifchecked1 = " ";    
       }else if ( meetingType.equals("1")){    
         ifchecked1 = "checked";    
         ifchecked2 = " ";             
       }     
        
    %>    
      <td width="8%" height="30"></td>    
      <td width="11%" height="30">会议类型：</td>    
      <td width="18%" colspan="3" height="30"><input type="radio" value="2" <%=ifchecked2%> name="type">会议&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;                                        
        <input type="radio" value="1" name="type" <%=ifchecked1%>>例会</td>    
      <td width="9%" colspan="2" height="30"></td>    
      <td width="9%" colspan="2" height="30"></td>    
      <td width="24%" colspan="2" height="30"></td>    
      <td width="60%" height="30"></td>    
    </tr>  
	
	<tr>
      <td width="8%" height="2" valign="top" align="left"></td>
      <td width="11%" height="2" valign="top" align="left">会议级别：</td>
      <td width="18%" colspan="3" height="2" valign="top" align="left"><input type="radio" value="1"  name="grade" <%=(grade!=null && grade.equals("1"))? "checked":""%>>综合会议&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;               
        <input type="radio" value="2" name="grade" <%=(grade!=null && grade.equals("2"))? "checked":""%>>处内会议</td>   
      <td width="9%" colspan="2" height="2" valign="top" align="left"></td>   
      <td width="9%" colspan="2" height="2" valign="top" align="left"></td>   
      <td width="24%" colspan="2" height="2" valign="top" align="center"></td>   
      <td width="60%" height="2"></td>   
    </tr> 
    <tr>    
      <td width="8%" height="30"></td>    
      <td width="11%" height="30">会议时间:</td>    
      <td width="36%" colspan="6" height="30"><font color="#000000" size="2">&nbsp;</font>    
    <input type="text" name="startYear" size="4" value="<%=(startYear ==null || startYear.equals("null"))?"": startYear%>" maxlength="4" onchange="year()" onkeyup="return keyUp(1)">年             
	<input type="text" name="startMonth" size="2" value="<%=(startMonth ==null || startMonth.equals("null"))?"": startMonth%>" onchange="month()" maxlength="2" onkeyup="return keyUp(2)">月           
	<input type="text" name="startDay" size="2" value="<%=(startDay ==null || startDay.equals("null"))?"": startDay%>" onchange="day()" maxlength="2" onkeyup="return keyUp(3)">日           
	<input type="text" name="startHour" size="2" value="<%=(startHour ==null || startHour.equals("null"))?"": startHour%>" maxlength="2" onchange="hour(1)" onkeyup="return keyUp(4)">时           
	<input type="text" name="startMinute" size="2" value="<%=(startMinute ==null || startMinute.equals("null"))?"": startMinute%>"  maxlength="2" onchange="minute(1)" onkeyup="return keyUp(5)">      
	分&nbsp;&nbsp;<B>至</B>&nbsp; <input type="text" name="endYear" size="4" value="<%=(endYear ==null || endYear.equals("null"))?"": endYear%>" maxlength="4" >年          
    <input type="text" name="endMonth" size="2" value="<%=(endMonth ==null || endMonth.equals("null"))?"": endMonth%>" maxlength="2" >月          
    <input type="text" name="endDay" size="2" value="<%=(endDay ==null || endDay.equals("null"))?"": endDay%>" maxlength="2" >日          
    <input type="text" name="endHour" size="2" value="<%=(endHour ==null || endHour.equals("null"))?"": endHour%>" maxlength="2" onchange="hour(2)" onkeyup="return keyUp(6)">时          
    <input type="text" name="endMinute" size="2" value="<%=(endMinute ==null || endMinute.equals("null"))?"": endMinute%>" maxlength="2" onchange="minute(2)" onkeyup="return keyUp(7)">分</td>    
      <td width="14%" colspan="2" height="30"></td>    
      <td width="22%" colspan="2" height="30"></td>    
    </tr>    
    <tr>    
      <td width="8%" height="30"></td>    
      <td width="11%" height="30">主持人：</td>   
      <td width="36%" colspan="6" height="30"><input type="text" name="presider" size="36" value="<%=(presider==null || presider.equals("null"))?"": presider%>"></td>   
      <td width="14%" colspan="2" height="30"></td>   
      <td width="22%" colspan="2" height="30"></td>   
    </tr>   
    <tr>   
      <td width="8%" rowspan="2" height="26"></td>   
      <td width="11%" rowspan="2" height="26">    
        公司领导：</td>   
      <td width="7%" rowspan="2" height="26">    
        <select name="selLeaders" multiple size="10" style="width: 127; height: 97">   
                            <%for (int i = 0; i < leaders.length; i++) {%>     
                            <option value="<%=leaders[i]%>"><%=leaders[i]%></option>   
                            <%}%>   
                          </select>       
      </td>   
      <td width="7%" height="5" valign="baseline" align="center">   
        <p align="center"><input type="button" name="11" value="添加>>" onclick="add()">   
        </p>   
      </td>   
      <td width="15%" rowspan="2" height="26">   
        <select name="leaders" multiple size="10" style="width: 127; height: 97">   
                       <% if ( selectedLeaders != null ){    
                            for (int i = 0; i < selectedLeaders.length; i++) {%>    
                            <option value="<%=selectedLeaders[i]%>"><%=selectedLeaders[i]%></option>   
                            <%}   
                            }%>   
                              
        </select>    
      </td>   
      <td width="13%" rowspan="2" height="26">   
      </td>   
      <td width="19%" rowspan="2" height="26" colspan="2">   
      </td>   
      <td width="44%" colspan="2" rowspan="2" height="26">   
      </td>   
      <td width="52%" rowspan="2" height="26">   
      </td>   
      <td width="60%" rowspan="2" height="26"></td>   
    </tr>   
    <tr>   
      <td width="7%" height="5" valign="top" align="center">   
        <input type="button" name="22" value="<<删除"  onclick="del()">   
      </td>   
    </tr>   
    <tr>   
      <td width="8%" height="62"></td>   
      <td width="11%" height="62"> 会议名称：</td>   
      <td width="15%" colspan="4" height="62"><textarea rows="3" name="content" cols="129"><%=(content ==null || content.equals("null"))?"": content%></textarea> </td>   
      <td width="15%" colspan="4" height="62"> </td>   
      <td width="30%" colspan="2" height="62"> </td>   
    </tr>   
    <tr>   
      <td width="8%" height="62"></td>   
      <td width="11%" height="62"> 参加单位/人员：</td>   
      <td width="15%" colspan="4" height="62"><textarea rows="3" name="depart" cols="129"><%=(depart ==null || depart.equals("null"))?"": depart%></textarea> </td>   
      <td width="15%" colspan="4" height="62"> </td>   
      <td width="30%" colspan="2" height="62"> </td>   
    </tr>   
    <tr>   
      <td width="8%" height="62"></td>   
      <td width="11%" height="62"> 有关说明：</td>   
      <td width="15%" colspan="4" height="62"><textarea rows="3" name="remark" cols="129"><%=(remark ==null || remark.equals("null"))?"": remark%></textarea></td>   
      <td width="15%" colspan="4" height="62"></td>   
      <td width="30%" colspan="2" height="62"></td>   
    </tr>   
      
    <tr>   
      <td width="50%" colspan="6" height="40">                                   
                  
  <p align="left" style="margin-top: 13">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;         
  <input type="button" value="提交" name="B1" style="font-size: 8pt" onclick="check()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;         
  <input type="reset" value="重写" name="B2" style="font-size: 8pt"></p>                          
      </td>    
      <td width="49%" colspan="6" height="40">                                    
                   
      </td>    
    </tr>    
  </table>    
 <input type="hidden" name="ctrl" value="meetingMod">     
  <input type="hidden" name="userId" value="<%=userId%>">     
	<input type="hidden" name="id" value="<%=id%>">    
</form>                                                                  
					    
<center>    
<p></p>                 
</center>    
</table>    
<p align="center">会议通知管理系统 江苏核电 2004</p>         
</body>    
