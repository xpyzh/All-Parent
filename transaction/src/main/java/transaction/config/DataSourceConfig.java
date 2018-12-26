package transaction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.Driver;

import javax.sql.DataSource;

/**
 * Created by youzhihao on 2018/12/20.
 * main purpose:init h2 database and init.sql
 */
@Configuration
public class DataSourceConfig {
    //数据库路径
    final private String userName = "root";

    final private String password = "root";


    @Bean(value = "dataSource")
    public DataSource initDataSourceOrder() {
        final String url = "jdbc:h2:mem:~/order;mode=mysql;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1;";
        EmbeddedDatabase datasource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .continueOnError(true)
                .addScript("script/init.sql")
                .setSeparator(";")
                .setDataSourceFactory(new DataSourceFactory() {
                    @Override
                    public ConnectionProperties getConnectionProperties() {
                        return new ConnectionProperties() {
                            @Override
                            public void setDriverClass(Class<? extends Driver> driverClass) {
                            }

                            @Override
                            public void setUrl(String url) {
                            }

                            @Override
                            public void setUsername(String username) {
                            }

                            @Override
                            public void setPassword(String password) {
                            }
                        };
                    }

                    @Override
                    public DataSource getDataSource() {
                        return new SimpleDriverDataSource(new org.h2.Driver(), url, userName, password);
                    }
                }).build();
        return datasource;
    }
}
