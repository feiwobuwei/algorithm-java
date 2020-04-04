package graph2;

import org.junit.Before;
import org.junit.Test;

/**
 * 对比案例
 */
public class AMGTest2 {

    private int n = 8;
    private String[] vertexes = {"1", "2", "3", "4", "5", "6", "7", "8"};
    private AMG graph2 = new AMG(n);

    @Before
    public void prepare() {
        for (String vertex : vertexes) {
            graph2.insertVertex(vertex);
        }

        // 添加九条边 1-2 2-4 2-5 4-8 5-8 1-3 3-6 3-7 6-7
        graph2.insertEdge(0, 1, 1);
        graph2.insertEdge(0, 2, 1);
        graph2.insertEdge(1, 3, 1);
        graph2.insertEdge(1, 4, 1);
        graph2.insertEdge(3, 7, 1);
        graph2.insertEdge(4, 7, 1);
        graph2.insertEdge(2, 5, 1);
        graph2.insertEdge(2, 6, 1);
        graph2.insertEdge(5, 6, 1);
    }

    @Test
    public void testBase() {
        graph2.showGraph();
    }


    @Test
    public void testDFS() {
        System.out.println("==========深度遍历===========");
        graph2.dfs();
    }

    @Test
    public void testBFS() {
        System.out.println("==========广度遍历===========");
        graph2.bfs();
    }


}
