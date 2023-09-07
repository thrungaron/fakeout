import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import themidibus.*; 
import java.awt.event.KeyEvent; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Fakeout extends PApplet {

OctoWS2811 leds = new OctoWS2811(240, 60);

public void setup() 
{
  frameRate(120);
  
  background(32, 32, 32);
  
  if (platformNames[platform] == "windows")
    midi.begin();
  else if (platformNames[platform] == "macosx")
    midi.begin("Bus 1");
  else
    midi.list(); // List all available MIDI device names and indexes to the console.

  midi.setInstrument(0, MIDIinstrument.TaikoDrum);  
  midi.setInstrument(1, MIDIinstrument.Woodblock);
  midi.setInstrument(2, MIDIinstrument.Tuba);
  
  midi.setInstrument(3, MIDIinstrument.OverdrivenGuitar);
  midi.setInstrument(4, MIDIinstrument.RockOrgan);
  
  midi.setInstrument(5, MIDIinstrument.Trumpet);
  midi.setInstrument(6, MIDIinstrument.ChoirAahs);
  
  midi.setInstrument(7, MIDIinstrument.Bassoon);
  midi.setInstrument(8, MIDIinstrument.Accordion);

  leds.begin();
  leds.show();
}

final int PADDLE_CHANNEL  = 9;
final int PADDLE_PITCH     = MIDIdrum.SideStick.ToInt();  
final int PADDLE_VELOCITY = 96;
final int PADDLE_LENGTH   = 20;

final int BRICK_CHANNEL  = 0;
final int BRICK_PITCH     = MIDInote.Fs6.ToInt();
final int BRICK_VELOCITY = 127;
final int BRICK_LENGTH   = 200;

final int WALL_CHANNEL  = 1;
final int WALL_PITCH     = MIDInote.C4.ToInt();
final int WALL_VELOCITY = 64;
final int WALL_LENGTH   = 20;

final int DIE_CHANNEL  = 2;
final int DIE_PITCH     = MIDInote.C4.ToInt();
final int DIE_VELOCITY = 96;
final int DIE_LENGTH   = 120;

final int THEME1_CHANNEL = 3;
final int THEME1_VELOCITY = 64;
final int THEME1_LENGTH = 200;

final int THEME2_CHANNEL = 4;
final int THEME2_VELOCITY = 64;
final int THEME2_LENGTH = 200;

final int VICTORY1_CHANNEL = 5;
final int VICTORY1_VELOCITY = 96;
final int VICTORY1_LENGTH = 1000;

final int VICTORY2_CHANNEL = 6;
final int VICTORY2_VELOCITY = 96;
final int VICTORY2_LENGTH = 1000;

final int DEFEAT1_CHANNEL = 7;
final int DEFEAT1_VELOCITY = 96;
final int DEFEAT1_LENGTH = 1000;

final int DEFEAT2_CHANNEL = 8;
final int DEFEAT2_VELOCITY = 96;
final int DEFEAT2_LENGTH = 1000;

int board[][] = {{0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000},
                 {0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000, 0x000000}};

int colorSets[][] = {{0x000000, 0xFF0000, 0x00FF00, 0x0000FF},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF, 0x00FFFF},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF, 0x00FFFF},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF, 0x00FFFF},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF, 0x00FFFF, 0xFFCCCC},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF, 0x00FFFF, 0xFFCCCC},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF, 0x00FFFF, 0xFFCCCC},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF, 0x00FFFF, 0xFFCCCC, 0x004400},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF, 0x00FFFF, 0xFFCCCC, 0x004400},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF, 0x00FFFF, 0xFFCCCC, 0x004400},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF, 0x00FFFF, 0xFFCCCC, 0x004400, 0x993300},
                     {0x000000, 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF, 0x00FFFF, 0xFFCCCC, 0x004400, 0x993300}};
int colorSetLengths[] = {
  4,
  5,
  5,
  5,
  6,
  6,
  6,
  7,
  7,
  7,
  8,
  8,
  8,
  9,
  9,
  9,
 10,
 10,
};

//int *colorSet;  //C++
int colorSet[] = {0x000000, 0xFF0000, 0x00FF00, 0x0000FF};
int colorSetLength = 4;

boolean splashScreenLoaded = false;
boolean splashScreen = true;
int splashCounter = 0;

int level = -1;
boolean nextLevel = true;
boolean betweenLevels = true;

int songSpeed = 30;
int songCounter = 0;
int chordProgressionLength = 32;
int chordProgressionCounter = 0;

int numBlocks = 0;
int rectWidth = 10;
int rectHeight = 3;
int maxX = 60;
int maxY = 32;
int maxBlockY = 6;

int paddleColor = 1;
int paddleWidth = 15;
int paddlePosition = (maxX - paddleWidth) / 2;
int paddleSpeed = 5;
int paddleFrameCounter = 0;
boolean paddleJustMoved = false;

int ballColor = 1;
int ballPosition[] = {maxX/2, maxY - 2};
boolean ballLaunched = true;
int ballFrameCounter = 0;
boolean ballJustMoved = false;
int ballSpeed = 8;
int ballDX = 1;
int ballDY = -1;

int lives = 3;
int score = 0;

public void draw()
{
  midi.processNoteOffs();
  
  if (splashScreen == false) {
    if(nextLevel == true && betweenLevels == false) {
      if (level != -1) {
        for(int i = 0; i < 20; i++) {
          delay(50);
          midi.processNoteOffs();
        }
      }
      nextLevel = false;
      level++;
      LoadLevel(level);
    } else if(nextLevel == true && betweenLevels == true) {
      betweenLevels = false;
      DrawLevel();
      
      DrawPaddle();
      DrawBall();
      
      DrawNumber(lives, 56, 24, 0xFFFFFF, false);
      DrawNumber(score, 1, 24, 0xFFFFFF, false);
    }
    else
    {
      DrawLevel();
    }
    
    if(betweenLevels == false && nextLevel == false) {
      if((buttons.l1 == true || buttons.l2 == true) &&
         (paddleFrameCounter % paddleSpeed == 0)) {
        if (paddlePosition >= 1) {
          paddlePosition--;
          if (ballLaunched == false) {
            ballPosition[0]--;
            ballDX = -1;
          }
        } else {
          paddlePosition = 0;
        }
      }
      
      if((buttons.r1 == true || buttons.r2 == true) &&
         (paddleFrameCounter % paddleSpeed == 0)) {
        if (paddlePosition + paddleWidth < 59) {
          paddlePosition++;
          if (ballLaunched == false) {
            ballPosition[0]++;
            ballDX = 1;
          }
        } else {
          paddlePosition = 59 - paddleWidth;
        }
      }
      
      if(buttons.x1Clicked() == true || buttons.x2Clicked() == true) {
        int offset = 1;
        MoveBlocks(offset);
      }
      
      if(buttons.b1Clicked() == true || buttons.b2Clicked() == true) {
        int offset = -1;
        MoveBlocks(offset);
      }
      
      if(buttons.y1Clicked() == true || buttons.y2Clicked() == true) {
        paddleColor++;
        if(paddleColor == colorSetLength) {
          paddleColor = 1;
        }
        
        if(ballLaunched == false) {
          ballColor = paddleColor;
        }
      }
      
      if(buttons.a1Clicked() == true || buttons.a2Clicked() == true) {
        paddleColor--;
        if(paddleColor == 0) {
          paddleColor = colorSetLength - 1;
        }
        
        if(ballLaunched == false) {
          ballColor = paddleColor;
        }
      }
      
      if(buttons.u1Clicked() == true || buttons.u2Clicked() == true) {
        if(ballLaunched == false) {
          ballLaunched = true;
        }
      }
      
      if(buttons.d1Clicked() || buttons.d2Clicked()) {
        if(ballLaunched == true && ballPosition[1] == 30) {
          if((ballPosition[0] >= paddlePosition) && (ballPosition[0] <= paddlePosition + paddleWidth)) {
            ballLaunched = false;
            ballColor = paddleColor;
          }
        }
      }
      
      DrawPaddle();
      DrawBall();
      
      DrawNumber(lives, 56, 24, 0xFFFFFF, false);
      DrawNumber(score, 1, 24, 0xFFFFFF, false);
      
      if(numBlocks <= 0) {
        score += 500;
        lives++;
        nextLevel = true;
        betweenLevels = true;
        
        midi.playNote(VICTORY1_CHANNEL, MIDInote.C5.ToInt(), VICTORY1_VELOCITY, VICTORY1_LENGTH);
        midi.playNote(VICTORY1_CHANNEL, MIDInote.E5.ToInt(), VICTORY1_VELOCITY, VICTORY1_LENGTH);
        midi.playNote(VICTORY1_CHANNEL, MIDInote.G5.ToInt(), VICTORY1_VELOCITY, VICTORY1_LENGTH);
        
        midi.playNote(VICTORY2_CHANNEL, MIDInote.G5.ToInt(), VICTORY2_VELOCITY, VICTORY2_LENGTH);
        midi.playNote(VICTORY2_CHANNEL, MIDInote.B5.ToInt(), VICTORY2_VELOCITY, VICTORY2_LENGTH);
        midi.playNote(VICTORY2_CHANNEL, MIDInote.D6.ToInt(), VICTORY2_VELOCITY, VICTORY2_LENGTH);
      }
      
      if(lives <= 0) {
        midi.playNote(DEFEAT1_CHANNEL, MIDInote.C3.ToInt(), DEFEAT1_VELOCITY, DEFEAT1_LENGTH);
        midi.playNote(DEFEAT1_CHANNEL, MIDInote.Ds3.ToInt(), DEFEAT1_VELOCITY, DEFEAT1_LENGTH);
        midi.playNote(DEFEAT1_CHANNEL, MIDInote.G3.ToInt(), DEFEAT1_VELOCITY, DEFEAT1_LENGTH);
        
        midi.playNote(DEFEAT2_CHANNEL, MIDInote.G3.ToInt(), DEFEAT2_VELOCITY, DEFEAT2_LENGTH);
        midi.playNote(DEFEAT2_CHANNEL, MIDInote.As3.ToInt(), DEFEAT2_VELOCITY, DEFEAT2_LENGTH);
        midi.playNote(DEFEAT2_CHANNEL, MIDInote.D4.ToInt(), DEFEAT2_VELOCITY, DEFEAT2_LENGTH);
        
        for(int i = 0; i < 20; i++) {
          delay(50);
          midi.processNoteOffs();
        }
        
        level = -1;
        nextLevel = true;
        betweenLevels = false;
        lives = 3;
  
        splashScreen = true;
        splashScreenLoaded = false;
  
        ballPosition[0] = maxX/2;
        ballPosition[1] = 30;
        ballLaunched = true;
        
        songCounter = 0;
        chordProgressionCounter = 0;
  
        ClearScreen();
      }
    }
  } else {
    if (splashScreenLoaded == false) {
      DrawSplashScreen();
      splashScreenLoaded = true;
    }
    
    if (songCounter % songSpeed == 0) {
      if (chordProgressionCounter % chordProgressionLength == 0 ||
          chordProgressionCounter % chordProgressionLength == 2 ||
          chordProgressionCounter % chordProgressionLength == 4 ||
          chordProgressionCounter % chordProgressionLength == 6)
      {
        midi.playNote(THEME1_CHANNEL, MIDInote.G4.ToInt(), THEME1_VELOCITY, THEME1_LENGTH);
        midi.playNote(THEME1_CHANNEL, MIDInote.B4.ToInt(), THEME1_VELOCITY, THEME1_LENGTH);
        midi.playNote(THEME1_CHANNEL, MIDInote.D5.ToInt(), THEME1_VELOCITY, THEME1_LENGTH);
      }
      else if (chordProgressionCounter % chordProgressionLength == 8 ||
               chordProgressionCounter % chordProgressionLength == 10 ||
               chordProgressionCounter % chordProgressionLength == 12 ||
               chordProgressionCounter % chordProgressionLength == 14)
      {
        midi.playNote(THEME1_CHANNEL, MIDInote.E4.ToInt(), THEME1_VELOCITY, THEME1_LENGTH);
        midi.playNote(THEME1_CHANNEL, MIDInote.G4.ToInt(), THEME1_VELOCITY, THEME1_LENGTH);
        midi.playNote(THEME1_CHANNEL, MIDInote.B4.ToInt(), THEME1_VELOCITY, THEME1_LENGTH);
      }
      else if (chordProgressionCounter % chordProgressionLength == 16 ||
               chordProgressionCounter % chordProgressionLength == 18 ||
               chordProgressionCounter % chordProgressionLength == 20 ||
               chordProgressionCounter % chordProgressionLength == 22)
      {
        midi.playNote(THEME1_CHANNEL, MIDInote.C4.ToInt(), THEME1_VELOCITY, THEME1_LENGTH);
        midi.playNote(THEME1_CHANNEL, MIDInote.E4.ToInt(), THEME1_VELOCITY, THEME1_LENGTH);
        midi.playNote(THEME1_CHANNEL, MIDInote.G4.ToInt(), THEME1_VELOCITY, THEME1_LENGTH);
      }
      else if (chordProgressionCounter % chordProgressionLength == 24 ||
               chordProgressionCounter % chordProgressionLength == 26 ||
               chordProgressionCounter % chordProgressionLength == 28 ||
               chordProgressionCounter % chordProgressionLength == 30)
      {
        midi.playNote(THEME1_CHANNEL, MIDInote.D4.ToInt(), THEME1_VELOCITY, THEME1_LENGTH);
        midi.playNote(THEME1_CHANNEL, MIDInote.Fs4.ToInt(), THEME1_VELOCITY, THEME1_LENGTH);
        midi.playNote(THEME1_CHANNEL, MIDInote.A4.ToInt(), THEME1_VELOCITY, THEME1_LENGTH);
      }
      
      if (chordProgressionCounter % chordProgressionLength == 1 || chordProgressionCounter % chordProgressionLength == 17) {
        midi.playNote(THEME2_CHANNEL, MIDInote.G5.ToInt(), THEME2_VELOCITY, THEME2_LENGTH);
      }
      if (chordProgressionCounter % chordProgressionLength == 3 || chordProgressionCounter % chordProgressionLength == 19) {
        midi.playNote(THEME2_CHANNEL, MIDInote.E6.ToInt(), THEME2_VELOCITY, THEME2_LENGTH);
      }
      if (chordProgressionCounter % chordProgressionLength == 6 || chordProgressionCounter % chordProgressionLength == 22) {
        midi.playNote(THEME2_CHANNEL, MIDInote.C6.ToInt(), THEME2_VELOCITY, THEME2_LENGTH);
      }
      if (chordProgressionCounter % chordProgressionLength == 7 || chordProgressionCounter % chordProgressionLength == 23) {
        midi.playNote(THEME2_CHANNEL, MIDInote.E5.ToInt(), THEME2_VELOCITY, THEME2_LENGTH);
      }
      if (chordProgressionCounter % chordProgressionLength == 10 || chordProgressionCounter % chordProgressionLength == 26) {
        midi.playNote(THEME2_CHANNEL, MIDInote.C5.ToInt(), THEME2_VELOCITY, THEME2_LENGTH);
      }
      if (chordProgressionCounter % chordProgressionLength == 11 || chordProgressionCounter % chordProgressionLength == 27) {
        midi.playNote(THEME2_CHANNEL, MIDInote.A5.ToInt(), THEME2_VELOCITY, THEME2_LENGTH);
      }
      if (chordProgressionCounter % chordProgressionLength == 13 || chordProgressionCounter % chordProgressionLength == 29) {
        midi.playNote(THEME2_CHANNEL, MIDInote.B5.ToInt(), THEME2_VELOCITY, THEME2_LENGTH);
      }
      if (chordProgressionCounter % chordProgressionLength == 15 || chordProgressionCounter % chordProgressionLength == 30) {
        midi.playNote(THEME2_CHANNEL, MIDInote.D5.ToInt(), THEME2_VELOCITY, THEME2_LENGTH);
      }
      
      midi.playDrum(MIDIdrum.OpenHiHat,64,50);
      if(chordProgressionCounter % chordProgressionLength == 0 ||
         chordProgressionCounter % chordProgressionLength == 2 ||
         chordProgressionCounter % chordProgressionLength == 4 ||
         chordProgressionCounter % chordProgressionLength == 6 ||
         chordProgressionCounter % chordProgressionLength == 8 ||
         chordProgressionCounter % chordProgressionLength == 10 ||
         chordProgressionCounter % chordProgressionLength == 12 ||
         chordProgressionCounter % chordProgressionLength == 14 ||
         chordProgressionCounter % chordProgressionLength == 16 ||
         chordProgressionCounter % chordProgressionLength == 18 ||
         chordProgressionCounter % chordProgressionLength == 20 ||
         chordProgressionCounter % chordProgressionLength == 22 ||
         chordProgressionCounter % chordProgressionLength == 24 ||
         chordProgressionCounter % chordProgressionLength == 26 ||
         chordProgressionCounter % chordProgressionLength == 28 ||
         chordProgressionCounter % chordProgressionLength == 30) {
           midi.playDrum(MIDIdrum.BassDrum1,64,50);
      }
      if(chordProgressionCounter % chordProgressionLength == 1 ||
         chordProgressionCounter % chordProgressionLength == 3 ||
         chordProgressionCounter % chordProgressionLength == 5 ||
         chordProgressionCounter % chordProgressionLength == 7 ||
         chordProgressionCounter % chordProgressionLength == 9 ||
         chordProgressionCounter % chordProgressionLength == 11 ||
         chordProgressionCounter % chordProgressionLength == 13 ||
         chordProgressionCounter % chordProgressionLength == 15 ||
         chordProgressionCounter % chordProgressionLength == 17 ||
         chordProgressionCounter % chordProgressionLength == 19 ||
         chordProgressionCounter % chordProgressionLength == 21 ||
         chordProgressionCounter % chordProgressionLength == 23 ||
         chordProgressionCounter % chordProgressionLength == 25 ||
         chordProgressionCounter % chordProgressionLength == 27 ||
         chordProgressionCounter % chordProgressionLength == 29 ||
         chordProgressionCounter % chordProgressionLength == 31) {
           midi.playDrum(MIDIdrum.ElectricSnare,64,50);
      }
      if(chordProgressionCounter % chordProgressionLength == 0) {
        midi.playDrum(MIDIdrum.CrashCymbal1,64,50);
      }
      
      chordProgressionCounter++;
      if(chordProgressionCounter == chordProgressionLength) chordProgressionCounter = 0;
    }
    songCounter++;
    if (songCounter == songSpeed) songCounter = 0;
    
    DrawBall();
    
    DrawNumber(score, 1, 24, 0xFFFFFF, false);
    
    if (buttons.x1Clicked() || buttons.x2Clicked() || buttons.y1Clicked() || buttons.y2Clicked() || buttons.a1Clicked() || buttons.a2Clicked() || buttons.b1Clicked() || buttons.b2Clicked()) {
      splashScreen = false;
      splashScreenLoaded = false;
      
      ballPosition[0] = maxX/2;
      ballPosition[1] = 30;
      ballLaunched = false;
      
      score = 0;
    }
    
    splashCounter++;
    if(splashCounter > 640 * 25) {
      ClearScreen();
      
      splashCounter = 0;
      splashScreenLoaded = false;
  
      ballPosition[0] = maxX/2;
      ballPosition[1] = 30;
      
      songCounter = 0;
      chordProgressionCounter = 0;
    }
  }
  
  leds.show();
}

public void DrawSplashScreen()
{
  DrawRect(5,5,9,7,0xFF0000);
  DrawRect(5,10,9,12,0x00FF00);
  DrawRect(3,11,5,17,0x0000FF);
  DrawRect(3,5,5,11,0xFFFF00);
  
  DrawLine(9,16,12,5,0xFF00FF);
  DrawLine(10,16,13,5,0x00FFFF);
  DrawLine(14,5,17,16,0xFFCCCC);
  DrawLine(15,5,18,16,0x004400);
  DrawRect(12,11,16,13,0x993300);
  
  DrawRect(20,5,22,11,0xFF0000);
  DrawRect(20,11,22,17,0x00FF00);
  DrawLine(22,10,24,5,0x0000FF);
  DrawLine(23,10,25,5,0xFFFF00);
  DrawLine(22,11,24,16,0xFF00FF);
  DrawLine(23,11,25,16,0x00FFFF);
  
  DrawRect(29,5,33,7,0xFFCCCC);
  DrawRect(27,5,29,11,0x004400);
  DrawRect(29,10,33,12,0x993300);
  DrawRect(27,11,29,17,0xFF0000);
  DrawRect(29,15,33,17,0x00FF00);
  
  DrawLine(34,10,36,5,0x0000FF);
  DrawLine(35,10,37,5,0xFFFF00);
  DrawLine(34,11,36,16,0xFF00FF);
  DrawLine(35,11,37,16,0x00FFFF);
  DrawLine(38,5,40,10,0xFFCCCC);
  DrawLine(39,5,41,10,0x004400);
  DrawLine(38,16,40,11,0x993300);
  DrawLine(39,16,41,11,0xFF0000);
  
  DrawRect(43,5,45,11,0x00FF00);
  DrawRect(43,11,45,17,0x0000FF);
  DrawRect(45,15,47,17,0xFFFF00);
  DrawRect(47,5,49,11,0xFF00FF);
  DrawRect(47,11,49,17,0x00FFFF);
  
  DrawRect(50,5,52,7,0xFFCCCC);
  DrawRect(54,5,56,7,0x004400);
  DrawRect(52,5,54,11,0x993300);
  DrawRect(52,11,54,17,0xFF0000);
}

public void LoadLevel(int level)
{
  if(level >= 17) level=17;
  
  numBlocks = 0;
  
  int rectWidths[] = {10,10,6,6,6,5,5,5,4,4,4,3,3,3,2,2,2,1};
  rectWidth = rectWidths[level];
  int rectHeights[] = {3,3,3,3,3,3,2,2,2,2,2,2,1,1,1,1,1,1};
  rectHeight = rectHeights[level];
  maxX = 60;
  int maxBlockYs[] = {6,6,6,6,12,12,12,12,18,18,18,18,22,22,22,22,23,23};
  maxBlockY = maxBlockYs[level];

  colorSet = colorSets[level];
  colorSetLength = colorSetLengths[level];

  for(int currentX = 0; currentX < 60; currentX ++) {
    for (int currentY = 0; currentY < 32; currentY++) {
      board[currentY][currentX] = 0;
    }
  }

  int currentColor = -1;
  int previousColor = -1;
  for(int currentY = 0; currentY < maxBlockY; currentY += rectHeight) {
    for (int currentX = 0; currentX < maxX; currentX += rectWidth) {
      currentColor = (int)random(1,colorSetLength);
      if (currentX > 0) {
        while (currentColor == previousColor) {
          currentColor = (int)random(1,colorSetLength);
        }
      }
      DrawRect(currentX, currentY, currentX + rectWidth, currentY + rectHeight, colorSet[currentColor]);
      numBlocks++;
      for(int rectY = currentY; rectY < currentY + rectHeight; rectY++) {
        for(int rectX = currentX; rectX < currentX + rectWidth; rectX++) {
          board[rectY][rectX] = currentColor;
        }
      }
      previousColor = currentColor;
    }
  }
  
  paddleColor = 1;
  
  int paddleWidths[] = {15, 15, 15, 11, 11, 11, 9, 9, 9, 5, 5, 5, 3, 3, 3, 1, 1, 1};
  paddleWidth = paddleWidths[level];
  paddlePosition = (maxX - paddleWidth) / 2;
  
  ballColor = 1;
  ballLaunched = false;
  ballPosition[0] = (maxX / 2);
  ballPosition[1] = 30;
  ballDY = -1;
  
  ballFrameCounter = 0;
  paddleFrameCounter = 0;
  ballJustMoved = false;
  paddleJustMoved = false;
}

public void DrawLevel()
{
  for(int currentX = 0; currentX < maxX; currentX ++) {
    for (int currentY = 0; currentY < maxY; currentY++) {
      leds.setPixelXY(currentX, currentY, colorSet[board[currentY][currentX]]);
    }
  }
}

public void DrawPaddle()
{
  DrawLine(paddlePosition, maxY - 1, paddlePosition + paddleWidth, maxY - 1, colorSet[paddleColor]);
  paddleFrameCounter++;
  if (paddleFrameCounter == paddleSpeed) paddleFrameCounter = 0;
}

public void DrawBall() {
  if (ballLaunched == true) {
    if(ballJustMoved == true) {
      
      if(ballPosition[0] == maxX - 1 || ballPosition[0] == 0) {
        ballDX = -(ballDX);
        if (!splashScreen)
          midi.playNote(WALL_CHANNEL, WALL_PITCH, WALL_VELOCITY, WALL_LENGTH);
      }
      if(ballPosition[1] == 0) {
        ballDY = -(ballDY);
        if (!splashScreen)
          midi.playNote(WALL_CHANNEL, WALL_PITCH, WALL_VELOCITY, WALL_LENGTH);
      }
      
      if(splashScreen == false) {
        if(ballPosition[1] == maxY - 2) {
          if((ballPosition[0] >= paddlePosition - 1) && (ballPosition[0] <= paddlePosition + paddleWidth + 1)) {
            ballColor = paddleColor;
            ballDY = -(ballDY);
            midi.playNote(PADDLE_CHANNEL, PADDLE_PITCH, PADDLE_VELOCITY, PADDLE_LENGTH);
          }
        }
        if(ballPosition[1] == maxY - 1) {
          ballDY = -(ballDY);
          
          lives--;
          score = ((score - 50) > 0) ? score - 50 : 0;

          if(lives > 0) midi.playNote(DIE_CHANNEL, DIE_PITCH, DIE_VELOCITY, DIE_LENGTH);
          
          ballLaunched = false;
          ballPosition[0] = maxX / 2;
          ballPosition[1] = 30;
          ballColor = paddleColor;
          
          ballFrameCounter = 0;
          ballJustMoved = false;
          
          paddlePosition = (maxX - paddleWidth) / 2;
        }
        
        if((ballDY < 0) && (ballDX < 0) && (board[ballPosition[1] - 1][ballPosition[0] - 1] != 0x000000) &&
           (board[ballPosition[1]][ballPosition[0] - 1] == 0x000000) && (board[ballPosition[1] - 1][ballPosition[0]] == 0x000000)) {
          ballDY = -(ballDY);
          ballDX = -(ballDX);
          
          if(board[ballPosition[1] - 1][ballPosition[0] - 1] == ballColor) {
            DestroyBlock(ballPosition[0]-1, ballPosition[1]-1);
          }
          else
          {
            score = ((score - (level + 1)) > 0) ? score - (level + 1) : 0;
            midi.playNote(WALL_CHANNEL, WALL_PITCH, WALL_VELOCITY, WALL_LENGTH);
          }
        }
        
        if((ballDY < 0) && (ballDX > 0) && (board[ballPosition[1] - 1][ballPosition[0] + 1] != 0x000000) &&
           (board[ballPosition[1]][ballPosition[0] + 1] == 0x000000) && (board[ballPosition[1] - 1][ballPosition[0]] == 0x000000)) {
          ballDY = -(ballDY);
          ballDX = -(ballDX);
          
          if(board[ballPosition[1] - 1][ballPosition[0] + 1] == ballColor) {
            DestroyBlock(ballPosition[0]+1, ballPosition[1]-1);
          }
          else
          {
            score = ((score - (level + 1)) > 0) ? score - (level + 1) : 0;
            midi.playNote(WALL_CHANNEL, WALL_PITCH, WALL_VELOCITY, WALL_LENGTH);
          }
        }
        
        if((ballDY > 0) && (ballDX < 0) && (board[ballPosition[1] + 1][ballPosition[0] - 1] != 0x000000) &&
           (board[ballPosition[1]][ballPosition[0] - 1] == 0x000000) && (board[ballPosition[1] + 1][ballPosition[0]] == 0x000000)) {
          ballDY = -(ballDY);
          ballDX = -(ballDX);
          
          if(board[ballPosition[1] + 1][ballPosition[0] - 1] == ballColor) {
            DestroyBlock(ballPosition[0]-1, ballPosition[1]+1);
          }
          else
          {
            score = ((score - 1) > 0) ? score - 1 : 0;
            midi.playNote(WALL_CHANNEL, WALL_PITCH, WALL_VELOCITY, WALL_LENGTH);
          }
        }
        
        if((ballDY > 0) && (ballDX > 0) && (board[ballPosition[1] + 1][ballPosition[0] + 1] != 0x000000) &&
           (board[ballPosition[1]][ballPosition[0] + 1] == 0x000000) && (board[ballPosition[1] + 1][ballPosition[0]] == 0x000000)) {
          ballDY = -(ballDY);
          ballDX = -(ballDX);
          
          if(board[ballPosition[1] + 1][ballPosition[0] + 1] == ballColor) {
            DestroyBlock(ballPosition[0]+1, ballPosition[1]+1);
          }
          else
          {
            score = ((score - (level + 1)) > 0) ? score - (level + 1) : 0;
            midi.playNote(WALL_CHANNEL, WALL_PITCH, WALL_VELOCITY, WALL_LENGTH);
          }
        }
        
        if((ballDY < 0) && (board[ballPosition[1] - 1][ballPosition[0]] != 0x000000)) {
          ballDY = -(ballDY);
          if(board[ballPosition[1] - 1][ballPosition[0]] == ballColor) {
            DestroyBlock(ballPosition[0], ballPosition[1]-1);
          }
          else
          {
            score = ((score - (level + 1)) > 0) ? score - (level + 1) : 0;
            midi.playNote(WALL_CHANNEL, WALL_PITCH, WALL_VELOCITY, WALL_LENGTH);
          }
        }
        
        if((ballDY > 0) && (board[ballPosition[1] + 1][ballPosition[0]] != 0x000000)) {
          ballDY = -(ballDY);
          if(board[ballPosition[1] + 1][ballPosition[0]] == ballColor) {
            DestroyBlock(ballPosition[0], ballPosition[1]+1);
          }
          else
          {
            score = ((score - (level + 1)) > 0) ? score - (level + 1) : 0;
            midi.playNote(WALL_CHANNEL, WALL_PITCH, WALL_VELOCITY, WALL_LENGTH);
          }
        }
        
        if((ballDX < 0) && (board[ballPosition[1]][ballPosition[0] - 1] != 0x000000)) {
          ballDX = -(ballDX);
          if(board[ballPosition[1]][ballPosition[0] - 1] == ballColor) {
            DestroyBlock(ballPosition[0] - 1, ballPosition[1]);
          }
          else
          {
            score = ((score - (level + 1)) > 0) ? score - (level + 1) : 0;
            midi.playNote(WALL_CHANNEL, WALL_PITCH, WALL_VELOCITY, WALL_LENGTH);
          }
        }
        
        if((ballDX > 0) && (board[ballPosition[1]][ballPosition[0] + 1] != 0x000000)) {
          ballDX = -(ballDX);
          if(board[ballPosition[1]][ballPosition[0] + 1] == ballColor) {
            DestroyBlock(ballPosition[0] + 1, ballPosition[1]);
          }
          else
          {
            score = ((score - (level + 1)) > 0) ? score - (level + 1) : 0;
            midi.playNote(WALL_CHANNEL, WALL_PITCH, WALL_VELOCITY, WALL_LENGTH);
          }
        }
      } else {
        if(ballPosition[1] == maxY - 1) {
          ballDY = -(ballDY);
        }
        
        if((ballDY < 0) && (ballDX < 0) && (leds.getPixelXY(ballPosition[0] - 1, ballPosition[1] - 1) != 0x000000) &&
           (leds.getPixelXY(ballPosition[0] - 1, ballPosition[1]) == 0x000000) && (leds.getPixelXY(ballPosition[0], ballPosition[1] - 1) == 0x000000)) {
          ballDY = -(ballDY);
          ballDX = -(ballDX);
          leds.setPixelXY(ballPosition[0] - 1, ballPosition[1] - 1, 0x000000);
        }
        
        if((ballDY < 0) && (ballDX > 0) && (leds.getPixelXY(ballPosition[0] + 1, ballPosition[1] - 1) != 0x000000) &&
           (leds.getPixelXY(ballPosition[0] + 1, ballPosition[1]) == 0x000000) && (leds.getPixelXY(ballPosition[0], ballPosition[1] - 1) == 0x000000)) {
          ballDY = -(ballDY);
          ballDX = -(ballDX);
          leds.setPixelXY(ballPosition[0] + 1, ballPosition[1] - 1, 0x000000);
        }
        
        if((ballDY > 0) && (ballDX < 0) && (leds.getPixelXY(ballPosition[0] - 1, ballPosition[1] + 1) != 0x000000) &&
           (leds.getPixelXY(ballPosition[0] - 1, ballPosition[1]) == 0x000000) && (leds.getPixelXY(ballPosition[0], ballPosition[1] + 1) == 0x000000)) {
          ballDY = -(ballDY);
          ballDX = -(ballDX);
          leds.setPixelXY(ballPosition[0] - 1, ballPosition[1] + 1, 0x000000);
        }
        
        if((ballDY > 0) && (ballDX > 0) && (leds.getPixelXY(ballPosition[0] + 1, ballPosition[1] + 1) != 0x000000) &&
           (leds.getPixelXY(ballPosition[0] + 1, ballPosition[1]) == 0x000000) && (leds.getPixelXY(ballPosition[0], ballPosition[1] + 1) == 0x000000)) {
          ballDY = -(ballDY);
          ballDX = -(ballDX);
          leds.setPixelXY(ballPosition[0] + 1, ballPosition[1] + 1, 0x000000);
        }
        
        if((ballDY < 0) && (leds.getPixelXY(ballPosition[0], ballPosition[1] - 1) != 0x000000)) {
          ballDY = -(ballDY);
          leds.setPixelXY(ballPosition[0], ballPosition[1] - 1, 0x000000);
        }
        
        if((ballDY > 0) && (leds.getPixelXY(ballPosition[0], ballPosition[1] + 1) != 0x000000)) {
          ballDY = -(ballDY);
          leds.setPixelXY(ballPosition[0], ballPosition[1] + 1, 0x000000);
        }
        
        if((ballDX < 0) && (leds.getPixelXY(ballPosition[0] - 1, ballPosition[1]) != 0x000000)) {
          ballDX = -(ballDX);
          leds.setPixelXY(ballPosition[0] - 1, ballPosition[1], 0x000000);
        }
        
        if((ballDX > 0) && (leds.getPixelXY(ballPosition[0] + 1, ballPosition[1]) != 0x000000)) {
          ballDX = -(ballDX);
          leds.setPixelXY(ballPosition[0] + 1, ballPosition[1], 0x000000);
        }
      }
    }
    
    ballJustMoved = false;
    if(ballLaunched == true && (ballFrameCounter % ballSpeed == 0)) {
      if(splashScreen == true) {
        leds.setPixelXY(ballPosition[0], ballPosition[1], 0x000000);
      }
      
      ballPosition[0] += ballDX;
      ballPosition[1] += ballDY;
      ballJustMoved = true;
    }
  }
  ballFrameCounter++;
  if(ballFrameCounter == ballSpeed) ballFrameCounter = 0;
  
  if(splashScreen == false) {
    leds.setPixelXY(ballPosition[0], ballPosition[1], colorSet[ballColor]);
  } else {
    leds.setPixelXY(ballPosition[0], ballPosition[1], 0xFFFFFF);
  }
}

public void DestroyBlock(int x, int y) {
  int startX = x - (x % rectWidth);
  int startY = y - (y % rectHeight);
  for(int rectX = startX; rectX < startX + rectWidth; rectX++) {
      for(int rectY = startY; rectY < startY + rectHeight; rectY++) {
        board[rectY][rectX] = 0x000000;
      }
  }
  numBlocks--;
  score += 10;
  midi.playNote(BRICK_CHANNEL, BRICK_PITCH, BRICK_VELOCITY, BRICK_LENGTH);
}

public void MoveBlocks(int offset) {
  int pixelShift = rectWidth * offset;
  
  //int tempBoard[32][60];  //C++
  int[][] tempBoard = new int[maxY][maxX];
  for(int currentX = 0; currentX < maxX; currentX ++) {
    for (int currentY = 0; currentY < maxY; currentY++) {
      int arrayIndex = (((currentX + pixelShift) % maxX) >= 0) ? ((currentX + pixelShift) % maxX) : maxX + ((currentX + pixelShift) % maxX);
      tempBoard[currentY][currentX] = board[currentY][arrayIndex];
    }
  }
  
  if(tempBoard[ballPosition[1]][ballPosition[0]] == 0x000000) {
    //board = tempBoard;
    for(int currentX = 0; currentX < 60; currentX ++) {
      for (int currentY = 0; currentY < 32; currentY++) {
        board[currentY][currentX] = tempBoard[currentY][currentX];
      }
    }
  }
}


public class MickeyMIDI
{
  private class noteoffstruct
  {
   int ms;
   int channel;
   int note;
   noteoffstruct(int ms, int channel, int note)
   {
     this.ms = ms;
     this.channel = channel;
     this.note = note;
   }
  };
  private noteoffstruct[] noteoffs = new noteoffstruct[64];
  private int noteoffCount = 0;
  
  private void scheduleNoteOff(int channel, int note, int duration)
  {
    if (noteoffCount == 64)
      return;
    for (int ix = 0; ix < noteoffCount; ix++)
    {
      if (noteoffs[ix].channel == channel && noteoffs[ix].note == note)
      {
        noteoffs[ix].ms = millis() + duration;
        return;
      }
    }
    noteoffs[noteoffCount++] = new noteoffstruct(millis() + duration, channel, note);
  }

  public MidiBus myBus;

  public MickeyMIDI()
  {
      
  }

  public void begin()
  {
    myBus = new MidiBus(this, -1, "Microsoft GS Wavetable Synth");
  }

  public void begin(String deviceNaame)
  {
    myBus = new MidiBus(this, -1, deviceNaame);
  }

  public void list()
  {
    MidiBus.list();
  }
  
  public void processNoteOffs()
  {
    if (noteoffCount == 0)
      return;
    for (int ix = 0; ix < noteoffCount; ix++)
    {
      if (millis() >= noteoffs[ix].ms)
      {
        myBus.sendNoteOff(noteoffs[ix].channel, noteoffs[ix].note, 0);
        noteoffCount--;
        if (noteoffCount != 0)
          noteoffs[ix] = noteoffs[noteoffCount];
      }
    }
  }

  public void playNote(int channel, int note, int velocity, int duration)
  {
    myBus.sendNoteOn(channel, note, velocity);
    scheduleNoteOff(channel, note, duration);
  }

  public void playNote(int channel, MIDInote note, int velocity, int duration)
  {
    myBus.sendNoteOn(channel, note.ToInt(), velocity);
    scheduleNoteOff(channel, note.ToInt(), duration);
  }
  
  public void playDrum(MIDIdrum drum, int velocity)
  {
    myBus.sendNoteOn(9, drum.ToInt(), velocity);
    scheduleNoteOff(9, drum.ToInt(), 1000);
  }
  
  public void playDrum(MIDIdrum drum, int velocity, int duration)
  {
    myBus.sendNoteOn(9, drum.ToInt(), velocity);
    scheduleNoteOff(9, drum.ToInt(), duration);
  }
  
  public void setInstrument(int channel, MIDIinstrument inst)
  {
    myBus.sendMessage(0xC0 | channel, inst.ToInt());
  }

};

MickeyMIDI midi = new MickeyMIDI();


public enum MIDIinstrument
{
  AcousticGrandPiano(0),
  BrightAcousticPiano(1),
  ElectricGrandPiano(2),
  HonkyTonkPiano(3),
  ElectricPiano1(4),
  ElectricPiano2(5),
  Harpsichord(6),
  Clavi(7),
  Celesta(8),
  Glockenspiel(9),
  MusicBox(10),
  Vibraphone(11),
  Marimba(12),
  Xylophone(13),
  TubularBells(14),
  Dulcimer(15),
  DrawbarOrgan(16),
  PercussiveOrgan(17),
  RockOrgan(18),
  ChurchOrgan(19),
  ReedOrgan(20),
  Accordion(21),
  Harmonica(22),
  TangoAccordion(23),
  AcousticGuitarNylon(24),
  AcousticGuitarSteel(25),
  ElectricGuitarJazz(26),
  ElectricGuitarClean(27),
  ElectricGuitarMuted(28),
  OverdrivenGuitar(29),
  DistortionGuitar(30),
  Guitarharmonics(31),
  AcousticBass(32),
  ElectricBassFinger(33),
  ElectricBassPick(34),
  FretlessBass(35),
  SlapBass1(36),
  SlapBass2(37),
  SynthBass1(38),
  SynthBass2(39),
  Violin(40),
  Viola(41),
  Cello(42),
  Contrabass(43),
  TremoloStrings(44),
  PizzicatoStrings(45),
  OrchestralHarp(46),
  Timpani(47),
  StringEnsemble1(48),
  StringEnsemble2(49),
  SynthStrings1(50),
  SynthStrings2(51),
  ChoirAahs(52),
  VoiceOohs(53),
  SynthVoice(54),
  OrchestraHit(55),
  Trumpet(56),
  Trombone(57),
  Tuba(58),
  MutedTrumpet(59),
  FrenchHorn(60),
  BrassSection(61),
  SynthBrass1(62),
  SynthBrass2(63),
  SopranoSax(64),
  AltoSax(65),
  TenorSax(66),
  BaritoneSax(67),
  Oboe(68),
  EnglishHorn(69),
  Bassoon(70),
  Clarinet(71),
  Piccolo(72),
  Flute(73),
  Recorder(74),
  PanFlute(75),
  BlownBottle(76),
  Shakuhachi(77),
  Whistle(78),
  Ocarina(79),
  Lead1square(80),
  Lead2sawtooth(81),
  Lead3calliope(82),
  Lead4chiff(83),
  Lead5charang(84),
  Lead6voice(85),
  Lead7fifths(86),
  Lead8bassnlead(87),
  Pad1newage(88),
  Pad2warm(89),
  Pad3polysynth(90),
  Pad4choir(91),
  Pad5bowed(92),
  Pad6metallic(93),
  Pad7halo(94),
  Pad8sweep(95),
  FX1rain(96),
  FX2soundtrack(97),
  FX3crystal(98),
  FX4atmosphere(99),
  FX5brightness(100),
  FX6goblins(101),
  FX7echoes(102),
  FX8scifi(103),
  Sitar(104),
  Banjo(105),
  Shamisen(106),
  Koto(107),
  Kalimba(108),
  Bagpipe(109),
  Fiddle(110),
  Shanai(111),
  TinkleBell(112),
  Agogo(113),
  SteelDrums(114),
  Woodblock(115),
  TaikoDrum(116),
  MelodicTom(117),
  SynthDrum(118),
  ReverseCymbal(119),
  GuitarFretNoise(120),
  BreathNoise(121),
  Seashore(122),
  BirdTweet(123),
  TelephoneRing(124),
  Helicopter(125),
  Applause(126),
  Gunshot(127);

  private int value;
  private MIDIinstrument(int value)
  {
    this.value = value;
  }
  public int ToInt()
  {  
    return value;
  }
};


public enum MIDIdrum {
  AcousticBassDrum(35),
  BassDrum1(36),
  SideStick(37),
  AcousticSnare(38),
  HandClap(39),
  ElectricSnare(40),
  LowFloorTom(41),
  ClosedHiHat(42),
  HighFloorTom(43),
  PedalHiHat(44),
  LowTom(45),
  OpenHiHat(46),
  LowMidTom(47),
  HiMidTom(48),
  CrashCymbal1(49),
  HighTom(50),
  RideCymbal1(51),
  ChineseCymbal(52),
  RideBell(53),
  Tambourine(54),
  SplashCymbal(55),
  Cowbell(56),
  CrashCymbal2(57),
  Vibraslap(58),
  RideCymbal2(59),
  HiBongo(60),
  LowBongo(61),
  MuteHiConga(62),
  OpenHiConga(63),
  LowConga(64),
  HighTimbale(65),
  LowTimbale(66),
  HighAgogo(67),
  LowAgogo(68),
  Cabasa(69),
  Maracas(70),
  ShortWhistle(71),
  LongWhistle(72),
  ShortGuiro(73),
  LongGuiro(74),
  Claves(75),
  HiWoodBlock(76),
  LowWoodBlock(77),
  MuteCuica(78),
  OpenCuica(79),
  MuteTriangle(80),
  OpenTriangle(81);

  private int value;
  private MIDIdrum(int value)
  {
    this.value = value;
  }
  public int ToInt()
  {  
    return value;
  }
};


public enum MIDInote {
  C0(0),  Cs0(1),  D0(2),  Ds0(3),  E0(4),  F0(5),  Fs0(6),  G0(7),  Gs0(8),  A0(9),  As0(10),  B0(11),
  C1(12),  Cs1(13),  D1(14),  Ds1(15),  E1(16),  F1(17),  Fs1(18),  G1(19),  Gs1(20),  A1(21),  As1(22),  B1(23),
  C2(24),  Cs2(25),  D2(26),  Ds2(27),  E2(28),  F2(29),  Fs2(30),  G2(31),  Gs2(32),  A2(33),  As2(34),  B2(35),
  C3(36),  Cs3(37),  D3(38),  Ds3(39),  E3(40),  F3(41),  Fs3(42),  G3(43),  Gs3(44),  A3(45),  As3(46),  B3(47),
  C4(48),  Cs4(49),  D4(50),  Ds4(51),  E4(52),  F4(53),  Fs4(54),  G4(55),  Gs4(56),  A4(57),  As4(58),  B4(59),
  C5(60),  Cs5(61),  D5(62),  Ds5(63),  E5(64),  F5(65),  Fs5(66),  G5(67),  Gs5(68),  A5(69),  As5(70),  B5(71),
  C6(72),  Cs6(73),  D6(74),  Ds6(75),  E6(76),  F6(77),  Fs6(78),  G6(79),  Gs6(80),  A6(81),  As6(82),  B6(83),
  C7(84),  Cs7(85),  D7(86),  Ds7(87),  E7(88),  F7(89),  Fs7(90),  G7(91),  Gs7(92),  A7(93),  As7(94),  B7(95),
  C8(96),  Cs8(97),  D8(98),  Ds8(99),  E8(100),  F8(101),  Fs8(102),  G8(103),  Gs8(104),  A8(105),  As8(106),  B8(107),
  C9(108),  Cs9(109),  D9(110),  Ds9(111),  E9(112),  F9(113),  Fs9(114),  G9(115),  Gs9(116),  A9(117),  As9(118),  B9(119),
  C10(120),  Cs10(121),  D10(122),  Ds10(123),  E10(124),  F10(125),  Fs10(126),  G10(127);        

  private int value;
  private MIDInote(int value)
  {
    this.value = value;
  }
  public int ToInt()
  {  
    return value;
  }
};


class Buttons { 
  boolean u1 = false;
  boolean d1 = false;
  boolean l1 = false;
  boolean r1 = false;
  boolean y1 = false;
  boolean a1 = false;
  boolean x1 = false;
  boolean b1 = false;
  boolean u2 = false;
  boolean d2 = false;
  boolean l2 = false;
  boolean r2 = false;
  boolean y2 = false;
  boolean a2 = false;
  boolean x2 = false;
  boolean b2 = false;

  private boolean u1Prev = false, d1Prev = false, l1Prev = false, r1Prev = false, y1Prev = false, a1Prev = false, x1Prev = false, b1Prev = false;  
  private boolean u2Prev = false, d2Prev = false, l2Prev = false, r2Prev = false, y2Prev = false, a2Prev = false, x2Prev = false, b2Prev = false;  

  public boolean anykey(){return (u1 || d1 || l1 || r1 || y1 || a1 || x1 || b1 || u2 || d2 || l2 || r2 || y2 || a2 || x2 || b2);}

  public boolean u1Clicked(){if(u1){if(!u1Prev){u1Prev=true;return(true);}}else{u1Prev=false;}return(false);}
  public boolean d1Clicked(){if(d1){if(!d1Prev){d1Prev=true;return(true);}}else{d1Prev=false;}return(false);}
  public boolean l1Clicked(){if(l1){if(!l1Prev){l1Prev=true;return(true);}}else{l1Prev=false;}return(false);}
  public boolean r1Clicked(){if(r1){if(!r1Prev){r1Prev=true;return(true);}}else{r1Prev=false;}return(false);}
  public boolean y1Clicked(){if(y1){if(!y1Prev){y1Prev=true;return(true);}}else{y1Prev=false;}return(false);}
  public boolean a1Clicked(){if(a1){if(!a1Prev){a1Prev=true;return(true);}}else{a1Prev=false;}return(false);}
  public boolean x1Clicked(){if(x1){if(!x1Prev){x1Prev=true;return(true);}}else{x1Prev=false;}return(false);}
  public boolean b1Clicked(){if(b1){if(!b1Prev){b1Prev=true;return(true);}}else{b1Prev=false;}return(false);}

  public boolean u2Clicked(){if(u2){if(!u2Prev){u2Prev=true;return(true);}}else{u2Prev=false;}return(false);}
  public boolean d2Clicked(){if(d2){if(!d2Prev){d2Prev=true;return(true);}}else{d2Prev=false;}return(false);}
  public boolean l2Clicked(){if(l2){if(!l2Prev){l2Prev=true;return(true);}}else{l2Prev=false;}return(false);}
  public boolean r2Clicked(){if(r2){if(!r2Prev){r2Prev=true;return(true);}}else{r2Prev=false;}return(false);}
  public boolean y2Clicked(){if(y2){if(!y2Prev){y2Prev=true;return(true);}}else{y2Prev=false;}return(false);}
  public boolean a2Clicked(){if(a2){if(!a2Prev){a2Prev=true;return(true);}}else{a2Prev=false;}return(false);}
  public boolean x2Clicked(){if(x2){if(!x2Prev){x2Prev=true;return(true);}}else{x2Prev=false;}return(false);}
  public boolean b2Clicked(){if(b2){if(!b2Prev){b2Prev=true;return(true);}}else{b2Prev=false;}return(false);}
} 

Buttons buttons = new Buttons(); 


public void keyPressed()
{
  switch (key)
  {
    case 'w': buttons.u1 = true; break;
    case 's': buttons.d1 = true; break;
    case 'a': buttons.l1 = true; break;
    case 'd': buttons.r1 = true; break;
    case 'i': buttons.y1 = true; break;
    case 'k': buttons.a1 = true; break;
    case 'j': buttons.x1 = true; break;
    case 'l': buttons.b1 = true; break;

    case '5': buttons.y2 = true; break;
    case '1': buttons.a2 = true; break;
    case '2': buttons.x2 = true; break;
    case '3': buttons.b2 = true; break;

    case CODED:
      switch (keyCode)
      {
        case KeyEvent.VK_UP:       buttons.u2 = true; break;
        case KeyEvent.VK_DOWN:     buttons.d2 = true; break;
        case KeyEvent.VK_LEFT:     buttons.l2 = true; break;
        case KeyEvent.VK_RIGHT:    buttons.r2 = true; break;
        case KeyEvent.VK_KP_UP:    buttons.y2 = true; break;
        case KeyEvent.VK_KP_DOWN:  buttons.a2 = true; break;
        case KeyEvent.VK_KP_LEFT:  buttons.x2 = true; break;
        case KeyEvent.VK_KP_RIGHT: buttons.b2 = true; break;
      }
    break;    
  }
}

public void keyReleased()
{
  switch (key)
  {
    case 'w': buttons.u1 = false; break;
    case 's': buttons.d1 = false; break;
    case 'a': buttons.l1 = false; break;
    case 'd': buttons.r1 = false; break;
    case 'i': buttons.y1 = false; break;
    case 'k': buttons.a1 = false; break;
    case 'j': buttons.x1 = false; break;
    case 'l': buttons.b1 = false; break;

    case '5': buttons.y2 = false; break;
    case '1': buttons.a2 = false; break;
    case '2': buttons.x2 = false; break;
    case '3': buttons.b2 = false; break;
    case CODED:
      switch (keyCode)
      {
        case KeyEvent.VK_UP:       buttons.u2 = false; break;
        case KeyEvent.VK_DOWN:     buttons.d2 = false; break;
        case KeyEvent.VK_LEFT:     buttons.l2 = false; break;
        case KeyEvent.VK_RIGHT:    buttons.r2 = false; break;
        case KeyEvent.VK_KP_UP:    buttons.y2 = false; break;
        case KeyEvent.VK_KP_DOWN:  buttons.a2 = false; break;
        case KeyEvent.VK_KP_LEFT:  buttons.x2 = false; break;
        case KeyEvent.VK_KP_RIGHT: buttons.b2 = false; break;
      }
      break;    
  }
}


public class Sprite
{
  public int width;
  public int height;
  public int[][] image;
  public int[] palate;
  public Sprite(
    int width,
    int height,
    int[][] image,
    int[] palate)
  {
    this.width = width;
    this.height = height;
    this.image = image;    
    this.palate = palate;
  }
};

public void DrawSprite(Sprite sprite, int x, int y, boolean mirror)
{
  int row, col;
  int firstrow, lastrow;
  int firstcol, lastcol;
  int palateindex;

  if (y < 0)
    firstrow = -y;
  else
    firstrow = 0;

  if (x < 0)
    firstcol = -x;
  else
    firstcol = 0;

  if (y + sprite.height >= leds.rows)
    lastrow = leds.rows - y;
  else
    lastrow = sprite.height;
    
  if (x + sprite.width >= leds.cols)
    lastcol = leds.cols - x;
  else
    lastcol = sprite.width;

  for (row = firstrow; row < lastrow; row++)
  {
    for (col = firstcol; col < lastcol; col++)
    {
      if (mirror)
      {
        palateindex = sprite.image[row][sprite.width - col - 1];
      }
      else
      {
        palateindex = sprite.image[row][col];
      }
      if (palateindex != 255)
      {
        leds.setPixelXY(x + col, y + row, sprite.palate[palateindex]);
      }
    }
  }
}

public boolean CollisionCoarse(Sprite s1, int x1, int y1, boolean mirror1, Sprite s2, int x2, int y2, boolean mirror2)
{
  if (x2 < x1 + s1.width &&
      x2 + s1.width > x1 &&
      y2 < y1 + s1.height &&
      s2.height + y2 > y1)
    return true;
  else
    return false;
}

public boolean CollisionFine(Sprite s1, int x1, int y1, boolean mirror1, Sprite s2, int x2, int y2, boolean mirror2)
{
  int row1, col1;
  int row2, col2;
  int rowfrom1, colfrom1, rowto1, colto1;
  int rowfrom2, colfrom2;
  int color1, color2;

  if (!CollisionCoarse(s1, x1, y1, mirror1, s2, x2, y2, mirror2))
    return false;
  
  if (x1 > x2)
  {  
    colfrom1 = 0;
    colfrom2 = x1 - x2;
    colto1 = Math.min(s1.width - 1, colfrom1 + s2.width - colfrom2 - 1);
  }
  else
  { 
    colfrom2 = 0;
    colfrom1 = x2 - x1;
    colto1 = Math.min(s1.width - 1, colfrom1 + s2.width - colfrom2 - 1);
  }

  if (y1 > y2)
  {
    rowfrom1 = 0;
    rowfrom2 = y1 - y2;
    rowto1 = Math.min(s1.height - 1, rowfrom1 + s2.height - rowfrom2 - 1);
  }
  else
  { 
    rowfrom2 = 0;
    rowfrom1 = y2 - y1;
    rowto1 = Math.min(s1.height - 1, rowfrom1 + s2.height - rowfrom2 - 1);
  }

  //print(x1 + " " + x2 + " " + colfrom1 + " " + colto1 + " " + colfrom2 + " | ");
  //println(y1 + " " + y2 + " " + rowfrom1 + " " + rowto1 + " " + rowfrom2);

  for (row1 = rowfrom1, row2 = rowfrom2; row1 <= rowto1; row1++, row2++)
  {
    for (col1 = colfrom1, col2 = colfrom2; col1 <= colto1; col1++, col2++)
    {
      if (mirror1)
      {
        color1 = s1.image[row1][s1.width - col1 - 1];
      }
      else
      {
        color1 = s1.image[row1][col1];
      }
      if (mirror2)
      {
        color2 = s2.image[row2][s2.width - col2 - 1];
      }
      else
      {
        color2 = s2.image[row2][col2];
      }
      if (color1 != 255 && color2 != 255)
      {
        return true;
      }
    }
  }
  return false;
}

public void DrawNumber(int number, int x, int y, int rgbcolor, boolean rjust)
{
  int digit;
  int divisor;
  int mask;
  int row;
  int col;
  boolean gotnonzerodigit = false;
  int[] numbers = {0x7b6f,0x2c92,0x73e7,0x73cf,0x5bc9,0x79cf,0x79ef,0x7a49,0x7bef,0x7bcf};
  
  if (number < 0)
   number = -number;
  else if (number > 99999)
   number = 0;
  for (divisor = 10000; divisor > 0; divisor /= 10)
  {
    digit = number / divisor;
    if (digit != 0 || gotnonzerodigit == true || divisor == 1)
    {
      gotnonzerodigit = true;
      mask = 0x4000;
      for (row = 0; row < 5; row++)
      {        
        for (col = 0; col < 3; col++, mask >>= 1)
        {
          if ((numbers[digit] & mask) != 0) 
            leds.setPixelXY(x + col, y + row, rgbcolor);
        }
      }
      x += 4;
    }
    else if (rjust)
    {
      x += 4;
    }
    number -= digit * divisor;
  }
}

public void DrawBackground(int[] from)
{  
  for (int ix = 0; ix < 1920; ix++)
  {
    leds.setPixel(ix, from[ix]);
  }
}

public void DrawLine(int x1, int y1, int x2, int y2, int c)
{
 int x,y,dx,dy,dx1,dy1,px,py,xe,ye,i;
 dx=x2-x1;
 dy=y2-y1;
 dx1=abs(dx);
 dy1=abs(dy);
 px=2*dy1-dx1;
 py=2*dx1-dy1;
 if(dy1<=dx1)
 {
  if(dx>=0)
  {
   x=x1;
   y=y1;
   xe=x2;
  }
  else
  {
   x=x2;
   y=y2;
   xe=x1;
  }
  leds.setPixelXY(x, y, c);
  for(i=0;x<xe;i++)
  {
   x=x+1;
   if(px<0)
   {
    px=px+2*dy1;
   }
   else
   {
    if((dx<0 && dy<0) || (dx>0 && dy>0))
    {
     y=y+1;
    }
    else
    {
     y=y-1;
    }
    px=px+2*(dy1-dx1);
   }
   leds.setPixelXY(x, y, c);
  }
 }
 else
 {
  if(dy>=0)
  {
   x=x1;
   y=y1;
   ye=y2;
  }
  else
  {
   x=x2;
   y=y2;
   ye=y1;
  }
  leds.setPixelXY(x, y, c);
  for(i=0;y<ye;i++)
  {
   y=y+1;
   if(py<=0)
   {
    py=py+2*dx1;
   }
   else
   {
    if((dx<0 && dy<0) || (dx>0 && dy>0))
    {
     x=x+1;
    }
    else
    {
     x=x-1;
    }
    py=py+2*(dx1-dy1);
   }
   leds.setPixelXY(x, y, c);
  }
 }
}

public void DrawRect(int x1, int y1, int x2, int y2, int c)
{
  for (int row = y1; row < y2; row++)
    for (int col = x1; col < x2; col++)
     leds.setPixelXY(col, row, c);
}

public void DrawElipse(int ox, int oy, int rx, int ry, int c)
{
  int ry2 = ry * ry;
  int rx2 = rx * rx;
  int ry2rx2 = ry2 * rx2;
  int x0 = rx;
  int dx = 0;
  
  for (int x = -rx; x <= rx; x++)
    leds.setPixelXY(ox + x, oy, c);
  for (int y = 1; y <= ry; y++)
  {
    int x1 = x0 - (dx - 1);
    for ( ; x1 > 0; x1--)
      if (x1 * x1 * ry2 + y * y * rx2 < ry2rx2)
        break;
    dx = x0 - x1;
    x0 = x1;
    for (int x = -x0; x <= x0; x++)
    {
      leds.setPixelXY(ox + x, oy - y, c);
      leds.setPixelXY(ox + x, oy + y, c);
    }
  }
}

public void ClearScreen()
{
  for (int ix = 0; ix < 1920; ix++)
  {
    leds.drawBuffer[ix] = 0;
  }
}
/*  OctoWS2811 LED Display Library Emulator For Processing
    Copyright (c) 2016 Mickey Delp, Delptronics


	OctoWS2811 - High Performance WS2811 LED Display Library
    http://www.pjrc.com/teensy/td_libs_OctoWS2811.html
    Copyright (c) 2013 Paul Stoffregen, PJRC.COM, LLC

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
*/


class OctoWS2811 {
	private	int stripLen;
	private	int rows;
	private	int cols;
	private	int[] drawBuffer;
	private	byte params;

	OctoWS2811(int numPerStrip, int numPerRow)
	{
		stripLen = numPerStrip;
		cols = numPerRow;
		rows = 8 * numPerStrip / numPerRow;
		drawBuffer = new int[stripLen * 8];
	}

	public void begin()
	{
		noStroke();
	}

	//void setPixelr(int num, byte ar, byte ge, byte be)
	//{
	//	setPixel(num, (ar << 16) | (ge << 8) | be);
	//}

  public void setPixelXY(int x, int y, int rgbcolor)
  {
    if ((y & 1) == 0) {
      drawBuffer[y * cols + x] = rgbcolor | 0xFF000000;
    } else {
      drawBuffer[y * cols + cols - 1 - x] = rgbcolor | 0xFF000000;
    }
  }
  
  public int getPixelXY(int x, int y)
  {
    if ((y & 1) == 0) {
      return drawBuffer[y * cols + x] & 0x00FFFFFF;
    } else {
      return drawBuffer[y * cols + cols - 1 - x] & 0x00FFFFFF;
    }
  }

	public void setPixel(int num, int rgbcolor)
	{
		drawBuffer[num] = rgbcolor | 0xFF000000;
	}

	public int getPixel(int num)
	{
		return drawBuffer[num] & 0x00FFFFFF;
	}

	public void show()
	{
		int num = 0, row, col, y;

    for (row = 1; row <= rows; row++)
		{
      y = row * 15;
			if ((row & 1) == 1) // even
			{
				for (col = 1; col <= cols; col++, num++)
				{
          fill(adjustBrightness(drawBuffer[num]));
          ellipse(col * 15, y, 9, 9);
        }
      }
      else // odd
      {
        for (col = cols; col > 0; col--, num++)
        {
          fill(adjustBrightness(drawBuffer[num]));
          ellipse(col * 15, y, 9, 9);
        }
      }
    }
  }


  public int busy()
  {
    return 0;
  }

  public int numPixels() 
  {
    return stripLen * 8;
  }

  public int adjustBrightness(int color1) 
  {
    int[] color2 = {0,2,5,8,11,14,17,20,23,26,28,31,34,37,39,42,45,47,50,52,55,57,60,62,65,67,70,72,75,77,79,82,84,86,89,91,93,95,97,100,102,104,106,108,110,112,114,116,118,120,122,124,126,128,130,131,133,135,137,139,140,142,144,146,147,149,151,152,154,156,157,159,160,162,163,165,166,168,169,171,172,173,175,176,178,179,180,182,183,184,185,187,188,189,190,192,193,194,195,196,197,198,199,200,202,203,204,205,206,207,208,209,210,210,211,212,213,214,215,216,217,217,218,219,220,221,221,222,223,224,224,225,226,227,227,228,229,229,230,230,231,232,232,233,233,234,235,235,236,236,237,237,238,238,239,239,240,240,240,241,241,242,242,243,243,243,244,244,244,245,245,245,246,246,246,247,247,247,247,248,248,248,249,249,249,249,249,250,250,250,250,250,251,251,251,251,251,251,252,252,252,252,252,252,252,253,253,253,253,253,253,253,253,253,253,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,254,255};
    int r, g, b;
    r = color2[(color1 & 0xff0000) >> 16];
    g = color2[(color1 & 0x00ff00) >> 8];
    b = color2[(color1 & 0x0000ff)];
    return (0xFF000000 | r<<16 | g<<8 | b);
  }

};
  public void settings() {  size(920, 500, P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Fakeout" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
