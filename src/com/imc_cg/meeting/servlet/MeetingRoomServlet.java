package com.imc_cg.meeting.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import com.imc_cg.meeting.bean.business.MeetingRoom;
//import com.imc_cg.meeting.bean.util.*;

/**
 
 */

public class MeetingRoomServlet extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
  
  //Initialize global variables
  public void init() throws ServletException {
  }

  //Process the HTTP Get request
  public void service(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    request.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession();
    ServletContext sc = getServletContext();
    RequestDispatcher rd = null;   
    String ctrl = request.getParameter("ctrl"); //获得控制变量,用于区别提交的页面
    String userid = (String) session.getAttribute("userid"); //用户id 
    Vector vec = (Vector)session.getAttribute("vec");        //用户权限    
    MeetingRoom meetingRoom = new MeetingRoom();   
    String roomID=null;//会议室编号
    String building=null;//所在楼
    String room = null; //房间号
    String capacity = null; //容纳人数
    String remark = null; //说明
    String deleted = null;//会议室可用状态
    String Error = "";  //系统报错信息    
    
    String[] roomSelected = null;//选中的会议记录            
    String[] result = null; // 一行多列记录结果
    String[][] results = null; // 多行多列结果
    int i = 0; // 页面来源标记
    int flag = 0; // 判断sql语句执行是否成功的标记

    if (vec == null) {
      Error = "系统session超时，请重新登录！";
      request.setAttribute("Error", Error);
      rd = sc.getRequestDispatcher("/error.jsp");
      rd.forward(request, response);
      return;
    }
    
    if (ctrl.equals("visit"))      { i = 1; } // 点击左侧的会议室维护链接
    if (ctrl.equals("add"))        { i = 2; } // 点击左侧的会议室"添加"链接
    if (ctrl.equals("add_submit")) { i = 3; } // 从添加页面提交 
    if (ctrl.equals("delete"))     { i = 4; } // 在浏览页面单击"删除"按钮 
    if (ctrl.equals("modify"))     { i = 5; } // 在浏览页面单击"修改"按钮
    if (ctrl.equals("modify_submit")) { i = 6; } // 从修改页面提交 
    
    switch (i) {
      case 1:  // 用户进入会议室管理页面
        if (vec.contains("380501")) {  
          // 调用方法得到结果集
          results = meetingRoom.getAllRoom(); 
          //查询记录
          request.setAttribute("results", results);
          rd = sc.getRequestDispatcher("/meetingRoomManage.jsp");
          rd.forward(request, response);
        }else {
         // 没有权限
          Error = "对不起，您没有会议室维护的权限！";
          request.setAttribute("Error", Error);
          rd = sc.getRequestDispatcher("/error.jsp");
          rd.forward(request, response);
          return;
        }          
        break; //跳出case
        
      case 2:  // 点击添加按钮
        if (vec.contains("380501")) {  
          rd = sc.getRequestDispatcher("/meetingRoomAdd.jsp");
          rd.forward(request, response);
        }else {
         // 没有权限
          Error = "对不起，您没有会议室添加的权限！";
          request.setAttribute("Error", Error);
          rd = sc.getRequestDispatcher("/error.jsp");
          rd.forward(request, response);
          return;
        }                    
        break; //跳出case          
      case 3:  // 从添加页面提交        
        building = request.getParameter("building");        
        room = request.getParameter("room");        
        capacity = request.getParameter("capacity");        
        remark = request.getParameter("remark");      
       // 添加记录并返回是否添加成功
        flag = meetingRoom.meetingRoomAdd( building, room, capacity, remark);
        if(flag != -999){
            if (flag != -1) { // 添加成功
              results = meetingRoom.getAllRoom(); // 调用方法获得查询结果
              request.setAttribute("results", results); // 保存结果         
              // 返回到浏览页面
              rd = sc.getRequestDispatcher("/meetingRoomManage.jsp");
              rd.forward(request, response);
            } else{ // 添加不成功
              rd = sc.getRequestDispatcher("/error.jsp");
              rd.forward(request, response); // 返回到错误页面
            }
        } else{
          rd = sc.getRequestDispatcher("/meetingRoomError.jsp");
          rd.forward(request, response); // 返回到错误页面
        } 
        break; //跳出case
      case 4:  // 在浏览页面单击删除按钮        
        roomSelected =request.getParameterValues("checkbox");     
        flag = meetingRoom.meetingRoomDelete(roomSelected);   
        if (flag != -1) { // 删除成功
          results = meetingRoom.getAllRoom(); // 调用方法获得查询结果
          request.setAttribute("results", results); // 保存结果
          rd = sc.getRequestDispatcher("/meetingRoomManage.jsp"); // 返回到游览页面
          rd.forward(request, response);
        } else { // 删除不成功
          rd = sc.getRequestDispatcher("/error.jsp"); // 返回到错误页面
          rd.forward(request, response);
        } break; //跳出case    
      case 5:  // 在浏览页面单击"修改"按钮        
        roomSelected =request.getParameterValues("checkbox");
        result = meetingRoom.getRoomInfo(roomSelected[0]); //得到会议室的信息          
        request.setAttribute("result", result); // 保存结果
        rd = sc.getRequestDispatcher("/meetingRoomModify.jsp"); // 返回到游览页面
        rd.forward(request, response);
        break; //跳出case
      case 6:  // 在修改页面提交        
        remark = request.getParameter("remark");
        roomID = request.getParameter("Id");                  
        flag = meetingRoom.meetingRoomModify(roomID,remark);   
        if (flag != -1) { // 修改成功
          results = meetingRoom.getAllRoom(); // 调用方法获得查询结果
          request.setAttribute("results", results); // 保存结果
          rd = sc.getRequestDispatcher("/meetingRoomManage.jsp"); // 返回到游览页面
          rd.forward(request, response);
        } else { // 删除不成功
          rd = sc.getRequestDispatcher("/error.jsp"); // 返回到错误页面
          rd.forward(request, response);
        } break; //跳出case
        
    }
    
  }

  //Clean up resources
  public void destroy() {
  }
}
