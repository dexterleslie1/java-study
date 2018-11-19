package com.future.study.spring.large.scale.solution;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * 对spring DataSourceInitializer再封装
 * 启动spring时，执行数据库初始化sql脚本，关闭spring时，执行数据库清除sql脚本
 * @author Dexterleslie
 * @date 2018年7月27日
 * @time 下午3:45:54
 */
public class DataSourceInitializerWrapper extends DataSourceInitializer{
	/**
	 * 
	 * @param dataSource
	 * @param databaseSQLFileList
	 */
	public DataSourceInitializerWrapper(DataSource dataSource,DatabaseSQLFileList databaseSQLFileList){
		this.setDataSource(dataSource);
		
		// 初始化数据库DatabasePopulator
		ResourceDatabasePopulator databasePopulator=new ResourceDatabasePopulator();
		if(databaseSQLFileList!=null){
			List<String> sqlFiles=databaseSQLFileList.getDatabasePopulatorFiles();
			if(sqlFiles!=null&&sqlFiles.size()!=0){
				for(String filePath:sqlFiles){
					ClassPathResource resource=new ClassPathResource(filePath);
					databasePopulator.addScript(resource);
				}
			}
		}
		databasePopulator.setSeparator("$$");
		this.setDatabasePopulator(databasePopulator);
		
		// 清除数据库DatabasePopulator
		ResourceDatabasePopulator databaseCleaner=new ResourceDatabasePopulator();
		if(databaseSQLFileList!=null){
			List<String> sqlFiles=databaseSQLFileList.getDatabaseCleanerFiles();
			if(sqlFiles!=null&&sqlFiles.size()!=0){
				for(String filePath:sqlFiles){
					ClassPathResource resource=new ClassPathResource(filePath);
					databasePopulator.addScript(resource);
				}
			}
		}
		databaseCleaner.setSeparator("$$");
		this.setDatabaseCleaner(databaseCleaner);
	}
}
