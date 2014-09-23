package br.com.ecoder.logistics.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ecoder.logistics.application.MapApplication;
import br.com.ecoder.logistics.dao.MapDAO;

@Service
public class MapApplicationImpl implements MapApplication {

    @Autowired
    private MapDAO dao;
}
