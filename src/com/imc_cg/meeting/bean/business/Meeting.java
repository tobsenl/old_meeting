package com.imc_cg.meeting.bean.business;

/**
 * <p>Title:会议通知管理</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: jnpc-imc-cg</p>
 * @author:  houyy
 * @version 1.0
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import com.imc_cg.meeting.bean.data.Oracle;
import com.imc_cg.meeting.bean.data.OracleConnection;
import com.imc_cg.meeting.bean.data.SqlGenerator;
import com.imc_cg.meeting.bean.util.JNPCTools;

public class Meeting {
	private String[] ColNames = null;
	private String[] AllColNames = null;

	public Meeting() {
		ColNames = new String[] { "id", // 会议通知流水号
				"roomid", // 会议室Id
				"starttime", // 会议开始时间
				"endtime", // 会议结束时间
				"content", // 会议内容
				"leader", // 会议领导
				"depart", // 参加会议部门
				"remark", // 会议说明
				"committime", // 会议申请提交时间
				"commiterid", // 申请提交人id
				"commitdepart", // 会议申请部门
				"approverid", // 会议审核人id
				"alloterid", // 会议室安排人id
				"status", // 会议申请状态(0未处理;1已审批通过;2审批拒绝;3已安排会议室;4冲突)
				"type", // 会议类型(1例会;2会议)
				"presider", // 会议主持人
				"grade" }; // 会议级别
		AllColNames = new String[] { "id", // 会议通知流水号
				"building", // 会议室所在楼
				"room", // 房间号
				"capacity", // 容纳人数
				"starttime", // 会议开始时间
				"endtime", // 会议结束时间
				"content", // 会议内容
				"leader", // 会议领导
				"depart", // 参加会议部门
				"remark", // 会议说明
				"committime", // 会议申请提交时间
				"commiterid", // 申请提交人id
				"commitdepart", // 会议申请部门
				"approverid", // 会议审核人id
				"alloterid", // 会议室安排人id
				"status", // 会议申请状态(0未处理;1已审批通过;2审批拒绝;3已安排会议室)
				"type", // 会议类型(1例会;2会议)
				"presider", // 会议主持人
				"grade" }; // 会议级别
	}

	/**
	 * 插入一条新记录
	 * 
	 * @param starttime
	 *            会议开始时间
	 * @param endtime
	 *            会议结束时间
	 * @param content
	 *            会议内容
	 * @param leader
	 *            参加会议领导
	 * @param depart
	 *            参加会议部门
	 * @param remark
	 *            说明
	 * @param commiterId
	 *            申请人ID
	 * @return 返回是否插入成功
	 */
	public int meetingAdd(String startTime, String endTime, String content,
			String[] leaders, String depart, String remark, String commiterId,
			String type, String presider, String grade) {
		int flag = 0;
		// 得到申请人所在的部门
		String commitDept = getOrg(commiterId);
		// 得到系统时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		Date currentTime_1 = new Date();
		String temp = sdf.format(currentTime_1);
		// 得到已经排序的参加会议领导
		String leader = "";
		if (leaders == null) {
			leader = "";
		} else
			leader = leadersSort(leaders);
		// 插入字符的名称
		String[] ColValues = new String[ColNames.length];
		ColValues[0] = "seq_meeting_id.nextval";
		ColValues[1] = "";
		ColValues[2] = startTime;
		ColValues[3] = endTime;
		ColValues[4] = content;
		ColValues[5] = leader;
		ColValues[6] = depart;
		ColValues[7] = remark;
		ColValues[8] = temp;
		ColValues[9] = commiterId;
		ColValues[10] = commitDept;
		ColValues[11] = "";
		ColValues[12] = "";
		ColValues[13] = "0";
		ColValues[14] = type;
		ColValues[15] = presider;
		ColValues[16] = grade;

		String sql = "";
		SqlGenerator sg = new SqlGenerator();

		// 生成一条sql语句,用于查询是否有相同的记录,防止页面刷新时自动添加记录
		String sql2 = "select count(*) count from meeting where startTime=to_date('"
				+ startTime + "','yyyy-mm-dd hh24:mi')";
		sql2 = sql2 + "and endTime=to_date('" + endTime
				+ "','yyyy-mm-dd hh24:mi') and content ='" + content
				+ "' and commiterid='" + commiterId + "'and type='" + type
				+ "'";
		// 执得查询语句
		String cut = sg.getSnglRowSnglCol(sql2, "count");
		// 得到insert语句
		sql = sg.genInsStmt("meeting", ColNames, ColValues);
		// 如果查询的结果为0,表示没有相同的记录,就执行sql语句进得插入一条记录

		if (!JNPCTools.isPresiderAvailable(startTime, endTime, presider,"")) {
			flag = -99;
			return flag;
		}
		if (!JNPCTools.isLeadersAvailable(startTime, endTime, leaders,"")) {
			flag = -98;
			return flag;
		}

		if (cut.equals("0")) {
			flag = sg.update(sql);
		} else {
			flag = -999;
		}
		return flag;
	}

	/*
	 * public String[] test(String tblName){ String[] result = null;
	 * SqlGenerator sg = new SqlGenerator(); result = sg.getColumns(tblName);
	 * return result; }
	 */

	/**
	 * 得到状态为status的会议通知信息
	 * 
	 * @param status
	 *            会议通知状态
	 * @return
	 */
	public String[][] getMeeting(String status) {
		String results[][] = null;
		SqlGenerator sg = new SqlGenerator();
		String sql = "";
		// 设置字段名数组
		StringBuffer strBuff = new StringBuffer(); // 用于保存查询语句
		// 设置查询语句,查询该类目下的所有记录
		strBuff.append("select * from view_meeting where status='");
		strBuff.append(status);
		strBuff.append("' and endtime> sysdate");
		strBuff.append(" order by starttime");
		sql = strBuff.toString(); // 生成sql语句
		results = sg.getMultiRowMultiCol(sql, AllColNames); // 调用方法执行查询
		return results;
	}

	/**
	 * 得到指定流水号的会议通知的全部信息
	 * 
	 * @param id
	 *            会议通知流水号
	 * @return
	 */
	public String[] getMeeting(int flowId) {
		String result[] = null;
		SqlGenerator sg = new SqlGenerator();
		String sql = "";
		// 设置字段名数组
		StringBuffer strBuff = new StringBuffer(); // 用于保存查询语句
		// 设置查询语句,查询该类目下的所有记录
		strBuff.append("select * from view_meeting where id=");
		strBuff.append(flowId);
		sql = strBuff.toString(); // 生成sql语句
		result = sg.getSnglRowMultiCol(sql, AllColNames); // 调用方法执行查询
		return result;
	}

	/**
	 * 得到状态为status的会议或例会通知信息
	 * 
	 * @param status
	 *            会议通知状态
	 * @param type
	 *            会议类型(1表示例会;2表示会议)
	 * @return
	 */
	public String[][] getMeeting(String status, String type) {
		String results[][] = null;
		SqlGenerator sg = new SqlGenerator();
		String sql = "";
		// 设置字段名数组
		StringBuffer strBuff = new StringBuffer(); // 用于保存查询语句
		// 设置查询语句,查询该类目下的所有记录
		strBuff.append("select * from view_meeting where status='");
		strBuff.append(status);
		strBuff.append("' and type='");
		strBuff.append(type);
		strBuff.append("' and endtime> sysdate");
		strBuff.append(" order by starttime");
		sql = strBuff.toString(); // 生成sql语句
		results = sg.getMultiRowMultiCol(sql, AllColNames); // 调用方法执行查询
		return results;
	}

	/**
	 * 得到会议已经结束的历史会议信息
	 * 
	 * @param
	 * @return
	 */
	public String[][] getHistoryMeeting(String type) {
		String results[][] = null;
		SqlGenerator sg = new SqlGenerator();
		String sql = "";
		// 设置字段名数组
		StringBuffer strBuff = new StringBuffer(); // 用于保存查询语句
		// 设置查询语句,查询该类目下的所有记录
		strBuff.append("select * from view_meeting where endtime< sysdate and type='");
		strBuff.append(type);
		strBuff.append("' and status='3' order by starttime");
		sql = strBuff.toString(); // 生成sql语句
		results = sg.getMultiRowMultiCol(sql, AllColNames); // 调用方法执行查询
		return results;
	}

	/**
	 * 查询已经分配会议室的会议的会议室信息
	 * 
	 * @param checkedMeetingInfr
	 *            :已经审核的会议信息
	 */
	public String[][] getAllotRoomInfr(String[][] checkedMeetingInfr) {
		MeetingRoom meetingroom = new MeetingRoom();
		String[][] results = new String[checkedMeetingInfr.length][3];
		if (checkedMeetingInfr != null) { // 如果已审核会议信息不为空
			for (int i = 0; i < checkedMeetingInfr.length; i++) {
				if (checkedMeetingInfr[i][0] != null) {
					String[] result = meetingroom
							.getRoomInfo(checkedMeetingInfr[i][1]);
					if (result == null)
						return null;
					// results[i][0] = result[0]; //会议室Id
					results[i][0] = result[0]; // 会议地点
					results[i][1] = result[1]; // 会议室房间号
					results[i][2] = result[2]; // 会议大小(容纳人数)
				}
			}
		}
		return results; // 返回会议室信息
	}

	/**
	 * 得到申请人所在部门的会议申请信息
	 * 
	 * @param userId
	 *            部门会议申请人id
	 * @return
	 */
	public String[][] meetingVisitAllByMe(String userId) {
		String results[][] = null;
		SqlGenerator sg = new SqlGenerator();
		String commiterDept = getOrg(userId);
		String sql = "";
		// 设置字段名数组
		String[] ColNames1 = { "id", "starttime", "endtime", "content",
				"leader", "depart", "remark", "committime", "status",
				"commiterid", "type", "presider" };
		StringBuffer strBuff = new StringBuffer(); // 用于保存查询语句
		// 设置查询语句,查询该类目下的所有记录
		strBuff.append("select id,starttime,endtime,content,leader,depart,remark,committime,status,commiterid,type,presider");
		strBuff.append(" from meeting where commitdepart='");
		strBuff.append(commiterDept);
		strBuff.append("' and endtime> sysdate");
		strBuff.append(" order by starttime");
		sql = strBuff.toString(); // 生成sql语句
		results = sg.getMultiRowMultiCol(sql, ColNames1); // 调用方法执行查询
		return results;
	}

	/**
	 * 会议申请者修改时读出会议记录
	 * 
	 * @param flowId
	 *            会议通知序列号
	 * @return 会议申请信息
	 */
	public String getRoomId(int flowId) {
		SqlGenerator sg = new SqlGenerator();
		String ColName = "roomid";
		StringBuffer strBuff = new StringBuffer();

		strBuff.append("select roomid ");
		strBuff.append("from meeting where id =");
		strBuff.append(flowId);
		String sql = strBuff.toString();
		return sg.getSnglRowSnglCol(sql, ColName);
	}

	/**
	 * 会议申请者修改时读出会议记录
	 * 
	 * @param flowId
	 *            会议通知序列号
	 * @return 会议申请信息
	 */
	public String[] getMyMeeting(int flowId) {
		String result[] = null;
		SqlGenerator sg = new SqlGenerator();
		String sql = "";
		// 设置字段名数组
		String[] ColNames2 = { "id", "starttime", "endtime", "content",
				"leader", "depart", "remark", "type", "presider", "grade" };
		StringBuffer strBuff = new StringBuffer(); // 用于保存查询语句
		// 设置查询语句,查询该类目下的所有记录
		strBuff.append("select id,starttime,endtime,content,leader,depart,remark,type,presider,grade");
		strBuff.append(" from meeting where id =");
		strBuff.append(flowId);
		sql = strBuff.toString(); // 生成sql语句
		result = sg.getSnglRowMultiCol(sql, ColNames2); // 调用方法执行查询
		return result;
	}

	/**
	 * 得到会议通知的状态
	 * 
	 * @param id
	 *            会议通知id
	 * @return 返回会议通知状态
	 */
	public String getStatus(String id) {
		String status = "";
		String sql = "";
		SqlGenerator sg = new SqlGenerator();
		// 查询语句
		sql = "select status from meeting where id=" + id;
		// 得到会议通知的状态(0未处理;1已审批通过;2审批拒绝;3已安排会议室)
		status = sg.getSnglRowSnglCol(sql, "status");
		return status;
	}

	/**
	 * 修改会议申请信息
	 * 
	 * @param id
	 *            会议通知序列号
	 * @param starttime
	 *            会议开始时间
	 * @param endtime
	 *            会议结束时间
	 * @param content
	 *            会议内容
	 * @param leader
	 *            参加会议领导
	 * @param depart
	 *            参加会议部门
	 * @param remark
	 *            说明
	 * @param commiterId
	 *            申请人ID
	 * @return 返回是否插入成功
	 */
	public int meetingModify(String id, String startTime, String endTime,
			String content, String[] leaders, String depart, String remark,
			String commiterId, String type, String status, String fromWhere,
			String presider, String grade) {
		int flag = 0;
		// 得到已经排序的参加会议领导
		String leader = "";
		if (leaders != null) {
			leader = leadersSort(leaders);
		}
		// 得到申请人所在的部门
		String commitDept = getOrg(commiterId);
		// 得到系统时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		Date currentTime_1 = new Date();
		String sb = sdf.format(currentTime_1);
		SqlGenerator sg = new SqlGenerator();
		StringBuffer sr = new StringBuffer();
		sr.append("update meeting set starttime=to_date('");
		sr.append(startTime);
		sr.append("','yyyy-mm-dd hh24:mi'),endtime=to_date('");
		sr.append(endTime);
		sr.append("','yyyy-mm-dd hh24:mi'),content='");
		sr.append(content);
		sr.append("',leader='");
		sr.append(leader);
		sr.append("',depart='");
		sr.append(depart);
		sr.append("',remark='");
		sr.append(remark);
		sr.append("',committime=to_date('");
		sr.append(sb);
		sr.append("','yyyy-mm-dd hh24:mi'),");
		// 如果是会议申请部门修改
		if (fromWhere.equals("dept")) {
			sr.append("commiterid='");
			sr.append(commiterId);
			sr.append("',commitdepart='");
			sr.append(commitDept);
			sr.append("',approverid='',alloterid='',");
		}
		sr.append("status='");
		sr.append(status);
		sr.append("',type='");
		sr.append(type);
		sr.append("',presider='");
		sr.append(presider);
		sr.append("',grade='");
		sr.append(grade);
		sr.append("' where id=");
		sr.append(id);
		String sql = sr.toString();

		// 生成一条sql语句,用于查询是否有相同的记录,防止页面刷新时自动添加记录
		String sql2 = "select count(*) count from meeting where startTime=to_date('"
				+ startTime + "','yyyy-mm-dd hh24:mi')";
		sql2 = sql2 + "and endTime=to_date('" + endTime
				+ "','yyyy-mm-dd hh24:mi') and content ='" + content
				+ "' and commiterid='" + commiterId + "'and type='" + type
				+ "'";
		// 执得查询语句
		String cut = sg.getSnglRowSnglCol(sql2, "count");
		// 如果查询的结果为0,表示没有相同的记录,就执行sql语句进得插入一条记录

		if (!JNPCTools.isPresiderAvailable(startTime, endTime, presider,id)) {
			flag = -99;
			return flag;
		}
		if (!JNPCTools.isLeadersAvailable(startTime, endTime, leaders,id)) {
			flag = -98;
			return flag;
		}

		if (cut.equals("0")) {
			flag = sg.update(sql);
		} else {
			flag = -999;
		}
		return flag;

	}

	/**
	 * 修改例会信息
	 * 
	 * @param id
	 *            会议通知序列号
	 * @param starttime
	 *            会议开始时间
	 * @param endtime
	 *            会议结束时间
	 * @param content
	 *            会议名称
	 * @param leader
	 *            参加会议领导
	 * @param depart
	 *            参加会议部门
	 * @param remark
	 *            说明
	 * @return 返回是否插入成功
	 */
	public int customModify(String id, String startTime, String endTime,
			String content, String[] leaders, String depart, String remark,
			String type, String status, String presider, String grade) {
		int flag = 0;
		// 得到已经排序的参加会议领导
		String leader = "";
		if (leaders != null) {
			leader = leadersSort(leaders);
		}
		// 得到系统时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		Date currentTime_1 = new Date();
		String sb = sdf.format(currentTime_1);
		SqlGenerator sg = new SqlGenerator();
		StringBuffer sr = new StringBuffer();
		sr.append("update meeting set starttime=to_date('");
		sr.append(startTime);
		sr.append("','yyyy-mm-dd hh24:mi'),endtime=to_date('");
		sr.append(endTime);
		sr.append("','yyyy-mm-dd hh24:mi'),content='");
		sr.append(content);
		sr.append("',leader='");
		sr.append(leader);
		sr.append("',depart='");
		sr.append(depart);
		sr.append("',remark='");
		sr.append(remark);
		sr.append("',committime=to_date('");
		sr.append(sb);
		sr.append("','yyyy-mm-dd hh24:mi'),status='");
		sr.append(status);
		sr.append("',type='");
		sr.append(type);
		sr.append("',presider='");
		sr.append(presider);
		sr.append("',grade='");
		sr.append(grade);
		sr.append("' where id=");
		sr.append(id);
		String sql = sr.toString();
		flag = sg.update(sql); // 执行sql语句,修改一条记录
		return flag;
	}

	/**
	 * 修改历史例会信息后当作新的例会发布
	 * 
	 * @param id
	 *            会议通知序列号
	 * @param starttime
	 *            会议开始时间
	 * @param endtime
	 *            会议结束时间
	 * @param content
	 *            会议名称
	 * @param leader
	 *            参加会议领导
	 * @param depart
	 *            参加会议部门
	 * @param remark
	 *            说明
	 * @return 返回是否插入成功
	 */
	public int customModifyInsert(String id, String startTime, String endTime,
			String content, String[] leaders, String depart, String remark,
			String type, String status, String presider, String grade) {
		String[] result = this.getMeeting(Integer.parseInt(id));
		String roomid = this.getRoomId(Integer.parseInt(id));

		int flag = 0;
		// 得到已经排序的参加会议领导
		String leader = "";
		if (leaders != null) {
			leader = leadersSort(leaders);
		}

		// 得到系统时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		Date currentTime_1 = new Date();
		String temp = sdf.format(currentTime_1);

		String[] ColValues = new String[ColNames.length];
		ColValues[0] = "seq_meeting_id.nextval";
		ColValues[1] = roomid;
		ColValues[2] = startTime;
		ColValues[3] = endTime;
		ColValues[4] = content;
		ColValues[5] = leader;
		ColValues[6] = depart;
		ColValues[7] = remark;
		ColValues[8] = temp;
		ColValues[9] = result[11];
		ColValues[10] = result[12];
		ColValues[11] = result[13];
		ColValues[12] = result[14];
		ColValues[13] = result[15];
		ColValues[14] = result[16];
		ColValues[15] = presider;
		ColValues[16] = grade;

		SqlGenerator sg = new SqlGenerator();

		// 生成一条sql语句,用于查询是否有相同的记录,防止页面刷新时自动添加记录
		String sql2 = "select count(*) count from meeting where startTime=to_date('"
				+ startTime + "','yyyy-mm-dd hh24:mi')";
		sql2 = sql2 + "and endTime=to_date('" + endTime
				+ "','yyyy-mm-dd hh24:mi') and content ='" + content
				+ "' and commiterid='" + result[9] + "'and type='" + type + "'";
		// 执得查询语句
		String cut = sg.getSnglRowSnglCol(sql2, "count");
		// 得到insert语句
		String sql = sg.genInsStmt("meeting", ColNames, ColValues);
		// 如果查询的结果为0,表示没有相同的记录,就执行sql语句进得插入一条记录
		if (cut.equals("0")) {
			flag = sg.update(sql);
		} else {
			flag = -999;
		}

		return flag;
	}

	/**
	 * 删除会议信息记录
	 * 
	 * @param ids
	 *            会议信息ids
	 * @return 返回是否删除成功
	 */
	public int meetingDelete(String[] ids) {
		int flag = 0;
		int tembox = 0; // 定义一整型变量，把得到的ids转化为整型
		// 定义一删除Sql语句数组，长度为ids的长度
		String[] sqlDel = new String[ids.length];
		SqlGenerator sg = new SqlGenerator();
		// 生成Sql语句数组
		for (int j = 0; j < ids.length; j++) {
			tembox = Integer.parseInt(ids[j].toString());
			sqlDel[j] = "delete from meeting where id=" + tembox;
		}
		flag = sg.update(sqlDel);
		return flag;
	}

	/**
	 * 会议审核
	 * 
	 * @param ids
	 *            会议信息id
	 * @param code
	 *            会议审核状态
	 * @return 返回是否成功更新
	 */
	public int meetingApprove(String[] ids, String code, String approverId) {
		int flag = 0;
		int tembox = 0; // 定义一整型变量，把得到的ids转化为整型
		String[] meeting = null;
		// 定义Sql语句数组，长度为ids的长度
		String[] sqlUpdate = new String[ids.length];
		SqlGenerator sg = new SqlGenerator();
		// 生成Sql语句数组
		for (int j = 0; j < ids.length; j++) {
			meeting = getMeeting(Integer.parseInt(ids[j].toString()));
			tembox = Integer.parseInt(ids[j].toString());
			if (meeting[1] != null && "1".equals(code)) {
				sqlUpdate[j] = "update meeting set status='3',approverid='"
						+ approverId + "' where id=" + tembox;
			} else {
				sqlUpdate[j] = "update meeting set status='" + code
						+ "',approverid='" + approverId + "' where id="
						+ tembox;
			}
		}
		flag = sg.update(sqlUpdate);
		return flag;
	}

	/**
	 * 会议室分配
	 * 
	 * @param meetingId
	 *            会议信息id
	 * @param roomId
	 *            会议室id
	 * @return 返回是否成功更新
	 */
	public int meetingAllot(String meetingId, String roomId, String alloterId) {
		int flag = 0;
		int tembox = 0; // 定义一整型变量，把得到的meetingId转化为整型
		String sql = "";
		SqlGenerator sg = new SqlGenerator();
		// 生成Sql语句数组
		tembox = Integer.parseInt(meetingId);
		sql = "update meeting set roomId='" + roomId + "',alloterid='"
				+ alloterId + "',status='3' where id=" + tembox;
		flag = sg.update(sql);
		return flag;
	}

	public Hashtable roomCheck(String meetingId) {
		String sql = "";
		StringBuffer strBuff = new StringBuffer();
		SqlGenerator sg = new SqlGenerator();
		String[] ColNames3 = { "startTime", "endTime" };
		// 会议时间查询语句
		sql = "select startTime,endTime from meeting where ID =" + meetingId;
		// 得到会议时间
		String[] time = sg.getSnglRowMultiCol(sql, ColNames3); // 调用方法执行查询
		// 会议开始时间
		String meetingStartTime = time[0].substring(0, 16);
		// 会议结束时间
		String meetingEndTime = time[1].substring(0, 16);
		String[] result = null;
		Hashtable unavailableRoom = new Hashtable();
		strBuff.append("select roomID from meeting where ((to_date('");
		strBuff.append(meetingStartTime);
		strBuff.append("','yyyy-mm-dd hh24:mi') >starttime and to_date('");
		strBuff.append(meetingStartTime);
		strBuff.append("','yyyy-mm-dd hh24:mi')<endtime) or (to_date('");
		strBuff.append(meetingEndTime);
		strBuff.append("','yyyy-mm-dd hh24:mi') >starttime and to_date('");
		strBuff.append(meetingEndTime);
		strBuff.append("','yyyy-mm-dd hh24:mi')<endtime )");
		strBuff.append("or (starttime>to_date('");
		strBuff.append(meetingStartTime);
		strBuff.append("','yyyy-mm-dd hh24:mi') and endtime<to_date('");
		strBuff.append(meetingEndTime);
		strBuff.append("','yyyy-mm-dd hh24:mi'))) and status='3'and id <> '");
		strBuff.append(meetingId);
		strBuff.append("'");
		
		// 查询语句
		String sqlStr = strBuff.toString();
		// 得到在这次会议时间段内,已经安排会议的会议室
		result = sg.getMultiRowSnglCol(sqlStr, "roomid"); // 调用方法执行查询
		int j = 1;
		for (int i = 0; i < result.length; i++) {
			if (unavailableRoom.containsValue(result[i]))
				;
			else {
				unavailableRoom.put(new Integer(j++), result[i]);

			}
		}
		return unavailableRoom;
	}

	/**
	 * 删除结束时间小于系统时间的会议信息 return 返回是否删除成功
	 * 
	 * public int historyMeetingDelete(){ int flag = 0; SqlGenerator sg = new
	 * SqlGenerator(); String sql =
	 * "delete from meeting where endtime < sysdate"; flag = sg.update(sql);
	 * //调用oracle中的方法进行删除 return flag; }
	 */

	/*
	 * 得到公司领导列表
	 * 
	 * @ return
	 */
	public String[] getLeaders() {
		String[] result = null;
		String sql = "";
		OracleConnection ora = new OracleConnection();
		sql = "select orgname from jnpc_dep where class in (1,2)";
		result = ora.getMultiRowSnglCol(sql, "orgname", 0);
		return result;
	}

	/*
	 * 对已选中的领导进行排序，生成逗号隔开的字符串
	 * 
	 * @param selectedLeaders 已选中的领导
	 * 
	 * @return 返回已排序的字符串
	 */
	public String leadersSort(String[] selectedLeaders) {
		String leader = "";
		Hashtable leaders = new Hashtable();
		String[] leaders1 = getLeaders();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < selectedLeaders.length; i++) {
			leaders.put(new Integer(i), selectedLeaders[i]);
		}
		for (int j = 0; j < leaders1.length; j++) {
			if (leaders.containsValue(leaders1[j])) {
				sb.append(leaders1[j]);
				sb.append(",");
			}
		}
		leader = sb.toString();
		leader = leader.substring(0, leader.lastIndexOf(","));
		return leader;
	}

	/*
	 * 得到已选中的领导数组
	 * 
	 * @param leader 用逗号隔开的领导字符串
	 * 
	 * @return
	 */
	public String[] leadersDivide(String leader) {
		if (leader.equals("") || leader.equals("null")) {
			return null;
		} else {
			// 分解各个领导
			StringTokenizer st = new StringTokenizer(leader, ",");
			Vector vector = new Vector();
			while (st.hasMoreTokens()) {
				vector.addElement(st.nextToken());
			}
			// 实例数据库Bean
			SqlGenerator sqlGen = new SqlGenerator();
			String[] leaders = sqlGen.cvtVtrToArr(vector);
			return leaders;
		}
	}

	/**
	 * 根据查询条件查询出会议信息
	 * 
	 * @param sqlInput
	 * @return
	 */
	public String[][] meetingQryDetail(String sqlInput) {
		String[] ColNames4 = new String[] { "id", // 会议通知流水号
				"building", // 会议室所在楼
				"room", // 房间号
				"capacity", // 容纳人数
				"starttime", // 会议开始时间
				"endtime", // 会议结束时间
				"content", // 会议内容
				"leader", // 会议领导
				"depart", // 参加会议部门
				"remark", // 会议说明
				"committime", // 会议申请提交时间
				"commiterid", // 申请提交人id
				"commitdepart", // 会议申请部门
				"approverid", // 会议审核人id
				"alloterid", // 会议室安排人id
				"status", // 会议申请状态(0未处理;1已审批通过;2审批拒绝;3已安排会议室)
				"type", // 会议类型(1例会;2会议)
				"presider", // 会议主持人
				"grade", }; // 会议级别

		// 实例数据库Bean
		SqlGenerator sg = new SqlGenerator();
		String sql = "select * from view_meeting where " + sqlInput
				+ " order by starttime";
		return sg.getMultiRowMultiCol(sql, ColNames4);

	}

	/*
	 * 得到员工所在部门
	 * 
	 * @param userId 员工编号
	 * 
	 * @ return
	 */
	public String getOrg(String userId) {
		OracleConnection ora = new OracleConnection();
		String sql = "select org from jnpc_user_simple where id='" + userId
				+ "'"; // 查询id等于用户id的部门名称
		String org = ora.getSnglRowSnglCol(sql, "org", 0);
		return org;
	}

	/**
	 * 得到状态为status的会议通知信息
	 * 
	 * @param status
	 *            会议通知状态
	 * @return
	 */
	public String[][] getSimpleMeeting(String status) {
		String results[][] = null;
		SqlGenerator sg = new SqlGenerator();
		String sql = "";
		// 设置字段名数组
		StringBuffer strBuff = new StringBuffer(); // 用于保存查询语句
		// 设置查询语句,查询该类目下的所有记录
		strBuff.append("select * from meeting where status='");
		strBuff.append(status);
		strBuff.append("' and endtime> sysdate");
		strBuff.append(" order by starttime");
		sql = strBuff.toString(); // 生成sql语句
		results = sg.getMultiRowMultiCol(sql, ColNames); // 调用方法执行查询
		return results;
	}

	/**
	 * 得到指定流水号的会议通知的全部信息
	 * 
	 * @param id
	 *            会议通知流水号
	 * @return
	 */
	public String[] getSimpleMeeting(int flowId) {
		String result[] = null;
		SqlGenerator sg = new SqlGenerator();
		String sql = "";
		// 设置字段名数组
		StringBuffer strBuff = new StringBuffer(); // 用于保存查询语句
		// 设置查询语句,查询该类目下的所有记录
		strBuff.append("select * from meeting where id=");
		strBuff.append(flowId);
		sql = strBuff.toString(); // 生成sql语句
		result = sg.getSnglRowMultiCol(sql, ColNames); // 调用方法执行查询
		return result;
	}

	/**
	 * 得到状态为status的会议或例会通知信息
	 * 
	 * @param status
	 *            会议通知状态
	 * @param type
	 *            会议类型(1表示例会;2表示会议)
	 * @return
	 */
	public String[][] getSimpleMeeting(String status, String type) {
		String results[][] = null;
		SqlGenerator sg = new SqlGenerator();
		String sql = "";
		// 设置字段名数组
		StringBuffer strBuff = new StringBuffer(); // 用于保存查询语句
		// 设置查询语句,查询该类目下的所有记录
		strBuff.append("select * from meeting where status='");
		strBuff.append(status);
		strBuff.append("' and type='");
		strBuff.append(type);
		strBuff.append("' and endtime> sysdate");
		strBuff.append(" order by starttime");
		sql = strBuff.toString(); // 生成sql语句
		results = sg.getMultiRowMultiCol(sql, ColNames); // 调用方法执行查询
		return results;
	}

	public int addMeetingFromTemplate(String[] template, SimpleDateFormat sdf,
			SqlGenerator sg) {
		int flag = 0;

		// 得到系统时间
		String time = sdf.format(new Date());

		String sql = "";
		String[] colValues = new String[ColNames.length];

		colValues[0] = "seq_meeting_id.nextval";
		;
		colValues[1] = template[21]; // meetingRoomId
		// TODO
		colValues[2] = template[4].substring(0, 16); // startTime
		colValues[3] = template[5].substring(0, 16); // endTime
		colValues[4] = template[6]; // content
		colValues[5] = template[7]; // leaders
		colValues[6] = template[8]; // depart
		colValues[7] = template[9]; // remark
		colValues[8] = time; // committime
		colValues[9] = template[11]; // commiterId
		colValues[10] = template[12]; // commitdepart
		colValues[11] = template[13]; // approverId
		colValues[12] = template[14]; // alloterId
		colValues[13] = "0"; // status=0未处理
		colValues[14] = "1"; // type=1例会
		colValues[15] = template[15]; // presider
		colValues[16] = template[16]; // grade

		sql = sg.genInsStmt("meeting", ColNames, colValues);

		// TODO 验证冲突

		if (!JNPCTools.isPresiderAvailable(colValues[2], colValues[3],
				colValues[15],"")) {
			flag = -99;
			colValues[13] = "4"; // status=4冲突
			sql = sg.genInsStmt("meeting", ColNames, colValues);
			sg.update(sql);
		} else if (!JNPCTools.isLeadersAvailable(colValues[2], colValues[3],
				JNPCTools.leadersDivide(colValues[5]),"")) {
			flag = -98;
			colValues[13] = "4";
			sql = sg.genInsStmt("meeting", ColNames, colValues);
			sg.update(sql);
		} else if (false) {// 验证是否重复触发
			flag = -999;
			colValues[13] = "4";
			sql = sg.genInsStmt("meeting", ColNames, colValues);
			sg.update(sql);
		} else {
			flag = sg.update(sql);
		}
		return flag;
	}

	public int getCountByRoomIDAndTime(String roomID, String meetingID) {
		String sql = "select count(*) from MEETING t where t.roomid="
				+ roomID
				+ " and (select t1.starttime from MEETING t1 where t1.id="
				+ meetingID
				+ ") between t.starttime and t.endtime or (select t2.starttime from MEETING t2 where t2.id="
				+ meetingID + ") between t.starttime and t.endtime and status='3'";
		Oracle ora = new Oracle();
		return ora.getRecordCount(sql);
	}

}
