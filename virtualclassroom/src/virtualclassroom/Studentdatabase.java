/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualclassroom;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author notammandal
 */
@Entity
@Table(name = "studentdatabase", catalog = "", schema = "testuser")
@NamedQueries({@NamedQuery(name = "Studentdatabase.findAll", query = "SELECT s FROM `studentdatabase`.`studentdatabase` s"), @NamedQuery(name = "Studentdatabase.findByRollnum", query = "SELECT s FROM `studentdatabase`.`studentdatabase` s WHERE s.rollnum = :rollnum"), @NamedQuery(name = "Studentdatabase.findByFname", query = "SELECT s FROM `studentdatabase`.`studentdatabase` s WHERE s.fname = :fname"), @NamedQuery(name = "Studentdatabase.findByStandard", query = "SELECT s FROM `studentdatabase`.`studentdatabase` s WHERE s.standard = :standard"), @NamedQuery(name = "Studentdatabase.findBySpass", query = "SELECT s FROM `studentdatabase`.`studentdatabase` s WHERE s.spass = :spass")})
public class Studentdatabase implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ROLLNUM")
    private BigDecimal rollnum;
    @Basic(optional = false)
    @Column(name = "FNAME")
    private String fname;
    @Basic(optional = false)
    @Column(name = "STANDARD")
    private BigInteger standard;
    @Basic(optional = false)
    @Column(name = "SPASS")
    private String spass;

    public Studentdatabase() {
    }

    public Studentdatabase(BigDecimal rollnum) {
        this.rollnum = rollnum;
    }

    public Studentdatabase(BigDecimal rollnum, String fname, BigInteger standard, String spass) {
        this.rollnum = rollnum;
        this.fname = fname;
        this.standard = standard;
        this.spass = spass;
    }

    public BigDecimal getRollnum() {
        return rollnum;
    }

    public void setRollnum(BigDecimal rollnum) {
        BigDecimal oldRollnum = this.rollnum;
        this.rollnum = rollnum;
        changeSupport.firePropertyChange("rollnum", oldRollnum, rollnum);
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        String oldFname = this.fname;
        this.fname = fname;
        changeSupport.firePropertyChange("fname", oldFname, fname);
    }

    public BigInteger getStandard() {
        return standard;
    }

    public void setStandard(BigInteger standard) {
        BigInteger oldStandard = this.standard;
        this.standard = standard;
        changeSupport.firePropertyChange("standard", oldStandard, standard);
    }

    public String getSpass() {
        return spass;
    }

    public void setSpass(String spass) {
        String oldSpass = this.spass;
        this.spass = spass;
        changeSupport.firePropertyChange("spass", oldSpass, spass);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rollnum != null ? rollnum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Studentdatabase)) {
            return false;
        }
        Studentdatabase other = (Studentdatabase) object;
        if ((this.rollnum == null && other.rollnum != null) || (this.rollnum != null && !this.rollnum.equals(other.rollnum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "virtualclassroom.Studentdatabase[rollnum=" + rollnum + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
