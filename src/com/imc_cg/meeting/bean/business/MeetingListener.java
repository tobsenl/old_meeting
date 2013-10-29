package com.imc_cg.meeting.bean.business;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.imc_cg.meeting.bean.util.DateTools;

public class MeetingListener implements ServletContextListener{

	private Timer timer = null;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
    timer.cancel();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		timer = new Timer(true);
		//2点触发
		int triggerHour = 2;
		long delay = DateTools.triggerInAdvance(triggerHour);
    //设置任务计划，启动和间隔时间(1天)
    timer.schedule(new RegularMeetingTask(), delay, 86400000);
	}
}
