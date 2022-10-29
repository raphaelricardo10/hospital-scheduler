package schedule;

import java.util.Set;
import java.util.TreeSet;

import java.time.LocalDate;
import java.time.LocalTime;

import workData.DayOfWork;
import comparators.TimeComparator;

public class DailySchedule {
    private LocalDate date;
    private DayOfWork dayOfWork;
    private LocalTime dayIterator;
    private TreeSet<LocalTime> availableTimes;

    public DailySchedule(DayOfWork dayOfWork, LocalDate date) {
        this.date = date;
        this.dayOfWork = dayOfWork;
        this.availableTimes = new TreeSet<LocalTime>(new TimeComparator());
        this.dayIterator = this.dayOfWork.getWorkTime().getStart();

        this.generateAvailableTimes();
    }

    public DailySchedule(DayOfWork dayOfWork) {
        this(dayOfWork, LocalDate.now());
    }

    public LocalDate getDate() {
        return date;
    }

    public Set<LocalTime> getAvailableTimes() {
        return this.availableTimes;
    }

    public void addAvailableTime(LocalTime availableTime) {
        this.availableTimes.add(availableTime);
    }

    public void removeAvailableTime(LocalTime availableTime) {
        this.availableTimes.remove(availableTime);
    }

    public LocalTime getFirstAvailableTime() {
        return this.availableTimes.first();
    }

    public boolean hasAvailableTime() {
        return this.availableTimes.size() != 0;
    }

    private boolean is_in_working_time() {
        return this.dayIterator.isBefore(this.dayOfWork.getWorkTime().getEnd());
    }

    private boolean is_in_lunch_time() {
        if (this.dayIterator.equals(this.dayOfWork.getLunchTime().getStart())) {
            return true;
        }

        if (this.dayIterator.isBefore(this.dayOfWork.getLunchTime().getStart())) {
            return false;
        }

        if (this.dayIterator.isAfter(this.dayOfWork.getLunchTime().getEnd())) {
            return false;
        }

        return true;
    }

    private void skip_lunch_time() {
        this.dayIterator = this.dayOfWork.getLunchTime().getEnd();
    }

    private void advanceIterator() {
        this.dayIterator = this.dayIterator.plusMinutes(this.dayOfWork.getAppointmentDuration());
    }

    private void generateAvailableTimes() {
        while (this.is_in_working_time()) {
            if (this.is_in_lunch_time()) {
                this.skip_lunch_time();
            }

            this.availableTimes.add(this.dayIterator);
            this.advanceIterator();
        }
    }
}
