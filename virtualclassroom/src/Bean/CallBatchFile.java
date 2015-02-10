/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bean;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 *
 * @author meghanaraob
 */

public class CallBatchFile {
public static void main(String args[]){
try
{
    String fp="I: / 3rd sem / recent from naresh / project/HHH.bat";
System.out.println("Start Running the batch file");
try{
Process exec1 = Runtime.getRuntime().exec(fp);
            exec1.waitFor();
            InputStream in=exec1.getInputStream();
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
             int c = -1;
            while((c = in.read()) != -1)
            {
                baos.write(c);
            }

            String response = new String(baos.toByteArray());
            System.out.println("Response From Exe : "+response);
}catch(Exception e){
e.getStackTrace();
}
System.out.println("Completed");
}
catch (Exception e)
{
e.printStackTrace();
}
}
}

