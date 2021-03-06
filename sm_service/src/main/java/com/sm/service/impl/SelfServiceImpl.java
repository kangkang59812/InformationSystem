package com.sm.service.impl;

import com.sm.dao.SelfDao;
import com.sm.dao.StaffDao;
import com.sm.entity.Staff;
import com.sm.service.SelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("selfService")
public class SelfServiceImpl implements SelfService {

    @Autowired
    @Qualifier("selfDao")
    private SelfDao selfDao;

    @Autowired
    @Qualifier("staffDao")
    private StaffDao staffDao;

    public Staff login(String account, String password) {
        Staff staff=selfDao.selectByAccount(account);
        if(staff==null)return null;
        if(staff.getPassword().equals(password))return staff;
        return null;
    }

    public void changePassword(Integer id, String password) {
        Staff staff=staffDao.selectById(id);
        staff.setPassword(password);
        staffDao.update(staff);
    }
}
