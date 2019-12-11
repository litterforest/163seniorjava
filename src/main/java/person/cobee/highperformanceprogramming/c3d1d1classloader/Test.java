package person.cobee.highperformanceprogramming.c3d1d1classloader;

import java.time.LocalDate;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-12-11
 */
public class Test {

    public static void main(String[] args) {
        LocalDate nowDate = LocalDate.now();
        // 检查上期账单是否已结清或不存在
        LocalDate lastMonth = nowDate.minusMonths(1);
        System.out.println(lastMonth);
    }

}
