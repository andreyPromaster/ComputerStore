package model;

import java.io.Serializable;
import java.util.Objects;

public class UserInfo  implements Serializable{
    private int idInfo;
    private String email;
    private String phone_number;
    private String date_of_birth;
    private String first_name;
    private String second_name;
    private int userid;

    @Override
    public String toString() {
        return "UserInfo{" +
                "idInfo=" + idInfo +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                ", first_name='" + first_name + '\'' +
                ", second_name='" + second_name + '\'' +
                ", userid=" + userid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return idInfo == userInfo.idInfo &&
                userid == userInfo.userid &&
                Objects.equals(email, userInfo.email) &&
                Objects.equals(phone_number, userInfo.phone_number) &&
                Objects.equals(date_of_birth, userInfo.date_of_birth) &&
                Objects.equals(first_name, userInfo.first_name) &&
                Objects.equals(second_name, userInfo.second_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInfo, email, phone_number, date_of_birth, first_name, second_name, userid);
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getIdInfo() {
        return idInfo;
    }

    public void setIdInfo(int idInfo) {
        this.idInfo = idInfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

}
