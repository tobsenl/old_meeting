<%@page contentType="text/html;charset=UTF-8"%>
<%
String userid = (String)session.getAttribute("userid");
String passwd = (String)session.getAttribute("passwd");
%>
<!-- 
  author: 
  name: left.jsp
  description: 菜单栏
-->
<HTML><HEAD>
<META content="text/html; charset=UTF-8" http-equiv=Content-Type>
<META content="MSHTML 5.00.3700.6699" name=GENERATOR>
<link href="new.css" rel="stylesheet" type="text/css">
<script language="JavaScript1.2">
function changeto(highlightcolor){
source=event.srcElement
if (source.tagName=="TR"||source.tagName=="TABLE")
return
while(source.tagName!="TD")
source=source.parentElement
if (source.style.backgroundColor!=highlightcolor&&source.id!="ignore")
source.style.backgroundColor=highlightcolor
}

function changeback(originalcolor){
if (event.fromElement.contains(event.toElement)||source.contains(event.toElement)||source.id=="ignore")
return
if (event.toElement!=source)
source.style.backgroundColor=originalcolor
}
</script>
</HEAD>
<BODY><!--左侧动态菜单-->
<style type="text/css">
<!--
a{text-decoration:none;color:#000000;font-size:8pt;font-family:verdana}
//-->
</style>
<SCRIPT language=JavaScript>
<!--
//菜单距离IE地址栏的高度
var top=80;
var left=0;
//边框颜色
var bordercolor="#000000";
//菜单以外剩余空间的颜色
var bgcolorLight= "#efefef";
//菜单中没有打开的菜单的颜色
var bgcolorDark="#698cc3";
//打开的菜单的颜色
var ActiveDivColor = "#efefef";
var um1 = new Array();
um1[0] = new Array("部门会议申请",
"会议申请", "MeetingServlet?ctrl=apply",
"会议维护", "MeetingServlet?ctrl=deptBrowse");
um1[1] = new Array("办公室审批",
"会议审批", "MeetingServlet?ctrl=approve");
um1[2] = new Array("会议室分配",
"会议室分配", "MeetingServlet?ctrl=toAllot",
"会议室调配","MeetingServlet?ctrl=toReAllot");
um1[3] = new Array("会议浏览修改",
"最新会议", "MeetingServlet?ctrl=visit",
"历史会议", "MeetingServlet?ctrl=historyVisit",
"最新例会", "MeetingServlet?ctrl=customVisit",
"历史例会", "MeetingServlet?ctrl=historyCustomVisit");
um1[4] = new Array("会议室维护",
"会议室添加", "MeetingRoomServlet?ctrl=add",
"会议室维护", "MeetingRoomServlet?ctrl=visit");
um1[5] = new Array("例会模板维护",
"例会模板添加", "RegularMeetingTemplateServlet?ctrl=add",
"例会模板维护", "RegularMeetingTemplateServlet?ctrl=qry");
um1[6] = new Array("会议查询",
"会议查询", "MeetingServlet?ctrl=toMeetingQry");
//-->
</SCRIPT>

<SCRIPT language=JavaScript>
<!-- 
function checkBrowser(){ 
this.ver=navigator.appVersion 
this.dom=document.getElementById?1:0 
this.ie8=(this.ver.indexOf("MSIE 8")>-1 && this.dom)?1:0; 
this.ie6=(this.ver.indexOf("MSIE 6")>-1 && this.dom)?1:0; 
this.ie7 =(this.ver.indexOf("MSIE 7")>-1 && this.dom)?1:0; 
this.ie5=(this.ver.indexOf("MSIE 5")>-1 && this.dom)?1:0; 
this.ie4=(document.all && !this.dom)?1:0; 
this.ns5=(this.dom && parseInt(this.ver) >= 5) ?1:0; 
this.ns4=(document.layers && !this.dom)?1:0; 
this.mac=(this.ver.indexOf('Mac') > -1) ?1:0; 
this.ope=(navigator.userAgent.indexOf('Opera')>-1); 
this.ie=(this.ie7||this.ie6 || this.ie5 || this.ie4) 
this.ns=(this.ns4 || this.ns5) 
this.bw=(this.ie7||this.ie6 || this.ie5 || this.ie4 || this.ns5 || this.ns4 || this.mac || this.ope) 
this.nbw=(!this.bw) 
return this} 
bw=new checkBrowser() 
var um1length = ((um1.length-1)/2); 
var um1nr = 0; 
var um1nr2 = 1; 
var um1nr3 = 2; 
init(); 
//左上标题图片
function logo() {
  document.write('<div style="position:absolute;top:0;left:2;width:127;height:80;;z-index=100">') 
  document.write('<img src="images/logos.gif" width="124" height="80" border=0 alt="  江苏核电有限公司\n会议管理系统">') 
  document.write('</div>') 
}
//推出按钮
function quit() {
  document.write('<div style="position:absolute;top:320;left:17;width:89;height:29;;z-index=101">') 
  document.write('<a href ="Logout">')
  document.write('<img src="images/quit.gif" width="89" height="29" border=0 alt="退出会议管理系统"></a>') 
  document.write('</div>') 
}
function init() { 
  logo();
  quit();
  if(bw.ie8||bw.ie7||bw.ie6 || bw.ie4 || bw.ie5){ 
    height=document.body.offsetHeight-4;
    document.write('<div id="menubg" style="position:absolute;top:0;left:'+left+';background-color:'+bgcolorLight+';border-style:solid;border-width:1px;border-color:'+bordercolor+';width:127;height:'+height+';border-top-width:0px">') 
    document.write('</div>') 
    document.write('<div id="m" style="position:absolute;top:'+top+';left:'+left+'">') 
    for (var i = 0; i < um1.length; i++) {
      var idIndex = 0;
      idIndex = i + 1;      
      document.write('<div style="background-color:'+bgcolorDark+';border-style:solid;border-width:1px;border-color:'+bordercolor+';width:127">') 
      document.write('<table align="center">') 
      document.write('<tr><td align="center"><a href="javascript:do_m_ie('+i+')">'+um1[i][0]+'</a></td></tr>') 
      document.write('</table>') 
      document.write('</div>') 
      document.write('<div id="um' + idIndex + '" style="position:absolute;top:20;left:0;background-color:'+bgcolorLight+';border-style:solid;border-width:1px;border-color:'+bordercolor+';width:127;border-bottom-width:0px">') 
      document.write('<table align="center" BGCOLOR='+ ActiveDivColor +' WIDTH="100%" onMouseover=changeto("#efefef") onMouseout=changeback("#efefef")>'); 
      um1length = (um1[i].length-1)/2;    
      um1nr2 = 1; 
      um1nr3 = 2; 
      for (var k = 0; k < um1length; k++) {
        document.write('<tr><td align="center"><a href="'+um1[i][um1nr3]+'" target="main">'+um1[i][um1nr2]+'</a></td></tr>');         
        um1nr2=um1nr2+2; 
        um1nr3=um1nr3+2;
      }
      document.write('</table>') 
      document.write('</div>') 
      if (i==(um1.length-1)) {
        document.write('<div id="m' + idIndex + '" style="height:'+((um1[i].length-1)/2*17.5+20+5)+';position:absolute;top:20;left:0;background-color:'+bgcolorLight+';border-style:solid;border-width:1px;border-color:'+bordercolor+';border-bottom-width:0px;width:127">') 
        document.write('</div></div></div></div>') 
      } else {
        document.write('<div id="m' + idIndex + '" style="position:absolute;top:20;left:0">') 
      }
    }
  } 
  else if(bw.ns4){ 
  height=innerHeight; 
  document.write('<layer id="menubg" position="absolute" top="0" left="'+left+'" bgcolor="'+bordercolor+'" width="127" height="'+(height)+'">') 
  document.write('</layer>') 
  document.write('<layer id="menubg2" position="absolute" top="0" left="'+(left+1)+'" bgcolor="'+bgcolorLight+'" width="125" height="'+(height-1)+'">') 
  document.write('</layer>') 
  document.write('<layer id="m" position="absolute" top="50" left="'+(left+1)+'">') 
  document.write('<layer bgcolor="'+bordercolor+'" width="125">') 
  document.write('<table align="center">') 
  document.write('<tr><td align="center"></td></tr>') 
  document.write('</table>') 
  document.write('</layer>') 
  document.write('<layer bgcolor="'+bgcolorDark+'" width="125" position="absolute" top="1" left="0">') 
  document.write('<table align="center">') 
  document.write('<tr><td align="center"><a href="javascript:do_m1_n()">'+um1[0]+'</a></td></tr>') 
  document.write('</table>') 
  document.write('</layer>') 
  document.write('<layer bgcolor="'+bordercolor+'" width="125" position="absolute" top="21" left="0">') 
  document.write('<table align="center">') 
  document.write('<tr><td align="center"></td></tr>') 
  document.write('</table>') 
  document.write('</layer>') 
  document.write('<layer id="um1" position="absolute" top="22" left="0" bgcolor="'+bgcolorLight+'" width="125">') 
  document.write('<table align="center">') 
  } 
  else if(bw.ns5){ 
  height=innerHeight-1; 
  document.write('<div id="menubg" style="position:absolute;top:0;left:'+left+';background-color:'+bgcolorLight+';border-style:solid;border-width:1px;border-color:'+bordercolor+';width:127;height:'+height+';border-top-width:0px">') 
  document.write('</div>') 
  document.write('<div id="m" style="position:absolute;top:'+top+';left:'+left+'">') 
  document.write('<div style="background-color:'+bgcolorDark+';border-style:solid;border-width:1px;border-color:'+bordercolor+';width:127">') 
  document.write('<table align="center">') 
  document.write('<tr><td align="center"><a href="javascript:do_m1_n6()">'+um1[0]+'</a></td></tr>') 
  document.write('</table>') 
  document.write('</div>') 
  document.write('<div id="um1" style="position:absolute;top:18;left:0;background-color:'+bgcolorLight+';border-style:solid;border-width:1px;border-color:'+bordercolor+';width:127;border-bottom-width:0px">') 
  document.write('<table align="center">') 
  } 
  else{ 
  height=document.body.offsetHeight-4; 
  document.write('<div id="menubg" style="position:absolute;top:0;left:'+left+';background-color:'+bgcolorLight+';border-style:solid;border-width:1px;border-color:'+bordercolor+';width:127;height:'+height+';border-top-width:0px">') 
  document.write('</div>') 
  document.write('<div id="m" style="position:absolute;top:'+top+';left:'+left+'">') 
  document.write('<div style="background-color:'+bgcolorDark+';border-style:solid;border-width:1px;border-color:'+bordercolor+';width:127">') 
  document.write('<table align="center">') 
  document.write('<tr><td align="center"><a href="#">'+um1[0]+'</a></td></tr>') 
  document.write('</table>') 
  document.write('</div>') 
  document.write('<div id="um1" style="position:absolute;top:20;left:0;background-color:'+bgcolorLight+';border-style:solid;border-width:1px;border-color:'+bordercolor+';width:127;border-bottom-width:0px">') 
  document.write('<table align="center">') 
  } 
} 
var m1pos_ie=20, m1jump_ie=-5; 
var m2pos_ie=20, m2jump_ie=-5; 
var m3pos_ie=20, m3jump_ie=-5; 
var m1pos_n6=18, m1jump_n6=-6; 
var m2pos_n6=18, m2jump_n6=-6; 
var m3pos_n6=18, m3jump_n6=-6; 
var m1pos_n=21, m1jump_n=-5; 
var m2pos_n=21, m2jump_n=-5; 
var m3pos_n=21, m3jump_n=-5; 
var time=5;
var mpos = new Array();
for (var i=0;i<um1.length;i++) {mpos[i] = new Array(20,-5);}
var menuIndex = -1;
function do_m_ie(mIndex) {
  if (menuIndex==-1) {menuIndex=mIndex;}
  if(menuIndex==mIndex) {
    mpos[mIndex][1] = -mpos[mIndex][1]; 
    if (mpos[mIndex][0]<=20 || mpos[mIndex][0]>=(um1[mIndex].length-1)/2*17.5+20) {m_move_ie(mIndex);}   
  } else {
     mpos[mIndex][1]=-5;
    mpos[mIndex][1]=-mpos[mIndex][1]; 
    if(mpos[mIndex][0]<=20 || mpos[mIndex][0]>=(um1[mIndex].length-1)/2*17.5+20) {m_move_ie(mIndex);}    
    mpos[menuIndex][1]=-mpos[menuIndex][1]; 
    if(mpos[menuIndex][0]>=(um1[menuIndex].length-1)/2*17.5+20) {m_move_ie(menuIndex);}     
  }
  menuIndex=mIndex;
} 
function m_move_ie(m) {
  var divIndex =0;
  divIndex = m + 1;
  document.all("m"+divIndex).style.top=mpos[m][0]; 
  mpos[m][0]+=mpos[m][1]; 
  if(mpos[m][0]>15 && mpos[m][0]<(um1[m].length-1)/2*17.5+20+5) {setTimeout("m_move_ie("+m+")", 5);}
}
function do_m1_n() { 
if(m2pos_n>0) { 
m2jump_n=-5; 
if(m2pos_n>=(um2.length-1)/2*20+20) 
m2_move_n(); 
} 
if(m3pos_n>0) { 
m3jump_n=-5; 
if(m3pos_n>=(um3.length-1)/2*20+20)
m3_move_n(); 
} 
m1jump_n=-m1jump_n; 
if(m1pos_n<=21 || m1pos_n>=(um1.length-1)/2*20+20) 
m1_move_n(); 
} 
function m1_move_n() 
{ 
document.m.document.m1.top=m1pos_n; 
m1pos_n+=m1jump_n; 
if(m1pos_n>=20 && m1pos_n<(um1.length-1)/2*20+20) 
setTimeout("m1_move_n()", time); 
} 
function do_m2_n() { 
if(m1pos_n>0) { 
m1jump_n=-5; 
if(m1pos_n>=(um1.length-1)/2*20+20) 
m1_move_n(); 
} 
if(m3pos_n>0) { 
m3jump_n=-5; 
if(m3pos_n>=(um3.length-1)/2*20+20) 
m3_move_n(); 
} 
m2jump_n=-m2jump_n; 
if(m2pos_n<=21 || m2pos_n>=(um2.length-1)/2*20+20) 
m2_move_n(); 
} 
function m2_move_n() { 
document.m.document.m1.document.m2.top=m2pos_n; 
m2pos_n+=m2jump_n; 
if(m2pos_n>20 && m2pos_n<(um2.length-1)/2*20+20) 
setTimeout("m2_move_n()", time); 
} 
function do_m3_n() { 
if(m2pos_n>0) { 
m2jump_n=-5; 
if(m2pos_n>=(um2.length-1)/2*20+20) 
m2_move_n(); 
} 
if(m1pos_n>0) { 
m1jump_n=-5; 
if(m1pos_n>=(um1.length-1)/2*20+20) 
m1_move_n(); 
} 
m3jump_n=-m3jump_n; 
if(m3pos_n<=21 || m3pos_n>=(um3.length-1)/2*20+20) 
m3_move_n(); 
} 
function m3_move_n() { 
document.m.document.m1.document.m2.document.m3.top=m3pos_n; 
m3pos_n+=m3jump_n; 
if(m3pos_n>20 && m3pos_n<(um3.length-1)/2*20+20) 
setTimeout("m3_move_n()", time); 
} 
function do_m1_n6() { 
if(m2pos_n6>0) { 
m2jump_n6=-6; 
if(m2pos_n6>=(um2.length-1)/2*17.5+20) 
m2_move_n6(); 
} 
if(m3pos_n6>0) { 
m3jump_n6=-6; 
if(m3pos_n6>=(um3.length-1)/2*17.5+20) 
m3_move_n6(); 
} 
m1jump_n6=-m1jump_n6; 
if(m1pos_n6<=18 || m1pos_n6>=(um1.length-1)/2*17.5+20) 
m1_move_n6(); 
} 
function m1_move_n6() { 
document.getElementById('m1').style.top=m1pos_n6; 
m1pos_n6+=m1jump_n6; 
if(m1pos_n6>15 && m1pos_n6<(um1.length-1)/2*17.5+20+5) 
setTimeout("m1_move_n6()", time); 
} 
function do_m2_n6() { 
if(m1pos_n6>0) { 
m1jump_n6=-6; 
if(m1pos_n6>=(um1.length-1)/2*17.5+20) 
m1_move_n6(); 
} 
if(m3pos_n6>0) { 
m3jump_n6=-6; 
if(m3pos_n6>=(um3.length-1)/2*17.5+20) 
m3_move_n6(); 
} 
m2jump_n6=-m2jump_n6; 
if(m2pos_n6<=18 || m2pos_n6>=(um2.length-1)/2*17.5+20) 
m2_move_n6(); 
} 
function m2_move_n6() { 
document.getElementById('m2').style.top=m2pos_n6; 
m2pos_n6+=m2jump_n6; 
if(m2pos_n6>15 && m2pos_n6<(um2.length-1)/2*17.5+20+5) 
setTimeout("m2_move_n6()", time); 
} 
function do_m3_n6() { 
if(m2pos_n6>0) { 
m2jump_n6=-6; 
if(m2pos_n6>=(um2.length-1)/2*17.5+20) 
m2_move_n6(); 
} 
if(m1pos_n6>0) { 
m1jump_n6=-6; 
if(m1pos_n6>=(um1.length-1)/2*17.5+20) 
m1_move_n6(); 
} 
m3jump_n6=-m3jump_n6; 
if(m3pos_n6<=18 || m3pos_n6>=(um3.length-1)/2*17.5+20) 
m3_move_n6(); 
} 
function m3_move_n6() { 
document.getElementById('m3').style.top=m3pos_n6; 
m3pos_n6+=m3jump_n6; 
if(m3pos_n6>15 && m3pos_n6<(um3.length-1)/2*17.5+20+5) 
setTimeout("m3_move_n6()", time); 
} 
do_m_ie(0);
//-->
</SCRIPT>
<script>
function backtop() {
	form1.submit();
}
</script>
</BODY></HTML>
