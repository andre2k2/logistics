package br.com.ecoder.logistics.dao.impl;

import org.neo4j.index.IndexService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.util.GraphDatabaseLifecycle;

public class MapRepository {

    private static final String PATH = "/tmp/logistics/db/";

    public EmbeddedGraphDatabase graphDb = null;
    public GraphDatabaseLifecycle lifecycle = null;
    public IndexService index = null;

    public MapRepository(String name) {

        graphDb = new EmbeddedGraphDatabase(PATH + name);
        lifecycle = new GraphDatabaseLifecycle(graphDb);
        lifecycle.addLuceneIndexService();
        index = lifecycle.indexService();
    }
}