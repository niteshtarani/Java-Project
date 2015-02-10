/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Bean;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.lang.String;
/**
 *
 * @author meghanaraob
 */
public class ServerSide {
private int port;
static final int DEFAULT_PORT= 8189;

//Constructor parameter is the port on which the server will listen for client requests

ServerSide(int port){
this.port=port;
}

public void run(){
try{
ServerSocket ss=new ServerSocket (port);
Socket incoming=ss.accept();
BufferedReader in;
in=new BufferedReader(new InputStreamReader(
        incoming.getInputStream()));
PrintWriter out=new PrintWriter(
        incoming.getOutputStream(),true);
String str;
while(!(str=in.readLine()).equals("")){
StringTokenizer st=new StringTokenizer(str);
try{
while(st.hasMoreTokens()){
    String result=null;
    result.concat(st.nextToken());
}
}
catch(Exception e){
out.println("Data not being read correctly");
}
}
incoming.close();
}
catch(IOException iox){
System.out.println(iox);
iox.printStackTrace();
}
}

public static void main(String args[]){
int port=DEFAULT_PORT;
//if(args.length>0){
//port=Integer.parseInt(args[0]);
//}
ServerSide serve=new ServerSide(port);
serve.run();
}
}
