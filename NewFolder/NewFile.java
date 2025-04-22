//===================================
//Deterministic memory movement test
//===================================

import java.util.Scanner;
import java.util.Arrays;

class Main {
  static int[] map = { //7x7 Grid. Center is 42
    0,  0,  0, -1,  0,  0,  0,
   -1, -1,  0, -1,  0, -1, -1,
    0,  0,  0,  0,  0,  0,  0,
    0,  0, -1,  0, -1,  0,  0,
   -1,  0, -1, -1,  0,  0,  0,
    0,  0,  0,  0,  0, -1, -1,
    0, -1,  0, -1,  0,  0,  0
  };
  static Scanner in = new Scanner(System.in);
  static int step = 1;//Starts at one, so non visited tiles will appear as 0
  static Prey Baldwin = new Prey('r', 'l', 'd', 'u', 0);
  static Prey Keilani = new Prey('l', 'r', 'u', 'd', 48);
  
  public static void main(String[] args) {
    System.out.println("Press enter to start!");
    for (int i = 0; i < 1000; i++) {
      map[Baldwin.position] = -2;
      map[Keilani.position] = -2;
      System.out.println("# # # # # # # # #");
      printMap();
      System.out.println("# # # # # # # # #");
      System.out.println(step);
      Baldwin.memory.put(Baldwin.position, step);
      Keilani.memory.put(Keilani.position, step);
      in.nextLine();
      step++;
      Baldwin.startMove();
      Keilani.startMove();
    }
  }
//The below function converts numbers on the map into symbols
  public static String draw (int tile) {
    //If I ever implement food, I will use positive whole numbers
    if (tile == -2) { //Creature
      return "@";
    }

    else if (tile == -1) { //wall
      return "#";
    }

    else if (tile == 0) { //empty 
      return "-";
    }

    else {return "E";}//Error
  }
//This function prints the map, using the symbols generated from the draw function!
  public static void printMap () {
    for (int row = 0; row < 7; row++) {
      System.out.print("# ");
      for (int i = 0; i < 7; i++) {
        System.out.print(draw(map[i+(row*7)]) + " ");
      }
      System.out.println("#");
    }
  }
  
}

/* 
This is how to sort an array:
int[] temp = {348,2,563,23,56,3,5,-4};
Tools.leastGreatest(temp);
System.out.println(Arrays.toString(temp));
//smh
*/