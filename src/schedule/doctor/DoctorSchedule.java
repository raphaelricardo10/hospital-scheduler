package schedule.doctor;



import java.util.Set;
import java.util.HashSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import allocation.ClientAllocation;

import hospital.Doctor;
import hospital.Specialty;
import person.Person;
import workData.WorkInfo;
import schedule.DailySchedule;

public class DoctorSchedule {
    private Doctor doctor;
    private WorkInfo workInfo;
    private LocalDate dayIterator;
    private LocalDate endDate;
    private LocalDate startDate;
    private Set<DailySchedule> schedule;
    private Set<ClientAllocation> allocations;

    public DoctorSchedule(Doctor doctor, WorkInfo workInfo, LocalDate startDate) {
        this.doctor = doctor;
        this.workInfo = workInfo;
        this.startDate = startDate;
        this.dayIterator = startDate;
        this.endDate = startDate.plusDays(30);
        this.schedule = new HashSet<DailySchedule>();

        this.generateSchedule();
    }

    public DoctorSchedule(Doctor doctor, WorkInfo workInfo) {
        this(doctor, workInfo, LocalDate.now());
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public WorkInfo getWorkInfo() {
        return workInfo;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Set<DailySchedule> getSchedule() {
        return schedule;
    }

    public DailySchedule getSchedule(LocalDate date) {
        return this.schedule.stream().filter(sch -> sch.getDate().equals(date)).findAny().get();
    }

    public Set<ClientAllocation> getAllocations() {
        return this.allocations;
    }

    public Set<ClientAllocation> getAllocations(String clientName) {
        return this.allocations.stream().filter(al -> al.getClient().getName().startsWith(clientName)).collect(Collectors.toSet());
    }

    public Set<ClientAllocation> getAllocations(LocalDate date) {
        return this.allocations.stream().filter(al -> al.getScheduleTime().toLocalDate().equals(date)).collect(Collectors.toSet());
    }

    private void validateSpecialty(Specialty specialty) throws Exception {
        if (!this.doctor.hasSpecialty(specialty)) {
            throw new Exception("The doctor does not have this specialty");
        }
    }
    
    public void allocateClient(Person client, Specialty specialty, LocalDateTime scheduledTo) throws Exception {
        this.validateSpecialty(specialty);

        this.allocations.add(new ClientAllocation(client, specialty, scheduledTo));
    }

    public void generateSchedule() {
        while (!this.dayIterator.isAfter(this.endDate)) {
            if (this.workInfo.getWorkingDays().contains(this.dayIterator.getDayOfWeek())) {
                this.schedule.add(new DailySchedule(this.workInfo.getDayOfWork(), this.dayIterator));
            }

            this.dayIterator = this.dayIterator.plusDays(1);
        }
    }

}
