package com.marsh.mpext.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

@Slf4j
@MappedJdbcTypes(JdbcType.ARRAY)
@MappedTypes(Long[].class)
public class LongArrayTypeHandler extends BaseTypeHandler<Long[]> {

    private final static String PG_JDBC_INT8 = "int8";


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Long[] parameter, JdbcType jdbcType) throws SQLException {
        Connection connection = ps.getConnection();
        Array array = connection.createArrayOf(PG_JDBC_INT8, parameter);
        ps.setArray(i, array);
        array.free();
    }

    @Override
    public Long[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getArray(rs.getArray(columnName));
    }

    @Override
    public Long[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getArray(rs.getArray(columnIndex));
    }

    @Override
    public Long[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getArray(cs.getArray(columnIndex));
    }

    private Long[] getArray(Array array) {
        if (array == null) {
            return null;
        }
        try {
            return (Long[]) array.getArray();
        } catch (Exception e) {
            log.error("LongArrayTypeHandler: cast array to Long[] exception");
        }
        return null;
    }
}
