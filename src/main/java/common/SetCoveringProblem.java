package common;

import java.util.*;

/**
 * 贪心算法 -- 集合覆盖问题
 */
public class SetCoveringProblem {

    public static void main(String[] args) {

        // 创建输入集合
        HashMap<String, HashSet<String>> broadcasts = new HashMap<>();

        HashSet<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");

        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");

        HashSet<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");

        HashSet<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");

        HashSet<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");

        broadcasts.put("K1", hashSet1);
        broadcasts.put("K2", hashSet2);
        broadcasts.put("K3", hashSet3);
        broadcasts.put("K4", hashSet4);
        broadcasts.put("K5", hashSet5);

        List<String> list = setCover(broadcasts);
        System.out.println(list);

    }

    private static List<String> setCover(HashMap<String, HashSet<String>> broadcasts) {

        HashSet<String> allAreas = new HashSet<>(); // 存放所有的地区

        for (HashSet<String> value : broadcasts.values()) {
            allAreas.addAll(value); // 自动去重
        }

        allAreas.forEach((i) -> System.out.print(i + " "));
        System.out.println();

        System.out.println(broadcasts);

        // 创建一个ArrayList 存放已经选择的电台集合
        List<String> selects = new ArrayList<>();

        // 定义一个临时的集合 在遍历的过程中 存放当前电台的覆盖地区与还没有覆盖地区的交集
        // 交集最大的第一个电台将作为本轮遍历的 maxKey (如果不为空的话) 加入到 selects 中
        HashSet<String> tempSet = new HashSet<>();

        String maxKey;
        Map<String, Integer> tempNum = new HashMap<>();

        // 当所有地区都被覆盖的时候 循环结束
        while (allAreas.size() != 0) {

            maxKey = null; // 每次while循环 先重置 maxKey

            for (String key : broadcasts.keySet()) {

                tempSet.clear(); // 每次for循环先重置 tempSet

                HashSet<String> hashSet = broadcasts.get(key); // 当前电台可以覆盖的地区
                tempSet.addAll(hashSet);

                // 求tempSet和allAreas的交集 交集(如果加入该电台后可以新增的覆盖地区) 赋给 tempSet
                tempSet.retainAll(allAreas);
                tempNum.put(key, tempSet.size()); // 记录该电台覆盖的 未覆盖地区的数量

                // 体现贪心算法的特点
                if (maxKey == null || tempSet.size() > tempNum.get(maxKey)) {
                    maxKey = key;
                }

            }

            if (maxKey != null) {
                selects.add(maxKey);
                allAreas.removeAll(broadcasts.get(maxKey));
                broadcasts.remove(maxKey); // 已经使用的电台移除掉
            }

        }

        return selects;
    }

}
