package br.com.ecoder.logistics.validation;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.LinkedList;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.ecoder.logistics.model.Map;
import br.com.ecoder.logistics.model.Point;
import br.com.ecoder.logistics.model.Route;

public class MapValidationTest {

    @Mock
    ConstraintValidatorContext context;

    @Mock
    ConstraintViolationBuilder builder;

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);

        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
    }

    @Test
    public void should_be_valid() {

        Map map = new Map();
        map.setName("test");
        map.setRoutes(new LinkedList<Route>());

        Route route = new Route();
        route.setOrigin(new Point());
        route.setDestiny(new Point());
        route.setDistance(1.0);
        route.getOrigin().setName("A");
        route.getDestiny().setName("B");

        map.getRoutes().add(route);

        MapValidation validation = new MapValidation();
        validation.initialize(null);

        boolean result = validation.isValid(map, context);

        Assert.assertTrue(result);
    }

    @Test
    public void should_be_invalid() {

        Map map = null;

        MapValidation validation = new MapValidation();

        boolean result = validation.isValid(map, context);

        Assert.assertFalse(result);
        Mockito.verify(context, Mockito.times(1)).buildConstraintViolationWithTemplate(anyString());
    }

    @Test
    public void should_be_invalid_with_name_null() {

        Map map = new Map();
        map.setName(null);

        MapValidation validation = new MapValidation();

        boolean result = validation.isValid(map, context);

        Assert.assertFalse(result);
        Mockito.verify(context, Mockito.atLeast(1)).buildConstraintViolationWithTemplate(anyString());
    }

    @Test
    public void should_be_invalid_with_name_empty() {

        Map map = new Map();
        map.setName("");

        MapValidation validation = new MapValidation();

        boolean result = validation.isValid(map, context);

        Assert.assertFalse(result);
        Mockito.verify(context, Mockito.atLeast(1)).buildConstraintViolationWithTemplate(anyString());
    }

    @Test
    public void should_be_invalid_with_route_name_null() {

        Map map = new Map();
        map.setName("teste");
        map.setRoutes(null);

        MapValidation validation = new MapValidation();

        boolean result = validation.isValid(map, context);

        Assert.assertFalse(result);
        Mockito.verify(context, Mockito.atLeast(1)).buildConstraintViolationWithTemplate(anyString());
    }

    @Test
    public void should_be_invalid_with_route_empty() {

        Map map = new Map();
        map.setName("teste");
        map.setRoutes(new LinkedList<Route>());

        MapValidation validation = new MapValidation();

        boolean result = validation.isValid(map, context);

        Assert.assertFalse(result);
        Mockito.verify(context, Mockito.atLeast(1)).buildConstraintViolationWithTemplate(anyString());
    }

    @Test
    public void should_be_invalid_with_route_null() {

        Map map = new Map();
        map.setName("teste");
        map.setRoutes(new LinkedList<Route>());
        map.getRoutes().add(null);

        MapValidation validation = new MapValidation();

        boolean result = validation.isValid(map, context);

        Assert.assertFalse(result);
        Mockito.verify(context, Mockito.atLeast(1)).buildConstraintViolationWithTemplate(anyString());
    }

    @Test
    public void should_be_invalid_with_route_origin_null() {

        Map map = new Map();
        map.setName("teste");
        map.setRoutes(new LinkedList<Route>());
        map.getRoutes().add(new Route());
        map.getRoutes().get(0).setOrigin(null);
        map.getRoutes().get(0).setDestiny(new Point());
        map.getRoutes().get(0).setDistance(1.0);

        MapValidation validation = new MapValidation();

        boolean result = validation.isValid(map, context);

        Assert.assertFalse(result);
        Mockito.verify(context, Mockito.atLeast(1)).buildConstraintViolationWithTemplate(anyString());
    }

    @Test
    public void should_be_invalid_with_route_destiny_null() {

        Map map = new Map();
        map.setName("teste");
        map.setRoutes(new LinkedList<Route>());
        map.getRoutes().add(new Route());
        map.getRoutes().get(0).setOrigin(new Point());
        map.getRoutes().get(0).setDestiny(null);
        map.getRoutes().get(0).setDistance(1.0);

        MapValidation validation = new MapValidation();

        boolean result = validation.isValid(map, context);

        Assert.assertFalse(result);
        Mockito.verify(context, Mockito.atLeast(1)).buildConstraintViolationWithTemplate(anyString());
    }

    @Test
    public void should_be_invalid_with_route_distance_null() {

        Map map = new Map();
        map.setName("teste");
        map.setRoutes(new LinkedList<Route>());
        map.getRoutes().add(new Route());
        map.getRoutes().get(0).setOrigin(new Point());
        map.getRoutes().get(0).setDestiny(new Point());
        map.getRoutes().get(0).setDistance(null);

        MapValidation validation = new MapValidation();

        boolean result = validation.isValid(map, context);

        Assert.assertFalse(result);
        Mockito.verify(context, Mockito.atLeast(1)).buildConstraintViolationWithTemplate(anyString());
    }

    @Test
    public void should_be_invalid_with_point_name_null() {

        Map map = new Map();
        map.setName("teste");
        map.setRoutes(new LinkedList<Route>());
        map.getRoutes().add(new Route());
        map.getRoutes().get(0).setOrigin(new Point());
        map.getRoutes().get(0).getOrigin().setName(null);
        map.getRoutes().get(0).setDestiny(new Point());
        map.getRoutes().get(0).getDestiny().setName("B");
        map.getRoutes().get(0).setDistance(1.0);

        MapValidation validation = new MapValidation();

        boolean result = validation.isValid(map, context);

        Assert.assertFalse(result);
        Mockito.verify(context, Mockito.atLeast(1)).buildConstraintViolationWithTemplate(anyString());
    }

    @Test
    public void should_be_invalid_with_point_name_empty() {

        Map map = new Map();
        map.setName("teste");
        map.setRoutes(new LinkedList<Route>());
        map.getRoutes().add(new Route());
        map.getRoutes().get(0).setOrigin(new Point());
        map.getRoutes().get(0).getOrigin().setName("");
        map.getRoutes().get(0).setDestiny(new Point());
        map.getRoutes().get(0).getDestiny().setName("B");
        map.getRoutes().get(0).setDistance(1.0);

        MapValidation validation = new MapValidation();

        boolean result = validation.isValid(map, context);

        Assert.assertFalse(result);
        Mockito.verify(context, Mockito.atLeast(1)).buildConstraintViolationWithTemplate(anyString());
    }
}
