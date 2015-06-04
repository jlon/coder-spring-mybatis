package com.ailink.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String getResult(Date startDate,Date endDate){

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		long startT =fromDateStringToLong(dateFormat.format(startDate)); // 定义上机时间
		long endT =fromDateStringToLong(dateFormat.format(endDate)); // 定义下机时间

		long ss = (startT - endT) / (1000); // 共计秒数
		int MM = (int) ss / 60; // 共计分钟数
		int hh = (int) ss / 3600; // 共计小时数
		//int dd = (int) hh / 24; // 共计天数

		
		String result =hh + " 小时 " + MM + " 分钟"+ ss + " 秒"; //共计：" + ss * 1000 + " 毫秒";
		return result;
	}
	/**
	 * 
	 * @param inVal
	 * @return
	 */
	private static long fromDateStringToLong(String inVal) { // 此方法计算时间毫秒
		Date date = null; // 定义时间类型
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd hh:ss");
		try {
			date = inputFormat.parse(inVal); // 将字符型转换成日期型
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date.getTime(); // 返回毫秒数
	}
}
