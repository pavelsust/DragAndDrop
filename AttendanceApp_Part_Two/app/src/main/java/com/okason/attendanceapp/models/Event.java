package com.okason.attendanceapp.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valentine on 6/16/2015.
 */
public class Event <Event>{
    private String Name;
    private Long EventDate;
    private String Venue;
    private String City;
    private String SerializedAttendantsList;
    private String EventPicturePath;
    private int EventPictureId;


    //Default constructor needed for SugarORM
    public Event(){}

    private List<Attendant> GuestList;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getEventDate() {
        return EventDate;
    }

    public void setEventDate(Long eventDate) {
        EventDate = eventDate;
    }

    public String getVenue() {
        return Venue;
    }

    public void setVenue(String venue) {
        Venue = venue;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getSerializedAttendantsList() {
        return SerializedAttendantsList;
    }

    public void setSerializedAttendantsList(String serializedAttendantsList) {
        SerializedAttendantsList = serializedAttendantsList;
    }

    public String getEventPicturePath() {
        return EventPicturePath;
    }

    public void setEventPicturePath(String eventPicturePath) {
        EventPicturePath = eventPicturePath;
    }

    public int getEventPictureId() {
        return EventPictureId;
    }

    public void setEventPictureId(int eventPictureId) {
        EventPictureId = eventPictureId;
    }


    public List<Attendant> getGuestList() {
        Gson gson = new Gson();
        List<Attendant> attendantsList = gson.<ArrayList<Attendant>>fromJson(getSerializedAttendantsList(), new TypeToken<ArrayList<Attendant>>(){
        }.getType());
        return attendantsList;
    }

    public void setGuestList(List<Attendant> guestList) {
        Gson gson = new Gson();
        String serializedAttendants = gson.toJson(guestList);
        this.setSerializedAttendantsList(serializedAttendants);
    }
}