package graph2;


/**
 * 普利姆算法
 */
public class PrimTest {

    public static void main(String[] args) {

        AMG graph = GraphUtils.buildVillage(10000);

        graph.showGraph();
        System.out.println("==============================================");

        // 从A出发
        int prim = prim(graph, 0);
        System.out.println("最小生成树的总权值为: " + prim);

        System.out.println("==============================================");

        // 从B出发
        int totalWeight = prim(graph, 1);
        System.out.println("最小生成树的总权值为: " + totalWeight);

    }

    /**
     * 普利姆算法
     *
     * @param graph      村庄图
     * @param startIndex 起点的索引
     * @return 返回最小生成树的总权值
     */
    private static int prim(AMG graph, int startIndex) {

        // 标记节点是否被访问过 0表示没有被访问过
        int[] visited = new int[graph.getVertexNum()];

        visited[startIndex] = 1;

        // 2个 辅助指针  记录加入的边的2个顶点的索引
        int temp1 = -1;
        int temp2 = -1;

        // 先初始化为最大值 后续遍历过程中逐步被较小值替换
        int minWeight = 10000;
        // 记录总权值
        int totalWeight = 0;

        // 7个顶点最后肯定只会取6条边
        for (int i = 1; i < graph.getVertexNum(); i++) {

            // 开始遍历
            for (int j = 0; j < graph.getVertexNum(); j++) {
                for (int k = 0; k < graph.getVertexNum(); k++) {
                    // 外层节点已经被访问过 内层节点还没有被访问过 不断获取更小权值的变

                    // 例如第一次唯一被访问过的是A 那么取得的边是AG=2
                    // 第二次被访问过的点为 A G 则以AG为核心向外扩展 最终找到是 GB=3
                    // 第三次被访问过的点为 A B G 则以ABG为核心向外扩展 最终找到是 GE=4
                    // 第四次被访问过的点为 A B E G 则以ABEG为核心向外扩展 最终找到是 EF=5 (此时还未入的点只剩CD)
                    // 第五次被访问过的点为 A B E F G 则以ABEFG为核心向外扩展 最终找到是 FD=4  (此时还未入的点只剩C)
                    // 第六次被访问过的点为 A B D E F G 则以ABEFG为核心向外扩展 最终找到是 AC=7 (所有与C连接的边中 AC=7最小)
                    if (visited[j] == 1 && visited[k] == 0 && graph.getEdges()[j][k] < minWeight) {
                        minWeight = graph.getEdges()[j][k];

                        // 记录最小权值边的两个起点
                        temp1 = j;
                        temp2 = k;
                    }
                }
            }

            System.out.println("边<" + graph.getValueByIndex(temp1) + "-" + graph.getValueByIndex(temp2) + ">被加入，权值" + graph.getEdges()[temp1][temp2]);
            totalWeight += graph.getEdges()[temp1][temp2];

            // 把另一个点标记为已访问
            visited[temp2] = 1;
            // 重置minWeight
            minWeight = 10000;
        }

        return totalWeight;
    }
}
