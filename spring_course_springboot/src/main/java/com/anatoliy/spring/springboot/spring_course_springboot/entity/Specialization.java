package com.anatoliy.spring.springboot.spring_course_springboot.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "специализация")
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "КодСпециализации")
    private int kodSpecializatsii;

    @Column(name = "НазваниеДолжности")
    private String nazvanieDolzhnosti;

    @Column(name = "КвалификацияСотрудника")
    private String kvalifikatsiyaSotrudnika;

    // Конструктор по умолчанию
    public Specialization() {
    }

    // Конструктор со всеми полями
    public Specialization(int kodSpecializatsii, String nazvanieDolzhnosti, String kvalifikatsiyaSotrudnika) {
        this.kodSpecializatsii = kodSpecializatsii;
        this.nazvanieDolzhnosti = nazvanieDolzhnosti;
        this.kvalifikatsiyaSotrudnika = kvalifikatsiyaSotrudnika;
    }

    // Конструктор без ID (для создания новых записей)
    public Specialization(String nazvanieDolzhnosti, String kvalifikatsiyaSotrudnika) {
        this.nazvanieDolzhnosti = nazvanieDolzhnosti;
        this.kvalifikatsiyaSotrudnika = kvalifikatsiyaSotrudnika;
    }

    // Геттеры и сеттеры
    public int getKodSpecializatsii() {
        return kodSpecializatsii;
    }

    public void setKodSpecializatsii(int kodSpecializatsii) {
        this.kodSpecializatsii = kodSpecializatsii;
    }

    public String getNazvanieDolzhnosti() {
        return nazvanieDolzhnosti;
    }

    public void setNazvanieDolzhnosti(String nazvanieDolzhnosti) {
        this.nazvanieDolzhnosti = nazvanieDolzhnosti;
    }

    public String getKvalifikatsiyaSotrudnika() {
        return kvalifikatsiyaSotrudnika;
    }

    public void setKvalifikatsiyaSotrudnika(String kvalifikatsiyaSotrudnika) {
        this.kvalifikatsiyaSotrudnika = kvalifikatsiyaSotrudnika;
    }
}

