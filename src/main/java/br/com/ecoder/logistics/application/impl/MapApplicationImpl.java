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

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(Map map) {

        List<Route> routes = map.getRoutes();
        String name = map.getName();

        for (Route route : routes) {

            String origin = route.getOrigin().getName();
            String destiny = route.getDestiny().getName();
            Double distance = route.getDistance().doubleValue();

            dao.createRelationship(name, origin, destiny, distance);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map findRoute(String name, String origin, String destiny, Double autonomy, Double cost) {

        List<Route> routes = dao.findRoute(name, origin, destiny);

        Map map = new Map();

        map.setName(name);
        map.setRoutes(routes);
        map.setAutonomy(autonomy);
        map.setGasCost(cost);
        map.setTotalCost(getTotalCost(routes, autonomy, cost));

        return map;
    }

    private Double getTotalCost(List<Route> routes, Double autonomy, Double cost) {

        Double distance = 0.0;

        for (Route route : routes) {
            distance += route.getDistance();
        }

        return (distance / autonomy) * cost;
    }
}
