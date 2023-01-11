package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name="Item",schema="storio")
@NamedQueries({
    @NamedQuery(name="findAllItems",
            query="SELECT i FROM Item i ORDER BY i.id DESC"),
    @NamedQuery(name="findAllItemsWithoutPack",
            query="SELECT i FROM Item i WHERE i.pack is NULL"),
    @NamedQuery(name="findAllModelsItems",
            query="SELECT i FROM Item i WHERE i.model = :model ORDER BY i.id DESC"),
    @NamedQuery(name="findAllPacksItems",
            query="SELECT i FROM Item i WHERE i.pack = :pack ORDER BY i.id DESC")
})
@XmlRootElement
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @XmlTransient
    private Model model;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateAdded;
    private String issues;
    @OneToMany(mappedBy = "item")
    private List<Report> report;
    @ManyToOne
    @XmlTransient
    private Pack pack;

    public Item() {
        super();
    }

    public Item(Integer id, Model model, Date dateAdded, String issues, List<Report> report, Pack pack) {
        this.id = id;
        this.model = model;
        this.dateAdded = dateAdded;
        this.issues = issues;
        this.report = report;
        this.pack = pack;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getIssues() {
        return issues;
    }

    public void setIssues(String issues) {
        this.issues = issues;
    }

    @XmlTransient
    public List<Report> getReport() {
        return report;
    }

    public void setReport(List<Report> report) {
        this.report = report;
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

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

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", model=" + model + ", dateAdded=" + dateAdded + ", issues=" + issues + ", report=" + report + ", pack=" + pack + '}';
    }
}
