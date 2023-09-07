OctoWS2811 leds = new OctoWS2811(240, 60);

void setup() 
{
  frameRate(120);
  size(920, 500, P2D);
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

void draw()
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

void DrawSplashScreen()
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

void LoadLevel(int level)
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

void DrawLevel()
{
  for(int currentX = 0; currentX < maxX; currentX ++) {
    for (int currentY = 0; currentY < maxY; currentY++) {
      leds.setPixelXY(currentX, currentY, colorSet[board[currentY][currentX]]);
    }
  }
}

void DrawPaddle()
{
  DrawLine(paddlePosition, maxY - 1, paddlePosition + paddleWidth, maxY - 1, colorSet[paddleColor]);
  paddleFrameCounter++;
  if (paddleFrameCounter == paddleSpeed) paddleFrameCounter = 0;
}

void DrawBall() {
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

void DestroyBlock(int x, int y) {
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

void MoveBlocks(int offset) {
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