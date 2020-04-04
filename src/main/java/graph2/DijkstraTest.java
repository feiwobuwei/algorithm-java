package graph2;

import org.junit.Test;

import java.util.Arrays;

/**
 * 迪杰斯特拉算法
 */
public class DijkstraTest {

    @Test
    public void test() {

        // 起点索引 改为G
        int start = 6;
        AMG graph = GraphUtils.buildVillage2(start);
        graph.showGraph();
        System.out.println("==========================================");

        // 基本打印
        int[] dijkstra = dijkstra(graph, start);
        System.out.println(Arrays.toString(dijkstra));

        printDistance(graph, dijkstra, start);

    }

    @Test
    public void test2() {

        // 起点索引 A
        int start = 0;
        AMG graph = GraphUtils.buildVillage2(start);

        int[] dijkstra = dijkstra2(graph, start);
        System.out.println(Arrays.toString(dijkstra));

        printDistance(graph, dijkstra, start);

        System.out.println("==========================================");

        // 输出路径
        printPath(graph, start);
    }


    /**
     * dijkstra 算法的完全体
     *
     * @param graph 村庄图
     * @param start 以A为起点
     * @return 单源最短路径
     */
    private static int[] dijkstra2(AMG graph, int start) {

        // 更新起点到所有可达节点(即邻接节点)的距离 并更新它们的前驱节点为自己
        update(graph, start);

        // 查看三个记录数组
        see(graph);

        // A行
        // 当前最短距离[0, 5, 7, 10000, 10000, 10000, 2]
        // 已访问节点表[1, 0, 0, 0, 0, 0, 0]
        // 前驱节点表[99, 0, 0, 99, 99, 99, 0] B C G的前驱节点被更新为A ; D E F不可达

        // 遍历剩余6个顶点

        // A剩余没有被访问过的节点里到G距离最短(AG=2)
        // 新的起点G
        // 当前最短距离[0, 5, 7, 10000, 6, 8, 2]
        // 已访问节点表[1, 0, 0, 0, 0, 0, 1]
        // 前驱节点表[99, 0, 0, 99, 6, 6, 0]  E F的前驱节点被更新为G ; D 不可达
        // A-G-E 6 A-G-F 8

        // A剩余没有被访问过的节点里到B距离最短(AB=5)
        // 新的起点B
        // 当前最短距离[0, 5, 7, 14, 6, 8, 2]
        // 已访问节点表[1, 1, 0, 0, 0, 0, 1]
        // 前驱节点表[99, 0, 0, 1, 6, 6, 0]  D的前驱节点被更新为B
        // A-B-D 14

        // A剩余没有被访问过的节点里到E距离最短(AGE=6)
        // 新的起点E
        // 当前最短距离[0, 5, 7, 14, 6, 8, 2]
        // 已访问节点表[1, 1, 0, 0, 1, 0, 1]
        // 前驱节点表[99, 0, 0, 1, 6, 6, 0]
        // 各个距离都没有变化

        // A剩余没有被访问过的节点里到C距离最短(AC=7)
        // 新的起点C
        // 当前最短距离[0, 5, 7, 14, 6, 8, 2]
        // 已访问节点表[1, 1, 1, 0, 1, 0, 1]
        // 前驱节点表[99, 0, 0, 1, 6, 6, 0]
        // 各个距离都没有变化

        // A剩余没有被访问过的节点里到F距离最短(AGF=8)
        // 新的起点F
        // 当前最短距离[0, 5, 7, 12, 6, 8, 2]
        // 已访问节点表[1, 1, 1, 0, 1, 1, 1]
        // 前驱节点表[99, 0, 0, 5, 6, 6, 0] D的前驱节点被更新为F
        // A-G-F-D 12

        // A剩余没有被访问过的节点里到D距离最短(AGFD=12)
        // 新的起点D
        // 当前最短距离[0, 5, 7, 12, 6, 8, 2]
        // 已访问节点表[1, 1, 1, 1, 1, 1, 1]
        // 前驱节点表[99, 0, 0, 5, 6, 6, 0]
        // 各个距离都没有变化

        // 遍历结束

        // 遍历剩余6个顶点
        for (int i = 1; i < graph.getVertexNum(); i++) {
            // 选择新的访问节点
            start = selectNext(graph);
            System.out.println("新的起点" + graph.getValueByIndex(start));
            // 以新的访问节点为起点进行更新
            update(graph, start);

            see(graph);
        }

        // 距离数组记录了起点到各点的距离
        return graph.getDistance();
    }

    /**
     * 查看三个记录数组
     *
     * @param graph 村庄图
     */
    private static void see(AMG graph) {

        System.out.println("当前最短距离" + Arrays.toString(graph.getDistance()));
        System.out.println("已访问节点表" + Arrays.toString(graph.getIsVisited()));
        System.out.println("前驱节点表" + Arrays.toString(graph.getPreVisited()));

        System.out.println("================================");
    }


    /**
     * 打印到每个其他点的最短路线图
     *
     * @param graph 村庄图
     * @param start 起点
     */
    private static void printPath(AMG graph, int start) {

        // 前驱节点表[99, 0, 0, 5, 6, 6, 0]
        int[] pre = graph.getPreVisited();
        int temp;
        StringBuilder s;

        int defaultIndex = graph.getDefaultIndex();

        for (int i = 0; i < pre.length; i++) {
            // 每次重置为空
            s = new StringBuilder();
            temp = pre[i];

            while (temp != defaultIndex) {
                // 每次都插入到前面
                s.insert(0, graph.getValueByIndex(temp));
                temp = pre[temp];
            }

            if (pre[i] != defaultIndex) {
                System.out.printf("从%s到%s的最短路径为: ", graph.getValueByIndex(start), graph.getValueByIndex(i));
                System.out.println(s.toString() + graph.getValueByIndex(i));
            }
        }
    }

    /**
     * 打印距离图
     *
     * @param graph    村庄图
     * @param dijkstra 路径数组
     * @param start    起点 索引
     */
    private static void printDistance(AMG graph, int[] dijkstra, int start) {
        // 可以从1开始 从A出发,到A的最短距离是0 这句话没有意义
        for (int i = 0; i < dijkstra.length; i++) {
            System.out.printf("从%s出发,到%s的最短距离是%d\n", graph.getValueByIndex(start),
                    graph.getValueByIndex(i), dijkstra[i]);
        }
    }

    private static int[] dijkstra(AMG graph, int start) {

        // 更新起点到所有邻接节点的距离,并把它们的前驱节点设为自己
        update(graph, start);

        for (int i = 1; i < graph.getVertexNum(); i++) {
            // 选择新的访问节点
            start = selectNext(graph);
            // 以新的访问节点为起点进行更新
            update(graph, start);
        }

        // 距离数组记录了起点到各点的距离
        return graph.getDistance();
    }


    /**
     * 更新index顶点周围的距离和相应的前驱节点
     *
     * @param graph 村庄图
     * @param index 待更新点
     */
    private static void update(AMG graph, int index) {

        int len;
        int[][] edges = graph.getEdges();

        for (int i = 0; i < edges[index].length; i++) {
            // len 含义是 出发顶点到index顶点的最短距离 + 从index顶点到i顶点的距离的和
            len = graph.getDistance(index) + edges[index][i];

            // 如果i顶点没有被访问过 且len小于出发顶点到i顶点的距离 就需要更新
            if (!graph.isVisited(i) && len < graph.getDistance(i)) {
                // 更新i节点的前驱为index
                graph.updatePre(i, index);
                // 更新出发点到i顶点的距离为len (找到更短的距离)
                graph.updateDistance(i, len);
            }

        }
    }


    /**
     * 找到目前还未访问但可达的节点中，距离最短的
     *
     * @param graph 村庄图
     * @return 单源最短路径
     */
    private static int selectNext(AMG graph) {
        int temp = 0;
        // 等于DEFAULT_WEIGHT
        int min = graph.getDefaultWeight();

        int[] visited = graph.getIsVisited();
        int[] distance = graph.getDistance();

        for (int i = 0; i < visited.length; i++) {
            if (visited[i] == 0 && distance[i] < min) {
                min = distance[i];
                temp = i;
            }
        }
        visited[temp] = 1;
        return temp;

    }


}
