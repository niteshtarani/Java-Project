/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bean;

/**
 *
 * @author meghanaraob
 */
public class Retrievalbean {

     private String rollnum;
    private String fname;
    private int standard;
    private String spass;



    public String getRollNo(){
        return rollnum;
    }

    public void setRollNo(String rollnum){
        this.rollnum=rollnum;
    }
    public String getClientname(){
        return fname;
    }

    public void setClientname(String fname){
        this.fname=fname;
    }
    public int getStandard(){
        return standard;
    }
    public void setStandard(int standard){
        this.standard=standard;
    }

 public String getspass(){
        return spass;
    }

    public void setspass(String spass){
        this.spass=spass;
    }
}