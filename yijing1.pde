PGraphics img;
String text;
PFont font;
int mode; // 0 = random, 1 = binary
int index; // For when we want to cycle through in order (binary mode)

void setup() {
  //noLoop(); // testing
  mode = 0;
  index = 0;
  size(400, 400);
  background(255);
  fill(0);
  frameRate(1);
  img = createGraphics(300, 300);
  text = "";
  font = loadFont("Consolas-32.vlw");
  textFont(font);
  textAlign(CENTER);
}

void draw() {
  Gua gua;
  background(255); // So we can redraw the text
  switch (mode) {
  case 1: // Binary sequence
    gua = new Gua(index);
    index = (index + 1) % 64;
    break;
  default: // random mode is default mode but also '0'
    gua = new Gua();
  }
    img = gua.drawGua();
    image(img, 50, 50);
    text = gua.toString();
    text(text, 200, 375);
    println("Binary: " + gua.toString());
    println("Int value: " + gua.toInt());
  
}

void mouseClicked() {
  mode = (mode + 1) % 2;
  println(mode);
}