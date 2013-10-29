package com.imc_cg.meeting.bean.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTools {

	public static int daysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
	
	public static int miliSecondsBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1);

		return Integer.parseInt(String.valueOf(between_days));
	}
	
	@SuppressWarnings("deprecation")
	public static long triggerInAdvance(int timeToTrigger){
		long rt = 0;
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), timeToTrigger, 0);
			rt = cal.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
		  return rt%86400000;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static String addDays(SimpleDateFormat sdf, String date, String days) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(date.substring(0,16)));
		cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(days.trim()));

		return sdf.format(cal.getTime());
	}

	public static boolean isMeetingTrigger(String startTime,
			String daysBeforeTrigger, String scheduledPeriod, SimpleDateFormat sdf) {

		try {
			Date startDate = sdf.parse(startTime.substring(0,16));
			int daysBeforeTriggerDate = Integer.parseInt(daysBeforeTrigger.trim());
			int period = Integer.parseInt(scheduledPeriod.trim());
			
			int interval = daysBetween(startDate, new Date())+daysBeforeTriggerDate;
			
			if(period<=0 || interval<0 || daysBeforeTriggerDate<0){
				return false;
			}else if(interval%period==0){
				return true;
			}else{
				return false;
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
			return false;
		} catch (NumberFormatException e2){
			e2.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args){
		System.out.println(DateTools.triggerInAdvance(9));
	}
}
