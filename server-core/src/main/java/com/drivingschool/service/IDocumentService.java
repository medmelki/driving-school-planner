package com.drivingschool.service;

import com.drivingschool.model.Document;
import org.springframework.stereotype.Service;

@Service
public interface IDocumentService extends IGenericService<Document, Integer> {
}
