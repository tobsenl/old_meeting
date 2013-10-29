package com.imc_cg.meeting.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import com.imc_cg.meeting.bean.business.Meeting;
import com.imc_cg.meeting.bean.business.MeetingRoom;

public class MeetingServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	private Meeting meeting;
	private MeetingRoom meetingRoom;

	// Initialize global variables
	public void init() throws ServletException {
		meeting = new Meeting();
		meetingRoom = new MeetingRoom();
	}

	// Process the HTTP Get request
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		ServletContext sc = getServletContext();
		RequestDispatcher rd = null;
		// ContentFactory holder = null;
		// 获得控制变量,用于区别提交的页面
		String ctrl = request.getParameter("ctrl");
		String userid = (String) session.getAttribute("userid"); // 用户id
		Vector vec = (Vector) session.getAttribute("vec"); // 用户权限
		// Meeting meeting = new Meeting();
		// MeetingRoom meetingRoom = new MeetingRoom();

		int flowID = 0; // 会议记录流水号
		String id = ""; // 会议记录流水号
		String startTime = ""; // 会议开始时间
		String endTime = ""; // 会议结束时间
		String content = ""; // 会议内容
		String leader = ""; // 参会领导
		String depart = ""; // 参会部门
		String roomID = ""; // 会议室编号
		String remark = ""; // 会议说明
		// String commitTime = ""; // 会议申请提交时间
		String commiterId = "";// 提交者ID
		String status = ""; // 会议通知状态
		String type = ""; // 会议类型
		String presider = ""; // 会议主持人
		String grade = ""; // 会议级别
		// 会议开始时间分段
		String startYear = "";
		String startMonth = "";
		String startDay = "";
		String startHour = "";
		String startMinute = "";
		// 会议结束时间分段
		String endYear = "";
		String endMonth = "";
		String endDay = "";
		String endHour = "";
		String endMinute = "";
		// 判断会议审核页面的审核项是否被选中
		String agree = "";
		// 判断会议审核页面的拒批项是否被选中
		String disagree = "";
		String Error = ""; // 系统报错信息
		String sql = ""; // 查询语句

		String[] leaders = null; // 公司领导
		String[] selectedLeaders = null; // 已选中的公司领导
		String[] result = null; // 一行多列记录结果
		String[] ids = null; // 要删除的会议信息id
		String[][] results = null; // 多行多列结果
		String[][] results1 = null; // 多行多列结果
		String[][] results2 = null; // 多行多列结果
		// String[][] meetingRoomInfr = null; //已审批通过会议的会议室信息
		Hashtable unavailableRoom = null;// 记录不可用的会议室
		int i = 0; // 页面来源标记
		int flag = 0; // 判断sql语句执行是否成功的标记
		int flag2 = 0;
		/*
		 * modified by liqs on August 3,2006,to link to query page on jnpc
		 */
		if (ctrl.equals("toMeetingQry") || ctrl.equals("meetingQry")) {
		} else {
			if (vec == null) {
				Error = "系统session超时，请重新登录！";
				request.setAttribute("Error", Error);
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
				return;
			}
		}
		if (ctrl.equals("apply")) {
			i = 1;
		} // 点击左侧部门会议申请链接
		else if (ctrl.equals("add")) {
			i = 2;
		} // 从会议申请页面提交
		else if (ctrl.equals("deptBrowse")) {
			i = 3;
		} // 点击左侧部门会议维护链接
		else if (ctrl.equals("deptMod")) {
			i = 4;
		} // 点击部门申请浏览页面的修改按钮
		else if (ctrl.equals("deptModified")) {
			i = 5;
		} // 从部门申请修改页面提交
		else if (ctrl.equals("deptDel")) {
			i = 6;
		} // 点击部门申请浏览页面的删除按钮
		else if (ctrl.equals("approve")) {
			i = 7;
		} // 点击左侧会议审核链接
		else if (ctrl.equals("approved")) {
			i = 8;
		} // 点击会议审核页面提交按钮
		else if (ctrl.equals("visit")) {
			i = 9;
		} // 在左侧点击“最新会议”链接
		else if (ctrl.equals("delete")) {
			i = 10;
		} // 从最新会议浏览单击‘删除’按钮
		else if (ctrl.equals("historyVisit")) {
			i = 11;
		} // 在左侧点击“历史会议”链接
		else if (ctrl.equals("historyDel")) {
			i = 12;
		} // 从历史会议浏览页面单击‘删除’按钮
		// if (ctrl.equals("deleteAll")) {i = 13; } // 从历史会议浏览页面单击‘删除历史记录’按钮
		else if (ctrl.equals("toAllot")) {
			i = 14;
		} // 在左侧点击“分配会议室”链接
		else if (ctrl.equals("allot")) {
			i = 15;
		} // 从会议室分配页面点击'分配会议室'按钮
		else if (ctrl.equals("allotSubmit")) {
			i = 16;
		} // 从会议室分配页面点击“提交”按钮
		else if (ctrl.equals("toReAllot")) {
			i = 17;
		} // 在左侧点击'调配会议室'链接
		else if (ctrl.equals("reAllot")) {
			i = 18;
		} // 从浏览页面点击'重新分配'按钮
		else if (ctrl.equals("reAllotSubmit")) {
			i = 19;
		} // 点击重新分配会议室页面的'提交'按钮
		else if (ctrl.equals("customVisit")) {
			i = 20;
		} // 点击左侧最新例会链接
		else if (ctrl.equals("customDel")) {
			i = 21;
		} // 点击最新例会浏览页面的'删除'按钮
		else if (ctrl.equals("toCustomMod")) {
			i = 22;
		} // 点击最新例会浏览页面的'修改'按钮
		else if (ctrl.equals("customMod")) {
			i = 23;
		} // 点击最新例会修改页面的'提交'按钮
		else if (ctrl.equals("toCustomRoomMod")) {
			i = 24;
		} // 点击最新例会浏览页面的'修改会议室'按钮
		else if (ctrl.equals("customRoomMod")) {
			i = 25;
		} // 点击会议室分配页面的'提交'按钮
		else if (ctrl.equals("historyCustomVisit")) {
			i = 26;
		} // 在左侧点击'历史例会'链接
		else if (ctrl.equals("toHistoryCustomMod")) {
			i = 27;
		} // 点击历史例会浏览页面的'修改'按钮'
		else if (ctrl.equals("historyCustomMod")) {
			i = 28;
		} // 点击历史例会修改页面的'提交'按钮
		else if (ctrl.equals("historyCustomDel")) {
			i = 29;
		} // 点击历史例会浏览页面的'删除'按钮
		else if (ctrl.equals("toMeetingMod")) {
			i = 30;
		} // 点击最新会议浏览页面的'修改'按钮
		else if (ctrl.equals("meetingMod")) {
			i = 31;
		} // 点击会议修改页面的'提交'按钮
		else if (ctrl.equals("toMeetingApply")) {
			i = 32;
		} // 点击部门会议浏览页面的'添加'按钮
		else if (ctrl.equals("toMeetingQry")) {
			i = 33;
		} // 点击左侧查询按钮
		else if (ctrl.equals("meetingQry")) {
			i = 34;
		} // 点击查询页面的查询按钮
		else if (ctrl.equals("CustomModSubmit")) {
			i = 35;
		} // 点击查询页面的查询按钮
		// if (ctrl.equals("test")) { i = 33; }

		switch (i) {
		case 1: // 点击左侧部门会议申请链接
			if (vec.contains("380101")) {
				// 得到公司领导
				result = meeting.getLeaders();
				request.setAttribute("result", result); // 保存结果
				// 链接到会议申请页面
				rd = sc.getRequestDispatcher("/meetingApply.jsp");
				rd.forward(request, response);
			} else {
				// 没有权限
				Error = "对不起，您没有部门会议申请的权限！";
				request.setAttribute("Error", Error);
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
				return;
			}
			break;

		case 2: // 从会议申请页面提交
			// 开始时间
			startYear = request.getParameter("startYear");
			startMonth = request.getParameter("startMonth");
			startDay = request.getParameter("startDay");
			startHour = request.getParameter("startHour");
			startMinute = request.getParameter("startMinute");
			startTime = startYear.trim() + "-" + startMonth.trim() + "-"
					+ startDay.trim() + " " + startHour.trim() + ":"
					+ startMinute.trim();
			// 结束时间
			endYear = request.getParameter("endYear");
			endMonth = request.getParameter("endMonth");
			endDay = request.getParameter("endDay");
			endHour = request.getParameter("endHour");
			endMinute = request.getParameter("endMinute");
			endTime = endYear.trim() + "-" + endMonth.trim() + "-"
					+ endDay.trim() + " " + endHour.trim() + ":"
					+ endMinute.trim();

			// 会议内容
			content = request.getParameter("content");
			// 参会部门
			depart = request.getParameter("depart");
			// 会议室编号
			roomID = request.getParameter("roomID");
			// 会议说明
			remark = request.getParameter("remark");
			// 会议申请提交者
			commiterId = userid;
			// 会议类型
			type = request.getParameter("type");

			// 参会领导
			leaders = request.getParameterValues("leaders");
			// 会议主持人
			presider = request.getParameter("presider");

			// System.out.println(leaders);

			// 会议级别
			grade = request.getParameter("grade");
			// 添加记录并返回是否添加成功
			flag = meeting.meetingAdd(startTime, endTime, content, leaders,
					depart, remark, commiterId, type, presider, grade);
			if (flag != -1 && flag != -999 && flag != -99 && flag != -98) { // 添加成功
				results = meeting.meetingVisitAllByMe(userid); // 得到登录者所在部门得申请会议信息
				request.setAttribute("results", results); // 保存结果
				// 返回到游览页面
				rd = sc.getRequestDispatcher("/meetingBrwByDept.jsp");
				rd.forward(request, response);
			} else if (flag == -99) {
				rd = sc.getRequestDispatcher("/meetingError2.jsp");
				rd.forward(request, response); // 返回到错误页面
			} else if (flag == -98) {
				rd = sc.getRequestDispatcher("/meetingError3.jsp");
				rd.forward(request, response); // 返回到错误页面
			} else if (flag == -999) {
				rd = sc.getRequestDispatcher("/meetingError.jsp");
				rd.forward(request, response); // 返回到错误页面
			} else { // 添加不成功
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response); // 返回到错误页面
			}
			break; // 跳出case
		case 3: // 点击左侧部门会议维护链接
			if (vec.contains("380101")) {
				results = meeting.meetingVisitAllByMe(userid); // 调用方法获得查询结果
				request.setAttribute("results", results); // 保存结果
				// 返回到游览页面
				rd = sc.getRequestDispatcher("/meetingBrwByDept.jsp");
				rd.forward(request, response);
			} else { // 没有权限
				Error = "对不起，您没有部门会议维护的权限！";
				request.setAttribute("Error", Error);
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
				return;
			}
			break;
		case 4: // 点击部门申请浏览页面的修改按钮
			id = request.getParameter("ID");
			flowID = Integer.parseInt(id);
			// 得到id为flowID的会议申请信息
			result = meeting.getMyMeeting(flowID);
			// 得到公司领导
			leaders = meeting.getLeaders();
			leader = result[4];
			if (leader == null || leader.equals("") || leader.equals("null")) {
				selectedLeaders = null;
			} else {
				selectedLeaders = meeting.leadersDivide(leader);
			}
			request.setAttribute("id", id);
			request.setAttribute("leaders", leaders);
			request.setAttribute("selectedLeaders", selectedLeaders);
			request.setAttribute("result", result);
			// 返回到游览页面
			rd = sc.getRequestDispatcher("/meetingModByDept.jsp");
			rd.forward(request, response);
			break;
		case 5:// 从部门申请修改页面提交
			id = request.getParameter("id");
			status = meeting.getStatus(id);
			if (status.equals("0") || status.equals("2") || status.equals("4")) {
				// 开始时间
				startYear = request.getParameter("startYear");
				startMonth = request.getParameter("startMonth");
				startDay = request.getParameter("startDay");
				startHour = request.getParameter("startHour");
				startMinute = request.getParameter("startMinute");
				startTime = startYear.trim() + "-" + startMonth.trim() + "-"
						+ startDay.trim() + " " + startHour.trim() + ":"
						+ startMinute.trim();
				// 结束时间
				endYear = request.getParameter("endYear");
				endMonth = request.getParameter("endMonth");
				endDay = request.getParameter("endDay");
				endHour = request.getParameter("endHour");
				endMinute = request.getParameter("endMinute");
				endTime = endYear.trim() + "-" + endMonth.trim() + "-"
						+ endDay.trim() + " " + endHour.trim() + ":"
						+ endMinute.trim();
				// 会议内容
				content = request.getParameter("content");
				// 参会领导
				leaders = request.getParameterValues("leaders");
				// 参会部门
				depart = request.getParameter("depart");
				// 会议室编号
				roomID = request.getParameter("roomID");
				// 会议说明
				remark = request.getParameter("remark");
				// 会议申请提交者
				commiterId = userid;
				// 会议类型
				type = request.getParameter("type");
				// 会议主持人
				presider = request.getParameter("presider");
				// 会议级别
				grade = request.getParameter("grade");
				// 添加记录并返回是否添加成功
				// TODO
				flag = meeting.meetingModify(id, startTime, endTime, content,
						leaders, depart, remark, commiterId, type, "0", "dept",
						presider, grade);
				if (flag != -1 && flag != -999 && flag != -99 && flag != -98) { // 添加成功
					results = meeting.meetingVisitAllByMe(userid); // 调用方法获得查询结果
					request.setAttribute("results", results); // 保存结果
					// 返回到游览页面
					rd = sc.getRequestDispatcher("/meetingBrwByDept.jsp");
					rd.forward(request, response);
				} else if (flag == -99) {
					rd = sc.getRequestDispatcher("/meetingError2.jsp");
					rd.forward(request, response); // 返回到错误页面
				} else if (flag == -98) {
					rd = sc.getRequestDispatcher("/meetingError3.jsp");
					rd.forward(request, response); // 返回到错误页面
				} else if (flag == -999) {
					rd = sc.getRequestDispatcher("/meetingError.jsp");
					rd.forward(request, response); // 返回到错误页面
				}
			} else {
				rd = sc.getRequestDispatcher("/modError.jsp");
				rd.forward(request, response); // 返回到修改错误页面
			}
			break; // 跳出case
		case 6: // 点击部门申请浏览页面的删除按钮
			ids = request.getParameterValues("ID");
			flag = meeting.meetingDelete(ids);
			if (flag != -1) {
				// 得到登录者所在部门的申请会议信息
				results = meeting.meetingVisitAllByMe(userid);
				request.setAttribute("results", results); // 保存结果
				// 返回到部门会议浏览页面
				rd = sc.getRequestDispatcher("/meetingBrwByDept.jsp");
				rd.forward(request, response);
			} else {
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response); // 返回到错误页面
			}
			break; // 跳出case
		case 7: // 点击左侧会议审核链接
			if (vec.contains("380201")) {
				results = meeting.getSimpleMeeting("0");
				request.setAttribute("results", results); // 保存结果
				// 返回到游览页面
				rd = sc.getRequestDispatcher("/meetingApprove.jsp");
				rd.forward(request, response);
			} else { // 没有权限
				Error = "对不起，您没有会议审核的权限！";
				request.setAttribute("Error", Error);
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
				return;
			}
			break;
		case 8:// 从审核页面提交
			agree = request.getParameter("agree");
			disagree = request.getParameter("disagree");
			if (agree.equals("1")) {
				// 选中'审核'的会议id
				ids = request.getParameterValues("approve");
				if (ids.length > 0) {
					flag = meeting.meetingApprove(ids, "1", userid);
				}
			}
			if (disagree.equals("1")) {
				// 选中'拒批'的会议id
				String[] disapprove = request.getParameterValues("disapprove");
				if (disapprove.length > 0) {
					flag2 = meeting.meetingApprove(disapprove, "2", userid);
				}
			}
			if (flag == -1 || flag2 == -1) {
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response); // 返回到错误页面
			} else {
				results = meeting.getSimpleMeeting("0");
				request.setAttribute("results", results); // 保存结果
				// 转到会议审批浏览页面
				rd = sc.getRequestDispatcher("/meetingApprove.jsp");
				rd.forward(request, response);
			}
			break;
		case 9: // 在左侧点击“最新会议”链接
			if (vec.contains("380401")) {
				// 得到已分配会议室的会议通知
				results = meeting.getMeeting("3", "2");

				// 得到已审批但未分配会议室的会议
				results2 = meeting.getSimpleMeeting("1", "2");
				request.setAttribute("results", results); // 保存结果
				request.setAttribute("results2", results2);
				// 返回到游览页面
				rd = sc.getRequestDispatcher("/meetingVisit.jsp");
				rd.forward(request, response);
			} else { // 没有权限
				Error = "对不起，您没有会议浏览的权限！";
				request.setAttribute("Error", Error);
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
				return;
			}
			break;
		case 10: // 从最新会议浏览浏览页面单击‘删除’按钮
			ids = request.getParameterValues("checkbox");
			flag = meeting.meetingDelete(ids);
			if (flag != -1) {
				results = meeting.getMeeting("3", "2");
				results2 = meeting.getSimpleMeeting("1", "2");
				request.setAttribute("results", results); // 保存结果
				request.setAttribute("results2", results2);
				// 返回到游览页面
				rd = sc.getRequestDispatcher("/meetingVisit.jsp");
				rd.forward(request, response);
			} else {
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response); // 返回到错误页面
			}
			break;

		case 11:// 在左侧点击“历史会议”链接
			if (vec.contains("380401")) {
				results = meeting.getHistoryMeeting("2");
				request.setAttribute("results", results); // 保存结果
				rd = sc.getRequestDispatcher("/historyMeetingVisit.jsp");
				rd.forward(request, response);
			} else { // 没有权限
				Error = "对不起，您没有会议浏览的权限！";
				request.setAttribute("Error", Error);
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
				return;
			}
			break;

		case 12: // 从历史会议浏览页面单击‘删除’按钮
			ids = request.getParameterValues("checkbox");
			flag = meeting.meetingDelete(ids);
			if (flag != -1) {
				results = meeting.getHistoryMeeting("2");
				request.setAttribute("results", results); // 保存结果

				// 返回到游览页面
				rd = sc.getRequestDispatcher("/historyMeetingVisit.jsp");
				rd.forward(request, response);
			} else {
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response); // 返回到错误页面
			}
			break;
		/**
		 * case 13:// 从历史会议浏览页面单击‘删除历史记录’按钮 flag =
		 * meeting.historyMeetingDelete(); if (flag != -1 ){ results =
		 * meeting.getHistoryMeeting(); results1 =
		 * meeting.getAllotRoomInfr(results); request.setAttribute("results",
		 * results); // 保存结果 request.setAttribute("results1", results1); //
		 * 返回到游览页面 rd = sc.getRequestDispatcher("/historyMeetingVisit.jsp");
		 * rd.forward(request, response); }else{ rd =
		 * sc.getRequestDispatcher("/error.jsp"); rd.forward(request, response);
		 * // 返回到错误页面 } break;
		 **/
		case 14: // 在左侧点击“分配会议室”链接
			if (vec.contains("380301")) {
				results = meeting.getSimpleMeeting("1");
				request.setAttribute("results", results); // 保存结果
				// 返回到游览页面
				rd = sc.getRequestDispatcher("/meetingToAllot.jsp");
				rd.forward(request, response);
			} else { // 没有权限
				Error = "对不起，您没有会议室分配的权限！";
				request.setAttribute("Error", Error);
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
				return;
			}
			break;
		case 15:// 从会议室分配页面点击'分配会议室'按钮
			id = request.getParameter("id");
			unavailableRoom = meeting.roomCheck(id);
			results = meetingRoom.getAllRoom();
			request.setAttribute("unavailableRoom", unavailableRoom);
			request.setAttribute("results", results);
			request.setAttribute("id", id);
			rd = sc.getRequestDispatcher("/meetingAllot.jsp");
			rd.forward(request, response);
			break;
		case 16:// 从会议室分配页面点击“提交”按钮
			roomID = request.getParameter("radio");
			id = request.getParameter("meetingID");

			allotRoom(request, response, sc, userid, id, roomID);
			break;
		case 17:// 在左侧点击'调配会议室'链接
			if (vec.contains("380301")) {
				results = meeting.getMeeting("3");
				request.setAttribute("results", results); // 保存结果

				// 返回到游览页面
				rd = sc.getRequestDispatcher("/meetingReAllot.jsp");
				rd.forward(request, response);
			} else { // 没有权限
				Error = "对不起，您没有会议室调配的权限！";
				request.setAttribute("Error", Error);
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
				return;
			}
			break;
		case 18:// 从浏览页面点击'重新分配'按钮
			id = request.getParameter("ID");
			unavailableRoom = meeting.roomCheck(id);
			roomID = "" + meeting.getRoomId(Integer.parseInt(id));
			results = meetingRoom.getAllRoom();
			request.setAttribute("unavailableRoom", unavailableRoom);
			request.setAttribute("results", results);
			request.setAttribute("id", id);
			request.setAttribute("roomID", roomID);
			rd = sc.getRequestDispatcher("/roomReAllot.jsp");
			rd.forward(request, response);
			break;
		case 19:// 从会议室分配页面点击“提交”按钮
			roomID = request.getParameter("radio");
			id = request.getParameter("meetingID");
			allotRoom(request, response, sc, userid, id, roomID);
			break;
		case 20:
			if (vec.contains("380401")) {
				// 得到已分配会议室的例会
				results = meeting.getMeeting("3", "1");
				// 得到已审批但未分配会议室的例会
				results2 = meeting.getSimpleMeeting("1", "1");
				request.setAttribute("results", results); // 保存结果
				// request.setAttribute("results1", results1);
				request.setAttribute("results2", results2);
				// 返回到游览页面
				rd = sc.getRequestDispatcher("/customVisit.jsp");
				rd.forward(request, response);
			} else { // 没有权限
				Error = "对不起，您没有例会浏览的权限！";
				request.setAttribute("Error", Error);
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
				return;
			}
			break;
		case 21: // 从最新例会浏览浏览页面单击‘删除’按钮
			ids = request.getParameterValues("checkbox");
			flag = meeting.meetingDelete(ids);
			if (flag != -1) {
				results = meeting.getMeeting("3", "1");
				results2 = meeting.getSimpleMeeting("1", "1");
				request.setAttribute("results", results); // 保存结果
				request.setAttribute("results2", results2);
				// 返回到游览页面
				rd = sc.getRequestDispatcher("/customVisit.jsp");
				rd.forward(request, response);
			} else {
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response); // 返回到错误页面
			}
			break;
		case 22: // 点击最新例会浏览页面的'修改'按钮
			id = request.getParameter("checkbox");
			flowID = Integer.parseInt(id);
			// 得到id为flowID的会议申请信息
			result = meeting.getMyMeeting(flowID);
			// 得到公司领导
			leaders = meeting.getLeaders();
			leader = result[4];
			if (leader == null || leader.equals("") || leader.equals("null")) {
				selectedLeaders = null;
			} else {
				selectedLeaders = meeting.leadersDivide(leader);
			}
			request.setAttribute("id", id);
			request.setAttribute("leaders", leaders);
			request.setAttribute("selectedLeaders", selectedLeaders);
			request.setAttribute("result", result);
			// 返回到最新例会修改页面
			rd = sc.getRequestDispatcher("/customMod.jsp");
			rd.forward(request, response);
			break;
		case 23: // 点击最新例会修改页面的'提交'按钮
			id = request.getParameter("id");
			// 开始时间
			startYear = request.getParameter("startYear");
			startMonth = request.getParameter("startMonth");
			startDay = request.getParameter("startDay");
			startHour = request.getParameter("startHour");
			startMinute = request.getParameter("startMinute");
			startTime = startYear.trim() + "-" + startMonth.trim() + "-"
					+ startDay.trim() + " " + startHour.trim() + ":"
					+ startMinute.trim();
			// 结束时间
			endYear = request.getParameter("endYear");
			endMonth = request.getParameter("endMonth");
			endDay = request.getParameter("endDay");
			endHour = request.getParameter("endHour");
			endMinute = request.getParameter("endMinute");
			endTime = endYear.trim() + "-" + endMonth.trim() + "-"
					+ endDay.trim() + " " + endHour.trim() + ":"
					+ endMinute.trim();
			// 会议内容
			content = request.getParameter("content");
			// 参会领导
			leaders = request.getParameterValues("leaders");

			// 参会部门
			depart = request.getParameter("depart");
			// 会议室编号
			roomID = request.getParameter("roomID");
			// 会议说明
			remark = request.getParameter("remark");
			// 会议申请提交者
			commiterId = userid;
			// 会议状态
			status = meeting.getStatus(id);
			// 会议类型
			type = request.getParameter("type");
			// 会议主持人
			presider = request.getParameter("presider");
			// 会议级别
			grade = request.getParameter("grade");
			// 添加记录并返回是否修改成功
			flag = meeting.customModify(id, startTime, endTime, content,
					leaders, depart, remark, type, status, presider, grade);
			if (flag != -1) { // 添加成功
				// 得到已分配会议室的例会
				results = meeting.getMeeting("3", "1");
				// 得到已审批但未分配会议室的例会
				results2 = meeting.getSimpleMeeting("1", "1");
				request.setAttribute("results", results); // 保存结果
				request.setAttribute("results2", results2);
				// 返回到游览页面
				rd = sc.getRequestDispatcher("/customVisit.jsp");
				rd.forward(request, response);
			} else { // 添加不成功
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response); // 返回到错误页面
			}
			break; // 跳出case
		case 24: // 点击最新例会浏览页面的'修改会议室'按钮
			id = request.getParameter("checkbox");
			unavailableRoom = meeting.roomCheck(id);
			results = meetingRoom.getAllRoom();
			request.setAttribute("unavailableRoom", unavailableRoom);
			request.setAttribute("results", results);
			request.setAttribute("id", id);
			rd = sc.getRequestDispatcher("/customAllot.jsp");
			rd.forward(request, response);
			break;
		case 25:// 在左侧点击“最新例会”链接
			// roomID = request.getParameter("radio");
			// id = request.getParameter("meetingID");
			// flag = meeting.meetingAllot(id,roomID,userid);
			// if (flag != -1 ){
			// 得到已分配会议室的例会
			results = meeting.getMeeting("3", "1");
			// 得到已审批但未分配会议室的例会
			results2 = meeting.getSimpleMeeting("1", "1");
			request.setAttribute("results", results); // 保存结果
			request.setAttribute("results2", results2);
			// 返回到游览页面
			rd = sc.getRequestDispatcher("/customVisit.jsp");
			rd.forward(request, response);
			// }else{
			// rd = sc.getRequestDispatcher("/error.jsp");
			// rd.forward(request, response); // 返回到错误页面
			// }
			break;
		case 26:// 在左侧点击“历史例会”链接
			if (vec.contains("380401")) {
				results = meeting.getHistoryMeeting("1");

				request.setAttribute("results", results); // 保存结果

				// 返回到游览页面
				rd = sc.getRequestDispatcher("/historyCustomVisit.jsp");
				rd.forward(request, response);
			} else { // 没有权限
				Error = "对不起，您没有历史例会浏览的权限！";
				request.setAttribute("Error", Error);
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
				return;
			}
			break;
		case 27: // 点击历史例会浏览页面的'修改'按钮
			id = request.getParameter("checkbox");
			flowID = Integer.parseInt(id);
			// 得到id为flowID的会议申请信息
			result = meeting.getMyMeeting(flowID);
			// 得到公司领导
			leaders = meeting.getLeaders();
			leader = result[4];
			if (leader == null || leader.equals("") || leader.equals("null")) {
				selectedLeaders = null;
			} else {
				selectedLeaders = meeting.leadersDivide(leader);
			}

			request.setAttribute("flowID", id);
			request.setAttribute("leaders", leaders);
			request.setAttribute("selectedLeaders", selectedLeaders);
			request.setAttribute("result", result);
			// 返回到最新例会修改页面
			rd = sc.getRequestDispatcher("/historyCustomMod.jsp");
			rd.forward(request, response);
			break;
		case 28: // 点击历史例会修改页面的'提交'按钮
			// 会议流水号
			id = request.getParameter("flowID"); // 开始时间
			startYear = request.getParameter("startYear");
			startMonth = request.getParameter("startMonth");
			startDay = request.getParameter("startDay");
			startHour = request.getParameter("startHour");
			startMinute = request.getParameter("startMinute");
			startTime = startYear.trim() + "-" + startMonth.trim() + "-"
					+ startDay.trim() + " " + startHour.trim() + ":"
					+ startMinute.trim();
			// 结束时间
			endYear = request.getParameter("endYear");
			endMonth = request.getParameter("endMonth");
			endDay = request.getParameter("endDay");
			endHour = request.getParameter("endHour");
			endMinute = request.getParameter("endMinute");
			endTime = endYear.trim() + "-" + endMonth.trim() + "-"
					+ endDay.trim() + " " + endHour.trim() + ":"
					+ endMinute.trim();
			// 会议内容
			content = request.getParameter("content");
			// 参会领导
			leaders = request.getParameterValues("leaders");
			// 参会部门
			depart = request.getParameter("depart");
			// 会议说明
			remark = request.getParameter("remark");
			// 会议类型
			type = request.getParameter("type");
			// 会议主持人
			presider = request.getParameter("presider");
			// 会议级别
			grade = request.getParameter("grade");
			if (grade == null) {
				grade = "";
			}
			// 添加记录并返回是否修改成功

			flag = meeting.customModify(id, startTime, endTime, content,
					leaders, depart, remark, type, "3", presider, grade);
			if (flag != -1) { // 添加成功
				results = meeting.getHistoryMeeting("1");
				request.setAttribute("results", results); // 保存结果
				// 返回到游览页面
				rd = sc.getRequestDispatcher("/historyCustomVisit.jsp");
				rd.forward(request, response);
			}
			break; // 跳出case
		case 29: // 点击历史例会浏览页面的'删除'按钮
			ids = request.getParameterValues("checkbox");
			flag = meeting.meetingDelete(ids);
			if (flag != -1) {
				results = meeting.getHistoryMeeting("1");
				request.setAttribute("results", results); // 保存结果

				// 返回到游览页面
				rd = sc.getRequestDispatcher("/historyCustomVisit.jsp");
				rd.forward(request, response);
			} else {
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response); // 返回到错误页面
			}
			break;
		case 30: // 点击最新会议浏览页面的修改按钮
			id = request.getParameter("checkbox");
			flowID = Integer.parseInt(id);
			// 得到id为flowID的会议申请信息
			result = meeting.getMyMeeting(flowID);
			// 得到公司领导
			leaders = meeting.getLeaders();
			leader = result[4];
			if (leader == null || leader.equals("") || leader.equals("null")) {
				selectedLeaders = null;
			} else {
				selectedLeaders = meeting.leadersDivide(leader);
			}
			request.setAttribute("id", id);
			request.setAttribute("leaders", leaders);
			request.setAttribute("selectedLeaders", selectedLeaders);
			request.setAttribute("result", result);
			// 跳转到最新会议修改页面
			rd = sc.getRequestDispatcher("/meetingMod.jsp");
			rd.forward(request, response);
			break;
		case 31: // 点击最新会议修改页面'提交'按钮
			id = request.getParameter("id");
			// 开始时间
			startYear = request.getParameter("startYear");
			startMonth = request.getParameter("startMonth");
			startDay = request.getParameter("startDay");
			startHour = request.getParameter("startHour");
			startMinute = request.getParameter("startMinute");
			startTime = startYear.trim() + "-" + startMonth.trim() + "-"
					+ startDay.trim() + " " + startHour.trim() + ":"
					+ startMinute.trim();
			// 结束时间
			endYear = request.getParameter("endYear");
			endMonth = request.getParameter("endMonth");
			endDay = request.getParameter("endDay");
			endHour = request.getParameter("endHour");
			endMinute = request.getParameter("endMinute");
			endTime = endYear.trim() + "-" + endMonth.trim() + "-"
					+ endDay.trim() + " " + endHour.trim() + ":"
					+ endMinute.trim();
			// 会议内容
			content = request.getParameter("content");
			// 参会领导
			leaders = request.getParameterValues("leaders");
			// 参会部门
			depart = request.getParameter("depart");
			// 会议室编号
			roomID = request.getParameter("roomID");
			// 会议说明
			remark = request.getParameter("remark");
			// 会议申请提交者
			commiterId = userid;
			// 会议状态
			status = meeting.getStatus(id);
			// 会议类型
			type = request.getParameter("type");
			// 会议主持人
			presider = request.getParameter("presider");
			// 会议级别
			grade = request.getParameter("grade");
			// 添加记录并返回是否修改成功
			flag = meeting.meetingModify(id, startTime, endTime, content,
					leaders, depart, remark, commiterId, type, status, "cao",
					presider, grade);
			if (flag != -1) { // 修改成功
				// 得到已分配会议室的会议通知
				results = meeting.getMeeting("3", "2");
				// 得到已审批但未分配会议室的会议
				results2 = meeting.getSimpleMeeting("1", "2");
				request.setAttribute("results", results); // 保存结果
				request.setAttribute("results2", results2);
				// 返回到游览页面
				rd = sc.getRequestDispatcher("/meetingVisit.jsp");
				rd.forward(request, response);
			} else { // 修改不成功
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response); // 返回到错误页面
			}
			break; // 跳出case
		case 32: // 点击左侧部门会议申请链接
			if (vec.contains("380101")) {
				// 得到公司领导
				result = meeting.getLeaders();
				request.setAttribute("result", result); // 保存结果
				// 链接到会议申请页面
				rd = sc.getRequestDispatcher("/meetingApply.jsp");
				rd.forward(request, response);
			} else {
				// 没有权限
				Error = "对不起，您没有部门会议申请的权限！";
				request.setAttribute("Error", Error);
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
				return;
			}
			break;
		case 33:
			// 链接到会议查询页面
			rd = sc.getRequestDispatcher("/meetingQry.jsp");
			rd.forward(request, response);
			break;
		case 34: // 根据条件查询出会议信息
			sql = request.getParameter("SQL");
			// 查询出符合条件物想的详细信息
			results = meeting.meetingQryDetail(sql);
			// 向页面传入的参数
			request.setAttribute("results", results);
			// 链接到会议查询浏览页面
			rd = sc.getRequestDispatcher("/meetingQryList.jsp");
			rd.forward(request, response);
			break;
		case 35:// 从最新例会会议室调配页面点击“提交”按钮
			roomID = request.getParameter("radio");
			id = request.getParameter("meetingID");
			flag = meeting.meetingAllot(id, roomID, userid);
			if (flag != -1) {
				results = meeting.getMeeting("3", "1");
				// 得到已审批但未分配会议室的例会
				results2 = meeting.getSimpleMeeting("1", "1");
				request.setAttribute("results", results); // 保存结果
				// request.setAttribute("results1", results1);
				request.setAttribute("results2", results2);
				// 返回到游览页面
				rd = sc.getRequestDispatcher("/customVisit.jsp");
			} else {
				rd = sc.getRequestDispatcher("/error.jsp");
			}
			rd.forward(request, response);
			break;

		}// end switch
	} // end service

	private void allotRoom(HttpServletRequest request,
			HttpServletResponse response, ServletContext sc, String userid,
			String id, String roomID) throws ServletException, IOException {
		RequestDispatcher rd;
		String[][] results;
		int flag;
		int count = meeting.getCountByRoomIDAndTime(roomID, id);
		if (count == 1) {
			flag = meeting.meetingAllot(id, roomID, userid);
			if (flag != -1) {
				results = meeting.getSimpleMeeting("1");
				request.setAttribute("results", results); // 保存结果
				// 返回到游览页面
				rd = sc.getRequestDispatcher("/meetingToAllot.jsp");
				rd.forward(request, response);
			} else {
				rd = sc.getRequestDispatcher("/error.jsp");
				rd.forward(request, response); // 返回到错误页面
			}
		} else {
			rd = sc.getRequestDispatcher("/meetingError4.jsp");
			rd.forward(request, response); // 返回到错误页面
		}
	}

	// Clean up resources
	public void destroy() {
	}
}
