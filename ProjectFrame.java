import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class ProjectFrame extends JFrame
{
   public ProjectFrame()
   {
      super("Project Frame");
      //Add panel and set layout/size
      Container contents = getContentPane();
      ProjectPanel lp = new ProjectPanel();
      contents.setBackground(Color.BLACK); //For black border
      contents.add(lp);
      contents.setLayout(new FlowLayout(FlowLayout.CENTER));
      setSize(823,648);
      setVisible(true);      
      
      lp.requestFocus();  
   }
   public static void main(String[] args)
   {
      ProjectFrame theFrame = new ProjectFrame();
      theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

}