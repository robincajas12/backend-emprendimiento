package com.project007.db;

import java.util.List;

public interface IUserService{
    public void create(User user);
    public List<User> selectAll();
}
