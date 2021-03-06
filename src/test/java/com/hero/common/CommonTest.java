package com.hero.common;

import com.hero.lamda.entity.Employee;
import com.hero.lamda.entity.RemedySheet;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @Author yejx
 * @Date 2020/5/16
 */
//@SpringBootTest
//@RunWith(SpringRunner.class)
public class CommonTest {

    /**
     * 生成指定时间
     */
    @Test
    public void localDatetime () {
        LocalDateTime assginTime = LocalDateTime.of(2021, 5, 5, 23, 49, 57);
        System.out.println(assginTime);
    }

    /**
     * 1、生成18岁到60岁的随机年龄   [18, 60)  不包含60岁
     * new Random().nextInt(42) + 18;
     * <p>
     * 2、生成18岁到60岁的随机年龄 [18, 60]    包含60岁
     * new Random().nextInt(43) + 18;
     * <p>
     * 3、生成-5分到20分的随机成绩 [-5, 20)    不含20分
     * new Random().nextInt(25) - 5;
     * <p>
     * 4、生成-5分到20的的随机成绩 [-5, 20]    包含20分
     * new Random().nextInt(26) - 5;
     */
    @Test
    public void uuidAge() {
        for (int i = 0; i < 10; i++) {
            int age = new Random().nextInt(43) + 18;
            System.out.println(age);
        }
    }

    /**
     * 获取昨天日期
     */
    @Test
    public void test() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
        String yesterdayDay = new SimpleDateFormat("dd").format(calendar.getTime());
        System.out.println("yesterdayDay：" + yesterdayDay);
    }

    /**
     * Double保留两位小数点
     */
    @Test
    public void test01() {
        int a = 1;
        int b = 6;
        double result = new BigDecimal((float) a * 100 / b).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println("计算结果：" + result + "%");
    }

    /**
     * 补救单数据
     *
     * @return
     */
    private List<RemedySheet> remedySheetList() {
        List<RemedySheet> listSheet = new ArrayList<>();
        RemedySheet r1 = new RemedySheet("c945328139ef4c", "8ec0dd9e522e47f783", "BJ10001");
        RemedySheet r2 = new RemedySheet("b42d191a11a6e4", "1850b1305ab547489c", "BJ10002");
        RemedySheet r3 = new RemedySheet("a3ee74afb4e3fe", "356682707c634305a7", "BJ10003");
        RemedySheet r4 = new RemedySheet("31d575872c0f08", "733397b6e2d243048f", "BJ10004");
        RemedySheet r5 = new RemedySheet("47a2e8a79f27b8", "96781b5854fd439a82", "BJ10005");
        RemedySheet r6 = new RemedySheet("e5223489848385", "6ffa5dd499ae4d38a0", "BJ10006");
        RemedySheet r7 = new RemedySheet("3f87d0c8dfbb07", "abfeed2d23b34cf481", "BJ10007");
        RemedySheet r8 = new RemedySheet("034aa340eaf5ab", "82a81aa965464667b2", "BJ10008");
        RemedySheet r9 = new RemedySheet("a1176a29e5a2eb", "ffc035542d5b4dcd82", "BJ10009");
        RemedySheet r10 = new RemedySheet("9d931152a4f672", "f1b56add35444ae4ab", "BJ10010");
        listSheet.add(r1);
        listSheet.add(r2);
        listSheet.add(r3);
        listSheet.add(r4);
        listSheet.add(r5);
        listSheet.add(r6);
        listSheet.add(r7);
        listSheet.add(r8);
        listSheet.add(r9);
        listSheet.add(r10);
        return listSheet;
    }

    /**
     * 接单人数据
     *
     * @return
     */
    private List<Employee> employeeList() {
        List<Employee> listUser = new ArrayList<>();
        Employee e1 = new Employee("张三", 23, 5000.00);
        Employee e2 = new Employee("李四", 18, 4000.00);
        Employee e3 = new Employee("赵武", 25, 7000.00);
        listUser.add(e1);
        listUser.add(e2);
        listUser.add(e3);
        return listUser;
    }

    /**
     * 随机分配
     * @throws InterruptedException
     */
    @Test
    public void testRandom() throws InterruptedException {
        List<RemedySheet> listSheet = this.remedySheetList();
        List<Employee> listUser = this.employeeList();

        int index = listUser.size(); // [0, index)
        System.out.println("原始下标：" + index);
        int ranNumber;
        Random random = new Random();
        for (RemedySheet remedySheet : listSheet) {
            boolean flag;
            Employee employee;
            do {
                ranNumber = random.nextInt(index);
                employee = listUser.get(ranNumber);
                if (employee.getAge() > 24) {
                    System.out.println("分单失败{接单人：" + employee.getName() + "，年龄：" + employee.getAge() + " 超龄：24}");
                    listUser.remove(ranNumber);
                    index = listUser.size();
                    flag = true;
                } else {
                    flag = false;
                }
            } while (flag && index > 0);
            if (index == 0) {
                break;
            }
            remedySheet.setHandleUser(employee.getName());
            listUser.get(ranNumber).setAge(employee.getAge() + 1);
            System.out.println("分单成功{接单人：" + employee.getName() + "，补救流水号：" + remedySheet.getRemedyCode() + "}");
        }
//        Map<String, List<RemedySheet>> collect = listSheet.stream().collect(Collectors.groupingBy(RemedySheet::getHandleUser));
//        System.out.println("分单情况：" + JSONObject.parse(JSON.toJSONString(collect)));
    }

    /**
     * 平均分配
     */
    @Test
    public void testAvg() {
        List<RemedySheet> listSheet = this.remedySheetList();
        List<Employee> listUser = this.employeeList();
        List<Integer> listAge = listUser.stream().map(Employee::getAge).collect(Collectors.toList());

        // 记录原始数据
        List<Employee> tmpListUser = new ArrayList<>(listUser);
        int index = 0;
        for (RemedySheet remedySheet : listSheet) {
            int age;
            boolean flag;
            Employee employee;
            String userName;
            do {
                age = listAge.get(index);
                employee = tmpListUser.get(index);
                userName = employee.getName();
                // 该接单人工单量是否超限
                if (age > 25) { // 已有工单量超过设置的最大工单量、结束本次循环
                    // 原数据同步排除超限的接单人
                    tmpListUser.remove(index);
                    listUser.remove(index);
                    listAge.remove(index);
                    System.out.println(userName + "-超龄-" + age);
                    if (index == tmpListUser.size()) {
                        index = 0;
                    }
                    flag = true;
                } else {
                    flag = false;
                }
            } while (flag && tmpListUser.size() > 0);

            if (listUser.size() <= 0) {
                break;
            } else {
                listAge.set(index, age + 1);//设置数量加1
                if (tmpListUser.size() == 0) { // 人员遍历完了，重新赋值
                    tmpListUser = listUser;
                    index = 0;
                } else if (index == tmpListUser.size() - 1) {
                    index = 0;
                } else {
                    index++;
                }
                System.out.println(userName + "-接单-" + remedySheet.getRemedyCode());
            }
        }
    }

    /**
     * 使用Stream流 根据实体属性 分组
     */
    @Test
    public void testGroupBy() {
        List<Employee> listUser = new ArrayList<>();
        Employee e1 = new Employee("张三", 23, 5000.00);
        Employee e2 = new Employee("张三", 18, 4000.00);
        Employee e3 = new Employee("", 25, 7000.00);
        listUser.add(e1);
        listUser.add(e2);
        listUser.add(e3);
        Map<String, List<Employee>> collect = listUser.stream().collect(Collectors.groupingBy(Employee::getName));
        System.out.println(collect);
    }

    @Test
    public void testMap() {
        Map<String, String> map = new HashMap<>();
        map.put("01", "2678");
        System.out.println(map.get("02"));

    }

}
