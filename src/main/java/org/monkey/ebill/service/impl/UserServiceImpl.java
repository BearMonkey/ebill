package org.monkey.ebill.service.impl;

import org.monkey.ebill.dao.IUserDao;
import org.monkey.ebill.entity.User;
import org.monkey.ebill.exception.UserException;
import org.monkey.ebill.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;

    @Override
    public void addUser(User user) throws UserException {
        if (null == user) {
            throw new UserException("user is null!");
        }
        try {
            userDao.save(user);
        } catch (Exception e) {
            throw new UserException("Occured exception when save user!");
        }
    }

    @Override
    public List<User> queryUser(String account, String password) {
        return userDao.findByAccountAndPassword(account, password);
    }

    @Override
    public List<User> queryAll() {
        return userDao.findAll();
    }
}
