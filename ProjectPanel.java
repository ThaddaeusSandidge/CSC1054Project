import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class ProjectPanel extends JPanel
{
   //Create variables
   private int xPlayer;
   private int yPlayer;
   private int columns;
   private int rows;
   private Player player;
   private ArrayList<ArrayList<GameObject>> gameGrid = new ArrayList<ArrayList<GameObject>>();
   private ProjectPanel p;
   
   public ProjectPanel()
   {
      //Add listeners
      p = this;
      addKeyListener(new KeyEventDemo());
      Timer t = new Timer(10, new TimeListener());
      setPreferredSize(new Dimension(800, 600));
      setFocusable(true);
      
      try
      {
         //Read in file with scanner
         Scanner read = new Scanner(new File("project.txt"));
         while(read.hasNext())
         {
            //Read in player data
            xPlayer = read.nextInt();
            yPlayer = read.nextInt();
            
            
            player = new Player((xPlayer*25), (yPlayer*25), Color.RED);
            
            
            //Grid data
            rows = read.nextInt();
            columns = read.nextInt();
            //Fill 2D array
            for(int i=0;i<rows;i++)
            {
               ArrayList<GameObject> innerList = new ArrayList<GameObject>();
               
               gameGrid.add(innerList);
            }

            //Read in level data
            int xPos = 0;
            int yPos = 0;
            
            //For loop for grid
            for(int i=0;i<rows;i++)
            {
               xPos = 0; //Set X to zero for to build rows down
               for(int j=0;j<columns;j++)
               {
                  int temp = read.nextInt();
                  //If 1, create block and add to grid
                  if(temp == 1)
                  {
                    Block block = new Block(xPos, yPos, Color.BLUE);
                    gameGrid.get(i).add(block);
                  } 
                  //If 2, create victory block and add to grid
                  if(temp == 2)
                  {
                    VictoryBlock victory = new VictoryBlock(xPos, yPos, Color.GREEN);
                    gameGrid.get(i).add(victory);
                  } 
                  //If 0, add null to grid
                  if(temp == 0)
                  {
                    gameGrid.get(i).add(null);
                  } 
                  xPos+=25; //Increment by width of block
               }
               yPos+=25; //Increment by height of block
               
            }
            
         }
      }
      //Catch FNFE
      catch(FileNotFoundException FNFE)
      {
         System.out.println("File not found");
      }
      
      //Start Timer
      t.start();

   }
   //Paint component
   public void paintComponent(Graphics g)    
   {  

      super.paintComponent(g);
      //Draw board according to gameGrid contents
      for(int i=0;i<rows;i++)
      {
         for(int j=0;j<columns;j++)
         {
            if(!(gameGrid.get(i).get(j) == null))
            {
               Color c = gameGrid.get(i).get(j).getColor();
               gameGrid.get(i).get(j).draw(g, c);
            }
         }
      }
      
      //Draw player
      Color playC = player.getColor();
      player.draw(g, playC); 
   }
   
  //Key listener
  public class KeyEventDemo  implements KeyListener 
  {
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e)
    {
      //Stop the square from moving if key is released
      if(e.getKeyCode() == KeyEvent.VK_A)
      {
         left = false;
         xMove = 0;
      }

      if(e.getKeyCode() == KeyEvent.VK_D)
      {
         right = false;
         xMove = 0;
      }
      
    } 
    public void keyPressed(KeyEvent e) 
    {
      //If player is on ground, set jump values
      if(e.getKeyCode() == KeyEvent.VK_W)
      {
         if(player.isOnGround(gameGrid))
         {
            jump = 7;
            N=1;
         } 
      }
      //For left/right movement
      if(e.getKeyCode() == KeyEvent.VK_A)
      {
         left = true;
      }

      if(e.getKeyCode() == KeyEvent.VK_D)
      {
         right = true;
      }
      repaint();
    } 
   }
   
   //Time listener variables
   private boolean gravity;
   private double jump;
   private boolean right;
   private boolean left;
   private int xMove;
   private int yMove;
   private int N = 1;
   private int gravCounter = 0;
   
   public class TimeListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {  
         //Player is on ground, reset gravity
         if(player.isOnGround(gameGrid))
         {
            N = 1;
            gravity = false;
         }
         
         //Right movement
         if(right)
         {
            xMove = 1;
            player.move(xMove, 0, gameGrid);
         }
         
         //Left movement
         if(left)
         {
            xMove = -1;
            player.move(xMove, 0, gameGrid);
         }
         
         //If player hits ceiling while jumping
         if(player.isOnCeiling(gameGrid))
         {
            jump = 0;
         }
         
         //Gravity
         if(!(player.isOnGround(gameGrid)))
         {
            gravity = true;
            //Increment gravity by timer ticks
            if(gravCounter % 20 == 0)
            {
               if(N<7)
               {
                  N+=1;
               }
            }
            
            //Move player for gravity
            for(int i=0;i<N;i++)
            {
               yMove = 1;
               player.move(0, yMove, gameGrid);
            }
         }
         
         //Jump movement
         if(jump>0)
         {
            for(int i=0;i<jump;i++)
            {              
               player.move(0, -1, gameGrid);
            } 
         }
         
         //Adjust values as specified by project doc
         gravCounter++;
         if(!(player.isOnGround(gameGrid)))
         {
            jump-=0.1;
         }
         p.repaint();
         
         //Option pane for victory
         if(player.victorious())
         {
            OptionPane victory = new OptionPane();
         }   

      } 
   }
   
   //Option Pane class for when player hits victory block
   public class OptionPane 
   {  
      OptionPane()
      {  
         JOptionPane.showMessageDialog(p,"You won!","Congratulations!",JOptionPane.WARNING_MESSAGE);   
         System.exit(1);  
      }  
   }
    
}