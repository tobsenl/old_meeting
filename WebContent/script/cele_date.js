//?????????????? 
var date_start,date_end,g_object  ;
var disble_ground=new Image;
//var a1=new Image;
//var a2=new Image;
//a1.src="../image/left.gif";
//a2.src="../image/right.gif";
//disble_ground.src="bg.gif";

//mode :??????????????0-?? 1-?? 2-??????????
function change_date(temp,mode)
{var t_month,t_year;
    if (mode){
        if(mode==1)
        t_month=parseInt(cele_date_month.value,10)+parseInt(temp,10);
        else
        t_month=parseInt(temp);
        if (t_month<cele_date_month.options(0).text) {
            cele_date_month.value=cele_date_month.options(cele_date_month.length-1).text;
            change_date(parseInt(cele_date_year.value,10)-1,0);
            }
        else{
            if (t_month>cele_date_month.options(cele_date_month.length-1).text){
                cele_date_month.value=cele_date_month.options(0).text;
                change_date(parseInt(cele_date_year.value,10)+1,0);
                }            
            else
                {cele_date_month.value=t_month;
                 set_cele_date(cele_date_year.value,cele_date_month.value);                
                }
        }
    }  
    else{
        t_year=parseInt(temp,10);
        
        if (t_year<cele_date_year.options(0).text) {
            cele_date_year.value=cele_date_year.options(0).text;
            set_cele_date(cele_date_year.value,1);                
            }
        else{
            if (parseInt(t_year,10)>parseInt(cele_date_year.options(cele_date_year.length-1).text,10)){
                cele_date_year.value=cele_date_year.options(cele_date_year.length-1).text;
                set_cele_date(cele_date_year.value,12);                
                }            
            else
                {cele_date_year.value=t_year;
                 set_cele_date(cele_date_year.value,cele_date_month.value);                
                }
        }
    }
}

//??????????
function init(d_start,d_end)
{
     var temp_str;
     var i=0;
     var j=0;
     date_start=new Date(2000,7,1);
     date_end=new Date(2004,8,1);
     
     //????????????????????
     document.writeln("<div name=\"cele_date\" id=\"cele_date\"  style=\"display:none\"    style=\"LEFT: 69px; POSITION: absolute; TOP: 159px\" onClick=\"event.cancelBubble=true;\" >&nbsp; </div>");
     
     
     window.cele_date.innerHTML="";
     temp_str="<table border=1 bgcolor='#DFDFDF'><tr><td colspan=7 nowrap>";
     //temp_str+="<img id=s_a1 name=s_a1 src=\"../image/left.gif\" language=\"javascript\" onclick=\"change_date(-1,1)\" >";//??????????
     temp_str+="Year";//??
     temp_str+="<select name=\"cele_date_year\" id=\"cele_date_year\" language=\"javascript\" onchange=\"change_date(this.value,0)\">"
     for (i=1990;i<=2050;i++)
     {
     temp_str+="<OPTION value=\""+i.toString()+"\">"+i.toString()+"</OPTION>";
     }
     temp_str+="</select>&nbsp;";
     
     temp_str+="Month"//??
     temp_str+="<select name=\"cele_date_month\" id=\"cele_date_month\" language=\"javascript\" onchange=\"change_date(this.value,2)\" >"
     for (i=1;i<=12;i++)
     {
     temp_str+="<OPTION value=\""+i.toString()+"\">"+i.toString()+"</OPTION>";
     }
     temp_str+="</select>&nbsp;";
     
     //temp_str+=""//??????
     //temp_str+="<img id=s_a2 name=s_a2 src=\"../image/right.gif\" language=\"javascript\" onclick=\"change_date(1,1)\" >";
     temp_str+="</td></tr><tr><td colspan=7 nowrap>"
      
     temp_str+="Day"//????
     temp_str+="<select name=\"cele_date_hour\" id=\"cele_date_hour\">"
     for (i=0;i<24;i++){
     	temp_str+="<OPTION value=\""+i.toString()+"\">"+i.toString()+"</OPTION>";
     }
     temp_str+="</select>&nbsp;";
     
     temp_str+="&nbsp;&nbsp;Hour"//????
     temp_str+="<select name=\"cele_date_min\" id=\"cele_date_min\">"    
     for (i=0;i<60;i++){
     	temp_str+="<OPTION value=\""+i.toString()+"\">"+i.toString()+"</OPTION>";
     }
     temp_str+="</select>&nbsp;";   
     
     temp_str+="</td></tr><tr><td>"
     
     temp_str+="Su</td><td>";temp_str+="Mo</td><td>"; temp_str+="Tu</td><td>"; temp_str+="We</td><td>"
     temp_str+="Th</td><td>";temp_str+="Fr</td><td>"; temp_str+="Sa</td></tr>";
     for (i=1 ;i<=6 ;i++)
     {
     temp_str+="<tr>";
        for(j=1;j<=7;j++){
            temp_str+="<td name=\"c"+i+"_"+j+"\"id=\"c"+i+"_"+j+"\" style=\"CURSOR: hand\"  language=\"javascript\" onclick=\"td_click(this)\">&nbsp;</td>"
            }
     temp_str+="</tr>"        
     }
     temp_str+="</td></tr></table>";
     window.cele_date.innerHTML=temp_str;
     //document.all("s_a1").src=a1.src
     //document.all("s_a2").src=a2.src
}
function set_cele_date(year,month)
{
   var i,j,p,k
   var nd=new Date(year,month-1,1);
   event.cancelBubble=true;
   cele_date_year.value=year;
   cele_date_month.value=month;   
   k=nd.getDay()-1
   for (i=1;i<=6;i++)
      for(j=1;j<=7;j++)
      {
      eval("c"+i+"_"+j+".innerHTML=\"\"");
      eval("c"+i+"_"+j+".bgColor=\"white\"");
      eval("c"+i+"_"+j+".style.cursor=\"hand\"");
      }
   while(month-1==nd.getMonth())
    { j=(nd.getDay() +1);
      p=parseInt((nd.getDate()+k) / 7)+1;
      eval("c"+p+"_"+j+".innerHTML="+"\""+nd.getDate()+"\"");
      
      
      if (nd>date_end || nd<date_start)
      {
      //eval("c"+p+"_"+j+".style.backgroundImage.src=disble_ground.src");
      eval("c"+p+"_"+j+".bgColor=\"DarkGray\"");
      eval("c"+p+"_"+j+".style.cursor=\"text\"");
      }
      nd=new Date(nd.valueOf() + 86400000)
    }
}

//eP??????????????d_start-d_end????????????????t_object??????????????????
function show_cele_date(evt,eP,d_start,d_end,t_object)
{
var s,cur_d
var eT = eP.offsetTop;  
var eH = eP.offsetHeight;  
var dH = window.cele_date.style.pixelHeight;  
var sT = document.body.scrollTop;  
event.cancelBubble=true;

//window.cele_date.style.left = eP.offsetLeft;  

//if(eT-dH >= sT && eT+eH+dH > document.body.clientHeight+sT)
//Window.cele_date.style.top = eT-dH;  
//else window.cele_date.style.top = eT+eH+80;  

//Window.cele_date.style.left = evt.screenX-160;  
window.cele_date.style.left = evt.x; 
//if(eT-dH >= sT && eT+eH+dH > document.body.clientHeight+sT)
//Window.cele_date.style.top = eT-dH;  
//else window.cele_date.style.top = eT+eH+80;  
window.cele_date.style.top = evt.y;  


s=d_start.split("-")
date_start=new Date(s[0],s[1]-1,s[2])

s=d_end.split("-")
date_end=new Date(s[0],s[1]-1,s[2])
g_object=t_object

cur_d=new Date()
set_cele_date(cur_d.getYear(),cur_d.getMonth()+1);
window.cele_date.style.display="block";
}

function td_click(t_object)
{
var t_d;
var new_date;
if (parseInt(t_object.innerHTML,10)>=1 && parseInt(t_object.innerHTML,10)<=31 ) 
{ t_d=new Date(cele_date_year.value,cele_date_month.value-1,t_object.innerHTML)
if (t_d<=date_end && t_d>=date_start)
{
	//new_date=cele_date_year.value+"-"+cele_date_month.value+"-"+t_object.innerHTML+" "+cele_date_hour.value+":"+cele_date_min.value
	// Add By Chenyongcai
	var newmonth = "" + cele_date_month.value;
	var newday = "" + t_object.innerHTML;
	if (newmonth.length==1)	{
		newmonth = "0" + newmonth;
	}
	if (newday.length==1)	{
		newday = "0" + newday;
	}
	// End Add
	new_date=cele_date_year.value+"-"+newmonth+"-"+newday
    //Modified By Chenyongcai 2004-03-16
	//if ((new Date(cele_date_year.value,cele_date_month.value-1,t_object.innerHTML)) > (new Date())) {
	//  alert("??????????????????????????????????????????");
	//} else {
	  g_object.value = new_date;
	//}
	window.cele_date.style.display="none";}
	}

}

function h_cele_date()
{
window.cele_date.style.display="none";
}

