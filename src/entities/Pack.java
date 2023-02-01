package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This entity
 *
 * @author 2dam
 */
@XmlRootElement
public class Pack implements Serializable {

    private Integer id;
    private String description;
    private Date datePackAdd;
    private List<Item> items;
    private PackState state;
    private PackType type;
    private List<Booking> bookings;

    public Pack() {
        super();
    }

    public Pack(Integer id, String description, Date datePackAdd, List<Item> items, PackState state, PackType type, List<Booking> bookings) {
        this.id = id;
        this.description = description;
        this.datePackAdd = datePackAdd;
        this.items = items;
        this.state = state;
        this.type = type;
        this.bookings = bookings;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public PackState getState() {
        return state;
    }

    public void setState(PackState state) {
        this.state = state;
    }

    public PackType getType() {
        return type;
    }

    public void setType(PackType type) {
        this.type = type;
    }

    public Date getDatePackAdd() {
        return datePackAdd;
    }

    public void setDatePackAdd(Date datePackAdd) {
        this.datePackAdd = datePackAdd;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.description);
        hash = 83 * hash + Objects.hashCode(this.datePackAdd);
        hash = 83 * hash + Objects.hashCode(this.items);
        hash = 83 * hash + Objects.hashCode(this.state);
        hash = 83 * hash + Objects.hashCode(this.type);
        hash = 83 * hash + Objects.hashCode(this.bookings);
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
        final Pack other = (Pack) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.datePackAdd, other.datePackAdd)) {
            return false;
        }
        if (!Objects.equals(this.items, other.items)) {
            return false;
        }
        if (this.state != other.state) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.bookings, other.bookings)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pack{" + "id=" + id + ", description=" + description + ", datePackAdd=" + datePackAdd + ", items=" + items + ", state=" + state + ", type=" + type + ", bookings=" + bookings + '}';
    }

}
