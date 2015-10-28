import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class yijing1 extends PApplet {

PGraphics img;
String text;
PFont font;
int mode; // 0 = random, 1 = binary
int index; // For when we want to cycle through in order (binary mode)

public void setup() {
  //noLoop(); // testing
  mode = 0;
  index = 0;
  
  background(255);
  fill(0);
  frameRate(1);
  img = createGraphics(300, 300);
  text = "";
  font = loadFont("Consolas-32.vlw");
  textFont(font);
  textAlign(CENTER);
}

public void draw() {
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

public void mouseClicked() {
  mode = (mode + 1) % 2;
  println(mode);
}


class Gua {
  int[] yao = new int[6];
  PImage img;
  int intValue = 0; // Used to cast the binary to base 10 to represent the hexagram that way
  static final int height = 300;
  static final int width = 300;
  static final int lineHeight = height / 12; // the height of a line; voids are equal height to lines
  static final int segmentSize = width / 5; // Used to determine the ratio of line to white in yin line; must be an odd number to allow middle to be empty

  private int x = 0; // remember that (0,0) is the top left corner. Not using x really.
  private int y = 0; // Used when moving down the hexagram while drawing it



  // Default constructor instantiates a random hexagram
  Gua() {
    for (int i = 0; i < 6; i++) {
      yao[i] = round(random(0, 1));
      print(yao[i]);
    }
    println();
  } // end Gua()
  
  Gua(int value) {
    String binary = binary(value, 6);
    println("parsed binary: " + binary);
    for (int i = 0; i < binary.length(); i++) {
    yao[i] = Character.getNumericValue(binary.charAt(i));
    print(yao[i]);
    }    
    println();
  } // end Gua(int)

  // Returns an image representation of the hexagram
  public PGraphics drawGua() {

    PGraphics pg = createGraphics(width, height); // the image buffer we will return
    pg.beginDraw();
    pg.fill(0);
    pg.background(255);
    int i = 0; 

      for (y = 0; y <= 250; y = y + 50) {
      //y = (height / 6 ) * i;
      //println("y = " + y);
      if (yao[i] == 0) { // yin        
        pg.rect(x, y, (segmentSize * 2), lineHeight); // left side //<>//
        pg.rect(segmentSize * 3, y, (segmentSize * 2), lineHeight); // right side //<>//
        println("-- --");
      } // end if
      else { // yang
        pg.rect(x, y, width, lineHeight); //<>//
        println("-----");
      } // end else
      i++;
    } //end for
    pg.endDraw();
    return pg; //<>//
  } // end drawGua()
  
  
  public String toString() {
    return join(str(yao),"");
  }// end toString()
  
  public int toInt() {
    return unbinary(toString());
  }// end toInt()
  
} // End Gua
  public void settings() {  size(400, 400); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "yijing1" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
