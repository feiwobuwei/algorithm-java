package huffman;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.*;

public class HuffmanCodingDemo {

    @Test
    public void test1() {
        String content = "i like like like java do you like a java";
        char[] chars = content.toCharArray();
        System.out.println(chars.length); // 40

        List<CNode> nodes = getNodes(chars);
        nodes.forEach(System.out::println);

        System.out.println("=========前序遍历============");
        CNode root = createHuffmanCodingTree(nodes); // 根节点
        System.out.println(root.weight);
        preOrder(root);

        System.out.println("=========中序遍历============");
        inOrder(root);

        System.out.println("=========编码表===========");

        // getHuffmanCodes(root, "", stringBuilder);
        // huffmanCodes .forEach((k, v) -> System.out.println(k + " : " + v));

        // 方法重载让调用更简单
        Map<Character, String> result = getHuffmanCodes(root); // 这里result和huffmanCodes指向的是同一对象
        result.forEach((k, v) -> System.out.println(k + " : " + v));

        System.out.println("=========编码结果============");
        byte[] zip = zip(chars, result);
        System.out.println("编码后的字节长度: " + zip.length);  // 17
        System.out.println(Arrays.toString(zip)); // 40个数变为了17个数

    }

    @Test
    public void test2() {
        String s = "10101000";
        System.out.println(Integer.parseInt(s, 2)); // 168  ( 128+32+8=168 )
        System.out.println((byte) Integer.parseInt(s, 2)); // -88 (1010 0111 -> 1101 1000 -> 64+16+8 )
        System.out.println((char) Integer.parseInt(s, 2)); // ¨ 168
        System.out.println((char) Integer.parseInt("100010", 2)); // " 34
    }

    // 测试重构后的方法
    @Test
    public void test3() {
        String content = "i like like like java do you like a java";
        char[] chars = content.toCharArray();

        byte[] zip = huffmanZip(chars);
        System.out.println("编码后的字节长度: " + zip.length);
        System.out.println(Arrays.toString(zip));

    }

    // 测试 byteToBitString 方法
    @Test
    public void test4() {
        byte b = -88;
        String s = byteToBitString(b, false);
        System.out.println(s); // 1010 1000

        b = 64;
        s = byteToBitString(b, false);
        System.out.println(s); // 0100 0000

        b = 31;
        s = byteToBitString(b, true);
        System.out.println(s); // 11111

    }

    // 测试解码方法
    @Test
    public void test5() {
        String content = "i like like like java do you like a java";
        char[] chars = content.toCharArray();

        List<CNode> nodes = getNodes(chars);
        CNode root = createHuffmanCodingTree(nodes); // 根节点
        Map<Character, String> map = getHuffmanCodes(root);
        byte[] zip = zip(chars, map);

        System.out.println("==============解码过程===============");
        char[] result = huffmanUnzip(map, zip);

        System.out.println(result);
    }

    // 测试压缩文件方法
    @Test
    public void test6() {
        String srcFile = "/Users/minwei/exercise/src/core-default.xml";
        String dstFile = "/Users/minwei/exercise/dst/core-default.zip";
        zipFile(srcFile, dstFile);
        System.out.println("压缩文件成功");
    }

    // 测试解压文件方法
    @Test
    public void test7() {
        String zipFile = "/Users/minwei/exercise/dst/core-default.zip";
        String dstFile = "/Users/minwei/exercise/src/core-default2.xml";
        unZipFile(zipFile, dstFile);
        System.out.println("解压文件成功");
    }

    /**
     * 压缩
     *
     * @param srcFile 传入待压缩文件的全路径
     * @param dstFile 压缩后的文件放置的目录
     */
    private static void zipFile(String srcFile, String dstFile) {

        // 创建输入流
        try {
            FileInputStream fileInputStream = new FileInputStream(srcFile);

            // 创建一个和源文件大小一样的byte[]数组
            byte[] b = new byte[fileInputStream.available()];

            // the total number of bytes read into the buffer, or
            // <code>-1</code> if there is no more data because the end of the file has been reached.
            fileInputStream.read(b);

            // 获取文件对应的赫夫曼编码表 中间需要经过一次byte[]到char[]的转换
            byte[] bytes = huffmanZip(getChars(b));

            // 创建文件的输出流
            FileOutputStream fileOutputStream = new FileOutputStream(dstFile);

            // 用对象输出流对文件输出流进行包装
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // 将编码后的字节数组写入压缩文件
            objectOutputStream.writeObject(bytes);

            // 再输出赫夫曼编码(写入压缩文件) 为了解压 不然以后恢复不了
            objectOutputStream.writeObject(huffmanCodes);

            // 关闭所有流
            fileInputStream.close();
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 解压
     *
     * @param zipFile 准备解压文件的路径
     * @param dstFile 解压后的文件放置的目录
     */
    private static void unZipFile(String zipFile, String dstFile) {

        try {
            // 文件输入流
            FileInputStream fileInputStream = new FileInputStream(zipFile);
            // 对象输入流 对 文件输入流 进行包装
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            // 读取对象  byte数组
            byte[] HuffmanBytes = (byte[]) objectInputStream.readObject();

            // 压缩文件内部有一个分界线

            // 读取赫夫曼编码表
            Map<Character, String> map = (Map<Character, String>) objectInputStream.readObject();

            // 解码
            char[] chars = huffmanUnzip(map, HuffmanBytes);

            // 文件输出流
            FileOutputStream fileOutputStream = new FileOutputStream(dstFile);

            fileOutputStream.write(getBytes(chars));

            // 关闭所有流
            fileInputStream.close();
            objectInputStream.close();
            fileOutputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    // char -> byte
    private static byte[] getBytes(char[] chars) {
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        return bb.array();
    }

    // byte -> char
    private static char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }

    /**
     * 1 将 byte[] 的每一个字节  先转为 二进制字符串 例如 -88 -> 10101000(补) -> 1010 0111(反) -> "1010 0111"(原)
     *
     * @param b    byte[] 的每一个字节
     * @param flag false 表示不是最后一个字节 true表示是最后一个字节
     * @return 补码格式的二进制字符串
     */
    private static String byteToBitString(byte b, boolean flag) {

        String s;
        if (!flag) {
            s = Integer.toBinaryString((int) b | 256); // 返回的是补码

            // 负数的时候需要截取后8位
            // 正数的时候自动 消去了前面的0 此时需要补到8位
            // 256  是 23个0 + 1 + 0000 0000 逐位或 会得到 1 + (原数的补码) 再获取后8位
            return s.substring(s.length() - 8);
        } else {
            // 最后一个数可能不是8位 那么既不需要截取,也不需要高位补0
            // 此时一定不会是负数，因为原 byte 会把最高位自动补0
            s = Integer.toBinaryString((int) b);
            // 假定最后一个字节只有5位 例如 (000)1 1111 生成的字符串就是 "11111"
            return s;
        }

    }

    /**
     * 2 将 二进制字符串 按照赫夫曼编码 重新 转为 (原) 字符串
     *
     * @param huffmanMap   赫夫曼编码表
     * @param huffmanBytes 赫夫曼编码得到的字节数组
     * @return 原来字符串对应的字符数组
     */
    private static char[] huffmanUnzip(Map<Character, String> huffmanMap, byte[] huffmanBytes) {

        // 先得到 huffmanBytes 对应的 二进制字符串
        StringBuilder stringBuilder = new StringBuilder();
        boolean flag;

        for (int i = 0; i < huffmanBytes.length; i++) {
            // 判断是不是最后一个字节
            flag = (i == huffmanBytes.length - 1);
            String s = byteToBitString(huffmanBytes[i], flag);
            stringBuilder.append(s);
        }

        // 把字符串按指定的赫夫曼编码进行解码 即101 -> a
        // 将编码表进行调换 键值互换 形成解码表
        Map<String, Character> decodeMap = new HashMap<>();
        for (Map.Entry<Character, String> entry : huffmanMap.entrySet()) {
            decodeMap.put(entry.getValue(), entry.getKey());
        }

        // 创建一个集合 存放 Character
        List<Character> list = new ArrayList<>();

        int count;   // 计数器
        Character c = null; // 待匹配的字符
        String key;

        // 逐位扫描 对比 解码表
        // 让i 每次循环 右移count个位置
        for (int i = 0; i < stringBuilder.length(); i += count) {

            count = 1;   // 每次重置计数器
            flag = true; // 变量的重复利用 防止反复创建销毁变量

            while (flag) {
                // 取出一个bit 1 or 0
                key = stringBuilder.substring(i, i + count);
                c = decodeMap.get(key);
                if (c == null) {
                    count++; // 让count移动 直到匹配到一个字符
                } else {
                    flag = false;
                }
            }
            list.add(c);
        }

        // 当for循环结束后 list就存放了所有的字符
        char[] result = new char[list.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }

        return result;

    }


    // 将1 2 3 4 重构抽取为一个方法
    private static byte[] huffmanZip(char[] characters) {
        // 1 首先获取用于构建赫夫曼树的List
        List<CNode> nodes = getNodes(characters);

        // 2 生成赫夫曼树 最后返回赫夫曼树的根节点 其值为最小WPL
        CNode root = createHuffmanCodingTree(nodes);

        // 3 递归生成赫夫曼树对应的赫夫曼编码
        Map<Character, String> map = getHuffmanCodes(root);

        // 4 将字符串对应的 char[]数组 通过赫夫曼编码表 返回一个赫夫曼编码压缩后的 byte[]数组
        return zip(characters, map);
    }


    // 1 首先获取用于构建赫夫曼树的List
    private static List<CNode> getNodes(char[] characters) {

        List<CNode> nodes = new ArrayList<>();

        // 遍历characters 存储每个字符出现的频率 使用map
        Map<Character, Integer> map = new HashMap<>();

        for (Character character : characters) {
            map.merge(character, 1, (a, b) -> a + b);
        }

        // 把每一个键值对转为一个CNode对象 并加入nodes中
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            CNode cNode = new CNode(entry.getKey(), entry.getValue());
            nodes.add(cNode);
        }

        return nodes;
    }

    // 2 生成赫夫曼树 最后返回赫夫曼树的根节点 其值为最小WPL
    private static CNode createHuffmanCodingTree(List<CNode> nodes) {

        // 1-5步是一个循环过程 当nodes里面只有一个节点的时候 循环结束
        while (nodes.size() > 1) {

            // 1 从小到大排序，将每一个数据都可以看成是一颗最简单的二叉树
            Collections.sort(nodes);

            // 2 取出根节点权值最小的两颗二叉树
            CNode leftNode = nodes.get(0);
            CNode rightNode = nodes.get(1);

            // 3 组成一颗新树
            CNode parentNode = new CNode(null, leftNode.weight + rightNode.weight);
            parentNode.left = leftNode;
            parentNode.right = rightNode;

            // 4 删除掉已经处理过的节点
            nodes.remove(leftNode);
            nodes.remove(rightNode);

            // 5 添加新的成员
            nodes.add(parentNode);

        }

        return nodes.get(0);
    }


    private static Map<Character, String> huffmanCodes = new HashMap<>();
    private static StringBuilder stringBuilder = new StringBuilder();

    // 方法重载
    private static Map<Character, String> getHuffmanCodes(CNode root) {

        if (root == null) {
            return null;
        }

        getHuffmanCodes(root.left, "0", stringBuilder);
        getHuffmanCodes(root.right, "1", stringBuilder);

        return huffmanCodes;
    }


    /**
     * 3 递归生成赫夫曼树对应的赫夫曼编码
     *
     * @param cnode         根节点
     * @param code          路径 左子节点为0 右为1
     * @param stringBuilder 拼接路径
     */
    private static void getHuffmanCodes(CNode cnode, String code, StringBuilder stringBuilder) {

        StringBuilder result = new StringBuilder(stringBuilder);

        result.append(code);

        if (cnode != null) {
            // cnode.data == null 是中间节点 非叶子节点
            if (cnode.data == null) {
                // 向左递归处理
                getHuffmanCodes(cnode.left, "0", result);
                // 向右递归处理
                getHuffmanCodes(cnode.right, "1", result);
            } else {
                // 找到的是叶子节点
                huffmanCodes.put(cnode.data, result.toString());
            }

        }
    }

    /**
     * 4 将字符串对应的 char[]数组 通过赫夫曼编码表 返回一个赫夫曼编码压缩后的 byte[]数组
     *
     * @param characters   字符串对应的 char[] 数组
     * @param huffmanCodes 赫夫曼编码映射集
     * @return 压缩后的 byte[] 数组
     */
    private static byte[] zip(char[] characters, Map<Character, String> huffmanCodes) {

        StringBuilder stringBuilder = new StringBuilder();

        for (Character c : characters) {
            stringBuilder.append(huffmanCodes.get(c));
        }

        System.out.println("编码后的bit数: " + stringBuilder.toString().toCharArray().length); //临时查看一下bit数
        System.out.println(stringBuilder.toString().toCharArray()); //临时查看一下二进制数据

        int len;
        if (stringBuilder.length() % 8 == 0) {
            len = stringBuilder.length() / 8;
        } else {
            len = stringBuilder.length() / 8 + 1;
        }

        byte[] bytes = new byte[len];
        int index = 0; // 记录是第几个byte

        // 步长为8 每8位放入一个字节
        for (int i = 0; i < stringBuilder.length(); i += 8) {
            String strByte;
            if (i + 8 > stringBuilder.length()) {
                strByte = stringBuilder.substring(i); // 不够8位 即最后一个字节
            } else {
                strByte = stringBuilder.substring(i, i + 8);
            }

            bytes[index] = (byte) Integer.parseInt(strByte, 2);
            index++;
        }

        return bytes;
    }

    // 使用前序遍历 验证树的格式
    private static void preOrder(CNode root) {

        // 只输出叶节点的值 叶节点都是字符 data不会为空
    /*    if (root.left == null && root.right == null) {
            System.out.println(root.data + " " + root.weight + " ");
        }
        */

        // 全部节点的输出
        if (root.data == null) {
            System.out.println("中间节点: " + root.weight + " ");
        } else {
            System.out.println(root.data + " " + root.weight + " ");
        }

        if (root.left != null) {
            preOrder(root.left);
        }

        if (root.right != null) {
            preOrder(root.right);
        }
    }

    // 使用中序遍历 验证树的格式
    private static void inOrder(CNode root) {

        if (root.left != null) {
            inOrder(root.left);
        }

        // 全部节点的输出
        if (root.data == null) {
            System.out.println("中间节点: " + root.weight + " ");
        } else {
            System.out.println(root.data + " " + root.weight + " ");
        }

        if (root.right != null) {
            inOrder(root.right);
        }
    }

}

// Character Node 字符节点
class CNode implements Comparable<CNode> {

    Character data;
    int weight; // 字符频率

    CNode left;
    CNode right;

    CNode(Character data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "CNode{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(CNode o) {
        // return (x < y) ? -1 : ((x == y) ? 0 : 1);
        return Integer.compare(this.weight, o.weight); // 先按频率 从小到大排序

        // 因为后续会使用无data字段的节点 因此会抛出空指针异常
        // return compare == 0 ? Integer.compare(this.data, o.data) : compare;    // 再按ASCII码 从小到大排序

    }
}
