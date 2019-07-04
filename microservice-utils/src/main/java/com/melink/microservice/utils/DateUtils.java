package com.melink.microservice.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	private static DateUtils instance = new DateUtils();

	// Empty private constructor
	private DateUtils() {
	}

	public static DateUtils getInstance() {
		return instance;
	}

	public static String getDate(Date now, int step) {
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.add(Calendar.DAY_OF_MONTH, step);
		Date theDate = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(theDate);
	}

	public static String getDate(Date now) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(now);
	}

	public static String getDate(Date now,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(now);
	}

	public static Date addDate(Date startDate, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.DATE, day);// 30为增加的天数，可以改变的
		return calendar.getTime();
	}

	public static String getCron(Date date) {
		String dateFormat = "ss mm HH dd MM ? yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String formatTimeStr = null;
		if (date != null) {
			formatTimeStr = sdf.format(date);
		}
		return formatTimeStr;
	}

	public static void main(String args[]) {
		System.out.println(getDate(new Date(), -1));
	}

    public static Date stringToDate(String dateStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
  			return null;
		}
		return date;
	}

    public static boolean isBetween(Date nowTime, Date beginTime,Date endTime) {
		if(nowTime == null || beginTime == null || endTime == null){
			return false;
		}
		if (nowTime.getTime() == beginTime.getTime()
				|| nowTime.getTime() == endTime.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
    }
}
