package com.drivingschool.service.impl;


import com.drivingschool.model.User;
import com.drivingschool.service.IUserService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserService extends GenericService<User, String> implements IUserService {

    public UserService() {
        super(User.class);
    }

    @Override
    public List<User> findByRole(String role) {
        return entityManager.createNamedQuery("User.findByRole",
                User.class)
                .setParameter("role", role)
                .getResultList();
    }
}
