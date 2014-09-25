package br.com.ecoder.logistics.application.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import br.com.ecoder.logistics.dao.MapDAO;
import br.com.ecoder.logistics.model.Map;
import br.com.ecoder.logistics.model.Point;
import br.com.ecoder.logistics.model.Route;

public class MapApplicationImplTest {

    @Mock
    MapDAO dao;

    MapApplicationImpl application;

    List<Route> routes;

    @Before
    public void init() throws IOException {

        MockitoAnnotations.initMocks(this);

        routes = new LinkedList<Route>();

        Route route1 = new Route();
        route1.setOrigin(new Point());
        route1.setDestiny(new Point());
        route1.setDistance(10.0);
        route1.getOrigin().setName("A");
        route1.getDestiny().setName("B");
        routes.add(route1);

        Route route2 = new Route();
        route2.setOrigin(new Point());
        route2.setDestiny(new Point());
        route2.setDistance(15.0);
        route2.getOrigin().setName("B");
        route2.getDestiny().setName("D");
        routes.add(route2);

        when(dao.findRoute(anyString(), anyString(), anyString())).thenReturn(routes);

        application = new MapApplicationImpl();

        Whitebox.setInternalState(application, "dao", dao);
    }

    @Test
    public void should_persist_map() {

        Map map = new Map();
        map.setName("test");
        map.setRoutes(routes);

        application.persist(map);

        Mockito.verify(dao, Mockito.times(1)).createRelationship("test", "A", "B", 10.0);
        Mockito.verify(dao, Mockito.times(1)).createRelationship("test", "B", "D", 15.0);
    }

    @Test
    public void should_return_better_route() {

        Map map = application.findRoute("test", "A", "D", 10.0, 2.5);

        Assert.assertNotNull(map);
        Assert.assertNotNull(map.getRoutes());
        Assert.assertEquals(routes, map.getRoutes());

        Assert.assertEquals(new Double(10.0), map.getAutonomy());
        Assert.assertEquals(new Double(2.5), map.getGasCost());
        Assert.assertEquals(new Double(6.25), map.getTotalCost());
    }

}
