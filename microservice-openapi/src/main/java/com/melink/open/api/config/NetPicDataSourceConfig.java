package com.melink.open.api.config;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "com.melink.open.api.mapper.netPicMapper",sqlSessionTemplateRef = "netpicSqlSessionTemplate")
public class NetPicDataSourceConfig {

    @Bean(name = "netpicDataSource")
    @ConfigurationProperties(prefix = "syh.jdbc.net-pic")//配置文件中的路径
    @Primary
    public DataSource netpicDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "netpicSqlSessionFactory")
    @Primary
    public SqlSessionFactory netpicSqlSessionFactory(@Qualifier("netpicDataSource")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(configuration);
        bean.setTypeHandlersPackage("com.melink.open.api.model");
        return bean.getObject();
    }

    @Bean(name = "netpicSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("netpicSqlSessionFactory")SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
