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
public class MapController {

    @Autowired
    private MapApplication application;

    /**
     * 
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Map map) {

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map> get(@PathVariable("id") Long id) {

        Map map = new Map();

        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    /**
     * 
     */
    @RequestMapping(value = "/route", method = RequestMethod.GET)
    public ResponseEntity<Map> route(
            @RequestParam(value = "map", required = true) String mapName, 
            @RequestParam(value = "origin", required = true) String origin, 
            @RequestParam(value = "destiny", required = true) String destiny, 
            @RequestParam(value = "cost", required = true) Float cost) {

        Map map = new Map();

        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }
}
