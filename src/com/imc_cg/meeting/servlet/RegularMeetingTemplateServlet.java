package com.imc_cg.meeting.servlet;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imc_cg.meeting.bean.business.MeetingRoom;
import com.imc_cg.meeting.bean.business.RegularMeetingTask;
import com.imc_cg.meeting.bean.business.RegularMeetingTemplate;
import com.imc_cg.meeting.bean.util.JNPCTools;

public class RegularMeetingTemplateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	private RegularMeetingTemplate regularMeetingTemplate;
	private MeetingRoom meetingRoom;

  public void init() throws ServletException {
  	regularMeetingTemplate = new RegularMeetingTemplate();
  	meetingRoom = new MeetingRoom();
  }

  //Process the HTTP Get request
  public void service(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    request.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession();
    ServletContext sc = getServletContext();
    RequestDispatcher rd = null;
    //ContentFactory holder = null;
    //获得控制变量,用于区别提交的页面
    String ctrl = request.getParameter("ctrl");
    String userid = (String) session.getAttribute("userid"); //用户id
    Vector vec = (Vector)session.getAttribute("vec");        //用户权限
    
    String id = ""; //例会模板流水号
		String building = ""; //会议室所在楼
		String room = ""; //房间号
		String capacity = ""; //容纳人数							
		String startTime = ""; //会议开始时间
    String endTime = ""; //会议结束时间
    String content = ""; //会议内容
    String[] selectedLeaders = null; //已选中的会议领导
    String depart = ""; // 参加会议部门
    String remark = ""; //会议说明
    String commiterId = ""; ///申请提交人id
    String approverId = ""; //会议审核人id
    String alloterId = ""; //会议室安排人id
    String presider = ""; //会议主持人
    String grade = ""; //会议级别 
    String scheduledPeriod = ""; //例会周期天数
    String daysBeforeTrigger = ""; //提前触发天数
    String lastTriggerDate = ""; //上次触发日期
    String isAutoTrigger = ""; //是否自动触发（0否；1是）
    String roomID = "";
    String Error = "";  //系统报错信息
  
    int flowID = 0; //会议记录流水号
    int i = 0; // 页面来源
    int flag = 0; //sql是否成功执行
    int flag2 = 0;

    String[] leaders = null; //公司领导
    String[] template = null; //单个模板
    String[] ids  = null;  //要删除的会议信息id
    String[] result = null;  //
    String[][] templates = null; //模板查询
    String[][] allRoom = null; //会议室信息
    
    Hashtable unavailableRoom = null;//记录不可用的会议室
    
    if (ctrl.equals( "toMeetingQry") || ctrl.equals( "meetingQry") ) {
    }else{
	    if (vec == null) {
	      Error = "系统session超时，请重新登录！";
	      request.setAttribute("Error", Error);
	      rd = sc.getRequestDispatcher("/error.jsp");
	      rd.forward(request, response);
	      return;
	    }
    }
    if (ctrl.equals("add")) { i = 1; }  //例会模板添加
    else if (ctrl.equals("qry")) { i = 2; }  //例会模板维护
    else if (ctrl.equals("saveAdd")) { i = 3; }
    else if (ctrl.equals("delete")) { i = 4; }
    else if (ctrl.equals("qrySingle")) { i = 5; }
    else if (ctrl.equals("saveEdit")) { i = 6; }
    else if (ctrl.equals("allot")) { i = 7; }
    else if (ctrl.equals("alloted")) { i = 8; }
    else if (ctrl.equals("triggerAll")) { i = 9; }
    else if (ctrl.equals("triggerSingle")) { i = 10; }
    
    switch (i) {
    	case 1: //点击左侧例会模板添加
    		if (vec.contains("380101")) {            
          //得到公司领导   
           leaders = JNPCTools.getLeaders();   
           request.setAttribute("leaders", leaders);
           rd = sc.getRequestDispatcher("/regularMeetingTemplateAdd.jsp");
           rd.forward(request, response);
         }else {
           Error = "对不起，您没有部门会议申请的权限！";
           request.setAttribute("Error", Error);
           rd = sc.getRequestDispatcher("/error.jsp");
           rd.forward(request, response);
           return;
         }
    		break;
    		
    	case 2: //点击左侧例会模板维护
    		if (vec.contains("380101")) {   
    			templates = regularMeetingTemplate.getRegularMeetingTemplates();
          request.setAttribute("templates", templates);
         
          rd = sc.getRequestDispatcher("/regularMeetingTemplateQry.jsp");
          rd.forward(request, response);
        }else{
          Error = "对不起，您没有部门会议维护的权限！";
          request.setAttribute("Error", Error);
          rd = sc.getRequestDispatcher("/error.jsp");
          rd.forward(request, response);
          return;
        }
    		break;
    		
    	case 3: //保存新建的模板
	  		building = request.getParameter("building");
	  		room = request.getParameter("room");
	  		capacity = request.getParameter("capacity");
	  		startTime = JNPCTools.getStartTime(request);
	  		endTime =  JNPCTools.getEndTime(request);
	  		content = request.getParameter("content");
	  		selectedLeaders = request.getParameterValues("leaders");
	  		depart = request.getParameter("depart");
	  		remark = request.getParameter("remark");
	  		commiterId = userid;
	  		approverId = request.getParameter("approverId");
	  		if("".equals(approverId) || approverId==null){
	  			approverId = userid;
	  		}
	  		alloterId = request.getParameter("alloterId");
	  		if("".equals(alloterId) || alloterId==null){
	  			alloterId = userid;
	  		}
	  		presider = request.getParameter("presider");
	  		grade = request.getParameter("grade");
	  		scheduledPeriod = request.getParameter("scheduledPeriod");
	  		daysBeforeTrigger = request.getParameter("daysBeforeTrigger");
	  		lastTriggerDate = request.getParameter("lastTriggerDate");
	  		isAutoTrigger = request.getParameter("isAutoTrigger");
	  		roomID = request.getParameter("roomID");
	  		
	  		flag = regularMeetingTemplate.addRegularMeetingTemplate(building, room, capacity, startTime, endTime, content, selectedLeaders, depart, remark, commiterId, approverId, alloterId, presider, grade, scheduledPeriod, daysBeforeTrigger, lastTriggerDate, isAutoTrigger, roomID);
	  		if(flag == -1){
	  			rd = sc.getRequestDispatcher("/error.jsp");
	  		}else if(flag == -99){
	  			rd = sc.getRequestDispatcher("/meetingError2.jsp");
	  		}else if(flag == -98){
	  			rd = sc.getRequestDispatcher("/meetingError3.jsp");
	  		}else if(flag == -999){
	  			rd = sc.getRequestDispatcher("/meetingError.jsp");
	  		}else{
	  			rd = sc.getRequestDispatcher("/RegularMeetingTemplateServlet?ctrl=qry");
	  		}
	  		rd.forward(request, response);
	  		break;
	  		
    	case 4: //删除模板
    		ids = request.getParameterValues("id");
    		flag = regularMeetingTemplate.deleteRegularMeetingTemplate(ids);
    		if(flag == -1){
	  			rd = sc.getRequestDispatcher("/error.jsp");
	  		}else{
	  			rd = sc.getRequestDispatcher("/RegularMeetingTemplateServlet?ctrl=qry");
	  		}
	  		rd.forward(request, response);
	  		break;
	  		
    	case 5: //根据id查询单个模板
    		id = request.getParameter("id");
    		template = regularMeetingTemplate.getRegularMeetingTemplateById(id);
    		request.setAttribute("template", template);
    		leaders = JNPCTools.getLeaders();   
        request.setAttribute("leaders", leaders);
        
        rd = sc.getRequestDispatcher("/singleRegularMeetingTemplate.jsp");
        rd.forward(request, response);
        break;
        
    	case 6: //保存编辑
    		id = request.getParameter("meetingID");
	  		building = request.getParameter("building");
	  		room = request.getParameter("room");
	  		capacity = request.getParameter("capacity");
	  		roomID = request.getParameter("roomID");
	  		startTime = JNPCTools.getStartTime(request);
	  		endTime =  JNPCTools.getEndTime(request);
	  		content = request.getParameter("content");
	  		selectedLeaders = request.getParameterValues("leaders");
	  		depart = request.getParameter("depart");
	  		remark = request.getParameter("remark");
	  		commiterId = request.getParameter("commiterId");
	  		approverId = request.getParameter("approverId");
	  		alloterId = request.getParameter("alloterId");
	  		presider = request.getParameter("presider");
	  		grade = request.getParameter("grade");
	  		scheduledPeriod = request.getParameter("scheduledPeriod");
	  		daysBeforeTrigger = request.getParameter("daysBeforeTrigger");
	  		lastTriggerDate = request.getParameter("lastTriggerDate");
	  		isAutoTrigger = request.getParameter("isAutoTrigger");
	  		
	  		flag = regularMeetingTemplate.modifyRegularMeetingTemplate(id, building, room, capacity, startTime, endTime, content, selectedLeaders, depart, remark, commiterId, approverId, alloterId, presider, grade, scheduledPeriod, daysBeforeTrigger, lastTriggerDate, isAutoTrigger, roomID);
	  		if(flag == -1){
	  			rd = sc.getRequestDispatcher("/error.jsp");
	  		}else if(flag == -99){
	  			rd = sc.getRequestDispatcher("/meetingError2.jsp");
	  		}else if(flag == -98){
	  			rd = sc.getRequestDispatcher("/meetingError3.jsp");
	  		}else if(flag == -999){
	  			rd = sc.getRequestDispatcher("/meetingError.jsp");
	  		}else{
	  			templates = regularMeetingTemplate.getRegularMeetingTemplates();
          request.setAttribute("templates", templates);
	  			rd = sc.getRequestDispatcher("/regularMeetingTemplateQry.jsp");
	  		}
	  		rd.forward(request, response);
	  		break;
    	case 7: //分配会议室
    		System.out.println(request.getParameter("commiterId"));
    		request.setAttribute("userId", request.getParameter("userId"));
    		request.setAttribute("meetingID", request.getParameter("meetingID"));
    		request.setAttribute("building", request.getParameter("building"));
    		request.setAttribute("room", request.getParameter("room"));
    		request.setAttribute("capacity", request.getParameter("capacity"));
    		
    		request.setAttribute("startYear", request.getParameter("startYear"));
    		request.setAttribute("startMonth", request.getParameter("startMonth"));
    		request.setAttribute("startDay", request.getParameter("startDay"));
    		request.setAttribute("startHour", request.getParameter("startHour"));
    		request.setAttribute("startMinute", request.getParameter("startMinute"));
    		request.setAttribute("endYear", request.getParameter("endYear"));
    		request.setAttribute("endMonth", request.getParameter("endMonth"));
    		request.setAttribute("endDay", request.getParameter("endDay"));
    		request.setAttribute("endHour", request.getParameter("endHour"));
    		request.setAttribute("endMinute", request.getParameter("endMinute"));
    		
    		request.setAttribute("content", request.getParameter("content"));
    		request.setAttribute("selectedLeaders", request.getParameterValues("leaders"));
    		request.setAttribute("depart", request.getParameter("depart"));
    		request.setAttribute("remark", request.getParameter("remark"));
    		request.setAttribute("commiterId", request.getParameter("commiterId"));
    		request.setAttribute("approverId", request.getParameter("approverId"));
    		request.setAttribute("alloterId", request.getParameter("alloterId"));
    		request.setAttribute("presider", request.getParameter("presider"));
    		request.setAttribute("grade", request.getParameter("grade"));
    		request.setAttribute("scheduledPeriod", request.getParameter("scheduledPeriod"));
    		request.setAttribute("daysBeforeTrigger", request.getParameter("daysBeforeTrigger"));
    		request.setAttribute("lastTriggerDate", request.getParameter("lastTriggerDate"));
    		request.setAttribute("isAutoTrigger", request.getParameter("isAutoTrigger"));

    		startTime = JNPCTools.getStartTime(request);
	  		endTime =  JNPCTools.getEndTime(request);
	  		unavailableRoom = JNPCTools.roomCheck(startTime, endTime);     
        allRoom = meetingRoom.getAllRoom();    
        request.setAttribute("unavailableRoom", unavailableRoom);
        request.setAttribute("allRoom", allRoom);

	  		rd = sc.getRequestDispatcher("/regularMeetingAllot.jsp");
	  		rd.forward(request, response);
	  		break;
    	case 8:
    		System.out.println(request.getParameter("commiterId"));
//    		id = request.getParameter("id");
//	  		building = request.getParameter("building");
//	  		room = request.getParameter("room");
//	  		capacity = request.getParameter("capacity");
//	  		startTime = JNPCTools.getStartTime(request);
//	  		endTime =  JNPCTools.getEndTime(request);
//	  		content = request.getParameter("content");
//	  		leaders = request.getParameterValues("leaders");
//	  		depart = request.getParameter("depart");
//	  		remark = request.getParameter("remark");
//	  		commiterId = request.getParameter("commiterId");
//	  		approverId = request.getParameter("approverId");
//	  		alloterId = request.getParameter("alloterId");
//	  		presider = request.getParameter("presider");
//	  		grade = request.getParameter("grade");
//	  		scheduledPeriod = request.getParameter("scheduledPeriod");
//	  		daysBeforeTrigger = request.getParameter("daysBeforeTrigger");
//	  		lastTriggerDate = request.getParameter("lastTriggerDate");
//	  		isAutoTrigger = request.getParameter("isAutoTrigger");
    		roomID = (String)request.getParameter("roomID");
        result = meetingRoom.getRoomInfo(roomID);
        String meetingID = request.getParameter("meetingID");
        request.setAttribute("userId", request.getParameter("userId"));
    		request.setAttribute("meetingID", meetingID);
    		request.setAttribute("roomID", roomID);
    		request.setAttribute("building", result[1]);
    		request.setAttribute("room", result[2]);
    		request.setAttribute("capacity", result[3]);
    		
    		request.setAttribute("startYear", request.getParameter("startYear"));
    		request.setAttribute("startMonth", request.getParameter("startMonth"));
    		request.setAttribute("startDay", request.getParameter("startDay"));
    		request.setAttribute("startHour", request.getParameter("startHour"));
    		request.setAttribute("startMinute", request.getParameter("startMinute"));
    		request.setAttribute("endYear", request.getParameter("endYear"));
    		request.setAttribute("endMonth", request.getParameter("endMonth"));
    		request.setAttribute("endDay", request.getParameter("endDay"));
    		request.setAttribute("endHour", request.getParameter("endHour"));
    		request.setAttribute("endMinute", request.getParameter("endMinute"));
    		
    		request.setAttribute("content", request.getParameter("content"));
    		request.setAttribute("selectedLeaders", request.getParameterValues("selectedLeaders"));
    		request.setAttribute("depart", request.getParameter("depart"));
    		request.setAttribute("remark", request.getParameter("remark"));
    		request.setAttribute("commiterId", request.getParameter("commiterId"));
    		request.setAttribute("approverId", request.getParameter("approverId"));
    		request.setAttribute("alloterId", request.getParameter("alloterId"));
    		request.setAttribute("presider", request.getParameter("presider"));
    		request.setAttribute("grade", request.getParameter("grade"));
    		request.setAttribute("scheduledPeriod", request.getParameter("scheduledPeriod"));
    		request.setAttribute("daysBeforeTrigger", request.getParameter("daysBeforeTrigger"));
    		request.setAttribute("lastTriggerDate", request.getParameter("lastTriggerDate"));
    		request.setAttribute("isAutoTrigger", request.getParameter("isAutoTrigger"));
	  		
    		leaders = JNPCTools.getLeaders();   
        request.setAttribute("leaders", leaders);
        if(meetingID==null || meetingID.equals("") || meetingID.equals("null")){
        	rd = sc.getRequestDispatcher("/regularMeetingTemplateAdd.jsp");
        } else {
        	rd = sc.getRequestDispatcher("/singleRegularMeetingTemplate.jsp");
        }
        rd.forward(request, response);
        break;
    	case 9:
    		RegularMeetingTask rmt = new RegularMeetingTask();
    		rmt.run();
    		
    		templates = regularMeetingTemplate.getRegularMeetingTemplates();
        request.setAttribute("templates", templates);
       
        rd = sc.getRequestDispatcher("/regularMeetingTemplateQry.jsp");
        rd.forward(request, response);
    		break;
    	case 10:
    		id = request.getParameter("id");
    		RegularMeetingTask rmt1 = new RegularMeetingTask();
    		rmt1.triggerSingle(id);
    		
    		templates = regularMeetingTemplate.getRegularMeetingTemplates();
        request.setAttribute("templates", templates);
       
        rd = sc.getRequestDispatcher("/regularMeetingTemplateQry.jsp");
        rd.forward(request, response);
    		break;
		}
	}
}
