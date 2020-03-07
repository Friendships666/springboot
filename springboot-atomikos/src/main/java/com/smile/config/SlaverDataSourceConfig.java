package com.smile.config;

import com.mysql.cj.jdbc.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @作者 liutao
 * @时间 2020/3/5 21:54
 * @描述 主数据库的配置
 */
@Configuration
@MapperScan(basePackages="com.smile.mapper.slaver",sqlSessionFactoryRef="slaverSqlSessionFactory")
public class SlaverDataSourceConfig {

    private static final String SLAVER_MAPPER_LOCATION = "classpath:mapping/slaver/*.xml";

//    @Bean("slaverDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.slaver")
//    public DataSource slaverDataSource(){
//        return DataSourceBuilder.create().build();
//    }

    @Bean(name = "slaverDataSource")
    public DataSource slaverDataSource(SlaverDataSource slaverDataSource){
        MysqlXADataSource dataSource = new MysqlXADataSource();
        dataSource.setUrl(slaverDataSource.getJdbcUrl());
        dataSource.setUser(slaverDataSource.getUserName());
        dataSource.setPassword(slaverDataSource.getPassword());

        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        bean.setXaDataSource(dataSource);
        bean.setUniqueResourceName("slaverDataSource");
        return bean;
    }

    @Bean(name = "slaverSqlSessionFactory")
    public SqlSessionFactory slaverSqlSessionFactory(@Qualifier("slaverDataSource") DataSource slaverDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(slaverDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(SLAVER_MAPPER_LOCATION));
        return bean.getObject();
    }


//    @Bean("slaverDataSourceTransactionManager")
//    public DataSourceTransactionManager slaverDataSourceTransactionManager(@Qualifier("slaverDataSource") DataSource slaverDataSource){
//        return new DataSourceTransactionManager(slaverDataSource);
//    }


    @Bean(name = "slaverSqlSessionTemplate")
    public SqlSessionTemplate slaverSqlSessionTemplate(@Qualifier("slaverSqlSessionFactory") SqlSessionFactory slaverSqlSessionFactory){
        return new SqlSessionTemplate(slaverSqlSessionFactory);
    }

}
