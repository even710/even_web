package com.even.design;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/11/24
 */
public class MementtoPattern {

    public static void main(String[] args) {
        Person person = new Person("even", "男", 26);
        /*备份person*/
        Person backup = new Person();
        backup.setName(person.getName());
        backup.setAge(person.getAge());
        backup.setSex(person.getSex());



    }

}

class Person {
    private String name;
    private String sex;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person() {
    }

    public Person(String name, String sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }
}