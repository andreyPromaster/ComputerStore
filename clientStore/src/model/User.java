package model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable{

    private int idUser;
    private String username;
    private String password;
    private int role;
    private String create_time;

    public User(){
        this.idUser = 0;
        this.username = "";
        this.password = "";
        this.role = 0;
        this.create_time = "";
    }
    public User(User user) {
        this.idUser = user.idUser;
        this.username = user.username;
        this.password = user.password;
        this.role = user.role;
        this.create_time = user.create_time;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", create_time='" + create_time + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return idUser == user.idUser &&
                role == user.role &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(create_time, user.create_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, username, password, role, create_time);
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
