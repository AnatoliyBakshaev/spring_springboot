Healthcare Clinic Management System
📌 About the Project
This project is a client-server web application designed to automate the operations of a healthcare clinic. The system streamlines key medical processes: patient registration, appointment scheduling, medical records management, service referrals, and analytical reporting.

🛠 Technology Stack
Component	Technology
Backend	Java 17, Spring Boot 3.x, Spring Data JPA, Hibernate
Frontend	Thymeleaf, HTML5, CSS3, JavaScript, Chart.js
Database	MySQL 8.x
Web Server	Apache Tomcat (embedded in Spring Boot)
Build Tool	Maven
IDE	IntelliJ IDEA Ultimate
Authentication	Session-based (HttpSession)
📊 Database Schema
The database consists of 6 tables, normalized to 3rd Normal Form (3NF):

Table	Description
пациент (Patient)	Registered patients
врач (Doctor)	Medical staff
специализация (Specialization)	Medical specialties
приём (Appointment)	Appointment records
услуги (Service)	Medical services
направлениянауслуги (Service Referral)	Service referrals (composite key)
Table Relationships:
Patient (1) → Appointment (N)

Doctor (1) → Appointment (N)

Doctor (N) → Specialization (1)

Appointment (1) → Service Referral (N)

Service (1) → Service Referral (N)

👥 User Roles and Functionality
👤 Patient
Registration and login

Book appointments with doctors

View appointment history

View referrals and service results

👨‍⚕️ Specialist Doctor
View personal schedule

Conduct appointments (diagnosis, recommendations)

Create service referrals

Enter results for completed services

⭐ Chief Physician
Manage doctors (CRUD operations)

Add medical services

View all appointments

Statistics and reports (pie charts with Chart.js)

SQL subqueries with DISTINCT, ORDER BY, LIMIT, OFFSET

📡 REST API
Available under the /api prefix:

Method	URL	Description
GET	/api/patient	Get all patients
GET	/api/patient/{id}	Get patient by ID
POST	/api/patient	Add a new patient
PUT	/api/patient	Update a patient
DELETE	/api/patient/{id}	Delete a patient
🚀 How to Run
1. Clone the repository
bash
git clone https://github.com/your-username/spring_course_springboot.git
2. Set up the database
sql
CREATE DATABASE my_db;
3. Configure database connection
In application.properties, specify your credentials:

properties
spring.datasource.url=jdbc:mysql://localhost:3306/my_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=bestuser
spring.datasource.password=bestuser
4. Run the application
bash
mvn spring-boot:run
5. Open in browser
text
http://localhost:3333/springboot-rest/
📁 Project Structure
text
src/main/java/com.anatoliy.spring.springboot.spring_course_springboot/
├── controller/
│   ├── WebController.java         # Web controller (HTML pages)
│   └── MyRESTController.java      # REST API controller
├── service/
│   ├── PatientService.java
│   ├── PatientServiceImpl.java
│   ├── DoctorService.java
│   ├── AppointmentService.java
│   ├── DirectionService.java
│   └── ...
├── dao/
│   ├── PatientDAO.java
│   ├── PatientDAOImpl.java
│   ├── DoctorDAO.java
│   ├── AppointmentDAO.java
│   └── ...
└── entity/
    ├── Patient.java
    ├── Doctor.java
    ├── Appointment.java
    ├── ServiceDirection.java
    ├── Specialization.java
    └── Uslugi.java
🏗 Application Architecture
The application follows a layered architecture:

text
Controller → Service → DAO → Database
Controller — Handles HTTP requests, validation, returns views

Service — Business logic, transactions, validations

DAO — Database operations via Hibernate

Database — MySQL

The web interface is built with Thymeleaf, a server-side template engine that generates HTML pages with data injected from the model. Apache Tomcat is embedded in Spring Boot, allowing the application to run as a standalone JAR file.

📝 License
This project was created for educational purposes and is not intended for commercial use.

Author: Anatoliy Bakshaev
Year: 2026
