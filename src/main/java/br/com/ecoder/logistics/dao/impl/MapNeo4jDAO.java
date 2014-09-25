package br.com.ecoder.logistics.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphalgo.CommonEvaluators;
import org.neo4j.graphalgo.CostEvaluator;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipExpander;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.Traversal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.ecoder.logistics.dao.MapDAO;
import br.com.ecoder.logistics.model.Point;
import br.com.ecoder.logistics.model.Route;

@Service
public class MapNeo4jDAO implements MapDAO {

    private static final Logger logger = LoggerFactory.getLogger(MapNeo4jDAO.class);
    private static final String DISTANCE = "distance";
    private static final String NAME = "name";

    private static final java.util.Map<String, MapRepository> repositories = new java.util.HashMap<String, MapRepository>();

    private static final RelationshipExpander expander;
    private static final CostEvaluator<Double> costEvaluator;
    private static final PathFinder<WeightedPath> pathFinder;

    static {
        expander = Traversal.expanderForTypes(MapRelationType.PATH, Direction.OUTGOING);
        costEvaluator = CommonEvaluators.doubleCostEvaluator(DISTANCE);
        pathFinder = GraphAlgoFactory.dijkstra(expander, costEvaluator);
    }

    public MapNeo4jDAO() {}

    private MapRepository getMapRepository(String map) {

        MapRepository repository = repositories.get(map);

        if (repository == null) {
            repository = new MapRepository(map);
            repositories.put(map, repository);
        }

        return repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createRelationship(String map, String origin, String destiny, Double distance) {

        MapRepository repository = getMapRepository(map);
        Transaction tx = repository.graphDb.beginTx();

        try {

            Node firstNode = findOrCreateNode(repository, origin);
            Node secondNode = findOrCreateNode(repository, destiny);

            Relationship rel = firstNode.createRelationshipTo(secondNode, MapRelationType.PATH);
            rel.setProperty(DISTANCE, distance);

            tx.success();

        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            tx.finish();
        }
    }

    private Node findOrCreateNode(MapRepository repository, String nodeName) {

        Node node = getNode(repository, nodeName);

        if (node == null) {
            node = repository.graphDb.createNode();
            node.setProperty(NAME, nodeName);
            repository.index.index(node, NAME, node.getProperty(NAME));
        }

        return node;
    }

    private Node getNode(MapRepository repository, String name) {
        return repository.index.getSingleNode(NAME, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Route> findRoute(String map, String origin, String destiny) {

        MapRepository repository = getMapRepository(map);
        List<Route> result = new LinkedList<Route>();

        Node start = getNode(repository, origin);
        Node end = getNode(repository, destiny);

        // executa a busca pelo menor caminho
        WeightedPath path = pathFinder.findSinglePath(start, end);

        for (Relationship relationship : path.relationships()) {

            Route route = createRoute(relationship);
            result.add(route);
        }

        return result;
    }

    private Route createRoute(Relationship relationship) {

        Node originNode = relationship.getStartNode();
        Node destinyNode = relationship.getEndNode();

        String originName = originNode.getProperty(NAME).toString();
        String destinyName = destinyNode.getProperty(NAME).toString();
        Double distance = (Double) relationship.getProperty(DISTANCE);

        Route route = new Route();
        route.setOrigin(new Point(originName));
        route.setDestiny(new Point(destinyName));
        route.setDistance(distance);

        return route;
    }

    /**
     * Metodo utilizado para liberar os recuros e executar flush nos arquivos.
     */
    public void shutdown() {

        for (MapRepository repository : repositories.values()) {
            repository.index.shutdown();
        }
    }
}
