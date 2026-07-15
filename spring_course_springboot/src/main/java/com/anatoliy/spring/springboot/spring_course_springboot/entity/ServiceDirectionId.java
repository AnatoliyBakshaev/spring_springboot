package com.anatoliy.spring.springboot.spring_course_springboot.entity;

import java.io.Serializable;
import java.util.Objects;

public class ServiceDirectionId implements Serializable {

    private int kodPriema;
    private int kodUslugi;

    public ServiceDirectionId() {}

    public ServiceDirectionId(int kodPriema, int kodUslugi) {
        this.kodPriema = kodPriema;
        this.kodUslugi = kodUslugi;
    }

    public int getKodPriema() {
        return kodPriema;
    }

    public void setKodPriema(int kodPriema) {
        this.kodPriema = kodPriema;
    }

    public int getKodUslugi() {
        return kodUslugi;
    }

    public void setKodUslugi(int kodUslugi) {
        this.kodUslugi = kodUslugi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceDirectionId that = (ServiceDirectionId) o;
        return kodPriema == that.kodPriema && kodUslugi == that.kodUslugi;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kodPriema, kodUslugi);
    }
}