package graph2;

import org.junit.Before;
import org.junit.Test;

public class AMGTest {

    private int n = 5;
    private String[] vertexes = {"A", "B", "C", "D", "E"};
    private AMG graph1 = new AMG(n);

    @Before
    public void prepare() {
        for (String vertex : vertexes) {
            graph1.insertVertex(vertex);
        }

        // 添加五条边 A-B A-C B-C B-D B-E
        graph1.insertEdge(0, 1, 1);
        graph1.insertEdge(0, 2, 1);
        graph1.insertEdge(1, 2, 1);
        graph1.insertEdge(1, 3, 1);
        graph1.insertEdge(1, 4, 1);
    }

    @Test
    public void testBase() {

        graph1.showGraph();

        /*
         * 0 1 1 0 0
         * 1 0 1 1 1
         * 1 1 0 0 0
         * 0 1 0 0 0
         * 0 1 0 0 0
         */

    }


    @Test
    public void testDFS() {
        System.out.println("==========深度遍历===========");
        // A->B->C->D->E->
        graph1.dfs();
    }

    @Test
    public void testBFS() {
        System.out.println("==========广度遍历===========");
        // A->B->C->D->E->
        graph1.bfs();
    }


}
