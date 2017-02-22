package com.drivingschool.service;

import com.drivingschool.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService extends IGenericService<User, String> {

    List<User> findByRole(String username);
}
