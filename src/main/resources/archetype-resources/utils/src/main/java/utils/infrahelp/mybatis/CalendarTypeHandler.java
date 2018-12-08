package com.github.chenjianjx.srb4jfullsample.utils.infrahelp.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class CalendarTypeHandler extends BaseTypeHandler<Calendar> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			Calendar parameter, JdbcType jdbcType) throws SQLException {
		ps.setTimestamp(i, calendarToTimestamp(parameter));
	}

	@Override
	public Calendar getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		Timestamp sqlTimestamp = rs.getTimestamp(columnName);
		return timestampToCalendar(sqlTimestamp);
	}

	@Override
	public Calendar getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		Timestamp sqlTimestamp = rs.getTimestamp(columnIndex);
		return timestampToCalendar(sqlTimestamp);
	}

	@Override
	public Calendar getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Timestamp sqlTimestamp = cs.getTimestamp(columnIndex);
		return timestampToCalendar(sqlTimestamp);
	}

	private Calendar timestampToCalendar(Timestamp ts) {
		if (ts == null) {
			return null;
		}
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(ts);
		return calendar;
	}

	private Timestamp calendarToTimestamp(Calendar c) {
		if (c == null) {
			return null;
		}
		Timestamp ts = new Timestamp(c.getTimeInMillis());
		return ts;

	}
}
