package pl.zablocki.petvet.entity;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    String email;
    String authority;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
