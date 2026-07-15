package com.anatoliy.spring.springboot.spring_course_springboot.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "услуги")
public class Uslugi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "КодУслуги")
    private int kodUslugi;

    @Column(name = "Название")
    private String nazvanie;

    @Column(name = "Тип")
    private String tip;

    @Column(name = "СтоимостьУслуги")
    private Double stoimostUslugi;

    // Конструктор по умолчанию
    public Uslugi() {
    }

    // Конструктор со всеми полями
    public Uslugi(int kodUslugi, String nazvanie, String tip, Double stoimostUslugi) {
        this.kodUslugi = kodUslugi;
        this.nazvanie = nazvanie;
        this.tip = tip;
        this.stoimostUslugi = stoimostUslugi;
    }

    // Конструктор без ID (для создания новых записей)
    public Uslugi(String nazvanie, String tip, Double stoimostUslugi) {
        this.nazvanie = nazvanie;
        this.tip = tip;
        this.stoimostUslugi = stoimostUslugi;
    }

    // Геттеры и сеттеры
    public int getKodUslugi() {
        return kodUslugi;
    }

    public void setKodUslugi(int kodUslugi) {
        this.kodUslugi = kodUslugi;
    }

    public String getNazvanie() {
        return nazvanie;
    }

    public void setNazvanie(String nazvanie) {
        this.nazvanie = nazvanie;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Double getStoimostUslugi() {
        return stoimostUslugi;
    }

    public void setStoimostUslugi(Double stoimostUslugi) {
        this.stoimostUslugi = stoimostUslugi;
    }
}