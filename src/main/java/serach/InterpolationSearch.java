package serach;

// 基于二分查找，将查找点的选择改进为自适应选择，可以提高查找效率
public class InterpolationSearch {


    public static void main(String[] args) {
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i + 1;
        }

//        System.out.println(Arrays.toString(arr));

//        int i = interpolationSearch(arr, 0, arr.length - 1, 2);
//        System.out.println(i);

        int j = binarySearch(arr, 0, arr.length - 1, 2);
        System.out.println(j);

        System.out.println("方法调用次数:" + (count + 1));

    }

    private static int count;

    /**
     * 递归法
     *
     * @param arr   待查找数组
     * @param left  左边索引
     * @param right 右边索引
     * @param value 待查找值
     * @return 找到了就返回对应的下标 否则-1
     */
    private static int interpolationSearch(int[] arr, int left, int right, int value) {

        // 设置提前退出 必须添加 而且可以防止下标越界
        if (left > right || value < arr[0] || value > arr[arr.length - 1]) {
            return -1;
        }

        int mid = left + (right - left) * (value - arr[left]) / (arr[right] - arr[left]);
        int midVal = arr[mid];

        if (value > midVal) {
            count++;
            return interpolationSearch(arr, mid + 1, right, value); // 向右递归
        } else if (value < midVal) {
            count++;
            return interpolationSearch(arr, left, mid - 1, value); // 向左递归
        } else {
            return mid; // 正好找到了
        }
    }

    /**
     * @param arr   待查找数组
     * @param left  左边索引
     * @param right 右边索引
     * @param value 待查找值
     * @return 找到了就返回对应的下标 否则-1
     */
    private static int binarySearch(int[] arr, int left, int right, int value) {

        if (left > right) {
            return -1;
        }

        int mid = (right - left) / 2 + left;
        int midVal = arr[mid];

        if (value > midVal) {
            count++;
            return binarySearch(arr, mid + 1, right, value); // 向右递归
        } else if (value < midVal) {
            count++;
            return binarySearch(arr, left, mid - 1, value); // 向左递归
        } else {
            return mid; // 正好找到了
        }

    }


}
