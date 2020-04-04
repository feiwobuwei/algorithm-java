package common;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 骑士周游问题
 *
 * @author minwei
 */
public class KnightTourProblem {

    private static int X = 8; //棋盘的行数
    private static int Y = 8; //棋盘的列数
    private static boolean[] visited;
    private static boolean finished = false;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("请输入棋盘的行数和列数");
        do {
            X = sc.nextInt();
            Y = sc.nextInt();
        } while (X <= 0 || Y <= 0);

        System.out.println("请输入起始位置（先行后列）");
        int row, column;

        do {
            row = sc.nextInt();
            column = sc.nextInt();
        } while (row <= 0 || row > X || column <= 0 || column > Y);

        int[][] chessBoard = new int[X][Y];
        visited = new boolean[X * Y];

        long starTime = System.currentTimeMillis();
        Travel(chessBoard, row - 1, column - 1, 1);
        long endTime = System.currentTimeMillis();
        System.out.println("共耗时：" + (endTime - starTime) + "ms");

        for (int[] rows : chessBoard) {
            for (int columns : rows) {
                System.out.print(columns + "\t");
            }
            System.out.println();
        }

        sc.close();
    }

    // 在当前位置处，搜寻下一步的可能位置，并放入list中

    private static ArrayList<Point> next(Point p) {
        ArrayList<Point> resultArrayList = new ArrayList<>();
        Point p1 = new Point();
        if ((p1.x = p.x - 2) >= 0 && (p1.y = p.y - 1) >= 0) {
            resultArrayList.add(new Point(p1));
        }
        if ((p1.x = p.x - 1) >= 0 && (p1.y = p.y - 2) >= 0) {
            resultArrayList.add(new Point(p1));
        }
        if ((p1.x = p.x + 1) < X && (p1.y = p.y - 2) >= 0) {
            resultArrayList.add(new Point(p1));
        }
        if ((p1.x = p.x + 2) < X && (p1.y = p.y - 1) >= 0) {
            resultArrayList.add(new Point(p1));
        }
        if ((p1.x = p.x + 2) < X && (p1.y = p.y + 1) < Y) {
            resultArrayList.add(new Point(p1));
        }
        if ((p1.x = p.x + 1) < X && (p1.y = p.y + 2) < Y) {
            resultArrayList.add(new Point(p1));
        }
        if ((p1.x = p.x - 1) >= 0 && (p1.y = p.y + 2) < Y) {
            resultArrayList.add(new Point(p1));
        }
        if ((p1.x = p.x - 2) >= 0 && (p1.y = p.y + 1) < Y) {
            resultArrayList.add(new Point(p1));
        }
        return resultArrayList;
    }

    // 遍历棋盘

    private static void Travel(int[][] chessBoard, int row, int column, int step) {
        chessBoard[row][column] = step;
        visited[row * Y + column] = true;

        ArrayList<Point> nextPoints = next(new Point(row, column));

        sort(nextPoints); // 按照当前(new Point(column, row))这步的下一步的下一步选择数目进行非递减排序

        while (!nextPoints.isEmpty()) {
            Point point = nextPoints.remove(0);
            if (!visited[point.x * Y + point.y]) {
                Travel(chessBoard, point.x, point.y, step + 1);
            }

        }
        if (step < X * Y && !finished) {
            chessBoard[row][column] = 0;
            visited[row * Y + column] = false;
        } else {
            finished = true;
        }
    }

    // 根据当前的这一步的所有下一步的选择数目进行非递减排序
    public static void sort(ArrayList<Point> ps) {
        ps.sort((o1, o2) -> {
            int count1 = next(o1).size();
            int count2 = next(o2).size();
            return Integer.compare(count1, count2);
        });
    }

}

