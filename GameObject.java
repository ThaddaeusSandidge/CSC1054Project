import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameObject
{
   //Member variables
   private int x;
   private int y;
   private Color c;
   
   //Constructor
   public GameObject(int x, int y, Color c)
   {
      this.x = x;
      this.y = y;
      this.c = c;

   }
   //X & Y mutators
   public void setY(int y)
   {
      this.y = y;
   }
   public void setX(int x)
   {
      this.x = x;
   }
   //X & Y accessor
   public int getX()
   {
      return x;
   }
   
   public int getY()
   {
      return y;
   }
   //Color accessor
   public Color getColor()
   {
      return c;
   }    
   
   public boolean collides(GameObject g)
   {
      //Ensure not colliding with self
      if (this == g) 
      {    
          return false;    
      }
      
      else
      {
         //This square
         double topthis = this.y;
         double bottomthis = this.y+25;
         double leftthis = this.x;
         double rightthis = this.x+25;
         
         //g square
         double topother = g.getY();
         double bottomother = g.getY()+25;
         double leftother = g.getX();
         double rightother = g.getX()+25;
         
         //Compare as specified by project doc
         boolean compare = (  (bottomthis <= topother) ||
                              (topthis >= bottomother) ||
                              (leftthis >= rightother) ||
                              (rightthis <= leftother));

         return (!(compare));
      } 
   } 
   
   //Draw method
   public void draw(Graphics g, Color c)
   {
      g.setColor(c);
      g.fillRect(x,y,25,25);
   }  

}