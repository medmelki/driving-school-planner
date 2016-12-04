package com.drivingschool.service.impl;


import com.drivingschool.model.User;
import com.drivingschool.service.IUserService;
import org.springframework.stereotype.Repository;

@Repository
public class UserService extends GenericService<User, String> implements IUserService {

    public UserService() {
        super(User.class);
    }
}
