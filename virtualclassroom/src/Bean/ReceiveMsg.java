/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bean;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author meghanaraob
 */
public class ReceiveMsg extends Thread {
String msg="";
DataInputStream dis=null;
JTextArea txt_area=null;

public ReceiveMsg(DataInputStream d, JTextArea a)
{
    this.dis=d;
    this.txt_area=a;
}
  //  @Override
public void run(){
while(true){
            try {
                msg = dis.readUTF();
                txt_area.append("\n" + this.getName() + ":" + msg);
            } catch (IOException ex) {
                Logger.getLogger(ReceiveMsg.class.getName()).log(Level.SEVERE, null, ex);
            }

}
}
};

