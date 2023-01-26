package entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
     *  Entity class Model has the following fields: 
     *  <ul>
     *      <li><strong>id:</strong> Identifier field. <em>Integer</em>.</li>
     *      <li><strong>model:</strong> Name of the model. <em>String</em>.</li>
     *      <li><strong>description:</strong> Short description of the Model, noting specifications. <em>String</em>.</li>
     *      <li><strong>notes:</strong> Any notable information about the model will be stored here. <em>String</em>.</li>
     *      <li><strong>items:</strong> List of {@link Item} that belong to the model. <em> List</em>.</li>
     *  </ul>
     *
     * @author Nicolás Rodríguez
 */
@XmlRootElement
public class Model implements Serializable {
    /**
     * Identifier field for the Model. 
     */
    private Integer id;
    /**
     * Name of the model. 
     */
    private String model;
    /**
     * Short description of the Model, noting specifications.
     */
    private String description;
    /**
     * Any notable information about the model will be stored here.
     */
    private String notes;
    /**
     * List of {@link Item} that belong to the model.
     */
    private List<Item> items;
    
    /**
     * Empty constructor for Model.
     */
    public Model() {
        super();
    }

    /**
     * Complete constructor for Model.
     *
     * @param id <strong>id:</strong> Identifier field. <em>Integer</em>.
     * @param model <strong>model:</strong> Name of the model. <em>String</em>.
     * @param description <strong>description:</strong> Short description of the Model, noting specifications.
     * @param notes <strong>notes:</strong> Any notable information about the model will be stored here.
     * @param items <strong>items:</strong> List of {@link Item} that belong to the model.
     */
    public Model(Integer id, String model, String description, String notes, List<Item> items) {
        this.id = id;
        this.model = model;
        this.description = description;
        this.notes = notes;
        this.items = items;
    }
    /**
     * Gets the id value of the Model.
     * @return The id value of the Model.
     */
    public Integer getId() {
        return id;
    }
    /**
     * Sets the id value of the Model.
     * @param id The id value of the Model.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * Gets the model name of the Model.
     * @return The model name of the Model.
     */
    public String getModel() {
        return model;
    }
    /**
     * Sets the model name of the Model.
     * @param model The model name of the Model.
     */
    public void setModel(String model) {
        this.model = model;
    }
    /**
     * Gets the description of the Model.
     * @return The description of the Model.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Sets the description of the Model.
     * @param description The description of the Model.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Gets the notes of the Model.
     * @return The notes of the Model.
     */
    public String getNotes() {
        return notes;
    }
    /**
     * Sets the notes of the Model.
     * @param notes The notes of the Model.
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    /**
     * Gets the List of {@link Item} of the Model.
     * @return The List of {@link Item} of the Model.
     */
    public List<Item> getItems() {
        return items;
    }
    /**
     * Sets the List of {@link Item} of the Model.
     * @param items The List of {@link Item} of the Model.
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }
    /**
     * HashCode method implementation for the entity.
     * @return An integer value as hashcode for the object. 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.model);
        hash = 71 * hash + Objects.hashCode(this.description);
        hash = 71 * hash + Objects.hashCode(this.notes);
        hash = 71 * hash + Objects.hashCode(this.items);
        return hash;
    }
    /**
     * Compares two Model fully. 
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
        final Model other = (Model) obj;
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.notes, other.notes)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.items, other.items)) {
            return false;
        }
        return true;
    }
    /**
     * This method returns a String for a Model instance.
     * @return The String representation for the Model object. 
     */
    @Override
    public String toString() {
       // return "Model{" + "id=" + id + ", model=" + model + ", description=" + description + ", notes=" + notes + ", items=" + items + '}';
       return model;
    }
}
