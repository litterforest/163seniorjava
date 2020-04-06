package person.cobee.vo;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-06
 */
public class Person {

    private String name;
    public volatile int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
