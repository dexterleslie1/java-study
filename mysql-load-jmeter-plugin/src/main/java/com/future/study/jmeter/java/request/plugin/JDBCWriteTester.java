package com.future.study.jmeter.java.request.plugin;

import com.mchange.v2.c3p0.DataSources;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 专门测试jdbc写性能
 * @author dexterleslie@gmail.com
 */
public class JDBCWriteTester {
    private final static String SQL = "insert into t_user(createTime,loginname,nickname,password,phone,email,sex,random)" +
            " values(?,?,?,?,?,?,?,?)";
    private final static List<Integer> sex = new ArrayList<Integer>();
    private Connection connection = null;
    private Random random = new Random();

    static {
		sex.add(0);
		sex.add(1);
		sex.add(2);
    }

    public void setup() throws SQLException, ClassNotFoundException {
        String driver = "com.mysql.jdbc.Driver";
//        String url = "jdbc:mysql://192.168.1.153:8066/backend";
//        String url = "jdbc:mysql://192.168.1.151:3306/backend";
        String url = "jdbc:mysql:loadbalance://192.168.1.151:3306,192.168.1.152:3306,192.168.1.153:3306/backend";
        String username = "root";
        String password = "Root@123";
        Class.forName(driver); //classLoader,加载对应驱动
        connection = DriverManager.getConnection(url, username, password);
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
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setDate(1, date);
            preparedStatement.setString(2, loginname);
            preparedStatement.setString(3, nickname);
            preparedStatement.setString(4, randomString);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, email);
            int index = random.nextInt(size);
            preparedStatement.setInt(7, sex.get(index));
            int randonInt = random.nextInt(Integer.MAX_VALUE);
            preparedStatement.setInt(8, randonInt);
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
        if(connection != null){
            connection.close();
            connection = null;
        }
    }
}
