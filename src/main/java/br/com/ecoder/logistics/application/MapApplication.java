package br.com.ecoder.logistics.application;

import br.com.ecoder.logistics.model.Map;

public interface MapApplication {

    /**
     * 
     * @param map
     */
    public void persist(Map map);

    /**
     * 
     * @param name
     * @return
     */
    public Map findByName(String name);

    /**
     * 
     * @param name
     * @param origin
     * @param destiny
     * @param cost
     * @return
     */
    public Map findRoute(String name, String origin, String destiny, Float autonomy, Float cost);

}
