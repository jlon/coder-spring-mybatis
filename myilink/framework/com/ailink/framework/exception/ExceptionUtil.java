package com.ailink.framework.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 获取异常信息
 * 
 * @author jlon
 *
 */
public class ExceptionUtil {

	private ExceptionUtil() {

	}

	// 方法一：
	public static String getExceptionAllinformation(Exception ex) {
		String sOut = "";
		StackTraceElement[] trace = ex.getStackTrace();
		for (StackTraceElement s : trace) {
			sOut += "\tat " + s + "\r\n";
		}
		return sOut;
	}

	// 方法二：
	public static String getExceptionAllinformation_01(Exception ex) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pout = new PrintStream(out);
		ex.printStackTrace(pout);
		String ret = new String(out.toByteArray());
		pout.close();
		try {
			out.close();
		} catch (Exception e) {
		}
		return ret;
	}

	// 方法三：
	public static String toString_02(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
}
