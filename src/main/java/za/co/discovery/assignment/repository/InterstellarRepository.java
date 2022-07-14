package za.co.discovery.assignment.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import za.co.discovery.assignment.entity.Planet;
import za.co.discovery.assignment.helper.Graph;
import za.co.discovery.assignment.service.EntityManagerService;
import za.co.discovery.assignment.service.InterstellarService;
import javax.annotation.PostConstruct;
import java.util.LinkedList;


@Component
public class InterstellarRepository {

    private static final String PATH_NOT_AVAILABLE = "There is no path to ";
    protected PlatformTransactionManager platformTransactionManager;
    private Graph graph;
    private EntityManagerService entityManagerService;

    @Autowired
    public InterstellarRepository(@Qualifier("transactionManager") PlatformTransactionManager platformTransactionManager, EntityManagerService entityManagerService) {
        this.platformTransactionManager = platformTransactionManager;
        this.entityManagerService = entityManagerService;
    }

    @PostConstruct
    public void initData() {

        TransactionTemplate tmpl = new TransactionTemplate(platformTransactionManager);
        tmpl.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                entityManagerService.persistGraph();
            }
        });
    }

    public String getShortestPath(String name) {
        StringBuilder path = new StringBuilder();
        graph = entityManagerService.selectGraph();
        InterstellarService sp = new InterstellarService(graph);
        Planet source = graph.getVertexes().get(0);
        Planet destination = entityManagerService.getPlanetById(name);
        sp.run(source);
        LinkedList<Planet> paths = sp.getPath(destination);
        if (paths != null) {
            for (Planet v : paths) {
                path.append(v.getName() + " (" + v.getPlanetId() + ")");
                path.append("\t");
            }
        } else {
            path.append(PATH_NOT_AVAILABLE + destination.getName());
            path.append(".");
        }

        return path.toString();
    }
}
