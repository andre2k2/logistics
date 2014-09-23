package br.com.ecoder.logistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ecoder.logistics.application.MapApplication;
import br.com.ecoder.logistics.model.Map;

@RestController
@RequestMapping("/map")
@SuppressWarnings("rawtypes")
public class MapController {

    @Autowired
    private MapApplication application;

    /**
     * Endpoint utilizado para persistir uma malha logistica.
     * 
     * Ex. de Chamada: POST http://127.0.0.1/logistics/map
     * 
     * Body Content:
     * {
     *     "name": "teste",
     *     "routes": [
     *         {
     *         "origin": { "name": "A" },
     *         "destiny": { "name": "B" },
     *         "distance": 10
     *         }
     *     ]
     * }
     * 
     * @return C�digo 201 (Created) se a malha logistica foi criada com sucesso.
     *         C�digo 500 (Internal server error) se houve algum problema na cria��o.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Map map) {

        application.persist(map);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * Endpoint utilizado para visualizar a malha logistica.
     * 
     * EX. de Chamada: GET http://127.0.0.1/logistics/map/meumapa
     * 
     * @return C�digo 200 (OK) e o conte�do da malha em formato JSON.
     *         C�digo 500 (Internal server error) se houverem erros na execu��o.
     */
    @RequestMapping(value = "/{map}", method = RequestMethod.GET)
    public ResponseEntity<Map> get(@PathVariable("map") String name) {

        Map map = application.findByName(name);

        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    /**
     * Endpoit utilizado para calcular a menor rota e menor custo entre dois pontos de uma malha logistica.
     * 
     * Ex. de Chamada: GET http://127.0.0.1/logistics/map/route?map=teste&origin=A&destiny=D&autonomy=10.0&cost=2.5
     * 
     * Ex. de Retorno:
     * {
     *     "name": "teste",
     *     "autonomy": 10.0,
     *     "gasCost": 2.5,
     *     "totalCost": 25.0,
     *     "routes": [
     *         {
     *         "origin": { "name": "A" },
     *         "destiny": { "name": "B" },
     *         "distance": 100
     *         }
     *     ]
     * }
     * 
     * @return C�digo 200 (OK) e o conte�do da malha em formato JSON.
     *         C�digo 500 (Internal server error) se houverem erros na execu��o.
     *         
     *         name: nome da malha logistica.
     *         autonomy: autonomia do caminh�o (Km/Litro).
     *         gasCost: custo do combustivel.
     *         totalCost: custo total de combustivel que ser� gasto na rota definida.
     *         route: menor rota na malha logistica entre os dois pontos.
     */
    @RequestMapping(value = "/route", method = RequestMethod.GET)
    public ResponseEntity<Map> route(
            @RequestParam(value = "map", required = true) String name, 
            @RequestParam(value = "origin", required = true) String origin, 
            @RequestParam(value = "destiny", required = true) String destiny, 
            @RequestParam(value = "autonomy", required = true) Float autonomy, 
            @RequestParam(value = "cost", required = true) Float cost) {

        Map map = application.findRoute(name, origin, destiny, autonomy, cost);

        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

}
