void takePictureMenu()
{
  camOn = true;
  background(0);
  if (cam.available() == true && camOn == true) {
    cam.read();
  }
  image(cam, 10, 10, cam.width/2, cam.height/2);
  
  //Button in the bottom
  pushStyle();
  strokeWeight(6);
  stroke(0, 0, 0, 255);

  if (rectOver2) {
    fill(rectHighlight);
  } else {
    fill(rectColor);
  }
  rect(rect2X, rect2Y, rectSize1, rectSize2);

  fill(255, 255, 255);
  textAlign(CENTER);
  textSize(48);
  text("Take Picture", width/2, height-50);
}

void takePicture()
{
  cam.save("data/MadPlan.png");
}
