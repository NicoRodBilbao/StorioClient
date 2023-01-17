package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
     *  Entity class Item has the following fields: 
     *  <ul>
     *      <li><strong>id:</strong> Identifier field. <em>Integer</em>.</li>
     *      <li><strong>model:</strong> {@link Model} that an Item is. <em>{@link Model}</em>.</li>
     *      <li><strong>dateAdded:</strong> Date in which an Item was added. <em>Date</em>.</li>
     *      <li><strong>issues:</strong> Any issues regarding the Item will be stored here. <em>String</em>.</li>
     *      <li><strong>report:</strong> List of {@link Report} associated to the item. <em>List</em>.</li>
     *      <li><strong>pack:</strong> {@link Pack} to which an Item belongs. <em>{@link Pack}</em>.</li>
     *  </ul>
     * 
     * @author Nicolás Rodríguez
 */
public class Item implements Serializable {
    /**
     * Identifier field for the Item. 
     */
    private Integer id;
    /**
     * {@link Model} that an Item is.
     */
    private Model model;
    /**
     * Date in which an Item was added.
     */
    private Date dateAdded;
    /**
     * Any issues regarding the Item will be stored here.
     */
    private String issues;
    /**
     * List of {@link Report} associated to the item.
     */
    private List<Report> report;
    /**
     * {@link Pack} to which an Item belongs.
     */
    private Pack pack;

    /**
     * Empty constructor for Item.
     */
    public Item() {
        super();
    }
    /**
     * Complete constructor for Item.
     * 
     * @param id <strong>id:</strong> Identifier field. <em>Integer</em>.
     * @param model <strong>model:</strong> {@link Model} that an Item is.
     * @param dateAdded <strong>dateAdded:</strong> Date in which an Item was added.
     * @param issues <strong>issues:</strong> Any issues regarding the Item will be stored here.
     * @param report <strong>report:</strong> List of {@link Report} associated to the item.
     * @param pack <strong>pack:</strong> {@link Pack} to which an Item belongs.
     */
    public Item(Integer id, Model model, Date dateAdded, String issues, List<Report> report, Pack pack) {
        this.id = id;
        this.model = model;
        this.dateAdded = dateAdded;
        this.issues = issues;
        this.report = report;
        this.pack = pack;
    }
    /**
     * Gets the id value of the Item.
     * @return The id value of the Item.
     */
    public Integer getId() {
        return id;
    }
    /**
     * Sets the id value of the Item.
     * @param id The id value of the Item.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * Gets the {@link Model} of the Item.
     * @return The {@link Model} of the Item.
     */
    public Model getModel() {
        return model;
    }
    /**
     * Sets the {@link Model} type of the Item.
     * @param model The {@link Model} type of the Item.
     */
    public void setModel(Model model) {
        this.model = model;
    }
    /**
     * Gets the adding date of the Item.
     * @return The adding date of the Item.
     */
    public Date getDateAdded() {
        return dateAdded;
    }
    /**
     * Sets the adding date of the Item.
     * @param dateAdded The adding date of the Item.
     */
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
    /**
     * Gets the issues of the Item.
     * @return The issues of the Item.
     */
    public String getIssues() {
        return issues;
    }
    /**
     * Sets the issues of the Item.
     * @param issues The issues of the Item.
     */
    public void setIssues(String issues) {
        this.issues = issues;
    }
    /**
     * Gets the {@link Report} List of the Item.
     * @return The {@link Report} List of the Item.
     */
    public List<Report> getReport() {
        return report;
    }
    /**
     * Sets the {@link Report} List of the Item.
     * @param report The {@link Report} List of the Item.
     */
    public void setReport(List<Report> report) {
        this.report = report;
    }
    /**
     * Gets the {@link Pack} of the Item.
     * @return The {@link Pack} of the Item.
     */
    public Pack getPack() {
        return pack;
    }
    /**
     * Sets the {@link Pack} of the Item.
     * @param pack The {@link Pack}  of the Item.
     */
    public void setPack(Pack pack) {
        this.pack = pack;
    }
    /**
     * HashCode method implementation for the entity.
     * @return An integer value as hashcode for the object. 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.id);
        hash = 61 * hash + Objects.hashCode(this.model);
        hash = 61 * hash + Objects.hashCode(this.dateAdded);
        hash = 61 * hash + Objects.hashCode(this.issues);
        hash = 61 * hash + Objects.hashCode(this.report);
        hash = 61 * hash + Objects.hashCode(this.pack);
        return hash;
    }
    /**
     * Compares two Item fully. 
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
        final Item other = (Item) obj;
        if (!Objects.equals(this.issues, other.issues)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        if (!Objects.equals(this.dateAdded, other.dateAdded)) {
            return false;
        }
        if (!Objects.equals(this.report, other.report)) {
            return false;
        }
        if (!Objects.equals(this.pack, other.pack)) {
            return false;
        }
        return true;
    }
    /**
     * This method returns a String for a Item instance.
     * @return The String representation for the Item object. 
     */
    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", model=" + model + ", dateAdded=" + dateAdded + ", issues=" + issues + ", report=" + report + ", pack=" + pack + '}';
    }
}
