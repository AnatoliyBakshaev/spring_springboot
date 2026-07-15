package com.anatoliy.spring.springboot.spring_course_springboot.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "приём")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "КодПриёма")
    private int kodPriema;

    @Column(name = "КодПациента")
    private int kodPacienta;

    @Column(name = "КодСотрудника")
    private int kodSotrudnika;

    @Column(name = "ДатаВремяПриема")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVremyaPriema;

    @Column(name = "СтатусВыполненияПриёма")
    private boolean statusVypolneniyaPriema;

    @Column(name = "Жалобы")
    private String zhaloby;

    @Column(name = "Диагноз")
    private String diagnoz;

    // Конструктор по умолчанию
    public Appointment() {
    }

    // Конструктор со всеми полями
    public Appointment(int kodPriema, int kodPacienta, int kodSotrudnika, Date dataVremyaPriema,
                       boolean statusVypolneniyaPriema, String zhaloby, String diagnoz) {
        this.kodPriema = kodPriema;
        this.kodPacienta = kodPacienta;
        this.kodSotrudnika = kodSotrudnika;
        this.dataVremyaPriema = dataVremyaPriema;
        this.statusVypolneniyaPriema = statusVypolneniyaPriema;
        this.zhaloby = zhaloby;
        this.diagnoz = diagnoz;
    }

    // Конструктор без ID (для создания новых записей)
    public Appointment(int kodPacienta, int kodSotrudnika, Date dataVremyaPriema,
                       String zhaloby, String diagnoz) {
        this.kodPacienta = kodPacienta;
        this.kodSotrudnika = kodSotrudnika;
        this.dataVremyaPriema = dataVremyaPriema;
        this.zhaloby = zhaloby;
        this.diagnoz = diagnoz;
        this.statusVypolneniyaPriema = false; // По умолчанию не выполнен
    }

    // Геттеры и сеттеры
    public int getKodPriema() {
        return kodPriema;
    }

    public void setKodPriema(int kodPriema) {
        this.kodPriema = kodPriema;
    }

    public int getKodPacienta() {
        return kodPacienta;
    }

    public void setKodPacienta(int kodPacienta) {
        this.kodPacienta = kodPacienta;
    }

    public int getKodSotrudnika() {
        return kodSotrudnika;
    }

    public void setKodSotrudnika(int kodSotrudnika) {
        this.kodSotrudnika = kodSotrudnika;
    }

    public Date getDataVremyaPriema() {
        return dataVremyaPriema;
    }

    public void setDataVremyaPriema(Date dataVremyaPriema) {
        this.dataVremyaPriema = dataVremyaPriema;
    }

    public boolean isStatusVypolneniyaPriema() {
        return statusVypolneniyaPriema;
    }

    public void setStatusVypolneniyaPriema(boolean statusVypolneniyaPriema) {
        this.statusVypolneniyaPriema = statusVypolneniyaPriema;
    }

    public String getZhaloby() {
        return zhaloby;
    }

    public void setZhaloby(String zhaloby) {
        this.zhaloby = zhaloby;
    }

    public String getDiagnoz() {
        return diagnoz;
    }

    public void setDiagnoz(String diagnoz) {
        this.diagnoz = diagnoz;
    }

    // Для удобства - метод toString()
    @Override
    public String toString() {
        return "Appointment{" +
                "kodPriema=" + kodPriema +
                ", kodPacienta=" + kodPacienta +
                ", kodSotrudnika=" + kodSotrudnika +
                ", dataVremyaPriema=" + dataVremyaPriema +
                ", statusVypolneniyaPriema=" + statusVypolneniyaPriema +
                ", zhaloby='" + zhaloby + '\'' +
                ", diagnoz='" + diagnoz + '\'' +
                '}';
    }
}

