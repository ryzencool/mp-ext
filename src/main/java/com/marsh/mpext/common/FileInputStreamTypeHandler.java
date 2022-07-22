package com.marsh.mpext.common;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(InputStream.class)
public class FileInputStreamTypeHandler extends BaseTypeHandler<InputStream> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, InputStream parameter, JdbcType jdbcType) throws SQLException {
        ps.setBinaryStream(i, parameter);
    }

    @Override
    public InputStream getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs != null) {
            byte[] imgBytes = rs.getBytes(columnName);
            return new ByteArrayInputStream(imgBytes);
        }
        return null;
    }

    @Override
    public InputStream getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs != null) {
            byte[] imgBytes = rs.getBytes(columnIndex);
            return new ByteArrayInputStream(imgBytes);
        }
        return null;
    }

    @Override
    public InputStream getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs != null) {
            byte[] imgBytes = cs.getBytes(columnIndex);
            return new ByteArrayInputStream(imgBytes);
        }
        return null;
    }
}
