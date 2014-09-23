package br.com.ecoder.logistics.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ecoder.logistics.application.MapApplication;
import br.com.ecoder.logistics.dao.MapDAO;
import br.com.ecoder.logistics.model.Map;

@Service
public class MapApplicationImpl implements MapApplication {

    @Autowired
    private MapDAO dao;

    @Override
    public void persist(Map map) {

    }

    @Override
    public Map findByName(String name) {
        return null;
    }

    @Override
    public Map findRoute(String name, String origin, String destiny, Float autonomy, Float cost) {
        return null;
    }
}
