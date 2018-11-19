package com.future.study.spring.large.scale.solution;

import com.alibaba.druid.pool.DruidDataSource;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@ComponentScan(value={"com.future.study.spring.large"})
@PropertySource("classpath:config.properties")
@EnableJpaRepositories(basePackages={"com.future.study.spring.large"})
public class Config {
    @Value("${mysql.driver}")
    private String driver;
    @Value("${mysql.url}")
    private String url;
    @Value("${mysql.user}")
    private String user;
    @Value("${mysql.password}")
    private String password;

    /**
     * 配置数据源
     * @return
     */
    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * 执行初始化数据库脚本
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceInitializer dataSourceInitializer(
            DataSource dataSource){
        DatabaseSQLFileList sqlFileList=new LargeScaleDatabaseSQLFileList();
        DataSourceInitializer initializer=new DataSourceInitializerWrapper(dataSource,sqlFileList);
        return initializer;
    }

    /**
     * 配置spring jpg
     * @param dataSource
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPackagesToScan("com.future.study.spring.large");
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setDataSource(dataSource);
        return factory;
    }

    /**
     * 配置spring jpa
     * @param entityManagerFactory
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}
