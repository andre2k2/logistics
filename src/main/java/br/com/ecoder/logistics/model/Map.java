package br.com.ecoder.logistics.model;

import java.util.List;

import br.com.ecoder.logistics.validation.MapConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@MapConstraint
public class Map {

    private String name;
    private Float autonomy;
    private Float gasCost;
    private Float totalCost;
    private List<Route> routes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(Float autonomy) {
        this.autonomy = autonomy;
    }

    public Float getGasCost() {
        return gasCost;
    }

    public void setGasCost(Float gasCost) {
        this.gasCost = gasCost;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

}
