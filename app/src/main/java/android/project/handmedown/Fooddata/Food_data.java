package android.project.handmedown.Fooddata;

public class Food_data {


    private String Title,Disc,ImageID,Userid,city,tag,date,besttime,time,remaingdays, Filepath,id,lat,log,reqstTag,reqstuser,reqstdist;
    public Food_data() {


    }
    public Food_data(String title,String time2, String time1, String filepath) {

        Title = title;
        time =time2;
        remaingdays =time1;
        Filepath =filepath;



    }

    public String getDisc() {
        return Disc;
    }

    public void setDisc(String disc) {
        Disc = disc;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImageID() {
        return ImageID;
    }

    public void setImageID(String imageID) {
        ImageID = imageID;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBesttime() {
        return besttime;
    }

    public void setBesttime(String besttime) {
        this.besttime = besttime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemaingdays() {
        return remaingdays;
    }

    public void setRemaingdays(String remaingdays) {
        this.remaingdays = remaingdays;
    }

    public String getFilepath() {
        return Filepath;
    }

    public void setFilepath(String filepath) {
        Filepath = filepath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }



    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getReqstTag() {
        return reqstTag;
    }

    public void setReqstTag(String reqstTag) {
        this.reqstTag = reqstTag;
    }

    public String getReqstuser() {
        return reqstuser;
    }

    public void setReqstuser(String reqstuser) {
        this.reqstuser = reqstuser;
    }

    public String getReqstdist() {
        return reqstdist;
    }

    public void setReqstdist(String reqstdist) {
        this.reqstdist = reqstdist;
    }
}
