package graph2;

import java.util.ArrayList;
import java.util.List;


/**
 * 克鲁斯卡尔算法
 */
public class KruskalTest {

    public static void main(String[] args) {

        AMG graph = GraphUtils.buildVillage(10000);

        graph.showGraph();

        System.out.println("==========================================");

        // 获取图的有效边集
        ArrayList<String> edgeList = graph.getEdgeList();
        // 直接打印
        System.out.println(edgeList);
        // 格式化打印
        graph.printEdge(edgeList);
        // 查看边数
        System.out.println(graph.getEdgeNum());

        System.out.println("==========================================");
        List<String> kruskal = kruskal(graph);
        System.out.println("最小生成树的边集为: " + kruskal);

        if (kruskal != null) {
            System.out.println("最小生成树的权值为: " + getMinWeight(kruskal));
        }

    }


    /**
     * 克鲁斯卡尔算法
     *
     * @param graph 村庄图
     * @return 返回最小生成树的总权值
     */
    private static List<String> kruskal(AMG graph) {

        // 用于保存 当前最小生成树 的每个顶点的终点
        int[] ends = new int[graph.getVertexNum()];

        // 创建结果数组 返回保存最后的MST
        List<String> result = new ArrayList<>();

        // 获取图中所有的边的集合
        ArrayList<String> tempList = graph.getEdgeList();

        // 首先要对所有边的权值进行排序
        graph.sortEdge(tempList);

        System.out.println("边排序后");
        // 格式化打印
        graph.printEdge(tempList);
        System.out.println("==========================================");

        // 遍历edges数组 将边添加到最小生成树中 并判断是否构成了回路
        for (String s : tempList) {
            // 先获取第一条边 起点和终点 的索引
            String[] split = s.split("-");
            int u = graph.getIndexByValue(split[0]);
            int v = graph.getIndexByValue(split[1]);

            // 获取起点和终点 在当前最小生成树的终点索引
            // 第一轮的时候 边AG 两个点的终点索引都是自己 0 6  if语句块内 A的终点改为G 权值2
            // 第二轮的时候 边BG 两个点的终点索引都是自己 1 6  if语句块内 B的终点改为G 权值3
            // 第三轮的时候 边DF 两个点的终点索引都是自己 3 5  if语句块内 D的终点改为F 权值4
            // 第四轮的时候 边EG 两个点的终点索引都是自己 4 6  if语句块内 E的终点改为G 权值4
            // 第五轮的时候 边EF E终点G F终点自己  6 5  if语句块内 G的终点改为F  权值5
            // 第六轮的时候 边AB A终点G B终点G  该边被跳过
            // 第七轮的时候 边FG F终点自己 5 G终点F 5  该边被跳过
            // 第八轮的时候 边AC A终点G 6 C终点自己 2  if语句块内 G的终点改为C 权值7

            // 注意此时已经加入了六条边

            int m = graph.getEnd(ends, u);
            int n = graph.getEnd(ends, v);

            // 终点不同 没有构成回路
            if (m != n) {
                // 设为MST中的终点
                ends[m] = n;
                // 该边加入MST
                result.add(s);
            }

            // 如果 result.size == 6 可以提前结束了 7个点的最小生成树一定是六条边
            if (result.size() == graph.getVertexNum() - 1) {
                return result;
            }

        }
        // 否则无法生成MST
        return null;

    }


    private static int getMinWeight(List<String> strings) {
        // 获取最小权值
        int weight = 0;
        for (String string : strings) {
            weight += Integer.parseInt(string.split("-")[2]);
        }
        return weight;
    }

}
