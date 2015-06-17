package com.okason.attendanceapp.models;

/**
 * Created by Valentine on 6/16/2015.
 */
public class Attendance {
    private Long CheckInTime;
    private Long CheckOutTime;
    private Event Event;
    private Attendant Attendant;

    //Default constructor
    public Attendance(){}

    public Long getCheckInTime() {
        return CheckInTime;
    }

    public void setCheckInTime(Long checkInTime) {
        CheckInTime = checkInTime;
    }

    public Long getCheckOutTime() {
        return CheckOutTime;
    }

    public void setCheckOutTime(Long checkOutTime) {
        CheckOutTime = checkOutTime;
    }

    public Event getEvent() {
        return Event;
    }

    public void setEvent(Event event) {
        Event = event;
    }

    public Attendant getAttendant() {
        return Attendant;
    }

    public void setAttendant(Attendant attendant) {
        Attendant = attendant;
    }
}
