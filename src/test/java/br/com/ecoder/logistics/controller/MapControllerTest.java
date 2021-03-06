package br.com.ecoder.logistics.controller;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.http.ResponseEntity;

import br.com.ecoder.logistics.application.MapApplication;
import br.com.ecoder.logistics.model.Map;
import br.com.ecoder.logistics.model.Point;
import br.com.ecoder.logistics.model.Route;

public class MapControllerTest {

    @Mock
    MapApplication application;

    MapController controller;

    Map map;

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);

        map = new Map();
        map.setName("test");
        map.setAutonomy(10.0);
        map.setGasCost(2.5);
        map.setTotalCost(6.25);
        map.setRoutes(new LinkedList<Route>());

        Route route = new Route();
        route.setOrigin(new Point());
        route.setDestiny(new Point());
        route.setDistance(1.0);
        route.getOrigin().setName("A");
        route.getDestiny().setName("B");

        map.getRoutes().add(route);

        when(application.findRoute(anyString(), anyString(), anyString(), anyDouble(), anyDouble())).thenReturn(map);

        controller = new MapController();
        Whitebox.setInternalState(controller, "application", application);
    }

    @Test
    public void should_call_persistence() {

        Map map = new Map();

        controller.save(map);

        Mockito.verify(application, Mockito.times(1)).persist(map);
    }

    @Test
    public void should_call_route() {

        ResponseEntity<Map> response = controller.route("test", "A", "B", 10.0, 2.5);

        Mockito.verify(application, Mockito.times(1)).findRoute("test", "A", "B", 10.0, 2.5);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getBody());
        Assert.assertNotNull(response.getBody().getName());
        Assert.assertEquals(map, response.getBody());

        Assert.assertEquals("test", response.getBody().getName());
        Assert.assertEquals(new Double(10.0), response.getBody().getAutonomy());
        Assert.assertEquals(new Double(2.5), response.getBody().getGasCost());
        Assert.assertEquals(new Double(6.25), response.getBody().getTotalCost());

        Assert.assertEquals(new Double(1), response.getBody().getRoutes().get(0).getDistance());
        Assert.assertEquals("A", response.getBody().getRoutes().get(0).getOrigin().getName());
        Assert.assertEquals("B", response.getBody().getRoutes().get(0).getDestiny().getName());
    }
}
