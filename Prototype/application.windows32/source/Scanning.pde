void reader(){
  while (x < 1) {
  img = loadImage("MadPlan.png");
  for (int y = 0; y <  readers.length; y = y+1) {
      readers[y] = new Reader(25+(180*y), 55, 180, 180, img, colors[y]);
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
