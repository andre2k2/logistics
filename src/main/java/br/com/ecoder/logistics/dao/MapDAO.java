package br.com.ecoder.logistics.dao;

import java.util.List;

import br.com.ecoder.logistics.model.Route;

public interface MapDAO {

    /**
     * Metodo utilizado para criar uma relacao entre o ponto de origem e destino na malha logistica.
     *
     * @param map Nome da malha logistica.
     * @param origin Nome do ponto de origem.
     * @param destiny Nome do ponto de destino.
     * @param distance Distancia entre os dois pontos.
     */
    public void createRelationship(String map, String origin, String destiny, Double distance);

    /**
     * Metodo utilizado para calcular a rota de menor custo entre dois pontos na malha logistica.
     *
     * @param map Nome da malha logistica.
     * @param origin Nome do ponto de origem.
     * @param destiny Nome do ponto de destino.
     *
     * @return Lista de pontos que compoe o caminho de menor custo.
     */
    public List<Route> findRoute(String map, String origin, String destiny);

}
