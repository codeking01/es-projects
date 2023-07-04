package cn.itcast.hotel;


import org.junit.jupiter.api.Test;

/**
 * @author CodeKing
 * @since 2023/7/3  19:20
 */
public class StaticTest {
    private static String name = "CodeKing";
    static void test() {
        name="CodeKing1111";
    }

    public static void main(String[] args) {
        test();
        System.out.println("mian:"+name);
    }
}
