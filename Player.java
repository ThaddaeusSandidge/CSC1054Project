import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Player extends GameObject
{
   //Player variables
   Color c;
   private static boolean victory;
   
   //Player constructor
   public Player(int x, int y, Color c)
   {
      super(x,y,c);
      this.c = c;    
   }

   //Move method
   public boolean move(int xMove, int yMove, ArrayList<ArrayList<GameObject>> map)
   {
      //Return boolean move
      boolean move = true;
      
      //Find current x and y location
      int tempX = getX();
      int tempY = getY();
      
      //Set locations of move
      int xCheck = getX() + xMove;
      int yCheck = getY() + yMove;
      
      //Check if x value collides, if true, set x back to previous position
      setX(xCheck);
      if(collides(map))
      {
         setX(tempX);
         move = false;
      }
      
      //Check if y value collides, if true, set y back to previous position
      setY(yCheck);
      if(collides(map))
      {
         setY(tempY);
         move = false;
      }
       
      return move;
   
   } 
   
   public boolean isOnGround(ArrayList<ArrayList<GameObject>> map)
   {
      //Will return on ground
      boolean onGround = false;
      
      //Find current y value
      int tempY = getY();
      
      //Set the value of y to be 1 pixel more
      int yCheck = getY() + 1;
      setY(yCheck);
      
      //Check if player collides with floor
      if(collides(map))
      {
         onGround = true;
      } 
      
      //Set y back to current location and return boolean
      setY(tempY);
      return onGround;
   } 
   public boolean isOnCeiling(ArrayList<ArrayList<GameObject>> map)
   {
      //Will return on ceiling
      boolean onCeiling = false;
      
      //Find current y value
      int tempY = getY();
      
      //Set the value of y to be 1 pixel more
      int yCheck = getY() - 1;
      setY(yCheck);
      
      //Check if player collides with floor
      if(collides(map))
      {
         onCeiling = true;
      } 
      //Set y back to current location and return boolean
      setY(tempY);
      return onCeiling;
   } 
   
   public boolean collides(ArrayList<ArrayList<GameObject>> map)
   {
      //Will return collide
      boolean collide = false;
      
      //Find row and column of player by dividing by 25
      int blockLevelY = getY()/25;
      int blockLevelX = getX()/25;
      
      //Ensure block level is not out of bound for for loop
      if(blockLevelY == 0)
      {
         blockLevelY = 1;
      }
      if(blockLevelX == 0)
      {
         blockLevelX = 1;
      }
      
      //For loop that checks for collision in a 3 block x 3 block area around player
      for(int i=(blockLevelX-1);i<(blockLevelX+2);i++)
      {
         for(int j=(blockLevelY-1);j<(blockLevelY+2);j++)
         {
            if(!(map.get(j).get(i) == null)) //If object is null, disregard
            {
               if(super.collides(map.get(j).get(i))) //Call super collide method
               {
                  collide = true;
                  if(map.get(j).get(i) instanceof VictoryBlock) //If collision is instance of victory block, set victory to be true
                  {
                     victory = true;
                  }
               }
            }
         }
      }
      return collide;
   } 
   
   //Static method for collision with victory block 
   public static boolean victorious()
   {
      return victory;
   }


}