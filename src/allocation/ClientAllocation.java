package allocation;

import java.time.LocalTime;
import java.time.LocalDateTime;

import hospital.Doctor;
import hospital.Specialty;
import person.Person;

public class ClientAllocation {
    private Person client;
    private Doctor doctor;
    private Specialty specialty;
    private LocalTime scheduleTime;
    private LocalDateTime createdAt;

    public ClientAllocation(Person client, Doctor doctor, Specialty specialty, LocalTime scheduleTime,
            LocalDateTime createdAt) throws Exception {
        
        this.validateSpecialty(specialty);

        this.client = client;
        this.doctor = doctor;
        this.specialty = specialty;
        this.createdAt = createdAt;
        this.scheduleTime = scheduleTime;
    }

    public Person getClient() {
        return client;
    }

    public void setClient(Person client) {
        this.client = client;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    private void validateSpecialty(Specialty specialty) throws Exception {
        if(!this.doctor.hasSpecialty(specialty)){
            throw new Exception("The doctor does not have this specialty");
        }
    }

    public void setSpecialty(Specialty specialty) throws Exception {
        this.validateSpecialty(specialty);
        this.specialty = specialty;
    }

    public LocalTime getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(LocalTime scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
