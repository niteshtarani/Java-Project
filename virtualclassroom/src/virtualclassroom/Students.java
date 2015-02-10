/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualclassroom;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author meghanaraob
 */
@Entity
@Table(name = "STUDENTS", catalog = "", schema = "USER")
@NamedQueries({@NamedQuery(name = "Students.findAll", query = "SELECT s FROM Students s"), @NamedQuery(name = "Students.findByRollno", query = "SELECT s FROM Students s WHERE s.rollno = :rollno"), @NamedQuery(name = "Students.findBySname", query = "SELECT s FROM Students s WHERE s.sname = :sname"), @NamedQuery(name = "Students.findByClass1", query = "SELECT s FROM Students s WHERE s.class1 = :class1"), @NamedQuery(name = "Students.findByPswd", query = "SELECT s FROM Students s WHERE s.pswd = :pswd")})
public class Students implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ROLLNO")
    private Integer rollno;
    @Basic(optional = false)
    @Column(name = "SNAME")
    private String sname;
    @Basic(optional = false)
    @Column(name = "CLASS")
    private int class1;
    @Basic(optional = false)
    @Column(name = "PSWD")
    private String pswd;

    public Students() {
    }

    public Students(Integer rollno) {
        this.rollno = rollno;
    }

    public Students(Integer rollno, String sname, int class1, String pswd) {
        this.rollno = rollno;
        this.sname = sname;
        this.class1 = class1;
        this.pswd = pswd;
    }

    public Integer getRollno() {
        return rollno;
    }

    public void setRollno(Integer rollno) {
        Integer oldRollno = this.rollno;
        this.rollno = rollno;
        changeSupport.firePropertyChange("rollno", oldRollno, rollno);
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        String oldSname = this.sname;
        this.sname = sname;
        changeSupport.firePropertyChange("sname", oldSname, sname);
    }

    public int getClass1() {
        return class1;
    }

    public void setClass1(int class1) {
        int oldClass1 = this.class1;
        this.class1 = class1;
        changeSupport.firePropertyChange("class1", oldClass1, class1);
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        String oldPswd = this.pswd;
        this.pswd = pswd;
        changeSupport.firePropertyChange("pswd", oldPswd, pswd);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rollno != null ? rollno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Students)) {
            return false;
        }
        Students other = (Students) object;
        if ((this.rollno == null && other.rollno != null) || (this.rollno != null && !this.rollno.equals(other.rollno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "virtualclassroom.Students[rollno=" + rollno + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
