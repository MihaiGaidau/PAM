package com.example.denis.calendarview;

public class Event {
    private String eTitle;
    private String eLocation;
    private boolean eAllDay;
    private String eStartTime;
    private String eEndTime;
    private String eNote;
    private boolean isComplete = false;
    private String eDate;


    public Event(String eDate, String eTitle, String eLocation, String eStartTime, String eEndTime, String eNote) {
        this.eTitle = eTitle;
        this.eLocation = eLocation;
        this.eStartTime = eStartTime;
        this.eEndTime = eEndTime;
        this.eNote = eNote;
        this.eDate = eDate;
        eAllDay = false;
        if (eStartTime == "" && eEndTime == ""){
            eAllDay = true;
        }


    }
    public Event(String eDate, String eId, String eTitle, String eLocation, boolean eAllDay, String eNote) {
        this.eTitle = eTitle;
        this.eLocation = eLocation;
        this.eAllDay = eAllDay;
        this.eNote = eNote;
        this.eDate = eDate;
        eStartTime = "";
        eEndTime = "";
    }

//    public String getEventId(){
//        return eId;
//    }
    public String getDate(){
        return eDate;
    }
    public void setDate(String date){
        eDate = date;
    }
    public String getTitle(){
        return eTitle;
    }


    public void setTitle(String title) {
        eTitle=title;
    }

    public String getLocation(){
        return eLocation;
    }

    public void setLocation(String location){
        eLocation = location;
    }

    public String getStartTime(){
        return eStartTime;
    }

    public void setStartTime(String startTime) {
        eAllDay = false;
        this.eStartTime = startTime;
    }

    public String getEndTime() {
        return eEndTime;
    }

    public void setEndTime(String endTime) {
        eAllDay = false;
        this.eEndTime = endTime;
    }

    public void setNote(String note) {
        this.eNote = note;
    }

    public String getNote() {
        return eNote;
    }

    public boolean isAllDay() {
        return eAllDay;
    }

    public void setAllDay(boolean allDay) {
        eStartTime = "";
        eEndTime = "";
        this.eAllDay = allDay;
    }
    private String getCompareKey(){
        return eTitle + "|"+eLocation+"|";

    }
    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o==null || getClass() != o.getClass()) return false;
        Event that = (Event) o;
        if (getCompareKey() != that.getCompareKey())
            return false;
        else
            if (eLocation != that.eLocation)
                return false;
        else return true;
    }
    @Override
    public String toString(){
        return getCompareKey();
    }
    @Override
    public int hashCode(){
        return getCompareKey().hashCode();
    }


}
