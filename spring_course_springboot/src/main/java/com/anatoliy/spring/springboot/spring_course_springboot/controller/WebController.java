package com.anatoliy.spring.springboot.spring_course_springboot.controller;

import com.anatoliy.spring.springboot.spring_course_springboot.entity.*;
import com.anatoliy.spring.springboot.spring_course_springboot.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class WebController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private SpecializationService specializationService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UslugiService uslugiService;

    @Autowired
    private DirectionService directionService;

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "register";
    }

    @PostMapping("/register")
    public String registerPatient(@RequestParam("day") String day,
                                  @RequestParam("month") String month,
                                  @RequestParam("year") String year,
                                  Patient patient,
                                  Model model) {
        try {
            if (day != null && month != null && year != null &&
                    !day.isEmpty() && !month.isEmpty() && !year.isEmpty()) {
                String dateString = year + "-" + month + "-" + day;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(dateString);
                patient.setDataRozhdeniya(date);
            }

            patientService.savePatient(patient);
            model.addAttribute("successMessage", "Пациент успешно зарегистрирован!");
            model.addAttribute("patient", new Patient());
            return "register";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка регистрации: " + e.getMessage());
            return "register";
        }
    }

    @GetMapping("/patients")
    public String showAllPatients(Model model) {
        List<Patient> patients = patientService.getAllPatient();
        model.addAttribute("patients", patients);
        return "patients";
    }
    // Панель главного врача с графиком
    @GetMapping("/doctor/chief/dashboard")
    public String chiefDashboard(HttpSession session, Model model) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"CHIEF".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        long doctorsCount = doctorService.getDoctorsCount();
        long patientsCount = patientService.getPatientsCount();

        List<Doctor> specialists = doctorService.getAllDoctors();
        specialists.removeIf(d -> "CHIEF".equals(d.getRole()));
        List<Specialization> specializations = specializationService.getAllSpecializations();

        // Данные для круговой диаграммы
        model.addAttribute("doctor", doctor);
        model.addAttribute("doctorsCount", doctorsCount);
        model.addAttribute("patientsCount", patientsCount);
        model.addAttribute("specialists", specialists);
        model.addAttribute("specializations", specializations);

        return "chief_dashboard";
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("login") String login,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        Patient patient = patientService.authenticate(login, password);

        if (patient != null) {
            session.setAttribute("loggedPatient", patient);
            return "redirect:/patient/dashboard";
        } else {
            model.addAttribute("error", "Неверный логин или пароль");
            return "login";
        }
    }

    @GetMapping("/patient/dashboard")
    public String patientDashboard(HttpSession session, Model model) {
        Patient patient = (Patient) session.getAttribute("loggedPatient");

        if (patient == null) {
            return "redirect:/login";
        }

        model.addAttribute("patient", patient);
        return "patient_dashboard";
    }

    // Страница записи на приём
    @GetMapping("/patient/book-appointment")
    public String showBookAppointmentForm(HttpSession session, Model model) {
        Patient patient = (Patient) session.getAttribute("loggedPatient");

        if (patient == null) {
            return "redirect:/login";
        }

        List<Doctor> doctors = doctorService.getAllDoctors();

        // Обогащаем каждого врача информацией о специализации
        for (Doctor doctor : doctors) {
            String specName = specializationService.getSpecializationName(doctor.getKodSpecializatsii());
            doctor.setSpecializationName(specName);
        }

        model.addAttribute("doctors", doctors);
        model.addAttribute("patient", patient);
        model.addAttribute("appointment", new Appointment());

        return "book_appointment";
    }

    // Обработка записи на приём
    @PostMapping("/patient/book-appointment")
    public String bookAppointment(@RequestParam("doctorId") int doctorId,
                                  @RequestParam("appointmentDate") String appointmentDateString,
                                  @RequestParam("appointmentTime") String appointmentTime,
                                  @RequestParam("complaints") String complaints,
                                  HttpSession session,
                                  Model model) {
        Patient patient = (Patient) session.getAttribute("loggedPatient");

        if (patient == null) {
            return "redirect:/login";
        }

        try {
            // Формируем дату и время приёма
            String dateTimeString = appointmentDateString + " " + appointmentTime;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date appointmentDateTime = formatter.parse(dateTimeString);

            // Создаем новую запись
            Appointment appointment = new Appointment();
            appointment.setKodPacienta(patient.getKodPacienta());
            appointment.setKodSotrudnika(doctorId);
            appointment.setDataVremyaPriema(appointmentDateTime);
            appointment.setZhaloby(complaints);
            appointment.setStatusVypolneniyaPriema(false);

            appointmentService.saveAppointment(appointment);

            model.addAttribute("successMessage", "Запись на приём успешно создана!");
            return "redirect:/patient/appointments";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при записи: " + e.getMessage());
            List<Doctor> doctors = doctorService.getAllDoctors();
            model.addAttribute("doctors", doctors);
            model.addAttribute("patient", patient);
            return "book_appointment";
        }
    }

    // Отмена записи
    @GetMapping("/patient/cancel-appointment/{id}")
    public String cancelAppointment(@PathVariable("id") int appointmentId, HttpSession session) {
        Patient patient = (Patient) session.getAttribute("loggedPatient");

        if (patient == null) {
            return "redirect:/login";
        }

        appointmentService.deleteAppointment(appointmentId);
        return "redirect:/patient/appointments";
    }





    @GetMapping("/doctor/login")
    public String showDoctorLoginForm() {
        return "doctor_login";
    }

    @PostMapping("/doctor/login")
    public String doctorLogin(@RequestParam("login") String login,
                              @RequestParam("password") String password,
                              HttpSession session,
                              Model model) {
        Doctor doctor = doctorService.authenticate(login, password);

        if (doctor != null) {
            session.setAttribute("loggedDoctor", doctor);

            // Перенаправление в зависимости от роли (определяется автоматически по специализации)
            if ("CHIEF".equals(doctor.getRole())) {
                return "redirect:/doctor/chief/dashboard";
            } else {
                return "redirect:/doctor/specialist/dashboard";
            }
        } else {
            model.addAttribute("error", "Неверный логин или пароль");
            return "doctor_login";
        }
    }

    @GetMapping("/doctor/specialist/dashboard")
    public String specialistDashboard(HttpSession session, Model model) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"SPECIALIST".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        // Получаем список пациентов, которые были на приёме у этого врача (уникальные)
        List<Patient> patients = appointmentService.getPatientsByDoctor(doctor.getKodSotrudnika());

        model.addAttribute("doctor", doctor);
        model.addAttribute("patients", patients);

        return "specialist_dashboard";
    }
    // Просмотр приёмов для врача-специалиста
    @GetMapping("/doctor/specialist/appointments")
    public String showDoctorAppointments(HttpSession session, Model model) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"SPECIALIST".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        List<Appointment> upcomingAppointments = appointmentService.getUpcomingAppointmentsByDoctor(doctor.getKodSotrudnika());
        List<Appointment> todayAppointments = appointmentService.getTodayAppointmentsByDoctor(doctor.getKodSotrudnika());
        List<Appointment> allAppointments = appointmentService.getAppointmentsByDoctor(doctor.getKodSotrudnika());

        // Для каждого приёма получаем информацию о пациенте и сохраняем в Map
        Map<Integer, Patient> patientMap = new HashMap<>();

        for (Appointment appointment : upcomingAppointments) {
            int patientId = appointment.getKodPacienta();
            if (!patientMap.containsKey(patientId)) {
                patientMap.put(patientId, patientService.getPatient(patientId));
            }
        }

        for (Appointment appointment : todayAppointments) {
            int patientId = appointment.getKodPacienta();
            if (!patientMap.containsKey(patientId)) {
                patientMap.put(patientId, patientService.getPatient(patientId));
            }
        }

        for (Appointment appointment : allAppointments) {
            int patientId = appointment.getKodPacienta();
            if (!patientMap.containsKey(patientId)) {
                patientMap.put(patientId, patientService.getPatient(patientId));
            }
        }

        model.addAttribute("doctor", doctor);
        model.addAttribute("upcomingAppointments", upcomingAppointments);
        model.addAttribute("todayAppointments", todayAppointments);
        model.addAttribute("allAppointments", allAppointments);
        model.addAttribute("patientMap", patientMap);

        return "doctor_appointments";
    }

    // Страница деталей приёма для врача
    @GetMapping("/doctor/appointment/{id}")
    public String showAppointmentDetails(@PathVariable("id") int appointmentId, HttpSession session, Model model) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null) {
            return "redirect:/doctor/login";
        }

        Appointment appointment = appointmentService.getAppointment(appointmentId);

        // Проверяем, что приём принадлежит этому врачу
        if (appointment.getKodSotrudnika() != doctor.getKodSotrudnika()) {
            return "redirect:/doctor/specialist/appointments";
        }

        // Получаем информацию о пациенте
        Patient patient = patientService.getPatient(appointment.getKodPacienta());

        model.addAttribute("appointment", appointment);
        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);

        return "appointment_details";
    }

    // Отметка о выполнении приёма
    @PostMapping("/doctor/appointment/complete/{id}")
    public String completeAppointment(@PathVariable("id") int appointmentId,
                                      @RequestParam("diagnosis") String diagnosis,
                                      HttpSession session) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null) {
            return "redirect:/doctor/login";
        }

        appointmentService.updateAppointmentStatus(appointmentId, true, diagnosis);

        return "redirect:/doctor/specialist/appointments";
    }

    // Отмена приёма врачом
    @PostMapping("/doctor/appointment/cancel/{id}")
    public String cancelAppointmentByDoctor(@PathVariable("id") int appointmentId, HttpSession session) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null) {
            return "redirect:/doctor/login";
        }

        appointmentService.deleteAppointment(appointmentId);

        return "redirect:/doctor/specialist/appointments";
    }



    // Просмотр всех приёмов для главного врача
    @GetMapping("/doctor/chief/all-appointments")
    public String showAllAppointmentsForChief(HttpSession session, Model model) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"CHIEF".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        List<Appointment> todayAppointments = appointmentService.getTodayAppointments();
        List<Appointment> upcomingAppointments = appointmentService.getUpcomingAppointments();

        Map<Integer, Patient> patientMap = new HashMap<>();
        Map<Integer, Doctor> doctorMap = new HashMap<>();

        for (Appointment appointment : allAppointments) {
            int patientId = appointment.getKodPacienta();
            int doctorId = appointment.getKodSotrudnika();

            if (!patientMap.containsKey(patientId)) {
                patientMap.put(patientId, patientService.getPatient(patientId));
            }
            if (!doctorMap.containsKey(doctorId)) {
                doctorMap.put(doctorId, doctorService.getDoctor(doctorId));
            }
        }

        for (Appointment appointment : todayAppointments) {
            int patientId = appointment.getKodPacienta();
            int doctorId = appointment.getKodSotrudnika();

            if (!patientMap.containsKey(patientId)) {
                patientMap.put(patientId, patientService.getPatient(patientId));
            }
            if (!doctorMap.containsKey(doctorId)) {
                doctorMap.put(doctorId, doctorService.getDoctor(doctorId));
            }
        }

        for (Appointment appointment : upcomingAppointments) {
            int patientId = appointment.getKodPacienta();
            int doctorId = appointment.getKodSotrudnika();

            if (!patientMap.containsKey(patientId)) {
                patientMap.put(patientId, patientService.getPatient(patientId));
            }
            if (!doctorMap.containsKey(doctorId)) {
                doctorMap.put(doctorId, doctorService.getDoctor(doctorId));
            }
        }

        model.addAttribute("doctor", doctor);
        model.addAttribute("allAppointments", allAppointments);
        model.addAttribute("todayAppointments", todayAppointments);
        model.addAttribute("upcomingAppointments", upcomingAppointments);
        model.addAttribute("patientMap", patientMap);
        model.addAttribute("doctorMap", doctorMap);

        return "chief_all_appointments";
    }


    @GetMapping("/doctor/chief/my-appointments")
    public String showChiefAppointments(HttpSession session, Model model) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"CHIEF".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        // Получаем приёмы главного врача (где он является врачом)
        List<Appointment> upcomingAppointments = appointmentService.getUpcomingAppointmentsByDoctor(doctor.getKodSotrudnika());
        List<Appointment> todayAppointments = appointmentService.getTodayAppointmentsByDoctor(doctor.getKodSotrudnika());
        List<Appointment> allAppointments = appointmentService.getAppointmentsByDoctor(doctor.getKodSotrudnika());

        // Получаем информацию о пациентах
        Map<Integer, Patient> patientMap = new HashMap<>();

        for (Appointment appointment : upcomingAppointments) {
            int patientId = appointment.getKodPacienta();
            if (!patientMap.containsKey(patientId)) {
                patientMap.put(patientId, patientService.getPatient(patientId));
            }
        }

        for (Appointment appointment : todayAppointments) {
            int patientId = appointment.getKodPacienta();
            if (!patientMap.containsKey(patientId)) {
                patientMap.put(patientId, patientService.getPatient(patientId));
            }
        }

        for (Appointment appointment : allAppointments) {
            int patientId = appointment.getKodPacienta();
            if (!patientMap.containsKey(patientId)) {
                patientMap.put(patientId, patientService.getPatient(patientId));
            }
        }

        model.addAttribute("doctor", doctor);
        model.addAttribute("upcomingAppointments", upcomingAppointments);
        model.addAttribute("todayAppointments", todayAppointments);
        model.addAttribute("allAppointments", allAppointments);
        model.addAttribute("patientMap", patientMap);

        return "chief_my_appointments";
    }

    // Детали приёма для главного врача (его личный приём)
    @GetMapping("/doctor/chief/my-appointment/{id}")
    public String showChiefAppointmentDetails(@PathVariable("id") int appointmentId, HttpSession session, Model model) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"CHIEF".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        Appointment appointment = appointmentService.getAppointment(appointmentId);

        // Проверяем, что приём принадлежит этому врачу
        if (appointment.getKodSotrudnika() != doctor.getKodSotrudnika()) {
            return "redirect:/doctor/chief/my-appointments";
        }

        Patient patient = patientService.getPatient(appointment.getKodPacienta());

        model.addAttribute("appointment", appointment);
        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);

        return "chief_my_appointment_details";
    }

    // Отметка о выполнении приёма для главного врача
    @PostMapping("/doctor/chief/my-appointment/complete/{id}")
    public String completeChiefAppointment(@PathVariable("id") int appointmentId,
                                           @RequestParam("diagnosis") String diagnosis,
                                           HttpSession session) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"CHIEF".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        appointmentService.updateAppointmentStatus(appointmentId, true, diagnosis);

        return "redirect:/doctor/chief/my-appointments";
    }

    // Отмена приёма главным врачом
    @GetMapping("/doctor/chief/cancel-appointment/{id}")
    public String cancelAppointmentByChief(@PathVariable("id") int appointmentId, HttpSession session) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"CHIEF".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        appointmentService.deleteAppointment(appointmentId);

        return "redirect:/doctor/chief/all-appointments";
    }

    // Управление врачами (список всех врачей)
    @GetMapping("/doctor/chief/manage-doctors")
    public String manageDoctors(HttpSession session, Model model) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"CHIEF".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        List<Doctor> allDoctors = doctorService.getAllDoctors();
        List<Specialization> specializations = specializationService.getAllSpecializations();

        model.addAttribute("doctor", doctor);
        model.addAttribute("allDoctors", allDoctors);
        model.addAttribute("specializations", specializations);

        return "manage_doctors";
    }

    // Форма добавления нового врача
    @GetMapping("/doctor/chief/add-doctor")
    public String showAddDoctorForm(HttpSession session, Model model) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"CHIEF".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        List<Specialization> specializations = specializationService.getAllSpecializations();

        model.addAttribute("specializations", specializations);
        model.addAttribute("newDoctor", new Doctor());

        return "add_doctor";
    }

    // Добавление нового врача
    @PostMapping("/doctor/chief/add-doctor")
    public String addDoctor(@RequestParam("fio") String fio,
                            @RequestParam("specializationId") int specializationId,
                            @RequestParam("nomerKabineta") int nomerKabineta,
                            @RequestParam("statusRaboty") String statusRaboty,
                            @RequestParam("login") String login,
                            @RequestParam("password") String password,
                            HttpSession session,
                            Model model) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"CHIEF".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        try {
            // Проверяем, не существует ли уже такой логин
            Doctor existingDoctor = doctorService.authenticate(login, password);
            if (existingDoctor != null) {
                List<Specialization> specializations = specializationService.getAllSpecializations();
                model.addAttribute("specializations", specializations);
                model.addAttribute("errorMessage", "Врач с таким логином уже существует!");
                return "add_doctor";
            }

            Doctor newDoctor = new Doctor();
            newDoctor.setFio(fio);
            newDoctor.setKodSpecializatsii(specializationId);
            newDoctor.setNomerKabineta(nomerKabineta);
            newDoctor.setStatusRaboty(statusRaboty);
            newDoctor.setLogin(login);
            newDoctor.setParol(password);

            doctorService.saveDoctor(newDoctor);

            return "redirect:/doctor/chief/manage-doctors";

        } catch (Exception e) {
            List<Specialization> specializations = specializationService.getAllSpecializations();
            model.addAttribute("specializations", specializations);
            model.addAttribute("errorMessage", "Ошибка при добавлении врача: " + e.getMessage());
            return "add_doctor";
        }
    }

    // Удаление врача
    @GetMapping("/doctor/chief/delete-doctor/{id}")
    public String deleteDoctor(@PathVariable("id") int doctorId, HttpSession session) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"CHIEF".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        // Не даём удалить самого себя
        if (doctor.getKodSotrudnika() == doctorId) {
            return "redirect:/doctor/chief/manage-doctors?error=不能删除自己";
        }

        doctorService.deleteDoctor(doctorId);

        return "redirect:/doctor/chief/manage-doctors";
    }

    // Редактирование врача
    @GetMapping("/doctor/chief/edit-doctor/{id}")
    public String showEditDoctorForm(@PathVariable("id") int doctorId, HttpSession session, Model model) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"CHIEF".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        Doctor editDoctor = doctorService.getDoctor(doctorId);
        List<Specialization> specializations = specializationService.getAllSpecializations();

        model.addAttribute("editDoctor", editDoctor);
        model.addAttribute("specializations", specializations);

        return "edit_doctor";
    }

    // Обновление данных врача
    @PostMapping("/doctor/chief/edit-doctor/{id}")
    public String updateDoctor(@PathVariable("id") int doctorId,
                               @RequestParam("fio") String fio,
                               @RequestParam("specializationId") int specializationId,
                               @RequestParam("nomerKabineta") int nomerKabineta,
                               @RequestParam("statusRaboty") String statusRaboty,
                               @RequestParam("login") String login,
                               @RequestParam("password") String password,
                               HttpSession session) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"CHIEF".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        Doctor updateDoctor = doctorService.getDoctor(doctorId);
        updateDoctor.setFio(fio);
        updateDoctor.setKodSpecializatsii(specializationId);
        updateDoctor.setNomerKabineta(nomerKabineta);
        updateDoctor.setStatusRaboty(statusRaboty);
        updateDoctor.setLogin(login);
        updateDoctor.setParol(password);

        doctorService.saveDoctor(updateDoctor);

        return "redirect:/doctor/chief/manage-doctors";
    }

    // Страница направлений для приёма (для врача)
    @GetMapping("/doctor/appointment/{id}/directions")
    public String showAppointmentDirections(@PathVariable("id") int appointmentId,
                                            HttpSession session,
                                            Model model) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null) {
            return "redirect:/doctor/login";
        }

        Appointment appointment = appointmentService.getAppointment(appointmentId);
        Patient patient = patientService.getPatient(appointment.getKodPacienta());
        List<Uslugi> allUslugi = uslugiService.getAllUslugi();
        List<ServiceDirection> existingDirections = directionService.getDirectionsByAppointment(appointmentId);

        // Обогащаем направления информацией об услугах
        for (ServiceDirection sd : existingDirections) {
            Uslugi usluga = uslugiService.getUsluga(sd.getKodUslugi());
            if (usluga != null) {
                sd.setServiceName(usluga.getNazvanie());
                sd.setServiceType(usluga.getTip());
                sd.setServiceCost(usluga.getStoimostUslugi());
            }
        }

        model.addAttribute("appointment", appointment);
        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);
        model.addAttribute("allUslugi", allUslugi);
        model.addAttribute("existingDirections", existingDirections);

        return "appointment_directions";
    }



    // Добавление направления
    @PostMapping("/doctor/appointment/{id}/add-direction")
    public String addDirection(@PathVariable("id") int appointmentId,
                               @RequestParam("uslugaId") int uslugaId,
                               @RequestParam("note") String note,
                               HttpSession session) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null) {
            return "redirect:/doctor/login";
        }

        // Проверяем, не существует ли уже такое направление
        ServiceDirection existing = directionService.getDirection(appointmentId, uslugaId);
        if (existing == null) {
            ServiceDirection sd = new ServiceDirection();
            sd.setKodPriema(appointmentId);
            sd.setKodUslugi(uslugaId);
            sd.setPrimechanie(note);
            sd.setDataUslugi(new Date());
            sd.setStatusVypolneniyaUslugi(false);
            sd.setNorma(false);

            directionService.saveDirection(sd);
        }

        return "redirect:/doctor/appointment/" + appointmentId + "/directions";
    }

    // Просмотр всех направлений для конкретного приёма (для пациента)
    @GetMapping("/patient/direction/{appointmentId}/all")
    public String showDirectionsByAppointment(@PathVariable("appointmentId") int appointmentId,
                                              HttpSession session,
                                              Model model) {
        Patient patient = (Patient) session.getAttribute("loggedPatient");

        if (patient == null) {
            return "redirect:/login";
        }

        // Проверяем, что приём принадлежит этому пациенту
        Appointment appointment = appointmentService.getAppointment(appointmentId);
        if (appointment.getKodPacienta() != patient.getKodPacienta()) {
            return "redirect:/patient/appointments";
        }

        List<ServiceDirection> directions = directionService.getDirectionsByAppointment(appointmentId);

        // Обогащаем направления информацией об услугах
        for (ServiceDirection sd : directions) {
            Uslugi usluga = uslugiService.getUsluga(sd.getKodUslugi());
            if (usluga != null) {
                sd.setServiceName(usluga.getNazvanie());
                sd.setServiceType(usluga.getTip());
                sd.setServiceCost(usluga.getStoimostUslugi());
            }
        }

        model.addAttribute("directions", directions);
        model.addAttribute("patient", patient);
        model.addAttribute("appointment", appointment);

        return "patient_directions_by_appointment";
    }

    // Удаление направления
    @GetMapping("/doctor/appointment/{appointmentId}/remove-direction/{uslugaId}")
    public String removeDirection(@PathVariable("appointmentId") int appointmentId,
                                  @PathVariable("uslugaId") int uslugaId,
                                  HttpSession session) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null) {
            return "redirect:/doctor/login";
        }

        directionService.deleteDirection(appointmentId, uslugaId);

        return "redirect:/doctor/appointment/" + appointmentId + "/directions";
    }

    // Форма для ввода результатов услуги
    @GetMapping("/doctor/direction/{appointmentId}/{uslugaId}/results")
    public String showDirectionResultsForm(@PathVariable("appointmentId") int appointmentId,
                                           @PathVariable("uslugaId") int uslugaId,
                                           HttpSession session,
                                           Model model) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null) {
            return "redirect:/doctor/login";
        }

        ServiceDirection sd = directionService.getDirection(appointmentId, uslugaId);
        Uslugi usluga = uslugiService.getUsluga(uslugaId);
        Appointment appointment = appointmentService.getAppointment(appointmentId);
        Patient patient = patientService.getPatient(appointment.getKodPacienta());

        model.addAttribute("direction", sd);
        model.addAttribute("usluga", usluga);
        model.addAttribute("patient", patient);
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("uslugaId", uslugaId);

        return "direction_results_form";
    }
    // Сохранение результатов услуги
    @PostMapping("/doctor/direction/{appointmentId}/{uslugaId}/save-results")
    public String saveDirectionResults(@PathVariable("appointmentId") int appointmentId,
                                       @PathVariable("uslugaId") int uslugaId,
                                       @RequestParam("results") String results,
                                       @RequestParam("norma") boolean norma,
                                       HttpSession session) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null) {
            return "redirect:/doctor/login";
        }

        directionService.updateDirectionResult(appointmentId, uslugaId, results, norma);

        return "redirect:/doctor/appointment/" + appointmentId + "/directions";
    }

    // Просмотр направлений пациентом
    @GetMapping("/patient/my-directions")
    public String showMyDirections(HttpSession session, Model model) {
        Patient patient = (Patient) session.getAttribute("loggedPatient");

        if (patient == null) {
            return "redirect:/login";
        }

        List<ServiceDirection> directions = directionService.getDirectionsByPatient(patient.getKodPacienta());

        // Обогащаем направления информацией об услугах
        for (ServiceDirection sd : directions) {
            Uslugi usluga = uslugiService.getUsluga(sd.getKodUslugi());
            if (usluga != null) {
                sd.setServiceName(usluga.getNazvanie());
                sd.setServiceType(usluga.getTip());
                sd.setServiceCost(usluga.getStoimostUslugi());
            }
        }

        model.addAttribute("directions", directions);
        model.addAttribute("patient", patient);

        return "patient_directions";
    }

    // Просмотр деталей направления пациентом
    @GetMapping("/patient/direction/{appointmentId}/{uslugaId}")
    public String showDirectionDetails(@PathVariable("appointmentId") int appointmentId,
                                       @PathVariable("uslugaId") int uslugaId,
                                       HttpSession session,
                                       Model model) {
        Patient patient = (Patient) session.getAttribute("loggedPatient");

        if (patient == null) {
            return "redirect:/login";
        }

        ServiceDirection sd = directionService.getDirection(appointmentId, uslugaId);
        Uslugi usluga = uslugiService.getUsluga(uslugaId);

        model.addAttribute("direction", sd);
        model.addAttribute("usluga", usluga);
        model.addAttribute("patient", patient);

        return "direction_details";
    }

    // Просмотр направлений на услуги для главного врача
    @GetMapping("/doctor/chief/appointment/{id}/directions")
    public String showChiefAppointmentDirections(@PathVariable("id") int appointmentId,
                                                 HttpSession session,
                                                 Model model) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"CHIEF".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        Appointment appointment = appointmentService.getAppointment(appointmentId);
        Patient patient = patientService.getPatient(appointment.getKodPacienta());
        List<Uslugi> allUslugi = uslugiService.getAllUslugi();
        List<ServiceDirection> existingDirections = directionService.getDirectionsByAppointment(appointmentId);

        // Обогащаем направления информацией об услугах
        for (ServiceDirection sd : existingDirections) {
            Uslugi usluga = uslugiService.getUsluga(sd.getKodUslugi());
            if (usluga != null) {
                sd.setServiceName(usluga.getNazvanie());
                sd.setServiceType(usluga.getTip());
                sd.setServiceCost(usluga.getStoimostUslugi());
            }
        }

        model.addAttribute("appointment", appointment);
        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);
        model.addAttribute("allUslugi", allUslugi);
        model.addAttribute("existingDirections", existingDirections);
        model.addAttribute("isChiefView", true); // Флаг для отображения режима просмотра

        return "chief_appointment_directions";
    }
    // Страница с моими записями для пациента
    @GetMapping("/patient/appointments")
    public String showMyAppointments(HttpSession session, Model model) {
        Patient patient = (Patient) session.getAttribute("loggedPatient");

        if (patient == null) {
            return "redirect:/login";
        }

        List<Appointment> allAppointments = appointmentService.getAppointmentsByPatient(patient.getKodPacienta());

        // Разделяем на предстоящие (не выполненные и дата в будущем) и историю (выполненные или просроченные)
        Date now = new Date();
        List<Appointment> upcomingAppointments = allAppointments.stream()
                .filter(a -> !a.isStatusVypolneniyaPriema() && a.getDataVremyaPriema().after(now))
                .collect(Collectors.toList());

        List<Appointment> historyAppointments = allAppointments.stream()
                .filter(a -> a.isStatusVypolneniyaPriema() || a.getDataVremyaPriema().before(now))
                .collect(Collectors.toList());

        model.addAttribute("upcomingAppointments", upcomingAppointments);
        model.addAttribute("historyAppointments", historyAppointments);
        model.addAttribute("patient", patient);

        return "my_appointments";
    }
    // Добавление новой услуги (для главного врача)
    @PostMapping("/doctor/chief/add-service")
    @ResponseBody
    public Map<String, Object> addService(@RequestParam("nazvanie") String nazvanie,
                                          @RequestParam("tip") String tip,
                                          @RequestParam("stoimostUslugi") Double stoimostUslugi,
                                          HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"CHIEF".equals(doctor.getRole())) {
            response.put("success", false);
            response.put("message", "Доступ запрещён");
            return response;
        }

        try {
            Uslugi newService = new Uslugi();
            newService.setNazvanie(nazvanie);
            newService.setTip(tip);
            newService.setStoimostUslugi(stoimostUslugi);

            uslugiService.saveUsluga(newService);

            response.put("success", true);
            response.put("message", "Услуга \"" + nazvanie + "\" успешно добавлена!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ошибка при добавлении услуги: " + e.getMessage());
        }

        return response;
    }
    @GetMapping("/doctor/chief/appointment/{id}")
    public String showAppointmentDetailsForChief(@PathVariable("id") int appointmentId,
                                                 HttpSession session,
                                                 Model model) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"CHIEF".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        Appointment appointment = appointmentService.getAppointment(appointmentId);
        Patient patient = patientService.getPatient(appointment.getKodPacienta());

        // Получаем информацию о враче, который вёл приём
        Doctor appointmentDoctor = doctorService.getDoctor(appointment.getKodSotrudnika());

        model.addAttribute("appointment", appointment);
        model.addAttribute("patient", patient);
        model.addAttribute("appointmentDoctor", appointmentDoctor);
        model.addAttribute("doctor", doctor);

        return "chief_appointment_details";
    }



    // Тестовый метод для демонстрации подзапросов (можно вызвать через браузер)
    @GetMapping("/test/subqueries")
    @ResponseBody
    public Map<String, Object> testSubqueries() {
        Map<String, Object> result = new HashMap<>();

        // 1. DISTINCT подзапрос - уникальные пациенты за последнюю неделю
        List<Patient> distinctPatients = appointmentService.getDistinctPatientsLastWeek();
        result.put("distinctPatientsLastWeek", distinctPatients.stream()
                .map(p -> Map.of("id", p.getKodPacienta(), "fio", p.getFio()))
                .collect(Collectors.toList()));
        result.put("distinctPatientsCount", distinctPatients.size());

        // 2. ORDER BY + LIMIT подзапрос - топ-5 врачей по количеству приёмов
        List<Object[]> topDoctors = appointmentService.getTop5DoctorsByAppointments();
        List<Map<String, Object>> doctorsList = new ArrayList<>();
        for (Object[] doc : topDoctors) {
            Map<String, Object> doctorMap = new HashMap<>();
            doctorMap.put("id", doc[0]);
            doctorMap.put("fio", doc[1]);
            doctorMap.put("cabinet", doc[2]);
            doctorMap.put("specialization", doc[3]);
            doctorMap.put("appointmentsCount", doc[4]);
            doctorsList.add(doctorMap);
        }
        result.put("top5Doctors", doctorsList);

        // 3. OFFSET + LIMIT подзапрос - пациенты с пагинацией (первые 5, сдвиг 0)
        List<Patient> patientsPage1 = appointmentService.getPatientsWithOffset(0, 5);
        result.put("patientsPage1_Offset0_Limit5", patientsPage1.stream()
                .map(p -> Map.of("id", p.getKodPacienta(), "fio", p.getFio()))
                .collect(Collectors.toList()));

        // Вторая страница (сдвиг 5, лимит 5)
        List<Patient> patientsPage2 = appointmentService.getPatientsWithOffset(5, 5);
        result.put("patientsPage2_Offset5_Limit5", patientsPage2.stream()
                .map(p -> Map.of("id", p.getKodPacienta(), "fio", p.getFio()))
                .collect(Collectors.toList()));

        result.put("message", "Подзапросы успешно выполнены!");
        result.put("operators_used", List.of("DISTINCT", "ORDER BY", "LIMIT", "OFFSET"));

        return result;
    }

    // Страница демонстрации подзапросов с пагинацией
    @GetMapping("/doctor/chief/subqueries")
    public String showSubqueriesDemo(HttpSession session,
                                     Model model,
                                     @RequestParam(defaultValue = "1") int page) {
        Doctor doctor = (Doctor) session.getAttribute("loggedDoctor");

        if (doctor == null || !"CHIEF".equals(doctor.getRole())) {
            return "redirect:/doctor/login";
        }

        int limit = 3;  // Количество записей на странице
        int offset = (page - 1) * limit;  // Смещение

        // 1. DISTINCT подзапрос
        List<Patient> distinctPatients = appointmentService.getDistinctPatientsLastWeek();

        // 2. ORDER BY + LIMIT подзапрос
        List<Object[]> topDoctors = appointmentService.getTop5DoctorsByAppointments();

        // 3. OFFSET + LIMIT подзапрос с пагинацией
        List<Patient> patientsPage = appointmentService.getPatientsWithOffset(offset, limit);
        long totalPatients = appointmentService.getTotalPatientsWithAppointments();

        // Расчёт количества страниц
        int totalPages = (int) Math.ceil((double) totalPatients / limit);

        model.addAttribute("distinctPatients", distinctPatients);
        model.addAttribute("topDoctors", topDoctors);
        model.addAttribute("patients", patientsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalPatients", totalPatients);
        model.addAttribute("limit", limit);

        return "subqueries_demo";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}