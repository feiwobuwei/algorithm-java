package graph2;

/**
 * 弗洛伊德算法
 */
public class FloydTest {

    public static void main(String[] args) {

        AMG graph = GraphUtils.buildVillage(10000);

        graph.showGraph();
        System.out.println("===============================");

        graph.showPre();
        System.out.println("=========== 调用方法后 =============");

        // 调用方法
        floyd(graph);

        graph.showGraph();
        System.out.println("================================");
        graph.showPre();

    }


    /**
     * 弗洛伊德算法 time O(V^3)
     *
     * @param graph 村庄图
     */
    private static void floyd(AMG graph) {

        int len; // 该变量保存距离
        int[][] edges = graph.getEdges();
        int[][] pre = graph.getPre();

        // i 为中间顶点的下标
        for (int i = 0; i < graph.getVertexNum(); i++) {
            // j 为出发顶点的下标
            for (int j = 0; j < graph.getVertexNum(); j++) {
                // k 为终点的下标
                for (int k = 0; k < graph.getVertexNum(); k++) {
                    // j-i-k的距离
                    len = edges[j][i] + edges[i][k];
                    // 如果绕道可以距离更短 那就绕道
                    if (len < edges[j][k]) {
                        // 更新距离
                        edges[j][k] = len;
                        // 更新前驱节点
                        pre[j][k] = pre[i][k];
                    }
                }
            }
        }

    }
}
