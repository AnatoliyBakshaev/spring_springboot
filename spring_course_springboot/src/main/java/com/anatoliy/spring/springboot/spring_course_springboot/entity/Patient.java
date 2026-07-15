package com.anatoliy.spring.springboot.spring_course_springboot.entity;


import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name="пациент")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "КодПациента")
    private int kodPacienta;

    @Column(name = "ФИО")
    private String fio;

    @Column(name = "ДатаРождения")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRozhdeniya;

    @Column(name = "Телефон")
    private String telefon;

    @Column(name = "НомерПолиса")
    private Long nomerPolisa;

    @Column(name = "Логин")
    private String login;

    @Column(name = "Пароль")
    private String parol;

    @Transient
    private String dataRozhdeniyaString;
    // Конструктор по умолчанию
    public  Patient() {
    }

    // Конструктор со всеми полями
    public Patient(int kodPacienta, String fio, Date dataRozhdeniya, String telefon,
                   Long nomerPolisa, String login, String parol) {
        this.kodPacienta = kodPacienta;
        this.fio = fio;
        this.dataRozhdeniya = dataRozhdeniya;
        this.telefon = telefon;
        this.nomerPolisa = nomerPolisa;
        this.login = login;
        this.parol = parol;
    }

    // Геттеры и сеттеры
    public int getKodPacienta() {
        return kodPacienta;
    }

    public void setKodPacienta(int kodPacienta) {
        this.kodPacienta = kodPacienta;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Date getDataRozhdeniya() {
        return dataRozhdeniya;
    }

    public void setDataRozhdeniya(Date dataRozhdeniya) {
        this.dataRozhdeniya = dataRozhdeniya;
    }
    public String getDataRozhdeniyaString() {
        return dataRozhdeniyaString;
    }

    public void setDataRozhdeniyaString(String dataRozhdeniyaString) {
        this.dataRozhdeniyaString = dataRozhdeniyaString;
    }
    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Long getNomerPolisa() {
        return nomerPolisa;
    }

    public void setNomerPolisa(Long nomerPolisa) {
        this.nomerPolisa = nomerPolisa;
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
}
