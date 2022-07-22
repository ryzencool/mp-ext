package com.marsh.mpext.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;


public class FillHandler implements MetaObjectHandler {

    public static final ThreadLocal<Integer> sessionThreadLocal = new ThreadLocal<>();

    @Override
    public void insertFill(MetaObject metaObject) {
//        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "creatorId", Integer.class,sessionThreadLocal.get() );
        this.strictInsertFill(metaObject, "updaterId", Integer.class,sessionThreadLocal.get() );
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)
    }
}
