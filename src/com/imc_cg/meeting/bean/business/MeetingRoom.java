package com.imc_cg.meeting.bean.business;

/**

 */
import java.util.Hashtable;
import com.imc_cg.meeting.bean.data.OracleConnection;
import com.imc_cg.meeting.bean.data.SqlGenerator;

public class MeetingRoom {
    
  /**
   * 得到所有可以使用的会议室的所有信息
   * @return 会议室信息
    */ 
  public String[][] getAllRoom(){
    String results[][] = null;
    SqlGenerator sg = new SqlGenerator();
    String sql = "";
    // 设置字段名数组
    String[] ColNames = {"id", "building", "room", "capacity","remark"};
    StringBuffer strBuff = new StringBuffer(); //用于保存查询语句
     //设置查询语句,查询该类目下的所有记录
    strBuff.append("select id,building,room,capacity,remark");
    
    strBuff.append(" from meetingroom"); 
    strBuff.append(" where deleted='0'");  
    strBuff.append(" order by building,capacity");
    sql = strBuff.toString(); //生成sql语句
    results = sg.getMultiRowMultiCol(sql, ColNames); //调用方法执行查询
    return results;
  }
  
  /**
   * 得到序列号为roomID的会议室信息
   *@param roomID 会议室序列号
   *@return 会议室信息
   */
  public String[] getRoomInfo(String roomID){
    int id = Integer.parseInt(roomID);
    String[] result = null;
    String[] ColNames = { "id","building","room","capacity","remark"};
    SqlGenerator sg = new SqlGenerator();
    StringBuffer strBuff = new StringBuffer(); //用于保存查询语句
    strBuff.append("select id,building,room,capacity,remark");
    strBuff.append(" from meetingRoom where id=");
    strBuff.append(id);    
    String sql = strBuff.toString(); //生成sql语句
    result = sg.getSnglRowMultiCol(sql, ColNames); //调用方法执行查询
    return result;
  }
  
  /**添加一个会议室
   *@param building 所在楼
   *@param room 房间号
   **@param capacity 容量
   *@param remark 备注
   *◎return 添加成功flag=0,不成功flag=-1
   */
  public int meetingRoomAdd(String building,String room,String capacity,String remark) {
    int flag = 0;
    String sql = "";
    int id = 0;
    OracleConnection oraconn = new OracleConnection();    
    StringBuffer strBuff = new StringBuffer(); //用于保存查询语句
    // 创建一个字段数组
    String[] ColNames = {"id","building", "room", "capacity", "remark","deleted"};        
    // 建立一个字段值数组
    String[] ColValues = new String[ColNames.length];
    ColValues[0] ="seq_MEETINGROOM_id.nextval"; 
    ColValues[1] = building;
    ColValues[2] = room;
    ColValues[3] = capacity;
    ColValues[4] = remark;
    ColValues[5] = "0";
    // 生成一条sql语句,用于查询是否有相同的记录,防止页面刷新时自动添加记录
    strBuff.append("select count(*) count from meetingRoom ");
    strBuff.append("where building='");
    strBuff.append(ColValues[1]);
    strBuff.append("' and room='");
    strBuff.append(ColValues[2]);
    strBuff.append("' and capacity='");
    strBuff.append(ColValues[3]); 
    strBuff.append("' and deleted='");
    strBuff.append(ColValues[5]);    
    strBuff.append("'");
    String sql2 = strBuff.toString();
    // 执得查询语句
    String cut = oraconn.getSnglRowSnglCol(sql2, "count");  
    if(cut.trim().equals("0") || cut==null){
      // 得到insert语句
    sql = oraconn.genInsStmt("meetingRoom", ColNames, ColValues);
    // 如果查询的结果为0,表示没有相同的记录,就执行sql语句进得插入一条记录
      flag = oraconn.update(sql); 
    }else{
      flag = -999;
    }
    return flag;
  }  

  /**
  * 会议室修改
  * @param  Id:会议室id
  * @param  remark:会议室说明
  * @return 返回是否删除成功
  */
  public int meetingRoomModify(String Id,String remark){
    int flag = 0;
    int tembox = 0; // 定义一整型变量，把得到的Id转化为整型
    StringBuffer sb = new StringBuffer();
    String sql = null;  
    SqlGenerator sg = new SqlGenerator();    
    tembox = Integer.parseInt(Id.toString());
    sb.append("update meetingroom set remark='");
    sb.append(remark);
    sb.append("' where id=");
    sb.append(tembox);
    sql = sb.toString();
    flag = sg.update(sql);
    return flag;
  }


/**
  * 会议室删除
  * @param  Id:会议室id
  * @return 返回是否删除成功
  */
  public int meetingRoomDelete(String[] Id){
    int flag = 0;
    int tembox = 0; // 定义一整型变量，把得到的Id转化为整型
    // 定义一删除Sql语句数组，长度为Id的长度
    String[] sqlDel = new String[Id.length];
    SqlGenerator sg = new SqlGenerator();
    // 生成Sql语句数组
    for(int j=0;j<Id.length;j++){  
      tembox = Integer.parseInt(Id[j].toString());
      sqlDel[j] = "update meetingroom set deleted='1' where id=" + tembox;
    }
    flag = sg.update(sqlDel);
    return flag;
  }

}
