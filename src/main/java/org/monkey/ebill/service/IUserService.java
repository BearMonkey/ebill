package org.monkey.ebill.service;

import org.monkey.ebill.entity.User;
import org.monkey.ebill.exception.UserException;

import java.util.List;

public interface IUserService {

    /**
     *
     * @param user
     * @throws UserException
     */
    public void addUser(User user) throws UserException;

    /**
     * 根据账号和密码查询用户
     * @param account
     * @param password
     * @return List<User>
     */
    public List<User> queryUser(String account, String password);

    public List<User> queryAll();
}
