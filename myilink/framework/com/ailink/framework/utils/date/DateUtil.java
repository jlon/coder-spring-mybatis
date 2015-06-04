package com.ailink.framework.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author jlon
 *
 */
public class DateUtil {
	private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();

	private static final Object object = new Object();

	/**
	 * 获取SimpleDateFormat
	 * 
	 * @param pattern
	 *            日期格式
	 * @return SimpleDateFormat对象
	 * @throws RuntimeException
	 *             异常：非法日期格式
	 */
	private static SimpleDateFormat getDateFormat(String pattern)
			throws RuntimeException {
		SimpleDateFormat dateFormat = threadLocal.get();
		if (dateFormat == null) {
			synchronized (object) {
				if (dateFormat == null) {
					dateFormat = new SimpleDateFormat(pattern);
					dateFormat.setLenient(false);
					threadLocal.set(dateFormat);
				}
			}
		}
		dateFormat.applyPattern(pattern);
		return dateFormat;
	}

	/**
	 * 获取日期中的某数值。如获取月份
	 * 
	 * @param date
	 *            日期
	 * @param dateType
	 *            日期格式
	 * @return 数值
	 */
	private static int getInteger(Date date, int dateType) {
		int num = 0;
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
			num = calendar.get(dateType);
		}
		return num;
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 * 
	 * @param date
	 *            日期字符串
	 * @param dateType
	 *            类型
	 * @param amount
	 *            数值
	 * @return 计算后日期字符串
	 */
	private static String addInteger(String date, int dateType, int amount) {
		String dateString = null;
		DateStyle dateStyle = getDateStyle(date);
		if (dateStyle != null) {
			Date myDate = StringToDate(date, dateStyle);
			myDate = addInteger(myDate, dateType, amount);
			dateString = DateToString(myDate, dateStyle);
		}
		return dateString;
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 * 
	 * @param date
	 *            日期
	 * @param dateType
	 *            类型
	 * @param amount
	 *            数值
	 * @return 计算后日期
	 */
	private static Date addInteger(Date date, int dateType, int amount) {
		Date myDate = null;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(dateType, amount);
			myDate = calendar.getTime();
		}
		return myDate;
	}

	/**
	 * 获取精确的日期
	 * 
	 * @param timestamps
	 *            时间long集合
	 * @return 日期
	 */
	private static Date getAccurateDate(List<Long> timestamps) {
		Date date = null;
		long timestamp = 0;
		Map<Long, long[]> map = new HashMap<Long, long[]>();
		List<Long> absoluteValues = new ArrayList<Long>();

		if (timestamps != null && timestamps.size() > 0) {
			if (timestamps.size() > 1) {
				for (int i = 0; i < timestamps.size(); i++) {
					for (int j = i + 1; j < timestamps.size(); j++) {
						long absoluteValue = Math.abs(timestamps.get(i)
								- timestamps.get(j));
						absoluteValues.add(absoluteValue);
						long[] timestampTmp = { timestamps.get(i),
								timestamps.get(j) };
						map.put(absoluteValue, timestampTmp);
					}
				}

				// 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的。此时minAbsoluteValue为0
				// 因此不能将minAbsoluteValue取默认值0
				long minAbsoluteValue = -1;
				if (!absoluteValues.isEmpty()) {
					minAbsoluteValue = absoluteValues.get(0);
					for (int i = 1; i < absoluteValues.size(); i++) {
						if (minAbsoluteValue > absoluteValues.get(i)) {
							minAbsoluteValue = absoluteValues.get(i);
						}
					}
				}

				if (minAbsoluteValue != -1) {
					long[] timestampsLastTmp = map.get(minAbsoluteValue);

					long dateOne = timestampsLastTmp[0];
					long dateTwo = timestampsLastTmp[1];
					if (absoluteValues.size() > 1) {
						timestamp = Math.abs(dateOne) > Math.abs(dateTwo) ? dateOne
								: dateTwo;
					}
				}
			} else {
				timestamp = timestamps.get(0);
			}
		}

		if (timestamp != 0) {
			date = new Date(timestamp);
		}
		return date;
	}

	/**
	 * 判断字符串是否为日期字符串
	 * 
	 * @param date
	 *            日期字符串
	 * @return true or false
	 */
	public static boolean isDate(String date) {
		boolean isDate = false;
		if (date != null) {
			if (getDateStyle(date) != null) {
				isDate = true;
			}
		}
		return isDate;
	}

	/**
	 * 获取日期字符串的日期风格。失敗返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 日期风格
	 */
	public static DateStyle getDateStyle(String date) {
		DateStyle dateStyle = null;
		Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();
		List<Long> timestamps = new ArrayList<Long>();
		for (DateStyle style : DateStyle.values()) {
			if (style.isShowOnly()) {
				continue;
			}
			Date dateTmp = null;
			if (date != null) {
				try {
					ParsePosition pos = new ParsePosition(0);
					dateTmp = getDateFormat(style.getValue()).parse(date, pos);
					if (pos.getIndex() != date.length()) {
						dateTmp = null;
					}
				} catch (Exception e) {
				}
			}
			if (dateTmp != null) {
				timestamps.add(dateTmp.getTime());
				map.put(dateTmp.getTime(), style);
			}
		}
		Date accurateDate = getAccurateDate(timestamps);
		if (accurateDate != null) {
			dateStyle = map.get(accurateDate.getTime());
		}
		return dateStyle;
	}

	/**
	 * 将日期字符串转化为日期。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 日期
	 */
	public static Date StringToDate(String date) {
		DateStyle dateStyle = getDateStyle(date);
		return StringToDate(date, dateStyle);
	}

	/**
	 * 将日期字符串转化为日期。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return 日期
	 */
	public static Date StringToDate(String date, String pattern) {
		Date myDate = null;
		if (date != null) {
			try {
				myDate = getDateFormat(pattern).parse(date);
			} catch (Exception e) {
			}
		}
		return myDate;
	}

	/**
	 * 将日期字符串转化为日期。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param dateStyle
	 *            日期风格
	 * @return 日期
	 */
	public static Date StringToDate(String date, DateStyle dateStyle) {
		Date myDate = null;
		if (dateStyle != null) {
			myDate = StringToDate(date, dateStyle.getValue());
		}
		return myDate;
	}

	/**
	 * 将日期转化为日期字符串。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return 日期字符串
	 */
	public static String DateToString(Date date, String pattern) {
		String dateString = null;
		if (date != null) {
			try {
				dateString = getDateFormat(pattern).format(date);
			} catch (Exception e) {
			}
		}
		return dateString;
	}

	/**
	 * 将日期转化为日期字符串。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param dateStyle
	 *            日期风格
	 * @return 日期字符串
	 */
	public static String DateToString(Date date, DateStyle dateStyle) {
		String dateString = null;
		if (dateStyle != null) {
			dateString = DateToString(date, dateStyle.getValue());
		}
		return dateString;
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 * 
	 * @param date
	 *            旧日期字符串
	 * @param newPattern
	 *            新日期格式
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, String newPattern) {
		DateStyle oldDateStyle = getDateStyle(date);
		return StringToString(date, oldDateStyle, newPattern);
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 * 
	 * @param date
	 *            旧日期字符串
	 * @param newDateStyle
	 *            新日期风格
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, DateStyle newDateStyle) {
		DateStyle oldDateStyle = getDateStyle(date);
		return StringToString(date, oldDateStyle, newDateStyle);
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 * 
	 * @param date
	 *            旧日期字符串
	 * @param olddPattern
	 *            旧日期格式
	 * @param newPattern
	 *            新日期格式
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, String olddPattern,
			String newPattern) {
		return DateToString(StringToDate(date, olddPattern), newPattern);
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 * 
	 * @param date
	 *            旧日期字符串
	 * @param olddDteStyle
	 *            旧日期风格
	 * @param newParttern
	 *            新日期格式
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, DateStyle olddDteStyle,
			String newParttern) {
		String dateString = null;
		if (olddDteStyle != null) {
			dateString = StringToString(date, olddDteStyle.getValue(),
					newParttern);
		}
		return dateString;
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 * 
	 * @param date
	 *            旧日期字符串
	 * @param olddPattern
	 *            旧日期格式
	 * @param newDateStyle
	 *            新日期风格
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, String olddPattern,
			DateStyle newDateStyle) {
		String dateString = null;
		if (newDateStyle != null) {
			dateString = StringToString(date, olddPattern,
					newDateStyle.getValue());
		}
		return dateString;
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 * 
	 * @param date
	 *            旧日期字符串
	 * @param olddDteStyle
	 *            旧日期风格
	 * @param newDateStyle
	 *            新日期风格
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, DateStyle olddDteStyle,
			DateStyle newDateStyle) {
		String dateString = null;
		if (olddDteStyle != null && newDateStyle != null) {
			dateString = StringToString(date, olddDteStyle.getValue(),
					newDateStyle.getValue());
		}
		return dateString;
	}

	/**
	 * 增加日期的年份。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param yearAmount
	 *            增加数量。可为负数
	 * @return 增加年份后的日期字符串
	 */
	public static String addYear(String date, int yearAmount) {
		return addInteger(date, Calendar.YEAR, yearAmount);
	}

	/**
	 * 增加日期的年份。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param yearAmount
	 *            增加数量。可为负数
	 * @return 增加年份后的日期
	 */
	public static Date addYear(Date date, int yearAmount) {
		return addInteger(date, Calendar.YEAR, yearAmount);
	}

	/**
	 * 增加日期的月份。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param monthAmount
	 *            增加数量。可为负数
	 * @return 增加月份后的日期字符串
	 */
	public static String addMonth(String date, int monthAmount) {
		return addInteger(date, Calendar.MONTH, monthAmount);
	}

	/**
	 * 增加日期的月份。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param monthAmount
	 *            增加数量。可为负数
	 * @return 增加月份后的日期
	 */
	public static Date addMonth(Date date, int monthAmount) {
		return addInteger(date, Calendar.MONTH, monthAmount);
	}

	/**
	 * 增加日期的天数。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加天数后的日期字符串
	 */
	public static String addDay(String date, int dayAmount) {
		return addInteger(date, Calendar.DATE, dayAmount);
	}

	/**
	 * 增加日期的天数。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加天数后的日期
	 */
	public static Date addDay(Date date, int dayAmount) {
		return addInteger(date, Calendar.DATE, dayAmount);
	}

	/**
	 * 增加日期的小时。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加小时后的日期字符串
	 */
	public static String addHour(String date, int hourAmount) {
		return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	}

	/**
	 * 增加日期的小时。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加小时后的日期
	 */
	public static Date addHour(Date date, int hourAmount) {
		return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	}

	/**
	 * 增加日期的分钟。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param minuteAmount
	 *            增加数量。可为负数
	 * @return 增加分钟后的日期字符串
	 */
	public static String addMinute(String date, int minuteAmount) {
		return addInteger(date, Calendar.MINUTE, minuteAmount);
	}

	/**
	 * 增加日期的分钟。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加分钟后的日期
	 */
	public static Date addMinute(Date date, int minuteAmount) {
		return addInteger(date, Calendar.MINUTE, minuteAmount);
	}

	/**
	 * 增加日期的秒钟。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加秒钟后的日期字符串
	 */
	public static String addSecond(String date, int secondAmount) {
		return addInteger(date, Calendar.SECOND, secondAmount);
	}

	/**
	 * 增加日期的秒钟。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加秒钟后的日期
	 */
	public static Date addSecond(Date date, int secondAmount) {
		return addInteger(date, Calendar.SECOND, secondAmount);
	}

	/**
	 * 获取日期的年份。失败返回0。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 年份
	 */
	public static int getYear(String date) {
		return getYear(StringToDate(date));
	}

	/**
	 * 获取日期的年份。失败返回0。
	 * 
	 * @param date
	 *            日期
	 * @return 年份
	 */
	public static int getYear(Date date) {
		return getInteger(date, Calendar.YEAR);
	}

	/**
	 * 获取日期的月份。失败返回0。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 月份
	 */
	public static int getMonth(String date) {
		return getMonth(StringToDate(date));
	}

	/**
	 * 获取日期的月份。失败返回0。
	 * 
	 * @param date
	 *            日期
	 * @return 月份
	 */
	public static int getMonth(Date date) {
		return getInteger(date, Calendar.MONTH) + 1;
	}

	/**
	 * 获取日期的天数。失败返回0。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 天
	 */
	public static int getDay(String date) {
		return getDay(StringToDate(date));
	}

	/**
	 * 获取日期的天数。失败返回0。
	 * 
	 * @param date
	 *            日期
	 * @return 天
	 */
	public static int getDay(Date date) {
		return getInteger(date, Calendar.DATE);
	}

	/**
	 * 获取日期的小时。失败返回0。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 小时
	 */
	public static int getHour(String date) {
		return getHour(StringToDate(date));
	}

	/**
	 * 获取日期的小时。失败返回0。
	 * 
	 * @param date
	 *            日期
	 * @return 小时
	 */
	public static int getHour(Date date) {
		return getInteger(date, Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取日期的分钟。失败返回0。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 分钟
	 */
	public static int getMinute(String date) {
		return getMinute(StringToDate(date));
	}

	/**
	 * 获取日期的分钟。失败返回0。
	 * 
	 * @param date
	 *            日期
	 * @return 分钟
	 */
	public static int getMinute(Date date) {
		return getInteger(date, Calendar.MINUTE);
	}

	/**
	 * 获取日期的秒钟。失败返回0。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 秒钟
	 */
	public static int getSecond(String date) {
		return getSecond(StringToDate(date));
	}

	/**
	 * 获取日期的秒钟。失败返回0。
	 * 
	 * @param date
	 *            日期
	 * @return 秒钟
	 */
	public static int getSecond(Date date) {
		return getInteger(date, Calendar.SECOND);
	}

	/**
	 * 获取日期 。默认yyyy-MM-dd格式。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 日期
	 */
	public static String getDate(String date) {
		return StringToString(date, DateStyle.YYYY_MM_DD);
	}

	/**
	 * 获取日期。默认yyyy-MM-dd格式。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @return 日期
	 */
	public static String getDate(Date date) {
		return DateToString(date, DateStyle.YYYY_MM_DD);
	}

	/**
	 * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 时间
	 */
	public static String getTime(String date) {
		return StringToString(date, DateStyle.HH_MM_SS);
	}

	/**
	 * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @return 时间
	 */
	public static String getTime(Date date) {
		return DateToString(date, DateStyle.HH_MM_SS);
	}

	/**
	 * 获取日期的星期。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 星期
	 */
	public static Week getWeek(String date) {
		Week week = null;
		DateStyle dateStyle = getDateStyle(date);
		if (dateStyle != null) {
			Date myDate = StringToDate(date, dateStyle);
			week = getWeek(myDate);
		}
		return week;
	}

	/**
	 * 获取日期的星期。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @return 星期
	 */
	public static Week getWeek(Date date) {
		Week week = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		switch (weekNumber) {
		case 0:
			week = Week.SUNDAY;
			break;
		case 1:
			week = Week.MONDAY;
			break;
		case 2:
			week = Week.TUESDAY;
			break;
		case 3:
			week = Week.WEDNESDAY;
			break;
		case 4:
			week = Week.THURSDAY;
			break;
		case 5:
			week = Week.FRIDAY;
			break;
		case 6:
			week = Week.SATURDAY;
			break;
		}
		return week;
	}

	/**
	 * 获取两个日期相差的天数
	 * 
	 * @param date
	 *            日期字符串
	 * @param otherDate
	 *            另一个日期字符串
	 * @return 相差天数。如果失败则返回-1
	 */
	public static int getIntervalDays(String date, String otherDate) {
		return getIntervalDays(StringToDate(date), StringToDate(otherDate));
	}

	/**
	 * @param date
	 *            日期
	 * @param otherDate
	 *            另一个日期
	 * @return 相差天数。如果失败则返回-1
	 */
	public static int getIntervalDays(Date date, Date otherDate) {
		int num = -1;
		Date dateTmp = DateUtil.StringToDate(DateUtil.getDate(date),
				DateStyle.YYYY_MM_DD);
		Date otherDateTmp = DateUtil.StringToDate(DateUtil.getDate(otherDate),
				DateStyle.YYYY_MM_DD);
		if (dateTmp != null && otherDateTmp != null) {
			long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
			num = (int) (time / (24 * 60 * 60 * 1000));
		}
		return num;
	}

	/**
	 * 获取当前日期的前一天 的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getBeforeDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}

	// 获得本周星期日的日期
	public static String getCurrentWeekday(Date date) {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTime(date);
		Date monday = currentDate.getTime();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得当前日期与本周日相差的天数
	private static int getMondayPlus() {
		int a = 0;
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一
		// 作为第一
		// 天所以这里减1

		switch (dayOfWeek) {
		case 1:
			a = 6;
			break;
		case 2:
			a = 5;
			break;
		case 3:
			a = 4;
			break;
		case 4:
			a = 3;
			break;
		case 7:
			a = 0;
			break;
		default:
			break;
		}
		return a;
	}

	// 获得当前日期与本周日相差的天数
	private static int getMondayOFPlus() {
		int a = 0;
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一
		// 作为第一
		// 天所以这里减1

		switch (dayOfWeek) {
		case 1:
			a = 6;
			break;
		case 2:
			a = 5;
			break;
		case 3:
			a = 4;
			break;
		case 4:
			a = 3;
			break;
		case 7:
			a = 0;
			break;
		default:
			break;
		}
		return a;
	}

	// 获得本周一的日期
	public static String getMondayOFWeek(Date date) {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTime(date);
		currentDate.add(GregorianCalendar.DATE, mondayPlus);

		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 时间戳
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Long Date2Time(Date date) {
		String string = DateToString(date, "yyyy-MM-dd HH:mm:ss");
		date = StringToDate(string, "yyyy-MM-dd HH:mm:ss");
		return date.getTime();
	}

	/**
	 * 时间戳 转化为时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date Time2Date(Long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = format.format(time);
		Date date = null;
		try {
			date = format.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 时间戳 转化为时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date Time2Date(Long time, String dateType) {
		SimpleDateFormat format = new SimpleDateFormat(dateType);
		String d = format.format(time);
		Date date = null;
		try {
			date = format.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static void main(String[] args) {
		String f = "2015-1-1";
		String l = "2015-5-1";

		System.err.println(getIntervalDays(l, f));
		/*
		 * Calendar c_begin = new GregorianCalendar(); Calendar c_end = new
		 * GregorianCalendar(); DateFormatSymbols dfs = new DateFormatSymbols();
		 * String[] weeks = dfs.getWeekdays();
		 * 
		 * c_begin.setTime(StringToDate("2015-03-23"));
		 * c_end.setTime(StringToDate("2015-04-23"));
		 * 
		 * int count = 1; c_end.add(Calendar.DAY_OF_YEAR, 1); //
		 * 结束日期下滚一天是为了包含最后一天
		 * 
		 * while (c_begin.before(c_end)) {
		 * 
		 * System.out.println("第" + count + "周  日期：" + new
		 * java.sql.Date(c_begin.getTime().getTime()) + "," +
		 * weeks[c_begin.get(Calendar.DAY_OF_WEEK)]);
		 * 
		 * if (c_begin.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) { count++;
		 * } c_begin.add(Calendar.DAY_OF_YEAR, 1);
		 * 
		 * }
		 */
	}
	
	public static Date getTimeOf12() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, 0);
        return  cal.getTime();
    }
}