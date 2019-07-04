package com.melink.open.api.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.melink.open.api.mapper.melink",sqlSessionTemplateRef = "melinkSqlSessionTemplate")
public class MelinkDataSourceConfig {

    @Bean(name = "melinkDataSource")
    @ConfigurationProperties(prefix = "syh.jdbc.melink")//配置文件中的路径
    public DataSource melinkDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "melinkSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("melinkDataSource")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        bean.setTypeHandlersPackage("com.melink.open.api.model");
        bean.setConfiguration(configuration);
        return bean.getObject();
    }

    @Bean(name = "melinkSqlSessionTemplate")
    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("melinkSqlSessionFactory")SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


//    @Bean
//    public PageHelper pageHelper(){
//        PageHelper pageHelper = new PageHelper();
//        Properties p = new Properties();
//        p.setProperty("offsetAsPageNum","true");
//        p.setProperty("rowBoundsWithCount","true");
//        p.setProperty("reasonable","true");
//        pageHelper.setProperties(p);
//        return pageHelper;
//    }
}
