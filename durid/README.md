### 主要目的
1. 搭建简单的durid数据源项目，可以启动监控页面
2. 研究数据库各项性能指标的采集原理
3. 研究多数据源配置
4. 研究连接池缓存原理

### 其他备注
1. web监控页面地址: `http://127.0.0.1:8888/druid/index.html`
2. api方式获取所有数据源监控数据: `DruidStatManagerFacade.getInstance().getDataSourceStatDataList()`