package com.marsh.mpext.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;


@Slf4j
@MappedJdbcTypes(JdbcType.ARRAY)
@MappedTypes(String[].class)
public class StringArrayTypeHandler extends BaseTypeHandler<String[]> {

    private final static String PG_JDBC_TEXT = "text";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
        Connection connection = ps.getConnection();
        Array array = connection.createArrayOf(PG_JDBC_TEXT, parameter);
        ps.setArray(i, array);
        array.free();
    }

    @Override
    public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getArray(rs.getArray(columnName));
    }

    @Override
    public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getArray(rs.getArray(columnIndex));
    }

    @Override
    public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getArray(cs.getArray(columnIndex));
    }

    private String[] getArray(Array array) {
        if (array == null) {
            return null;
        }
        try {
            return (String[]) array.getArray();
        } catch (Exception e) {
            log.error("StringArrayTypeHandler: cast array to String[] exception");
        }
        return null;
    }

}
