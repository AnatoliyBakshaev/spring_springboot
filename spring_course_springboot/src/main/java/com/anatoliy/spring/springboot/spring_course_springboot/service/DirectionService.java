package com.anatoliy.spring.springboot.spring_course_springboot.service;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.ServiceDirection;
import java.util.List;

public interface DirectionService {

    // Получить все направления на услуги
    public List<ServiceDirection> getAllDirections();

    // Сохранить направление на услугу
    public void saveDirection(ServiceDirection serviceDirection);

    // Получить направление по составному ключу
    public ServiceDirection getDirection(int kodPriema, int kodUslugi);

    // Удалить направление
    public void deleteDirection(int kodPriema, int kodUslugi);

    // Получить все направления для конкретного приёма
    public List<ServiceDirection> getDirectionsByAppointment(int kodPriema);

    // Получить все направления для конкретного пациента
    public List<ServiceDirection> getDirectionsByPatient(int kodPacienta);

    // Обновить результаты выполнения услуги
    public void updateDirectionResult(int kodPriema, int kodUslugi, String results, boolean norma);
}