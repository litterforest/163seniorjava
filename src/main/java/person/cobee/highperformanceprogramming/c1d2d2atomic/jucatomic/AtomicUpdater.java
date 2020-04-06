package person.cobee.highperformanceprogramming.c1d2d2atomic.jucatomic;

import person.cobee.vo.Person;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-06
 */
public class AtomicUpdater {

    public static void main(String[] args) {
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(Person.class, "age");
        Person person = new Person("cobee", 34);
        atomicIntegerFieldUpdater.getAndIncrement(person);
        System.out.println(person.getAge());
    }

}
