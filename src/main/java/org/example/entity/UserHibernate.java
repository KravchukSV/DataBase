package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserHibernate {
    @Id
    //@GeneratedValue
    @Column(name = "user_id")
    private int userId;
    @Column(name = "name")
    private String name;

    /*@Column(name = "user_details_id")
    private int userDetailsId;*/

    @OneToOne
    @JoinColumn(name = "user_details_id")
    private UserDetails userDetails;


    public UserHibernate(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserHibernate)) return false;

        UserHibernate that = (UserHibernate) o;

        if (getUserId() != that.getUserId()) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return getUserDetails() != null ? getUserDetails().equals(that.getUserDetails()) : that.getUserDetails() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getUserDetails() != null ? getUserDetails().hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "UserHibernate{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", userDetails=" + userDetails +
                '}';
    }
}
