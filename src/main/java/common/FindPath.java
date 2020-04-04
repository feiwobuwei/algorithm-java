package common;

import java.util.Scanner;


/**
 * DFS
 *
 * 搜寻人质
 */
public class FindPath {

    //人质所在迷宫的位置
    private static int fx, fy;
    // 迷宫为5*5
    private static int n = 5;
    // 右 下 左 上
    private static int[][] temp = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    // 迷宫数组
    private static int[][] map = new int[n][n];
    // 标记数组，走过就标记为1
    private static int[][] book = new int[n][n];
    // 最短步数 初始化无穷大
    private static int min = 9999999;

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("请输入迷宫5*5：");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                map[i][j] = scan.nextInt();
            }
        }

        System.out.println("请输入人质所在位置：");
        fx = scan.nextInt();
        fy = scan.nextInt();

        book[0][0] = 1; // 起点标记为1
        dfs(0, 0, 0);
        System.out.println(min);

    }

    private static void dfs(int x, int y, int step) {
        // 如果到达地点，结束
        if (x == fx && y == fy) {
            if (step < min) {
                min = step;
            }
            return;
        }
        // 循环移动到四个方向 i=0 1 2 3 分别对应 右 下 左 上
        for (int i = 0; i < 4; i++) {
            int tx = temp[i][0];
            int ty = temp[i][1];
            //如果该方向越界了，改变到另一个方向
            if (x + tx < 0 || x + tx >= n)
                continue;
            if (y + ty < 0 || y + ty >= n)
                continue;
            // 如果该位置没有障碍物并且也没有走过
            if (map[x + tx][y + ty] == 0 && book[x + tx][y + ty] == 0) {
                // 标记为走过
                book[x + tx][y + ty] = 1;
                // 往下一层递归
                dfs(x + tx, y + ty, step + 1);
                // 取消标记，回到上一层
                book[x + tx][y + ty] = 0;
            }
        }
    }
}
