package com.imc_cg.meeting.bean.business;

import com.imc_cg.meeting.bean.data.SqlGenerator;
import com.imc_cg.meeting.bean.util.JNPCTools;

public class RegularMeetingTemplate {

  private String[] AllColNames = null;
  private static String DefaultDays = "1";
  
  public RegularMeetingTemplate(){

	AllColNames = new String[] {    			
						"id",          //例会模板流水号
            "building",    //会议室所在楼
            "room",        //房间号
            "capacity",    //容纳人数
            "starttime",   //会议开始时间
            "endtime",     //会议结束时间
            "content",     //会议内容
            "leader",      //会议领导
            "depart",      //参加会议部门
            "remark",      //会议说明
            "committime",  //会议申请提交时间
            "commiterid",  //申请提交人id
            "commitdepart",   //会议申请部门
            "approverid",   //会议审核人id
            "alloterid",    //会议室安排人id
            "presider", //会议主持人
            "grade",    //会议级别 (1综合会议，2处内会议)
						"scheduledperiod",   //例会周期天数
						"daysbeforetrigger", //提前触发天数
						"lasttriggerdate",   //上次触发日期
						"isautotrigger",    //是否自动触发（0否；1是）
						"meetingRoomId"};
  }
  
  public int addRegularMeetingTemplate(
				String building,
				String room,
				String capacity, 											
				String startTime,
				String endTime,
				String content,
				String[] leaders,
				String depart, 
				String remark,
				String commiterId, 
				String approverId,
				String alloterId,
				String presider,
				String grade,
				String scheduledPeriod,
				String daysBeforeTrigger,
				String lastTriggerDate,
				String isAutoTrigger,
				String meetingRoomId) {
  	
			//插入字符的名称
			String[] ColValues = new String[AllColNames.length];
			ColValues[0] = "seq_regularmeetingtemplate_id.nextval";
			ColValues[1] = building;
			ColValues[2] = (room==null || room.equals("null")) ? "" : room;
			ColValues[3] = (capacity==null || capacity.equals("null")) ? "" : capacity;
			ColValues[4] = startTime;
			ColValues[5] = endTime;
			ColValues[6] = content;
			//得到已经排序的参加会议领导
			ColValues[7] = (leaders==null || leaders[0].equals("null")) ? "" : JNPCTools.leadersSort(leaders);
			ColValues[8] = depart;
			ColValues[9] = remark;
			//得到系统时间
			ColValues[10] = JNPCTools.getSystemDate();
			ColValues[11] = commiterId;
			//得到申请人所在的部门
			ColValues[12] = JNPCTools.getOrg(commiterId);
			ColValues[13] = approverId;
			ColValues[14] = alloterId;
			ColValues[15] = presider==null ? "" : presider;
			ColValues[16] = grade;
			ColValues[17] = scheduledPeriod==null ? "3" : scheduledPeriod;
			ColValues[18] = daysBeforeTrigger==null ? DefaultDays : daysBeforeTrigger;
			ColValues[19] = lastTriggerDate==null ? "" : lastTriggerDate;;
			ColValues[20] = isAutoTrigger==null ? "0" : isAutoTrigger;
			ColValues[21] = meetingRoomId;
						
			int flag = 0;
			//判断时间是否冲突
			if(presider!=null && !"".equals(presider) && !JNPCTools.isPresiderAvailable(startTime,endTime,presider,"")){
				flag = -99; 
			} else if(leaders!=null && "".equals(leaders.toString().trim()) && !JNPCTools.isLeadersAvailable(startTime,endTime,leaders,"")){
				flag = -98; 
			}	else {
				SqlGenerator sg = new SqlGenerator();
				// 生成一条sql语句,用于查询是否有相同的记录,防止页面刷新时自动添加记录
				String sql2 = "select count(*) count from regular_meeting_template where startTime=to_date('"+startTime+"','yyyy-mm-dd hh24:mi')";
				sql2 = sql2 + "and endTime=to_date('"+endTime+"','yyyy-mm-dd hh24:mi') and content ='"+content+"' and commiterid='"+commiterId+"'";
				String cut = sg.getSnglRowSnglCol(sql2, "count");
				if (cut.equals("0")) {
					// 如果查询的结果为0,表示没有相同的记录,就插入一条记录
					 String sql = sg.genInsStmt("regular_meeting_template", AllColNames, ColValues);
					flag = sg.update(sql);
				} else {
					flag = -999;
				}
			}
			return flag;
	}
  
  public int deleteRegularMeetingTemplate(String[] ids) {
    int flag = 0;
    int id = 0;
    String[] sql = new String[ids.length];
    SqlGenerator sg = new SqlGenerator();
    for(int i=0;i<ids.length;i++){
      id = Integer.parseInt(ids[i].toString());
      sql[i] = "delete from regular_meeting_template where id="+id;
    }
    flag = sg.update(sql);
    return flag;
  }
  
  public int modifyRegularMeetingTemplate(String[] template){
		int flag = 0;
		SqlGenerator sg = new SqlGenerator();
		String sql = sg.genDateUpdateStmt("regular_meeting_template", "id", template[0], AllColNames, template, "yyyy-mm-dd hh24:mi");
		flag = sg.update(sql);
  	return flag;
  }
  
  public int modifyRegularMeetingTemplate(
  		String id,
			String building,
			String room,
			String capacity, 											
			String startTime,
	    String endTime,
	    String content,
	    String[] leaders,
	    String depart, 
	    String remark,
	    String commiterId, 
	    String approverId,
	    String alloterId,
	    String presider,
	    String grade,
	    String scheduledPeriod,
	    String daysBeforeTrigger,
	    String lastTriggerDate,
	    String isAutoTrigger,
			String meetingRoomId) {
  	
		//插入字符的名称
		String[] ColValues = new String[AllColNames.length];
		ColValues[0] = id;
		ColValues[1] = building;
		ColValues[2] = room;
		ColValues[3] = capacity;
		ColValues[4] = startTime;
		ColValues[5] = endTime;
		ColValues[6] = content;
		//得到已经排序的参加会议领导
		ColValues[7] = JNPCTools.leadersSort(leaders);
		ColValues[8] = depart;
		ColValues[9] = remark;
		//得到系统时间
		ColValues[10] = JNPCTools.getSystemDate();
		ColValues[11] = commiterId;
		//得到申请人所在的部门
		ColValues[12] = JNPCTools.getOrg(commiterId);
		ColValues[13] = approverId;
		ColValues[14] = alloterId;
		ColValues[15] = presider;
		ColValues[16] = grade;
		ColValues[17] = scheduledPeriod;
		ColValues[18] = daysBeforeTrigger;
		ColValues[19] = lastTriggerDate;
		ColValues[20] = isAutoTrigger;
		ColValues[21] = meetingRoomId;
		
		//判断时间是否冲突
		int flag = 0;
		if(presider!=null && !"".equals(presider) && !JNPCTools.isPresiderAvailable(startTime,endTime,presider,id)){
			flag = -99; 
		} else if(leaders!=null && "".equals(leaders.toString().trim()) && !JNPCTools.isLeadersAvailable(startTime,endTime,leaders,id)){
			flag = -98; 
		} else {
			SqlGenerator sg = new SqlGenerator();
			String sql = sg.genDateUpdateStmt("regular_meeting_template", "id", id, AllColNames, ColValues, "yyyy-mm-dd hh24:mi");
			flag = sg.update(sql);
		}
  	return flag;
	}

	public String[] getRegularMeetingTemplateById(String id) {
		String template[] = null;
    String sql = "select * from regular_meeting_template where id="+id;
    
    SqlGenerator sg = new SqlGenerator();
    template = sg.getSnglRowMultiCol(sql, AllColNames); 
    
    return template;
	}

	public String[][] getRegularMeetingTemplates() {
		String templates[][] = null;
    SqlGenerator sg = new SqlGenerator();
    String sql = "select * from regular_meeting_template order by starttime";
   
    templates = sg.getMultiRowMultiCol(sql, AllColNames);
    return templates;
	}

	public String[] getIds() {
    String sql = "select id from regular_meeting_template";
    SqlGenerator sg = new SqlGenerator();
    return sg.getMultiRowSnglCol(sql, "id"); 
	}
}
