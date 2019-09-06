//Functions contolling the different imputs

void mouseDragged() {
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

void mouseReleased() {
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
} // mouseReleased()

void keyPressed() {
 if (key == ' ') {
    x = 0;
    camOn = true;
 }
}
