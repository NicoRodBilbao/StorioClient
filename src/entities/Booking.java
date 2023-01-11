package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="booking",schema="storio")
//@XmlRootElement
@XmlRootElement
public class Booking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //@ManyToOne
    //@JoinColumn(name = "id")
    //private Client user;
    @ManyToMany(mappedBy = "bookings")
    private List<Pack> packs;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endDate;
    private String description;
    @Enumerated(EnumType.STRING)
    private BookingState state;
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    /*
    public Client getUser() {
        return user;
    }

    public void setUser(Client user) {
        this.user = user;
    }
    */

    @XmlTransient
    public List<Pack> getPacks() {
        return packs;
    }

    public void setPacks(List<Pack> packs) {
        this.packs = packs;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BookingState getState() {
        return state;
    }

    public void setState(BookingState state) {
        this.state = state;
    }
   

    public Booking() {
        super();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        //hash = 89 * hash + Objects.hashCode(this.user);
        hash = 89 * hash + Objects.hashCode(this.packs);
        hash = 89 * hash + Objects.hashCode(this.startDate);
        hash = 89 * hash + Objects.hashCode(this.endDate);
        hash = 89 * hash + Objects.hashCode(this.description);
        hash = 89 * hash + Objects.hashCode(this.state);
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
        final Booking other = (Booking) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        /*if (!Objects.equals(this.user, other.user)) {
            return false;
        }*/
        if (!Objects.equals(this.packs, other.packs)) {
            return false;
        }
        if (!Objects.equals(this.startDate, other.startDate)) {
            return false;
        }
        if (!Objects.equals(this.endDate, other.endDate)) {
            return false;
        }
        if (this.state != other.state) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Booking{" + "id=" + id + /*", user=" + user +*/ ", packs=" + packs + ", startDate=" + startDate + ", endDate=" + endDate + ", description=" + description + ", state=" + state + '}';
    }
    
    
}
