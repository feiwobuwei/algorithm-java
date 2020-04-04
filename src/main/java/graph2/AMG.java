package graph2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Adjacency Matrix Graph 图的邻接矩阵表示
 * 和 Java 自带的 Graph 类区分
 */
public class AMG {

    /**
     * 存储顶点
     */
    private ArrayList<String> vertexList;

    /**
     * 存储邻接矩阵
     */
    private int[][] edges;

    /**
     * 边的数目
     */
    private int edgeNum;


    /**
     * 存储实际有效边集 使用 "0-1-10"(索引-索引-权值) 的格式 取的时候用"-"裂开字符串
     */
    private ArrayList<String> edgeList;

    public AMG(int n) {
        // 1 初始三要素
        edges = new int[n][n];
        vertexList = new ArrayList<>(n);
        // 初始边为0 不连接
        edgeNum = 0;

        // 2 深度优先遍历时添加
        flags = new boolean[n];

        // 3 克鲁斯卡尔算法时添加
        edgeList = new ArrayList<>();

        // 4 佛洛依德算法时添加
        this.pre = new int[n][n];
        for (int i = 0; i < n; i++) {
            // 每一排用行下标填充
            Arrays.fill(pre[i], i);
        }
    }

    // ================== 基本方法 =============== //

    public int getVertexNum() {
        // 返回图的顶点数
        return vertexList.size();
    }


    public int getEdgeNum() {
        // 返回边数
        return edgeNum;
    }


    public String getValueByIndex(int i) {
        // 返回指定索引对应的点
        return vertexList.get(i);
    }


    public int getWeight(int v1, int v2) {
        // 返回边的权值
        return edges[v1][v2];
    }


    public void showGraph() {
        // 打印图对应的邻接矩阵
        for (int[] row : edges) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }


    public void insertVertex(String vertex) {
        // 给图加入一个顶点
        vertexList.add(vertex);
    }


    public void insertEdge(int v1, int v2, int weight) {
        // 添加边 无向图
        // v1 v2 都是点的下标 第几个顶点
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        edgeNum++;

        // 克鲁斯卡尔算法是添加
        // 同时把有效边加入到边集中
        String tmp = getValueByIndex(v1) + "-" + getValueByIndex(v2) + "-" + weight;
        edgeList.add(tmp);
    }


    // ================== DFS =============== //

    /**
     * 标记某个节点是否被访问过
     */
    private boolean[] flags;

    /**
     * 深度优先遍历
     */
    public void dfs() {
        // 对dfs进行重载 遍历所有的节点 并进行dfs
        for (int i = 0; i < getVertexNum(); i++) {
            if (!flags[i]) {
                dfs(flags, i);
            }
        }
    }


    /**
     * 对每一个节点进行深度优先搜索 DFS 递归法
     *
     * @param flags 标记某个节点是否被访问过
     * @param i     顶点的索引
     */
    private void dfs(boolean[] flags, int i) {

        System.out.print(getValueByIndex(i) + "->");
        // 设置i为已经访问
        flags[i] = true;

        // 获取该节点的第一个邻接节点
        int w = getFirstNeighbor(i);

        while (w != -1) {
            if (!flags[w]) {
                // 递归
                dfs(flags, w);
            }
            // 如果w已经被访问过 寻找下一个邻接节点
            w = getNextNeighbor(i, w);
        }

    }


    /**
     * 得到某个节点 的第一个邻接节点的下标
     *
     * @param index 待查找节点的索引
     * @return 第一个邻接节点的下标
     */
    public int getFirstNeighbor(int index) {

        for (int i = 0; i < vertexList.size(); i++) {
            // 只要该边存在
            if (edges[index][i] > 0) {
                return i;
            }
        }
        // 否则不存在 返回-1
        return -1;
    }


    /**
     * 获取点 v1 的邻接点集合中点v2的下一个节点
     *
     * @param v1 待查找节点
     * @param v2 前一个邻接节点的下标v2
     * @return 下一个邻接节点
     */
    public int getNextNeighbor(int v1, int v2) {
        // 只需改变遍历起始位置
        for (int i = v2 + 1; i < vertexList.size(); i++) {
            if (edges[v1][i] > 0) {
                return i;
            }
        }
        return -1;
    }

    // ================== BFS =============== //

    public void bfs() {
        // 遍历所有节点都进行广度优先
        for (int i = 0; i < getVertexNum(); i++) {
            if (!flags[i]) {
                bfs(flags, i);
            }
        }
    }

    private void bfs(boolean[] flags, int i) {
        //   LinkedList用作队列
        LinkedList<Integer> queues = new LinkedList<>();

        // 表示队列的头节点对应下标
        int u;
        // u的第一个邻接节点下标
        int w;

        System.out.print(getValueByIndex(i) + "->");
        // 设置为已经访问
        flags[i] = true;

        // 加到队尾
        queues.addLast(i);

        while (!queues.isEmpty()) {

            // 取出队列的头节点 自动拆箱
            u = queues.removeFirst();
            //  获取u的第一个邻接节点
            w = getFirstNeighbor(u);

            while (w != -1) {
                if (!flags[w]) {
                    System.out.print(getValueByIndex(w) + "->");
                    // 设置为已经访问
                    flags[w] = true;
                    // 加到队尾
                    queues.addLast(w);
                }
                // 第一个入参 u 体现出广度优先，循环过程中 u不变，层数不变
                w = getNextNeighbor(u, w);
            }
        }

    }

    // =========== prim ============ //

    public int[][] getEdges() {
        return edges;
    }

    public void maxWeight(int m) {
        // 所有边权重 置为一个指定值
        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < edges[i].length; j++) {
                edges[i][j] = m;
            }
        }
    }

    // ================== Kruskal   =============== //

    public ArrayList<String> getEdgeList() {
        return edgeList;
    }

    public int getIndexByValue(String s) {
        // 返回值对应的索引
        for (int i = 0; i < vertexList.size(); i++) {
            if (vertexList.get(i).equals(s)) {
                return i;
            }
        }
        // 否则为没有找到
        return -1;
    }

    public void printEdge(List<String> strings) {
        // 打印有效边集
        for (String string : strings) {
            String[] split = string.split("-");
            System.out.println("边<" + split[0] + split[1] + ">的权值是" + split[2]);
        }

    }

    /**
     * 有效边排序
     *
     * @param strings 有效边集
     */
    public void sortEdge(List<String> strings) {

        // 交换整个字符串用的临时字符串
        String temp;
        // 提前结束循环标志
        boolean flag = false;

        // 根据权值进行排序 选择冒泡法
        for (int i = 0; i < strings.size(); i++) {
            for (int j = i + 1; j < strings.size(); j++) {
                if (Integer.parseInt(strings.get(i).split("-")[2]) > Integer.parseInt(strings.get(j).split("-")[2])) {
                    flag = true;
                    temp = strings.get(i);
                    strings.set(i, strings.get(j));
                    strings.set(j, temp);
                }
            }
            // 内层循环一次也没进入 说明已经有序 提前结束
            if (!flag) {
                break;
            } else {
                flag = false;
            }
        }
    }


    /**
     * 核心方法
     * 获取下标为i的顶点的终点 ends 记录各个顶点对应的终点是哪一个
     * 并非初始遍历一次可以获得 而是在算法运行(不断加入边)的过程中 动态更新
     *
     * @param ends 终点数组
     * @param i    要判断的点
     * @return 返回终点的下标
     */
    public int getEnd(int[] ends, int i) {

        // 在MST扩大的过程中 如果该边已经被加入了 那么它的两个顶点 ends[i] 都不为0
        // 此时循环到 ends[i]==0 为止 (即一个还未加入过的顶点)
        while (ends[i] != 0) {
            // 找到这个节点的前一个节点 直到终点位置索引
            i = ends[i];
        }

        // 返回终点的下标
        return i;
    }


    // ================== Dijkstra  =============== //

    // 迪杰斯特拉算法

    /**
     * 各个顶点是否被访问过
     */
    private int[] isVisited;

    /**
     * 记录每个顶点最短路径条件下的前驱顶点下标
     */
    private int[] preVisited;

    /**
     * 记录出发顶点到其他所有顶点的距离 会动态更新
     */
    private int[] distance;

    /**
     * 也可以用 Integer.MAX_VALUE
     */
    private final static int DEFAULT_WEIGHT = 10000;


    /**
     * 在迪杰斯特拉算法中使用
     */
    private final static int DEFAULT_INDEX = 99;

    /**
     * 初始化迪杰斯特拉算法相关参数
     *
     * @param length 顶点的个数
     * @param start  出发顶点的下标
     */
    public void createVisitedVertex(int length, int start) {
        this.isVisited = new int[length];
        this.preVisited = new int[length];
        this.distance = new int[length];

        // 距离 全部先初始化为一个较大值
        Arrays.fill(distance, DEFAULT_WEIGHT);

        // 边的权值 全部先初始化为一个较大值
        maxWeight(DEFAULT_WEIGHT);
        // 设置出发点到自身的距离为0
        this.distance[start] = 0;
        // 起点必然被访问过
        this.isVisited[start] = 1;

        //  前驱节点全部先初始化为一个较大值 完全体版本里面使用
        Arrays.fill(preVisited, DEFAULT_INDEX);
    }


    public boolean isVisited(int index) {
        // 判断index顶点是否被访问过
        return isVisited[index] == 1;
    }

    public void updateDistance(int index, int length) {
        // 更新出发顶点到index这个顶点的距离
        distance[index] = length;
    }

    public void updatePre(int cur, int pre) {
        // 更新当前顶点 current 的前驱索引值为 pre
        preVisited[cur] = pre;
    }

    public int getDistance(int des) {
        // 返回出发顶点到 des 顶点的距离
        return distance[des];
    }

    public int[] getDistance() {
        // 不传入参数 代表返回整个数组
        return distance;
    }

    public int getDefaultWeight() {
        return DEFAULT_WEIGHT;
    }

    public int[] getIsVisited() {
        return isVisited;
    }

    public int[] getPreVisited() {
        return preVisited;
    }

    public int getDefaultIndex() {
        return DEFAULT_INDEX;
    }

    // =========== floyd =================== //

    /**
     * 前驱节点矩阵
     */
    private int[][] pre;

    public void showPre() {
        // 返回图对应的前驱数组
        for (int[] row : pre) {
            for (int element : row) {
                System.out.printf("%-8s", getValueByIndex(element));
            }
            System.out.println();
        }
    }

    public int[][] getPre() {
        return pre;
    }


}
