
class Reader{
  int x;
  int y;
  int w;
  int h;
  String decodedText = "";
  PImage qrReadArea;
  PImage img2;
  color c = color(255, 0, 0);
  boolean isThere = false;


  Reader(int x, int y, int w, int h, PImage img2, color c){
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.img2 = img2;
    this.c = c;
    qrReadArea = img;
  }

  //function reading the image - it gets a part of the image and passes it to the libary decoding the images
  void read(PImage img){
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



  void draw(){
    tint(255, 180);
    image(qrReadArea, x+10, y+10, w, h);
    tint(255, 255);
    stroke(c);
    strokeWeight(4);
    fill(0, 0, 0, 0);
    rect(x+10, y+10, w, h);
  }
  
 int getPosX(){
   return x;
 }
 
 int getPosY(){
   return y;
 }

  boolean mouseOver() {
    return (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h);
  } // mouseOver()
}
