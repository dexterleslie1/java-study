package com.future.study.jmeter.java.request.plugin;

import com.mchange.v2.c3p0.DataSources;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dexterleslie@gmail.com
 */
public class ShardingSphereTester {
    private String host = "192.168.1.153";
    private int port = 3306;
    private DataSource dataSource;
    private final static String SQL = "insert into t_user(createTime,loginname,nickname,password,phone,email,sex)" +
            " values(?,?,?,?,?,?,?)";
    private final static List<Integer> sex = new ArrayList<Integer>();
    private Connection connection = null;
    private Random random = new Random();

    static {
		sex.add(0);
		sex.add(1);
		sex.add(2);
    }

    public void setup() throws SQLException {
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // 配置第一个数据源
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://" + host + ":" + port + "/backend");
        dataSource.setUsername("root");
        dataSource.setPassword("Root@123");
        dataSourceMap.put("ds0", dataSource);

        // 配置Order表规则
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
        orderTableRuleConfig.setLogicTable("t_user");
        orderTableRuleConfig.setActualDataNodes("ds0.t_user");

        // 配置分库 + 分表策略
        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "ds${id % 4}"));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);

        // 获取数据源对象
        this.dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap(), new Properties());
        this.connection = this.dataSource.getConnection();
    }

    public void doInsert(){
        PreparedStatement preparedStatement = null;
        try {
            int size = sex.size();
            java.sql.Date date = new java.sql.Date(new Date().getTime());
            String randomString = RandomUtils.getCharacterAndNumber(20);
            String loginname = "ln" + randomString;
            String nickname = "nn" + randomString;
            String phone = "135" + randomString;
            String email = "gm" + randomString + "@gmail.com";
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQL);
            preparedStatement.setDate(1, date);
            preparedStatement.setString(2, loginname);
            preparedStatement.setString(3, nickname);
            preparedStatement.setString(4, randomString);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, email);
            int index = random.nextInt(size);
            preparedStatement.setInt(7, sex.get(index));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement = null;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                    preparedStatement = null;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void teardown() throws SQLException {
        if(this.connection != null){
            this.connection.close();
            this.connection = null;
        }
        if(this.dataSource != null){
            DataSources.destroy(this.dataSource);
            this.dataSource = null;
        }
    }
}
