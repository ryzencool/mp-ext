package com.marsh.mpext.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

@Slf4j
@MappedJdbcTypes(JdbcType.ARRAY)
@MappedTypes(Integer[].class)
public class IntArrayTypeHandler extends BaseTypeHandler<Integer[]> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Integer[] parameter, JdbcType jdbcType) throws SQLException {
        Connection connection = ps.getConnection();
        Array array = connection.createArrayOf("integer", parameter);
        ps.setArray(i, array);
        array.free();
    }

    @Override
    public Integer[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getArray(rs.getArray(columnName));
    }

    @Override
    public Integer[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getArray(rs.getArray(columnIndex));
    }

    @Override
    public Integer[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getArray(cs.getArray(columnIndex));
    }

    private Integer[] getArray(Array array) {
        if (array == null) {
            return null;
        }
        try {
            return (Integer[]) array.getArray();
        } catch (Exception e) {
            log.error("IntArrayTypeHandler: cast array to Integer[] exception");
        }
        return null;
    }


}
