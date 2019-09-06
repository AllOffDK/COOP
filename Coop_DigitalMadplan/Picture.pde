void takePictureMenu(){
  camOn = true;
  background(246,251,249);
  if (cam.available() == true && camOn == true) {
    cam.read();
  }

  image(cam, 10, 10, cam.width/2, cam.height/2);
  
  //Button in the bottom

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
  
    for (int y = 0; y <  readers.length; y = y+1) {
      pushStyle();
      
      stroke(255,54,25);
      strokeWeight(4);
      fill(0,0,0,0);
      rect(25+(180*y), 55, 180, 180);
      
      popStyle();
    }
}

void takePicture(){
  cam.save("data/MadPlan.png");
}
