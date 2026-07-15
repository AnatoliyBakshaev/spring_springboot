package com.anatoliy.spring.springboot.spring_course_springboot.entity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "направлениянауслуги")
@IdClass(ServiceDirectionId.class)
public class ServiceDirection implements Serializable {

    @Id
    @Column(name = "КодПриёма")
    private int kodPriema;

    @Id
    @Column(name = "КодУслуги")
    private int kodUslugi;

    @Column(name = "Примечание")
    private String primechanie;

    @Column(name = "СтатусВыполненияУслуги")
    private boolean statusVypolneniyaUslugi;

    @Column(name = "Норма")
    private boolean norma;

    @Column(name = "ДатаУслуги")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUslugi;

    @Column(name = "ПодробныеРезультатыУслуги")
    private String podrobnyeRezultatyUslugi;

    // Транзиентные поля для связанных данных
    @Transient
    private String serviceName;

    @Transient
    private String serviceType;

    @Transient
    private Double serviceCost;

    public ServiceDirection() {}

    public ServiceDirection(int kodPriema, int kodUslugi, String primechanie,
                            boolean statusVypolneniyaUslugi, boolean norma,
                            Date dataUslugi, String podrobnyeRezultatyUslugi) {
        this.kodPriema = kodPriema;
        this.kodUslugi = kodUslugi;
        this.primechanie = primechanie;
        this.statusVypolneniyaUslugi = statusVypolneniyaUslugi;
        this.norma = norma;
        this.dataUslugi = dataUslugi;
        this.podrobnyeRezultatyUslugi = podrobnyeRezultatyUslugi;
    }

    // Геттеры и сеттеры
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

    public String getPrimechanie() {
        return primechanie;
    }

    public void setPrimechanie(String primechanie) {
        this.primechanie = primechanie;
    }

    public boolean isStatusVypolneniyaUslugi() {
        return statusVypolneniyaUslugi;
    }

    public void setStatusVypolneniyaUslugi(boolean statusVypolneniyaUslugi) {
        this.statusVypolneniyaUslugi = statusVypolneniyaUslugi;
    }

    public boolean isNorma() {
        return norma;
    }

    public void setNorma(boolean norma) {
        this.norma = norma;
    }

    public Date getDataUslugi() {
        return dataUslugi;
    }

    public void setDataUslugi(Date dataUslugi) {
        this.dataUslugi = dataUslugi;
    }

    public String getPodrobnyeRezultatyUslugi() {
        return podrobnyeRezultatyUslugi;
    }

    public void setPodrobnyeRezultatyUslugi(String podrobnyeRezultatyUslugi) {
        this.podrobnyeRezultatyUslugi = podrobnyeRezultatyUslugi;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(Double serviceCost) {
        this.serviceCost = serviceCost;
    }
}