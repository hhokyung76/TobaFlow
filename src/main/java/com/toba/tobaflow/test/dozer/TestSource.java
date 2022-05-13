package com.toba.tobaflow.test.dozer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestSource {
    private String name;
    private String address;
    private int age;

    public TestSource() {}

    public TestSource(String name, String address, int age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dest{" +
                "name='" + name + '\'' +
                "address='" + address + '\'' +
                ", age=" + age +
                '}';
    }

}