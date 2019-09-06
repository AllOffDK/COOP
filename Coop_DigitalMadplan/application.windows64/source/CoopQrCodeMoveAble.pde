//The libary doing the QR decoding
import com.cage.zxing4p3.*;
ZXING4P zxing4p;

// Java array tool
import java.util.Arrays;

import processing.video.*;

Capture cam;

boolean  rectOver1, rectOver2, rectOver3;
boolean camOn = true;

// NumberOfItemsInArray is the array with pictures of the madplan
// days is the number of days in the madplan
int NumberOfItemsInArray = 3;
int days = 5;
int currentImg = 0;
int rect1X, rect1Y, rect2X, rect2Y, rect3X, rect3Y;
int rectSize1 = 1280/3-6;
int rectSize2 =  123;

color rectColor, rectHighlight;
color[] colors = {color(255, 255, 0), color(255, 0, 255), color(0, 255, 255), color(255, 0, 0), color(0, 255, 0), color(0, 0, 255), color(255, 255, 255)};

PImage img;
int x = 0;
Reader[] readers = new Reader[days];

String[] opskrifter = new String[days];
String[] dage = new String[days];

void setup()
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
    cam = new Capture(this, cameras[23]);
    cam.start();
  }      


  size(1280, 860);

  //The libary decoding the QR codes
  zxing4p = new ZXING4P();
  zxing4p.version();

  //the clickable button in the bottom of the app
  rectColor = color(194, 0, 0);
  rectHighlight = color(51);
  rect1X =  10;
  rect1Y = height-126;
  rect2X =  10+rectSize1;
  rect2Y = height-126;
  rect3X = 10+(rectSize1*2);
  rect3Y = height-126;


  dage[0] = "Mandag";
  dage[1] = "Tirsdag";
  dage[2] = "Onsdag";
  dage[3] = "Torsdag";
  dage[4] = "Fredag";
  //dage[5] = "Lørdag";
  //dage[6] = "Søndag";


  //makes sure the app starts on the menu screen
}

void draw()
{


  update(mouseX, mouseY);

  if (camOn == true) takePictureMenu();
  else reader();
}

//controls the highlight of the button in th bottom
void update(int x, int y)
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
String[] listFileNames(String dir) {
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
boolean overRect(int x, int y, int width, int height) {
  if (mouseX >= x && mouseX <= x+width && 
    mouseY >= y && mouseY <= y+height) {
    return true;
  } else {
    return false;
  }
}
