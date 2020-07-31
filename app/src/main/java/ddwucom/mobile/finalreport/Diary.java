package ddwucom.mobile.finalreport;

import java.io.Serializable;
//원본 항목 데이터 저장 클래스 (DTO)
public class Diary implements Serializable{
    long _id;
    String nation;
    String place;
    String date;
    String time;
//국가 장소 날짜 시간


    public Diary(long _id, String nation, String place, String date, String time) {
        this._id = _id;
        this.nation = nation;
        this.place = place;
        this.date = date;
        this.time = time;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
