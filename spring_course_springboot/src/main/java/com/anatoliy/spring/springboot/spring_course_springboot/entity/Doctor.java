package com.anatoliy.spring.springboot.spring_course_springboot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "врач")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "КодСотрудника")
    private int kodSotrudnika;

    @Column(name = "ФИО")
    private String fio;

    @Column(name = "КодСпециализации")
    private int kodSpecializatsii;

    @Column(name = "НомерКабинета")
    private int nomerKabineta;

    @Column(name = "СтатусРаботы")
    private String statusRaboty;

    @Column(name = "Логин")
    private String login;

    @Column(name = "Пароль")
    private String parol;

    // Transient поле - не сохраняется в БД, вычисляется на лету
    @Transient
    private String role;

    @Transient
    private String specializationName;

    // Конструктор по умолчанию
    public Doctor() {
    }

    // Конструктор со всеми полями
    public Doctor(int kodSotrudnika, String fio, int kodSpecializatsii, int nomerKabineta,
                  String statusRaboty, String login, String parol) {
        this.kodSotrudnika = kodSotrudnika;
        this.fio = fio;
        this.kodSpecializatsii = kodSpecializatsii;
        this.nomerKabineta = nomerKabineta;
        this.statusRaboty = statusRaboty;
        this.login = login;
        this.parol = parol;
    }

    // Геттеры и сеттеры
    public int getKodSotrudnika() {
        return kodSotrudnika;
    }

    public void setKodSotrudnika(int kodSotrudnika) {
        this.kodSotrudnika = kodSotrudnika;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getKodSpecializatsii() {
        return kodSpecializatsii;
    }

    public void setKodSpecializatsii(int kodSpecializatsii) {
        this.kodSpecializatsii = kodSpecializatsii;
    }

    public int getNomerKabineta() {
        return nomerKabineta;
    }

    public void setNomerKabineta(int nomerKabineta) {
        this.nomerKabineta = nomerKabineta;
    }

    public String getStatusRaboty() {
        return statusRaboty;
    }

    public void setStatusRaboty(String statusRaboty) {
        this.statusRaboty = statusRaboty;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getParol() {
        return parol;
    }

    public void setParol(String parol) {
        this.parol = parol;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }
}