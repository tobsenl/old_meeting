<%@ page contentType="text/html; charset=UTF-8"%>
<!-- 
	Author: houyy
	Name:  meetingQry.jsp
	Description: 会议查询
-->
<script language="JavaScript" SRC="script/global.js"></script>
<script language="JavaScript" SRC="script/CheckFormat.js"></script>
<script language="JavaScript" SRC="script/cele_date.js"></script>
<script language="JavaScript" SRC="script/genSQL.js"></script>
<script>init(1,1,1);</script>



<html>
<head>
<title>会议详细信息查询</title>
<meta Name="generator" Content="editplus">
<meta Name="author" Content="">
<meta Name="keywords" Content="">
<meta Name="description" Content="">
<link Href="new.css" Rel="stylesheet" Type="text/css">
<meta Http-equiv="content-type" Content="text/html; Charset=UTF-8">

</head>
<body Leftmargin="0" Topmargin="0" Marginwidth="0" Marginheight="0">
<table Width="98%" Border=0 Align=center Cellpadding=0 Cellspacing=0 Background="images/topbg.gif" Bgcolor=#ffffff>
<tr> 
  <td Width="31%" Rowspan="2"><img Src="images/logo.gif" Width="210" Height="110"></td>  
  <td Width="69%" Height="80">&nbsp;</td>
</tr>
<tr>  
<td>&nbsp;</td>
</tr>
</table>
<table Width="98%" Height="80%" Border="0" Cellpadding="0" Cellspacing="0" Bgcolor="#ffffff" Align=center Style="table-layout: Fixed">
  <tr> 
    <td Valign="top">

  	<table width="800"  cellspacing="0" align="center" height="40"><tr>
    <td align="center"> <font size="2"><b>会议详细信息查询 <b></font> </td>
  </tr></table>
<br>

<form name="form" method="post" action="MeetingServlet">
        <table width="800" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="698cc3">
          <tr>
            <td>       
						<table width="100%" border="0" cellpadding="1" cellspacing="1" bordercolordark="#FFFFFF" bordercolorlight="#999999" align="center">
                <tr align="center" bgcolor="#FFFFFF"> 
                  <td nowrap> 
        <select name="fields" onChange="value_change(document.form);changeAll(document.form)">
        </select>
        </td>
      <td nowrap>
        <select name="compare" change="setOpt(document.form.compare,arrays[arrays['field_types'][document.form.fields.selectedIndex] + '_compare_vals'],arrays[arrays['field_types'][document.form.fields.selectedIndex] + '_compare_texts'])">
        </select>
        </td>
      <td height="30" width="21"><img src="images/select.gif" name="image1" id="image1" style="display:none" onClick="show_cele_date(event,image1,'1970-1-1','2050-1-1',mvalue)" change="value_change(document.form)" width="20" height="20"> 
      </td>
      <td align="left"> 
        <input type="text" name="mvalue" id="mvalue" size="30">
        </td>
      <td nowrap height="30">
        <input type="radio" name="Logic" value="and" checked>
        与
        <input type="radio" name="Logic" value="or">
        或</td>
      <td width="60">
        <input type="button" style="COLOR: #44606B;background-color: #FFFFFF" name="set" value="设置" onClick="createSQL(this.form)">
        </td>
    </tr>
			<tr align="center" bgcolor="#FFFFFF"> 
      <td colspan="5">
        <textarea name="SHOW" cols="80" rows="4" wrap="VIRTUAL" readonly></textarea>
        </td>
      <td width="60"> 
        <input type="hidden" name="SQL">
        <input type="button" name="operate" style="COLOR: #44606B;background-color: #FFFFFF" value="查找" onClick="checksub(this.form)">
        <input type="reset" name="Reset" style="COLOR: #44606B;background-color: #FFFFFF" value="重写">
                  </td>
                </tr>
              </table></td>
          </tr>
        </table>

<input type="hidden" name="ctrl" value="meetingQry">
</form>  
<script>
	arrays['field_vals'] = new Array('content','starttime','building','room','commitdepart');
	arrays['field_texts'] = new Array('会议名称','会议时间','会议地点','会议房间号','会议申请部门');
	arrays['field_types'] = new Array('string','date','string','string','string');
	setOpt(document.form.fields,arrays['field_vals'],arrays['field_texts'])
	changeAll(document.form);
</script>  

		</td>
	</tr>
</table>
<center>
<p align="center">会议通知管理系统 江苏核电 2004</p>  
</center>
</body>
</html>
