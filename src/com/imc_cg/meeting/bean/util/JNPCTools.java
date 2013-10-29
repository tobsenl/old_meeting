package com.imc_cg.meeting.bean.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.imc_cg.meeting.bean.data.OracleConnection;
import com.imc_cg.meeting.bean.data.SqlGenerator;
import com.jnpc.datasource.Oracle;

public class JNPCTools {

	/**
	 * 得到员工所在部门
	 * 
	 * @param userId 员工编号
	 * 
	 * @ return
	 */
	public static String getOrg(String userId) {
		OracleConnection ora = new OracleConnection();
		String sql = "select org from jnpc_user_simple where id='" + userId + "'"; // 查询id等于用户id的部门名称
		String org = ora.getSnglRowSnglCol(sql, "org", 0);
		return org;
	}

	/**
	 * 得到公司领导列表
	 * 
	 * @ return
	 */
	public static String[] getLeaders() {
		String[] result = null;
		String sql = "";
		OracleConnection ora = new OracleConnection();
		sql = "select orgname from jnpc_dep where class in (1,2)";
		result = ora.getMultiRowSnglCol(sql, "orgname", 0);
		return result;
	}

	/**
	 * 对已选中的领导进行排序，生成逗号隔开的字符串
	 * 
	 * @param selectedLeaders 已选中的领导
	 * 
	 * @return 返回已排序的字符串
	 */
	public static String leadersSort(String[] selectedLeaders) {
		if(selectedLeaders == null){
			return "";
		}
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
	
  /**
   * 得到已选中的领导数组
   * @param leader 用逗号隔开的领导字符串
   * @return
   */
  public static String[] leadersDivide(String leader){  
    if(leader==null || leader.equals("") || leader.equals("null")){
      return null;
    }else{
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
	 * 得到系统时间
	 * @return
	 */
	public static String getSystemDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm"); 
		Date currentTime_1 = new Date(); 
		return sdf.format(currentTime_1); 
	}
	
	/**
	 * 判断领导与会时间是否冲突
	 * 
	 * @param startTime 会议开始时间
	 * 
	 * @param endTime 会议结束时间
	 * 
	 * @param leaders 领导名单
	 * 
	 * @return 返回是否冲突
	 */
	public static boolean isLeadersAvailable(String startTime, String endTime,
			String[] leaders,String meetingId) {
		boolean flag = false;
		Oracle ora = new Oracle();
		ora.setDatasource("jdbc/intraweb");
		
		if(leaders==null){
			return true;
		}

		for (int i = 0; i < leaders.length; i++) {
			String sql3 = "select count(*) count from meeting where to_date('"
					+ startTime + "','yyyy-mm-dd hh24:mi') <endTime" + " and  to_date('"
					+ endTime + "','yyyy-mm-dd hh24:mi')>=startTime and leader like'%"
					+ leaders[0] + "%'";
			// System.out.println(sql3);
			if(meetingId != null &&!"".equals(meetingId)){
			    sql3 += " and id<>"+meetingId;
			}
			String count = ora.getSnglRowSnglCol(sql3);
			if (!count.equals("0"))
				return flag;
		}
		return true;
	}

	/**
	 * 判断主持人与会时间是否冲突
	 * 
	 * @param startTime 会议开始时间
	 * 
	 * @param endTime 会议结束时间
	 * 
	 * @param presider 主持人
	 * 
	 * @return 返回是否冲突
	 */
	public static boolean isPresiderAvailable(String startTime, String endTime,
			String presider,String meetingId) {
		boolean flag = false;
		Oracle ora = new Oracle();
		ora.setDatasource("jdbc/intraweb");
		String sql3 = "select count(*) count from meeting where to_date('"
				+ startTime + "','yyyy-mm-dd hh24:mi') <endTime" + " and  to_date('"
				+ endTime + "','yyyy-mm-dd hh24:mi')>=startTime and presider='"
				+ presider + "'";
		if(meetingId != null && !"".equalsIgnoreCase(meetingId)){
		    sql3 += " and id <>" + meetingId;
		}//是否为修改会议
		String count = ora.getSnglRowSnglCol(sql3);
		if (!count.equals("0"))
			return flag;

		return true;
	}
	
	public static String getStartTime(HttpServletRequest request){
		String startYear = request.getParameter("startYear");
		String startMonth = request.getParameter("startMonth");
		String startDay = request.getParameter("startDay");
		String startHour = request.getParameter("startHour");
		String startMinute = request.getParameter("startMinute");        
		String startTime = startYear.trim()+"-"+startMonth.trim()+"-"+startDay.trim()+" "+startHour.trim()+":"+startMinute.trim();
		return startTime;
	}
	
	public static String getEndTime(HttpServletRequest request){
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String endDay = request.getParameter("endDay");
		String endHour = request.getParameter("endHour");
		String endMinute = request.getParameter("endMinute");        
		String endTime = endYear.trim()+"-"+endMonth.trim()+"-"+endDay.trim()+" "+endHour.trim()+":"+endMinute.trim();
		return endTime;
	}
	
	public static Hashtable roomCheck(String startTime, String endTime){
    
		StringBuffer strBuff = new StringBuffer();
    strBuff.append("select roomID from meeting where ((to_date('");
    strBuff.append(startTime);
    strBuff.append("','yyyy-mm-dd hh24:mi') between starttime and endtime) or (to_date('");
    strBuff.append(endTime);
    strBuff.append("','yyyy-mm-dd hh24:mi') between starttime and endtime) ");
    strBuff.append("or (starttime>to_date('");
    strBuff.append(startTime);
    strBuff.append("','yyyy-mm-dd hh24:mi') and endtime<to_date('");
    strBuff.append(endTime);
    strBuff.append("','yyyy-mm-dd hh24:mi'))) and status='3'");
    String sqlStr= strBuff.toString();
    
    //得到在这次会议时间段内,已经安排会议的会议室
    String[] result= null;
    Hashtable unavailableRoom = new Hashtable();     
    SqlGenerator sg = new SqlGenerator();
    result = sg.getMultiRowSnglCol(sqlStr,"roomid");
    int j=1;
    for(int i=0;i<result.length;i++){
        if(unavailableRoom.containsValue(result[i]));
        else {
            unavailableRoom.put(new Integer(j++),result[i]);
        }
    }
    return unavailableRoom;
	}
}
