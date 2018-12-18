package com.future.study.sharding.sphere;

import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ComponentScan(value={"com.future.study.sharding.sphere"})
@EnableJpaRepositories(basePackages={"com.future.study.sharding.sphere"})
public class Config {
    @Bean
    public DataSource dataSource() throws SQLException{
    	// 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        
        // 配置第一个数据源
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.1.153:3306/backend");
        dataSource.setUsername("root");
        dataSource.setPassword("Root@123");
        dataSourceMap.put("ds0", dataSource);
        
        // 配置第二个数据源
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.1.154:3306/backend");
        dataSource.setUsername("root");
        dataSource.setPassword("Root@123");
        dataSourceMap.put("ds1", dataSource);
        
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.1.155:3306/backend");
        dataSource.setUsername("root");
        dataSource.setPassword("Root@123");
        dataSourceMap.put("ds2", dataSource);
        
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.1.156:3306/backend");
        dataSource.setUsername("root");
        dataSource.setPassword("Root@123");
        dataSourceMap.put("ds3", dataSource);
        
        // 配置Order表规则
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
        orderTableRuleConfig.setLogicTable("t_user");
        orderTableRuleConfig.setActualDataNodes("ds${0..3}.t_user");
        
        // 配置分库 + 分表策略
        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "ds${id % 4}"));
        
        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);
        
        // 获取数据源对象
        DataSource dataSourceReturn = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap(), new Properties());
        return dataSourceReturn;
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
        factory.setPackagesToScan("com.future.study.sharding.sphere");
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
