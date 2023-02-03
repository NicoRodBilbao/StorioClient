package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

	private String login;
        
	private String email;

    private Integer phoneNumber;

    private String fullName;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserPrivilege privilege;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserPrivilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(UserPrivilege privilege) {
		this.privilege = privilege;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + Objects.hashCode(this.id);
		hash = 53 * hash + Objects.hashCode(this.status);
		hash = 53 * hash + Objects.hashCode(this.login);
		hash = 53 * hash + Objects.hashCode(this.email);
		hash = 53 * hash + Objects.hashCode(this.phoneNumber);
		hash = 53 * hash + Objects.hashCode(this.fullName);
		hash = 53 * hash + Objects.hashCode(this.password);
		hash = 53 * hash + Objects.hashCode(this.privilege);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if (!Objects.equals(this.login, other.login)) {
			return false;
		}
		if (!Objects.equals(this.email, other.email)) {
			return false;
		}
		if (!Objects.equals(this.fullName, other.fullName)) {
			return false;
		}
		if (!Objects.equals(this.password, other.password)) {
			return false;
		}
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		if (this.status != other.status) {
			return false;
		}
		if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
			return false;
		}
		if (this.privilege != other.privilege) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", status=" + status + ", login=" + login + ", email=" + email + ", phoneNumber=" + phoneNumber + ", fullName=" + fullName + ", password=" + password + ", privilege=" + privilege + '}';
	}

}
