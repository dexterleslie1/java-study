package com.future.study.jmeter.java.request.plugin;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 专门测试jdbc读性能
 * 01:27 14165/sec mariadb galera集群3个节点
 * 02:19 8690/sec mariadb galera集群2个节点
 * 03:38 5550/sec mariadb galera集群1个节点
 * 03:06 6439/sec mariadb单机版
 * @author dexterleslie@gmail.com
 */
public class JDBCReadTester {
    private final static String SQL = "select * from t_user where id=?";
    private Connection connection = null;
    private Random random = new Random();
    private int totalCount = 0;

    public void setup() throws SQLException, ClassNotFoundException {
        String driver = "com.mysql.jdbc.Driver";
//        String url = "jdbc:mysql://192.168.1.153:8066/backend";
//        String url = "jdbc:mysql://192.168.1.151:3306/backend";
        String url = "jdbc:mysql:loadbalance://192.168.1.151:3306,192.168.1.152:3306,192.168.1.153:3306,192.168.1.154:3306/backend";
        String username = "root";
        String password = "Root@123";
        Class.forName(driver); //classLoader,加载对应驱动
        connection = DriverManager.getConnection(url, username, password);

        String sql = "select max(id) from t_user";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                totalCount = resultSet.getInt(1);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            if(resultSet != null){
                resultSet.close();
                resultSet = null;
            }
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                    preparedStatement = null;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        totalCount = totalCount + 1;
    }

    public void doRead() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            int id = random.nextInt(totalCount);
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Date date = resultSet.getDate("createTime");
                String loginname = resultSet.getString("loginname");
                String nickname = resultSet.getString("nickname");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                int sex = resultSet.getInt("sex");
                int randomInt = resultSet.getInt("random");
            }
            resultSet.close();
            resultSet = null;
            preparedStatement.close();
            preparedStatement = null;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            if(resultSet != null){
                resultSet.close();
                resultSet = null;
            }
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
