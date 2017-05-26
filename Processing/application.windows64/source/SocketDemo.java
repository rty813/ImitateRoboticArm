import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.net.ServerSocket; 
import java.net.Socket; 
import processing.net.*; 
import java.io.DataInputStream; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SocketDemo extends PApplet {






Server s;
Client c;
String input;
float data[];
float x,y,z,x1,y1,z1, x2, y2, z2;
float x3, y3, z3, x4, y4, z4;

public void setup(){
  
  
  textSize(20);
  background(0);
  //frameRate(60);
  //s = new Server(this, 12345);

    new Thread(new Runnable() {
      public void run(){
            try {
                ServerSocket serverSocket = new ServerSocket(12345);
                while(true){
                    Socket socket = serverSocket.accept();
                    println("Connected!");
                    new SocketClient(socket).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
      }

   }).start();
   
   new Thread(new Runnable() {
      public void run(){
            try {
                ServerSocket serverSocket = new ServerSocket(12346);
                while(true){
                    Socket socket = serverSocket.accept();
                    println("Connected!");
                    new SocketClient2(socket).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
      }

   }).start();
}



public void draw(){
  background(0);
  //getData();
  if (x == 1000){
    x = x2;
    y = y2;
    z = z2;
  }
  text((x-x1) + " - " + (y-y1) + " - " + (z-z1), 50, 50);
  text((x3-x4) + " - " + (y3-y4) + " - " + (z3-z4), 50, 100);
  lights();
  stroke(255);
  translate(width/2, 2*height/4);
  rotateZ((x - x1)*PI/180);
  rotateY((z - z1)*PI/180);
  rotateX((y - y1)*PI/180);
  x2 = x;
  y2 = y;
  z2 = z;
  fill(100);
  box(50, 50, 500);
  line(0, 0, 0, -500, 0, 0);
  line(0, 0, 0, 0, 500, 0);
  line(0, 0, 0, 0, 0, 500);
  stroke(0);
  translate(0,0, -260);
  //reset
  rotateZ(-(x2 - x1)*PI/180);
  rotateY(-(z2 - z1)*PI/180);
  rotateX(-(y2 - y1)*PI/180);
  
  rotateZ((x3 - x4)*PI/180);
  rotateY((z3 - z4)*PI/180);
  rotateX((y3 - y4)*PI/180);
  fill(0, 200, 100);
  box(40,40,400);
  stroke(150,100, 200);
  line(0, 0, 0, -500, 0, 0);
  line(0, 0, 0, 0, 500, 0);
  line(0, 0, 0, 0, 0, 500);

}

public void mouseClicked(){
  x1 = x;
  y1 = y;
  z1 = z;
  x4 = x3;
  y4 = y3;
  z4 = z3;
  println("Mouse Clicked!");
}

class SocketClient extends Thread{
  private DataInputStream inputStream;
  public SocketClient(Socket socket) throws IOException{
    this.inputStream = new DataInputStream(socket.getInputStream());
  }
  
  public void run(){
    while(true){
      try{
        input = inputStream.readUTF();
        println(input);
        data = new float[4];
        data = PApplet.parseFloat(input.split(" "));
        println(data.length);
        if (data.length < 4){
          x = 1000;
          y = 1000;
          z = 1000;
          continue;
        }
        x = data[1];
        y = data[2];
        z = data[3];
      } catch (IOException e){
        e.printStackTrace();
      }
    }
  }
}

class SocketClient2 extends Thread{
  private DataInputStream inputStream;
  public SocketClient2(Socket socket) throws IOException{
    this.inputStream = new DataInputStream(socket.getInputStream());
  }
  
  public void run(){
    while(true){
      try{
        input = inputStream.readUTF();
        println(input);
        data = new float[4];
        data = PApplet.parseFloat(input.split(" "));
        println(data.length);
        if (data.length < 4){
          x3 = 1000;
          y3 = 1000;
          z3 = 1000;
          continue;
        }
        x3 = data[1];
        y3 = data[2];
        z3 = data[3];
      } catch (IOException e){
        e.printStackTrace();        
      }
    }
  }
}
  public void settings() {  size(1500, 800, P3D);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SocketDemo" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
