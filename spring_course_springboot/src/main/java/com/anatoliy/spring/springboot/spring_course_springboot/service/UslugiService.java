package com.anatoliy.spring.springboot.spring_course_springboot.service;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.Uslugi;
import java.util.List;

public interface UslugiService {
    public List<Uslugi> getAllUslugi();
    public Uslugi getUsluga(int id);
    public List<Uslugi> getUslugiByType(String type);
    public void saveUsluga(Uslugi usluga);
}