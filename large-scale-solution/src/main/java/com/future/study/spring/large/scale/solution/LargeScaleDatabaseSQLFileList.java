package com.future.study.spring.large.scale.solution;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dexterleslie.Chan
 */
public class LargeScaleDatabaseSQLFileList extends DatabaseSQLFileList{
    @Override
    public List<String> getDatabasePopulatorFiles() {
        List<String> files=new ArrayList<>();
        files.add("/sql-files/user.sql");
        return files;
    }
}
