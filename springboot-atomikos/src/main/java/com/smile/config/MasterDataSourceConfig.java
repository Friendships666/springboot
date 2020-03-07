package com.smile.config;

import com.mysql.cj.jdbc.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionManager;
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
@MapperScan(basePackages="com.smile.mapper.master", sqlSessionFactoryRef="masterSqlSessionFactory")
public class MasterDataSourceConfig {

    private static final String MASTER_MAPPER_LOCATION = "classpath:mapping/master/*.xml";

//    @Bean("masterDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.master")
//    @Primary
//    public DataSource masterDataSource(){
//        return DataSourceBuilder.create().build();
//    }

    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterDataSource(MasterDataSource masterDataSource){
        MysqlXADataSource dataSource = new MysqlXADataSource();
        dataSource.setUrl(masterDataSource.getJdbcUrl());
        dataSource.setUser(masterDataSource.getUserName());
        dataSource.setPassword(masterDataSource.getPassword());

        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        bean.setXaDataSource(dataSource);
        bean.setUniqueResourceName("masterDataSource");
        return bean;
    }



    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(masterDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MASTER_MAPPER_LOCATION));
        return bean.getObject();
    }


//    @Bean("masterDataSourceTransactionManager")
//    @Primary
//    public DataSourceTransactionManager masterDataSourceTransactionManager(@Qualifier("masterDataSource") DataSource masterDataSource){
//        return new DataSourceTransactionManager(masterDataSource);
//    }


    @Bean(name = "masterSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory masterSqlSessionFactory){
        return new SqlSessionTemplate(masterSqlSessionFactory);
    }

}
