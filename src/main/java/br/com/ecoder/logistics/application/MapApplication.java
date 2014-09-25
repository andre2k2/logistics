package br.com.ecoder.logistics.application;

import br.com.ecoder.logistics.model.Map;

public interface MapApplication {

    /**
     * Metodo utilizado para persistir uma malha logistica.
     *
     * @param map Malha logistica que sera persistida.
     */
    public void persist(Map map);

    /**
     * Metodo utilizado para calcular a menor rota e menor custo entre dois pontos de uma malha logistica.
     *
     * @param name Nome da malha logistica.
     * @param origin Nome do ponto de origem.
     * @param destiny Nome do ponto de destino.
     * @param cost Custo da gasolina.
     *
     * @return Rota de menor custo e custo total da viagem.
     */
    public Map findRoute(String name, String origin, String destiny, Double autonomy, Double cost);

}
