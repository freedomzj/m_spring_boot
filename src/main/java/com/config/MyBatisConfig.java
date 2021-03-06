package com.config;

import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by zengjie on 17/6/22.
 */

//@MapperScan("com.mapper") //配置@MapperScan('package name') 配置mapper 扫描路径。这个按照我的理解就是为mapper 产生bean 放进ioc 容器内
//@Configuration  使用spring boot纯注解 不需要单独配置该bean
public class MyBatisConfig {

    private static final Logger logger = Logger.getLogger(MyBatisConfig.class);

    @Autowired(required = false)
    private JdbcConfig jdbcConfig;
    @Bean
    public DataSource createDataSource() throws SQLException {
        return DataSourceBuilder.create(Thread.currentThread().getContextClassLoader())
                .driverClassName(jdbcConfig.getDriverClass())
                .url(jdbcConfig.url)
                .username(jdbcConfig.userName)
                .password(jdbcConfig.password).build();
    }

   /* @Bean
    public PageHelper pageHelper() {
        logger.info("注册MyBatis分页插件PageHelper");
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }*/


    @PropertySource(value = "application.properties")
    @Component
    static class JdbcConfig {
        /**         * 数据库用户名         */
        @Value("${spring.datasource.username}")
        private String userName;
        /**         * 驱动名称         */
        @Value("${spring.datasource.driver-class-name}")
        private String driverClass;
        /**         * 数据库连接url         */
        @Value("${spring.datasource.url}")
        private  String url;
        /**         * 数据库密码         */
        @Value("${spring.datasource.password}")
        private String password;
        public String getUserName() {            return userName;        }
        public void setUserName(String userName) {            this.userName = userName;        }
        public String getDriverClass() {            return driverClass;        }
        public void setDriverClass(String driverClass) {            this.driverClass = driverClass;        }
        public String getUrl() {            return url;        }
        public void setUrl(String url) {            this.url = url;        }
        public String getPassword() {            return password;        }
        public void setPassword(String password) {            this.password = password;        }    }}