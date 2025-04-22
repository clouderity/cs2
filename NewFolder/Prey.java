//PROBLEM (solved)! It is not updating the up - left variables.
//PROBLEM! It can not identify bad tiles (walls, void)

import java.util.HashMap;
import static java.lang.Math.min;

class Prey {
  int position = 0;//The tile that the creature is on
  int up;//The tile # of the tile above the creature
  int down;
  int right;
  int left;
  int upGood = 1; //1 = true, 0 = false
  int downGood = 1;
  int rightGood = 1;
  int leftGood = 1;
  
  char primary = 'x';
  char secondary = 'x';
  char p1 = 'x';
  char p2 = 'x';
  char s1 = 'x';
  char s2 = 'x';
  
  boolean noMove = false;
  HashMap <Integer, Integer> memory = new HashMap <Integer, Integer>(); //Tile #, Timestep
  
//Prey constructor
  Prey (char p1, char p2, char s1, char s2, int position) {
    primary = p1;
    secondary = s1;
    this.p1 = p1;
    this.p2 = p2;
    this.s1 = s1;
    this.s2 = s2;
    this.position = position;//16 is 1 up and 1 left of center (24)
    up = position - 7;
    down = position + 7;
    right = position + 1;
    left = position - 1;
  }
  //The below is the first thing that the prey does upon being told to move
  public void startMove () {
    upGood = 1;//resets the goodness of the move
    downGood = 1;
    rightGood = 1;
    leftGood = 1;
    possibleMove();
  }//startMove

//This filters out any tiles that can not be physically moved to
  public void possibleMove () {
    //Is up out of bounds?
    if (up < 0) {
      upGood = 0;
    }
    //is up a wall?
    else if (Main.map[up] != 0) {
      upGood = 0;
    }
    //Is down out of bounds?
    if (down > 48) {
      downGood = 0;
    }
    //Is down a wall?
    else if (Main.map[down] != 0) {
      downGood = 0;
    }
    //Is right possible?
    for (int i = 6; i <= 48; i = i + 7) {
      if (position == i) {//out of bounds
        rightGood = 0;
        break;
      }
      else if (i == 48 && Main.map[right] != 0) {//wall
        rightGood = 0;
      }
    }
    //Is left possible?
    for (int i = 0; i <= 42; i = i + 7) {
      if (position == i) {//out of bounds
        leftGood = 0;
        break;
      }
      else if (i == 42 && Main.map[left] != 0) {
        leftGood = 0;
      }
    }
    //Is any move possible?
    if (upGood + downGood + rightGood + leftGood == 0) {
      Main.main(null);
    }

    else {
      recentMove();
    }
  }//possibleMove

//Which tile has been moved to least recently?
  public void recentMove () {
    int upTime;
    int downTime;
    int rightTime;
    int leftTime;
    int goodTime;
    //up
    if (upGood == 0) {
      upTime = 1000;
    }
    else if (memory.containsKey(up) == false) {
      upTime = 0;
    }
    else {
      upTime = memory.get(up);
    }
    //down
    if (downGood == 0) {
      downTime = 1000;
    }
    else if (memory.containsKey(down) == false) {
      downTime = 0;
    }
    else {
      downTime = memory.get(down);
    }
    //right
    if (rightGood == 0) {
      rightTime = 1000;
    }
    else if (memory.containsKey(right) == false) {
      rightTime = 0;
    }
    else {
      rightTime = memory.get(right);
    }
    //left
    if (leftGood == 0) {
      leftTime = 1000;
    }
    else if (memory.containsKey(left) == false) {
      leftTime = 0;
    }
    else {
      leftTime = memory.get(left);
    }
    //Comparing the times
    goodTime = min(upTime, downTime);
    goodTime = min(goodTime, rightTime);
    goodTime = min(goodTime, leftTime);
    //Final decision
    if (goodTime != upTime) {
      upGood = 0;
    }
    if (goodTime != downTime) {
      downGood = 0;
    }
    if (goodTime != rightTime) {
      rightGood = 0;
    }
    if (goodTime != leftTime) {
      leftGood = 0;
    }
    //Final move
    pMove();
  }//recentMove

//The below is the primary move function
  public void pMove () {//Not static becaue it belongs to an object
     if (primary == 'u' && upGood == 1) {
       Main.map[position] = 0;
       updatePosition(up);
     }

    else if (primary == 'd' && downGood == 1) {
      Main.map[position] = 0;
      updatePosition(down);
    }

    else if (primary == 'r' && rightGood == 1) {
      Main.map[position] = 0;
      updatePosition(right);
    }

    else if (primary == 'l' && leftGood == 1) {
      Main.map[position] = 0;
      updatePosition(left);
    }
//pMove was unsuccesfult, so the secondary move is attempted
    else {
      pSwitch();
      sMove();
    }
  }//pMove

//The below is the secondary move function
  public void sMove () {
     if (secondary == 'u' && upGood == 1) {
       Main.map[position] = 0;
       updatePosition(up);
     }

    else if (secondary == 'd' && downGood == 1) {
      Main.map[position] = 0;
      updatePosition(down);
    }

    else if (secondary == 'r' && rightGood == 1) {
      Main.map[position] = 0;
      updatePosition(right);
    }

    else if (secondary == 'l' && leftGood == 1) {
      Main.map[position] = 0;
      updatePosition(left);
    }
//sMove was unsuccessful
    else {
      sSwitch();
      pMove();
    }
  }//sMove

//The below switches the primary move
  public void pSwitch () {
    if (primary == p1) {
      primary = p2;
    }

    else {
      primary = p1;
    }
  }//pSwitch
  
//The below switches the secondary move
  public void sSwitch () {
    if (secondary == s1) {
    secondary = s2;
    }

    else {
      secondary = s1;
    }
  }//sSwitch

  public void updatePosition (int direction) {
    position = direction;
    up = position - 7;
    down = position + 7;
    right = position + 1;
    left = position - 1;
  }
}//Class