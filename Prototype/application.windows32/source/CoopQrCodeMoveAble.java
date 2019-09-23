import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import com.cage.zxing4p3.*; 
import java.util.Arrays; 
import processing.video.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class CoopQrCodeMoveAble extends PApplet {

//The libary doing the QR decoding

ZXING4P zxing4p;

// Java array tool


//Libary to control video stream

Capture cam;

boolean  rectOver1, rectOver2, rectOver3,guideOn;
boolean camOn = true;

// NumberOfItemsInArray is the array with pictures of the madplan
// days is the number of days in the madplan
int NumberOfItemsInArray = 3;
int days = 5;
int currentImg = 0;
int rect1X, rect1Y, rect2X, rect2Y, rect3X, rect3Y;
int rectSize1 = 1280/3-6;
int rectSize2 =  123;

int rectColor, rectHighlight;
int[] colors = {color(255, 255, 0), color(255, 0, 255), color(0, 255, 255), color(255, 0, 0), color(0, 255, 0), color(0, 0, 255), color(255, 255, 255)};

PImage img;
int x = 0;
Reader[] readers = new Reader[days];

String[] opskrifter = new String[days];
String[] dage = new String[days];

public void setup()
{
  String[] cameras = Capture.list();

  if (cameras.length == 0) {
    println("There are no cameras available for capture.");
    exit();
  } else {
    println("Available cameras:");
    for (int i = 0; i < cameras.length; i++) {
      print(i + " ");
      println(cameras[i]);
    }

    // The camera can be initialized directly using an 
    // element from the array returned by list():
        for (int i = 0; i < cameras.length; i++) {
      print(i + " ");
      println(cameras[i]);
      
      if( cameras[i].equals("name=EpocCam,size=1920x1080,fps=30") == true)
      {
        cam = new Capture(this, cameras[i]);
        println("this cam");
      }
    }
    cam.start();
  }      
  //Size of the window
  

  //The libary decoding the QR codes
  zxing4p = new ZXING4P();
  zxing4p.version();

  //the clickable button in the bottom of the app
  rectColor = color(200, 222, 226);
  rectHighlight = color(227,238,240);
  rect1X =  50;
  rect1Y = height-146;
  rect2X =  rectSize1;
  rect2Y = height-146;
  rect3X = (rectSize1*2);
  rect3Y = height-146;

  dage[0] = "Mandag";
  dage[1] = "Tirsdag";
  dage[2] = "Onsdag";
  dage[3] = "Torsdag";
  dage[4] = "Fredag";
}

public void draw()
{
  update(mouseX, mouseY);
  if (camOn == true) takePictureMenu();
  else reader();
}

//controls the highlight of the button in th bottom
public void update(int x, int y)
{
  if ( overRect(rect1X, rect1Y, rectSize1, rectSize2) ) {
    rectOver1 = true;
  } else {
    rectOver1 = false;
  }
  if ( overRect(rect2X, rect2Y, rectSize1, rectSize2) ) {
    rectOver2 = true;
  } else {
    rectOver2 = false;
  }
  if ( overRect(rect3X, rect3Y, rectSize1, rectSize2) ) {
    rectOver3 = true;
  } else {
    rectOver3 = false;
  }
}

//pulls a list of filesnames in the data folder
public String[] listFileNames(String dir) {
  File file = new File(dir);
  if (file.isDirectory()) {
    String names[] = file.list();
    return names;
  } else {
    // If it's not a directory
    return null;
  }
}

//checks if the cursor is over the button in the bottom
public boolean overRect(int x, int y, int width, int height) {
  if (mouseX >= x && mouseX <= x+width && 
    mouseY >= y && mouseY <= y+height) {
    return true;
  } else {
    return false;
  }
}
//Functions controlling the different imputs

public void mouseDragged() {
  cursor(HAND);
  for (int i = 0; i < readers.length; i = i+1) {
    if (readers[i].mouseOver()) {
      if (mouseX > pmouseX) {
        readers[i].x = readers[i].x + (mouseX - pmouseX);
      } else {
        readers[i].x = readers[i].x - (pmouseX - mouseX);
      } // if (mouseX > pmouseX)
      if (mouseY > pmouseY) {
        readers[i].y = readers[i].y + (mouseY - pmouseY);
      } else {
        readers[i].y = readers[i].y - (pmouseY - mouseY);
      }
    }
  }
} 

public void mouseReleased() {
  cursor(ARROW);
  if (rectOver2 && camOn == true) {
    takePicture();
    //instantiating the QR code readers
    
    camOn = false;
  } else if (rectOver2 && camOn == false) {
    for (int i = 0; i < readers.length; i = i+1) {
      println(i);
      readers[i].read(img);
    }
  }
}

public void keyPressed() {
 if (key == ' ') {
    x = 0;
    camOn = true;
 }
  if (key == 'x' && guideOn == true) {
    guideOn = false;
 }
 else if (key == 'x' && guideOn == false) {
    guideOn = true;
 }

 
}
public void takePictureMenu()
{
  camOn = true;
  background(246,251,249);
  if (cam.available() == true && camOn == true) {
    cam.read();
  }
  image(cam, 10, 10, cam.width/2, cam.height/2);
  
  //Button in the bottom
  pushStyle();
  noStroke();
  if (rectOver2) {
    fill(rectHighlight);
  } else {
    fill(rectColor);
  }
  rect(rect2X, rect2Y, rectSize1, rectSize2);
  
  fill(57, 72, 74);
  textAlign(CENTER);
  textSize(48);
  text("Take Picture", width/2, height-70);
  if(guideOn){
    for (int y = 0; y <  readers.length; y = y+1) {
      rect(15+(190*y), 335, 180, 180);
    }
  }
}

public void takePicture()
{
  cam.save("data/MadPlan.png");
}

class Reader
{
  int x;
  int y;
  int w;
  int h;
  String decodedText = "";
  PImage qrReadArea;
  PImage img2;
  int c = color(255, 0, 0);
  boolean isThere = false;


  Reader(int x, int y, int w, int h, PImage img2, int c)
  {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.img2 = img2;
    this.c = c;
    qrReadArea = img;
  }

  //function reading the image - it gets a part of the image and passes it to the libary decoding the images
  public void read(PImage img)
  {
    println("reading");
    qrReadArea = img.get(x*2, y*2, w*2, h*2); 
    decodedText = "";

    try {  
      // QRCodeReader(PImage img, boolean tryHarder)
      // tryHarder: false => fast detection (less accurate)
      //            true  => best detection (little slower)
      decodedText = zxing4p.QRCodeReader(qrReadArea, true);
      isThere = true;
    } 
    catch (Exception e) {  
      println("Zxing4processing exception: "+e);
      decodedText = "Didn't Read";
      isThere = false;
    } // try
  } 



  public void draw()
  {
    tint(255, 180);
    image(qrReadArea, x+10, y+10, w, h);
    tint(255, 255);
    stroke(c);
    strokeWeight(4);
    fill(0, 0, 0, 0);
    rect(x+10, y+10, w, h);
  }
  
 public int getPosX()
 {
   return x;
 }
 
 public int getPosY()
 {
   return y;
 }

  public boolean mouseOver() {
    return (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h);
  } // mouseOver()
}
public void reader()
{
  while (x < 1) {
  img = loadImage("MadPlan.png");
  for (int y = 0; y <  readers.length; y = y+1) {
      readers[y] = new Reader(15+(190*y), 335, 180, 180, img, colors[y]);
      opskrifter[y] = "";
    }
    println("gogogo");
  x= 10;
} 
  //black background
  background(246,251,249);

  //Picture of madplan
  pushStyle();
  pushMatrix();
  image(img, 10, 10, img.width/2, img.height/2);
  popStyle();
  popMatrix();

  //Button in the bottom
  pushStyle();
  noStroke();
  if (rectOver2) {
    fill(rectHighlight);
  } else {
    fill(rectColor);
  }
  rect(rect2X, rect2Y, rectSize1, rectSize2);

  fill(57, 72, 74);
  textAlign(CENTER);
  textSize(48);
  text("Scan", width/2, height-70);
  textSize(20);
  textAlign(LEFT);
  text("Press space to take new picture", 10, height-70);

  //Upper left text
  textSize(24);
  textAlign(LEFT);
  for (int i = 0; i < readers.length; i = i+1) {
    if (readers[i].isThere == false) 
    {
      pushStyle();
      fill(readers[i].c);
      text("None",  width-120, 60+(20*i));
      opskrifter[i] = "none";
      popStyle();
    } else 
    {
      pushStyle();
      fill(readers[i].c);
      text(readers[i].decodedText, width-120, 60+(20*i));
      opskrifter[i] = readers[i].decodedText;
      popStyle();
    }
  }
  popStyle();

  //The readers
  for (int i = 0; i < readers.length; i = i+1) {
    readers[i].draw();
    fill(colors[i]);
    text(i+1, readers[i].getPosX(), readers[i].getPosY());
  }
  
}
  public void settings() {  size(1280, 720); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "CoopQrCodeMoveAble" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
