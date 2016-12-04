package com.drivingschool.service.impl;


import com.drivingschool.model.Document;
import com.drivingschool.service.IDocumentService;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentService extends GenericService<Document, Integer> implements IDocumentService {

    public DocumentService() {
        super(Document.class);
    }
}
