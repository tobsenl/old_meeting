package com.imc_cg.meeting.bean.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import com.imc_cg.meeting.bean.data.SqlGenerator;
import com.imc_cg.meeting.bean.util.DateTools;

public class RegularMeetingTask extends TimerTask {
	
	private RegularMeetingTemplate regularMeetingTemplate = new RegularMeetingTemplate();
	private Meeting meeting = new Meeting();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private SqlGenerator sg = new SqlGenerator();
	
	private String[] ids;
	private String[] template;

	public void run() {
		ids = regularMeetingTemplate.getIds();
		for(int i=0;i<ids.length;i++){
			template = regularMeetingTemplate.getRegularMeetingTemplateById(ids[i]);
			
			if("1".equals(template[20])){
				if(DateTools.isMeetingTrigger(template[4], template[18], template[17], sdf)){
					//去除多余的字符
					template[4] = template[4].substring(0,16);
					template[5] = template[5].substring(0,16);
					template[10] = template[10].substring(0,16);
					//记录最后触发时间
					template[19] = sdf.format(new Date()).substring(0,16);
					regularMeetingTemplate.modifyRegularMeetingTemplate(template);
					
					try {
						template[4] = DateTools.addDays(sdf, template[4], ""+(DateTools.daysBetween(sdf.parse(template[4]), new Date())+Integer.parseInt(template[18])));
						template[5] = DateTools.addDays(sdf, template[5], ""+(DateTools.daysBetween(sdf.parse(template[5]), new Date())+Integer.parseInt(template[18])));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (NumberFormatException e2){
						e2.printStackTrace();
					}
					//插入触发的例会
					meeting.addMeetingFromTemplate(template, sdf, sg);
					System.out.println("自动触发例会id:"+template[0]+",自"+template[4]+"至"+template[5]);
				}
			}
		}
  }
	
	public void triggerSingle(String templateId){
		template = regularMeetingTemplate.getRegularMeetingTemplateById(templateId);
		String date = sdf.format(new Date()).substring(0,16);
		try {
			template[4] = DateTools.addDays(sdf, date, "7");
			template[5] = DateTools.addDays(sdf, date, "7");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NumberFormatException e2){
			e2.printStackTrace();
		}
		meeting.addMeetingFromTemplate(template, sdf, sg);
	}
}
