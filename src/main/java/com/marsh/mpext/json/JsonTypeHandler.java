package com.marsh.mpext.json;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.marsh.mpext.date.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Slf4j
public class JsonTypeHandler<T extends Object> extends AbstractJsonTypeHandler<T> {


    private static final PGobject PG_JSON = new PGobject();

    private static final String PG_JDBC_JSONB = "jsonb";

    private final Class<T> type;

    public JsonTypeHandler(Class<T> type) {
        if (log.isTraceEnabled()) {
            log.trace("JacksonTypeHandler(" + type + ")");
        }
        Assert.notNull(type, "Type argument cannot be null");
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        PG_JSON.setType(PG_JDBC_JSONB);
        PG_JSON.setValue(toJson(parameter));
        ps.setObject(i, PG_JSON);
    }


    @SneakyThrows
    @Override
    protected T parse(String json) {
        var mapper = mapper();
        return mapper.readValue(json, type);
    }

    @SneakyThrows
    @Override
    protected String toJson(Object obj) {
        var mapper = mapper();
        return mapper.writeValueAsString(obj);
    }

    public ObjectMapper mapper() {
        var mapper = new ObjectMapper();
        var module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addSerializer(LocalTime.class, new LocalTimeSerializer());
        mapper.registerModule(module);
        return mapper;
    }

}
