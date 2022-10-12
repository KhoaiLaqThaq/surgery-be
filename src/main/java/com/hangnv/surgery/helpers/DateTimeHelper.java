package com.hangnv.surgery.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hangnv.surgery.constant.DatePatternEnum;
import com.hangnv.surgery.constant.SymbolEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateTimeHelper {
	private DateTimeHelper() {
		throw new IllegalStateException("Date Utility class");
	}
	
	public static List<LocalDate> getDatePeriodFromString(String dateTimeString){
		List<LocalDate> dateTime = new ArrayList<>();
		if(StringUtils.isNotEmpty(dateTimeString)) {
        	DateTimeFormatter pattern = DateTimeFormatter.ofPattern(DatePatternEnum.MM_DD_YYYY_1.pattern);
        	String[] dates = dateTimeString.trim().split("-");     
        		try {
        			LocalDate startDate = LocalDate.parse(dates[0].trim() ,pattern);
    				LocalDate endDate = LocalDate.parse(dates[1].trim() ,pattern);	
    				if(startDate.isBefore(endDate)) {
    					dateTime.add(startDate);
    					dateTime.add(endDate);
    				}else {
    					dateTime.add(endDate);
    					dateTime.add(startDate);
    				}
				} catch (DateTimeParseException e) {
					log.error("Parsing date error: {}", e);
				}		
		}
		return dateTime ;
	}
	
	public static long getMillis() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis();
	}

	//----- LocalDate -----//
	public static LocalDate toLocalDate(String date, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(date, formatter);
	}

	public static String toDateString(LocalDate date, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return date.format(formatter);
	}
	
	public static LocalDate convertToLocalDate(String date, String pattern) {
		String dateStr = date.substring(0, 10);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(dateStr, formatter);
	}
	
	public static LocalDate stringToLocalDate(String date, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(date, formatter);
	}
	//----- LocalDateTime -----//
	public static LocalDateTime toLocalDateTime(String datetime, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(datetime, formatter);
	}

	public static String toDateTimeString(LocalDateTime datetime, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return datetime.format(formatter);
	}

	public static LocalDateTime toLocalDateTimeZone(String dateWithTimeZone) {
		return toLocalDateTime(dateWithTimeZone, DatePatternEnum.YYYY_MM_DD_T_HH_MM_SS_SSSSSSXXX.pattern);
	}
	
	public static LocalDateTime convertToLocalDateTime(String datetime, String pattern) {
		String dateStr = datetime.substring(0, 19);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(dateStr, formatter);
	}
	//----- Convert pattern ------//
	public static String convertParternDisplay(String date) {
		//date theo dinh dang yyyy-mm-dd -> dd.mm.yyyy
		StringBuilder builder = new StringBuilder();
		String[] arr = StringUtils.split(date, SymbolEnum.HYPHEN.val);
		if(arr.length >= 3) {
			builder.append(arr[2]).append(SymbolEnum.DOT.val).append(arr[1]).append(SymbolEnum.DOT.val).append(arr[0]);
		}
		return builder.toString();
	}
	
	public static String convertParternSave(String date) {
		//date theo dinh dang dd.mm.yyyy -> yyyy-mm-dd
		StringBuilder builder = new StringBuilder();
		String[] arr = StringUtils.split(date, SymbolEnum.DOT.val);
		if(arr.length >= 3) {
			builder.append(arr[2]).append(SymbolEnum.HYPHEN.val).append(arr[1]).append(SymbolEnum.HYPHEN.val).append(arr[0]);
		}
		return builder.toString();
	}
	
	public static String convertParternSendToSAP(String date) {
		//date theo dinh dang yyyy-mm-dd -> yyyymmdd
		String str = date.replaceAll(SymbolEnum.HYPHEN.val, "");
		return str;
	}
	
	public static LocalDate convertDateToReportPR(String date, String pattern) {
		// MM/dd/yyyy -> yyyy-MM-dd
		StringBuilder builder = new StringBuilder();
		String[] arr = StringUtils.split(date, SymbolEnum.SLASH.val);
		if (arr.length >= 3) {
			builder.append(arr[2]).append(SymbolEnum.HYPHEN.val).append(arr[0]).append(SymbolEnum.HYPHEN.val).append(arr[1]);
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(builder.toString(), formatter);
	}
	
	public static LocalDateTime convertDateToStartInDate(String date, String pattern) {
		// yyyy-MM-dd -> yyyy-MM-dd 00:00:00
		StringBuilder builder = new StringBuilder();
		builder.append(date).append(DatePatternEnum.DD_MM_YYYY__00_00_00.pattern);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(builder.toString(), formatter);
	}
	
	public static LocalDateTime convertDateToEndInDay(String date, String pattern) {
		// MM/dd/yyyy -> yyyy-MM-dd
		StringBuilder builder = new StringBuilder();
		String[] arr = StringUtils.split(date, SymbolEnum.SLASH.val);
		if (arr.length >= 3) {
			builder.append(arr[2]).append(SymbolEnum.HYPHEN.val).append(arr[0]).append(SymbolEnum.HYPHEN.val).append(arr[1]).append(DatePatternEnum.DD_MM_YYYY__23_59_59.pattern);
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(builder.toString(), formatter);
	}
	
	public static LocalDateTime convertDateToStartInDay(String date, String pattern) {
		// MM/dd/yyyy -> yyyy-MM-dd
		StringBuilder builder = new StringBuilder();
		String[] arr = StringUtils.split(date, SymbolEnum.SLASH.val);
		if (arr.length >= 3) {
			builder.append(arr[2]).append(SymbolEnum.HYPHEN.val).append(arr[0]).append(SymbolEnum.HYPHEN.val).append(arr[1]).append(DatePatternEnum.DD_MM_YYYY__00_00_00.pattern);
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(builder.toString(), formatter);
	}
	
	public static List<LocalDate> convertPeriodLocalDate() {
		List<LocalDate> localDates = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePatternEnum.YYYY_MM_DD.pattern);
		LocalDate daysInPeriod = LocalDate.now();
		ValueRange range = daysInPeriod.range(ChronoField.DAY_OF_MONTH);
		String localDateStr = DateTimeHelper.toDateString(daysInPeriod, DatePatternEnum.YYYY_MM_DD.pattern);
		String[] periodArr = localDateStr.split("-");
		String fromDate = (new StringBuilder()).append(periodArr[0]).append(SymbolEnum.HYPHEN.val).append(periodArr[1]).append(SymbolEnum.HYPHEN.val).append("01").toString();
		String toDate = (new StringBuilder()).append(periodArr[0]).append(SymbolEnum.HYPHEN.val).append(periodArr[1]).append(SymbolEnum.HYPHEN.val).append(range.getMaximum()).toString();
		localDates.add(LocalDate.parse(fromDate, formatter));
		localDates.add(LocalDate.parse(toDate, formatter));
		return localDates;
	}
}
