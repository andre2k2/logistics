package br.com.ecoder.logistics.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ecoder.logistics.application.MapApplication;
import br.com.ecoder.logistics.dao.MapDAO;
import br.com.ecoder.logistics.model.Map;
import br.com.ecoder.logistics.model.Route;

@Service
public class MapApplicationImpl implements MapApplication {

    @Autowired
    private MapDAO dao;

    @Override
    public void persist(Map map) {

        List<Route> routes = map.getRoutes();
        String name = map.getName();

        for (Route route : routes) {

            String origin = route.getOrigin().getName();
            String destiny = route.getDestiny().getName();
            Float distance = route.getDistance().floatValue();

            dao.createRelationship(name, origin, destiny, distance);
        }
    }

    @Override
    public Map findRoute(String name, String origin, String destiny, Float autonomy, Float cost) {

        List<Route> routes = dao.findRoute(name, origin, destiny);

        Map map = new Map();

        map.setName(name);
        map.setRoutes(routes);
        map.setAutonomy(autonomy);
        map.setGasCost(cost);
        map.setTotalCost(getTotalCost(routes, autonomy, cost));

        return map;
    }

    private Float getTotalCost(List<Route> routes, Float autonomy, Float cost) {

        Float distance = 0f;

        for (Route route : routes) {
            distance += route.getDistance();
        }

        return (distance / autonomy) * cost;
    }
}
