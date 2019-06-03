package org.monkey.ebill.dao;

import org.monkey.ebill.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserDao extends JpaRepository<User, String> {
    /**
     *
     * @param account
     * @param password
     * @return
     */
    public List<User> findByAccountAndPassword(String account, String password);
}
