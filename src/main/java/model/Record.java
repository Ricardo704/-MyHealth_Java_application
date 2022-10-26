package model;



public class Record {

    private String date;
    private Integer recordID;
    private String username;
    private Double weight;
    private Double temperature;
    private Double bloodPressure_low;
    private Double bloodPressure_high;
    private String notes;

    public Record(Integer recordID, Double weight, Double temperature, Double bloodPressure_low, Double bloodPressure_high, String notes, String date, String username){
        this.date = date;
        this.recordID = recordID;
        this.username = username;
        this.weight = weight;
        this.temperature = temperature;
        this.bloodPressure_low = bloodPressure_low;
        this.bloodPressure_high = bloodPressure_high;
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public String getUsername(){
         return username;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getBloodPressure_low() {
        return bloodPressure_low;
    }

    public Double getBloodPressure_high() {
        return bloodPressure_high;
    }

    public String getNotes() {
        return notes;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setBloodPressure_low(Double bloodPressure_low) {
        this.bloodPressure_low = bloodPressure_low;
    }

    public void setBloodPressure_high(Double bloodPressure_high) {
        this.bloodPressure_high = bloodPressure_high;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
