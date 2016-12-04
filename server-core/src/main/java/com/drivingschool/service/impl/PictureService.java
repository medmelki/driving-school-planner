package com.drivingschool.service.impl;


import com.drivingschool.model.Picture;
import com.drivingschool.service.IPictureService;
import org.springframework.stereotype.Repository;

@Repository
public class PictureService extends GenericService<Picture, Integer> implements IPictureService {

    public PictureService() {
        super(Picture.class);
    }
}
