database.createOrAlter("system_info")
    .addColumn().name("org_id").alias("orgId").varchar(32).notNull().comment("机构id").commit()
    .addColumn().name("device_id").alias("deviceId").varchar(128).notNull().comment("设备ID").commit()
    .addColumn().name("install_time").alias("installTime").datetime().comment("安装时间").commit()
    .comment("系统信息")
    .commit();
