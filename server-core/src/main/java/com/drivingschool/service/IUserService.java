package com.drivingschool.service;

import com.drivingschool.model.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService extends IGenericService<User, String> {
}
