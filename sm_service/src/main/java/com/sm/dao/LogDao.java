package com.sm.dao;

import com.sm.entity.Log;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("logDap")
public interface LogDao {
 void insert(Log log);
 List<Log> selectByType(String type);

}
