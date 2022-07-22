package com.marsh.mpext.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class ListJsonTypeHandler extends AbstractJsonTypeHandler<List<?>> {

    private static final PGobject PG_JSON = new PGobject();

    private static final String PG_JDBC_JSONB = "jsonb";

    private final Class<?> type;



    public ListJsonTypeHandler(Class<?> type) {
        if (log.isTraceEnabled()) {
            log.trace("JacksonTypeHandler(" + type + ")");
        }
        Assert.notNull(type, "Type argument cannot be null");
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<?> parameter, JdbcType jdbcType) throws SQLException {
        PG_JSON.setType(PG_JDBC_JSONB);
        PG_JSON.setValue(toJson(parameter));
        ps.setObject(i, PG_JSON);
    }

    @Override
    protected List<?> parse(String json) {
        return JSON.parseArray(json, type);
    }

    @Override
    protected String toJson(List<?> obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty);
    }
}
