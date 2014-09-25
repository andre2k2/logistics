package br.com.ecoder.logistics.dao.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.ecoder.logistics.model.Route;

public class MapNeo4jDAOTest {

    @BeforeClass
    public static void init() throws IOException {

        File db = new File("/tmp/logistics/db/test");
        if (db.exists()) {
            FileUtils.deleteDirectory(db);
        }
    }

    @Test
    public void should_create_db() {

        MapNeo4jDAO dao = new MapNeo4jDAO();

        dao.createRelationship("test", "A", "B", 10.0);
        dao.createRelationship("test", "B", "D", 15.0);
        dao.createRelationship("test", "A", "C", 20.0);
        dao.createRelationship("test", "C", "D", 30.0);
        dao.createRelationship("test", "B", "E", 50.0);
        dao.createRelationship("test", "D", "E", 30.0);

        Assert.assertTrue(new File("/tmp/logistics/db/test/neostore").exists());
    }

    @Test
    public void should_create_graph() {

        MapNeo4jDAO dao = new MapNeo4jDAO();

        dao.createRelationship("test", "A", "B", 10.0);
        dao.createRelationship("test", "B", "D", 15.0);
        dao.createRelationship("test", "A", "C", 20.0);
        dao.createRelationship("test", "C", "D", 30.0);
        dao.createRelationship("test", "B", "E", 50.0);
        dao.createRelationship("test", "D", "E", 30.0);

        List<Route> routes = dao.findRoute("test", "A", "D");

        Assert.assertNotNull(routes);
        Assert.assertEquals(2, routes.size());
        Assert.assertEquals("A", routes.get(0).getOrigin().getName());
        Assert.assertEquals("B", routes.get(0).getDestiny().getName());
        Assert.assertEquals(new Double(10.0), routes.get(0).getDistance());
        Assert.assertEquals("B", routes.get(1).getOrigin().getName());
        Assert.assertEquals("D", routes.get(1).getDestiny().getName());
        Assert.assertEquals(new Double(15.0), routes.get(1).getDistance());
    }

}
