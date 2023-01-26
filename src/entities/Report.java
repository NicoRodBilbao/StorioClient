package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
     *  Entity class Report has the following fields: 
     *  <ul>
     *      <li><strong>id:</strong> Identifier field. <em>Integer</em>.</li>
     *      <li><strong>description:</strong> Short description of the issues regarding the report. <em>String</em>.</li>
     *      <li><strong>date:</strong> Date in which the report was submitted. <em>Date</em>.</li>
     *      <li><strong>item:</strong> Item to which the report is associated. <em>{@link Item}</em>.</li>
     *  </ul>
     *
     * @author Nicolás Rodríguez
 */
@XmlRootElement
public class Report implements Serializable {

    /**
     * Identifier field for the Report. 
     */
    private Integer id;
    /**
     * Short description of the issues of the Report
     */
    private String description;
    /**
     * Date in which the report was submitted.
     */
    private Date date;
    /**
     * {@link Item} to which the report is associated. 
     */
    private Item item;

    /**
     * Empty constructor for Report.
     */
    public Report() {
        super();
    }

    /**
     * Complete constructor for Report.
     * 
     * @param id <strong>id:</strong> Identifier field. <em>Integer</em>.
     * @param description <strong>description:</strong> Short description of the issues regarding the report. <em>String</em>.
     * @param date <strong>date:</strong> Date in which the report was submitted. <em>Date</em>.
     * @param item <strong>item:</strong> Item to which the report is associated. <em>Item</em>.
     */
    public Report(Integer id, String description, Date date, Item item) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.item = item;
    }
    /**
     * Gets the id value of the Report.
     * @return The id value of the Report.
     */
    public Integer getId() {
        return id;
    }
    /**
     * Sets the id value of the Report.
     * @param id The id value of the Report.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * Gets the description of the Report.
     * @return The description of the Report.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Sets the description of the Report.
     * @param description The description of the Report.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Gets the date of the Report.
     * @return The date of the Report.
     */
    public Date getDate() {
        return date;
    }
    /**
     * Sets the date of the Report.
     * @param date The date of the Report.
     */
    public void setDate(Date date) {
        this.date = date;
    }
    /**
     * Gets the {@link Item} of the Report.
     * @return The {@link Item} of the Report.
     */
    public Item getItem() {
        return item;
    }
    /**
     * Sets the {@link Item} of the Report.
     * @param item The {@link Item} of the Report.
     */
    public void setItem(Item item) {
        this.item = item;
    }
    /**
     * HashCode method implementation for the entity.
     * @return An integer value as hashcode for the object. 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.description);
        hash = 53 * hash + Objects.hashCode(this.date);
        hash = 53 * hash + Objects.hashCode(this.item);
        return hash;
    }
    /**
     * Compares two Reports fully. 
     * @param obj The object to compare to.
     * @return True if objects are equals, otherwise false.
     */
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
        final Report other = (Report) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.item, other.item)) {
            return false;
        }
        return true;
    }
    /**
     * This method returns a String for a Report instance.
     * @return The String representation for the Report object. 
     */
    @Override
    public String toString() {
        return "Report{" + "id=" + id + ", description=" + description + ", date=" + date + ", item=" + item + '}';
    }
}
