package graph2;

public class GraphUtils {

    /**
     * 根据入参 返回需要的村庄图
     *
     * @param defaultWeight 一个默认的较大权值
     * @return 村庄图
     */
    public static AMG buildVillage(int defaultWeight) {
        AMG graph = initial();

        // 先把所有边的代价置为一个较大值 此处不能是默认值0 因为要求较小的修路代价
        graph.maxWeight(defaultWeight);

        // 添加十条边
        return insertVillageEdge(graph);
    }


    /**
     * 迪杰斯特拉算法用的
     * 根据入参 返回需要的村庄图
     *
     * @param start 起点索引
     * @return 村庄图
     */
    public static AMG buildVillage2(int start) {
        AMG graph = initial();

        // 初始化迪杰斯特拉算法相关参数 该方法内部含有maxWeight()
        graph.createVisitedVertex(graph.getVertexNum(), start);

        // 添加十条边
        return insertVillageEdge(graph);
    }


    private static AMG initial() {
        String s = "ABCDEFG";
        char[] data = s.toCharArray();
        int n = data.length;

        AMG graph = new AMG(n);

        for (char vertex : data) {
            graph.insertVertex(vertex + "");
        }

        return graph;
    }

    private static AMG insertVillageEdge(AMG graph) {
        //  A-B  5
        graph.insertEdge(0, 1, 5);
        //  A-C  7
        graph.insertEdge(0, 2, 7);
        //  A-G  2
        graph.insertEdge(0, 6, 2);
        //  B-D  9
        graph.insertEdge(1, 3, 9);
        //  B-G  3
        graph.insertEdge(1, 6, 3);
        //  C-E  8
        graph.insertEdge(2, 4, 8);
        //  D-F  4
        graph.insertEdge(3, 5, 4);
        //  E-F  5
        graph.insertEdge(4, 5, 5);
        //  E-G  4
        graph.insertEdge(4, 6, 4);
        //  F-G  6
        graph.insertEdge(5, 6, 6);

        return graph;
    }
}
