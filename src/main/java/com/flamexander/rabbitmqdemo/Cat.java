package com.flamexander.rabbitmqdemo;

import java.io.Serializable;

public class Cat implements Serializable {
    private String name;

    public Cat() {
    }

    public Cat(String name) {
        this.name = name;
    }

    public void info() {
        System.out.println("Cat " + name);
    }
}
