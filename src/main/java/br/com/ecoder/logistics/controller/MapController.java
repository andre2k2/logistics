package br.com.ecoder.logistics.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * Ex. de Chamada: POST http://127.0.0.1:8080/logistics/map
     *
     * Body Content:
     * {
     *     "name": "teste",
     *     "routes": [
     *         {
     *             "origin": { "name": "A" },
     *             "destiny": { "name": "B" },
     *             "distance": 10.0
     *         },
     *         ...
     *     ]
     * }
     *
     * @return Codigo 201 (Created) se a malha logistica foi criada com sucesso.
     *         Codigo 400 (Bad Request) se houver erros de validacao. Um objeto JSON
     *                    com uma lista de erros será retornanda.
     *                    Ex.: { "errors": [ {"message": "O nome do mapa esta nulo."} ] }
     *         Codigo 500 (Internal server error) se houve algum problema na criacao.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody @Valid Map map) {

        application.persist(map);

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Endpoit utilizado para calcular a menor rota e menor custo entre dois pontos de uma malha logistica.
     *
     * Ex. de Chamada: GET http://127.0.0.1:8080/logistics/map/route?map=teste&origin=A&destiny=D&autonomy=10.0&cost=2.5
     *
     * Ex. de Retorno:
     * {
     *     "name": "teste",
     *     "autonomy": 10.0,
     *     "gasCost": 2.5,
     *     "totalCost": 6.25,
     *     "routes": [
     *         {
     *             "origin": { "name": "A" },
     *             "destiny": { "name": "B" },
     *             "distance": 10.0
     *         },
     *         ...
     *     ]
     * }
     *
     * name: nome da malha logistica.
     * autonomy: autonomia do caminhao (Km/Litro).
     * gasCost: custo do combustivel.
     * totalCost: custo total de combustivel que sera gasto na rota definida.
     * route: menor rota na malha logistica entre os dois pontos.
     *
     * @return Codigo 200 (OK) e o conteudo da malha em formato JSON.
     *         Codigo 400 (Bad Request) se houver erros de validacao. Um objeto JSON
     *                    com uma lista de erros será retornanda.
     *                    Ex.: { "errors": [ {"message": "O nome do mapa esta nulo."} ] }
     *         Codigo 500 (Internal server error) se houverem erros na execucao.
     *
     */
    @RequestMapping(value = "/route", method = RequestMethod.GET)
    public ResponseEntity<Map> route(
            @RequestParam(value = "map", required = true) String name,
            @RequestParam(value = "origin", required = true) String origin,
            @RequestParam(value = "destiny", required = true) String destiny,
            @RequestParam(value = "autonomy", required = true) Double autonomy,
            @RequestParam(value = "cost", required = true) Double cost) {

        Map map = application.findRoute(name, origin, destiny, autonomy, cost);

        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

}
