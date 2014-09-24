package br.com.ecoder.logistics.dao;

import java.util.List;

import br.com.ecoder.logistics.model.Route;


public interface MapDAO {

    public void createRelationship(String map, String origin, String destiny, Float distance);

    public List<Route> findRoute(String map, String origin, String destiny);

}
