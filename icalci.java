import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

class icalci extends JFrame implements ActionListener
{
 JOptionPane JP=new JOptionPane();
 JButton b[]=new JButton[21];
 JButton jb,off;
 JLabel labdisp;
 
 String Str[]={"0","",".","","1","2","3","=","4","5","6","-","7","8","9","+","AC","+/-","/","*","c"};
 String oprtr[]=new String[2];
 String oprnd[]=new String[2];
 String result="0", Last_Result="", Last_Oprnd="", Last_Oprtr="", Last_OprtrX="", Temp_op="", spl_op="", mnemo="";

 int X_LOC=0,Y_LOC=650;
 int count=0,lab_width=0;
 int x=0,y=0;
 char arr[]=new char [25];

 Font Res_Font=new Font("Viner Hand ITC", Font.BOLD, 40);
 Font Buttons_Font=new Font("Forte", Font.BOLD, 40); 
 FontMetrics metrics;
  
 Border thickBorder = new LineBorder(Color.WHITE, 4);
 Border thickBorderlab = new LineBorder(Color.WHITE, 10);
 JFrame frame=new JFrame();
 icalci()
 {
  setLayout(null);
  Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
  setSize(((int)screenSize.getWidth()/2-(int)screenSize.getWidth()/16),(int)screenSize.getHeight());

  for(int i=0;i<Str.length;i++)
     {
      b[i]=new JButton(Str[i]);
    
      if(Str[i].equals("="))
         b[i].setMnemonic('=');

      if(i%4==0)
        {
         X_LOC=0;
         Y_LOC-=100;
        }
      X_LOC+=100;
      if(!Str[i].equals("c"))
         b[i].setBounds(X_LOC,Y_LOC,100,100);
     
      if(Str[i].equals("0"))
         b[i].setBounds(X_LOC,Y_LOC,200,100);    
      if(Str[i].equals("="))
         b[i].setBounds(X_LOC,Y_LOC,100,200);
      if(Str[i].equals(""))
         b[i].setVisible(false);
      if(Str[i].equals("AC"))
         jb=b[i];
      if(Str[i].equals("c"))
        {
         b[i].setBounds(jb.getBounds());
         b[i].setVisible(false);
  	    }

      add(b[i]);
	  b[i].setFont(Buttons_Font);
      b[i].setBorder(thickBorder);
      b[i].addActionListener(this);
     }

  labdisp=new JLabel(result,SwingConstants.CENTER);
  labdisp.setBounds(100,50,400,100);
  labdisp.setFont(Res_Font);
  labdisp.setOpaque(true);
  labdisp.setForeground(Color.ORANGE);
  labdisp.setBackground(Color.GRAY);
  labdisp.setBorder(thickBorderlab);
  add(labdisp);
  labdisp.repaint();

  setTitle("iCalculator");
  setVisible(true);
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }

 public static void main(String arg [])
 {
  new icalci();
 }

 String chkOperand(String Str)
 {
  for(int i=0;i<10;i++)
      if(Str.equals(i+""))
         return("operand");
   
  if(Str.equals("c") || Str.equals("+/-") || Str.equals(".") || Str.equals("AC"))
    { 
     spl_op=Str;
     return("special_operator"); 
    }

  return("operator"); 
 }
 
 boolean chkSameOprtrExists(String Str)
 { 
  if(Str.equals(oprtr[0])) // To avoid repitition of operators        
    return(true);
  else
    return(false);
 }
  
 boolean chkOpExistance(String Str)
 { 
  if(!Str.equals(""))    // To change operator, To ChkIf Operator/Operand Exists 
     return(true);

  return(false);
 }
 
 public String changeSign(String oprnd)
 {
  if(oprnd.contains("."))    
     return(Float.valueOf(oprnd)*(-1)+"");
  else  
     return(Integer.parseInt(oprnd)*(-1)+"");
 }
 
 public String calculate(String oprnd0, String oprnd1, String oprtr) throws ArithmeticException
 {
  String result="";
  
  if(oprtr.equals("+"))
     if(oprnd0.contains(".") || oprnd1.contains("."))
         result=(Float.valueOf(oprnd0)+Float.valueOf(oprnd1))+"";
     else
         result=(Integer.parseInt(oprnd0)+Integer.parseInt(oprnd1))+""; 

  if(oprtr.equals("-"))
     if(oprnd0.contains(".") || oprnd1.contains("."))
        result=(Float.valueOf(oprnd0)-Float.valueOf(oprnd1))+"";
     else
        result=(Integer.parseInt(oprnd0)-Integer.parseInt(oprnd1))+"";

  if(oprtr.equals("*"))
     if(oprnd0.contains(".") || oprnd1.contains("."))
        result=(Float.valueOf(oprnd0)*Float.valueOf(oprnd1))+"";
     else
        result=(Integer.parseInt(oprnd0)*Integer.parseInt(oprnd1))+"";

  if(oprtr.equals("/"))
    {
     try
     { 
      result=(Float.valueOf(oprnd0)/Float.valueOf(oprnd1))+"";
      if(result.equals("Infinity"))
         throw new ArithmeticException();
     }
     catch(ArithmeticException ae)
          {
           if(result.equals("Infinity") || result.equals("-Infinity"))
              JOptionPane.showMessageDialog(JP," Cannot / by Zero!");
           else
              JOptionPane.showMessageDialog(JP,ae.getMessage()+"Exception!");
          }
    } 
  return(result);
 }
  
 public void actionPerformed(ActionEvent e)
 {  
  String cmd=e.getActionCommand();
  System.out.println("User Entered --> "+cmd);
  
  for(int i=0;i<Str.length;i++)
      if(e.getSource().equals(b[i]))
        {
         if(count++>0)
            jb.setBorderPainted(true);
         b[i].setBorderPainted(false);
         jb=b[i];
        }
  if(off!=null)
     off.setBorderPainted(true);
  
  if((x%2==0 & y%2==0) || (cmd.equals("AC")))
    {
     for(int k=0;k<2;k++)
        {
         oprnd[k]="";
         oprtr[k]="";
 	}
     x=0;
     y=0;
     if(cmd.equals("AC"))
       {  
        Temp_op="";
        Last_Result="";
        Last_Oprtr="";
        Last_Oprnd="";
        Last_OprtrX="";  
       } 
     if(Temp_op.equals("") && !cmd.equals("+/-"))
        result="";
    }

  if(chkOperand(cmd).equals("operator"))
    {
     result=cmd;
     result="";
    }
  else
  if((chkOperand(cmd).equals("operand") && y==1) || chkOperand(cmd).equals("operand"))
      result+=cmd;
  else
  if(cmd.equals("."))
    if(!result.contains("."))
       result+=cmd;
  if(result.contains(".") && (result.indexOf(".")==0))
     result="0.";

  if(chkOpExistance(result))
     for(int i=0;i<Str.length;i++)
        {
         if(b[i].getText().equals("AC"))
            b[i].setVisible(false);
         if(b[i].getText().equals("c"))
            b[i].setVisible(true);
        }

  Vector<String> v=new Vector<String>(4,1);
  v.add(cmd);
  Iterator it=v.iterator();
  while(it.hasNext())
       {      
        String op=it.next()+"";

        try
 	    {   
         if(chkOperand(op).equals("operand") && y==1)  	 			// OPERAND'S OP x
           {
            x=1;
            if(result.contains("."))
               oprnd[x]=result;
            else
               oprnd[x]+=op;
           }  
         else
         if(chkOperand(op).equals("operand") || result.equals("0."))
            if(result.contains("."))
               Temp_op=result;
            else
               Temp_op+=op;
         else
         if(chkOperand(op).equals("operator"))
           {
            if(x==0 && chkOpExistance(Temp_op))
               oprnd[x++]=Temp_op;
               
            if(chkOpExistance(oprnd[0]) && chkOpExistance(oprnd[1])) 
               x=2;
           }
         
         if(chkOperand(op).equals("operator"))         // OPERATOR'S OP y
           {
            if(y==2)
              {
               if(x==0)
                 {
                  oprtr[y-=2]=op;
                  oprtr[++y]="";
                 }
 	           else
               if(x==1)                               // FOR CALC. After Result
                 {
                  oprtr[y-=2]=op;
                  oprtr[++y]="";
                  oprnd[1]="";
                 } 
              }
            else 
            if(y==0 || (y==1 && op.equals("=") && !chkSameOprtrExists(op)) || (y==1 && x==2) || (y==1 && x==1 && !Last_OprtrX.equals("=")))
               oprtr[y++]=op;
            else
            if(x==1 && !chkSameOprtrExists(oprtr[0]))
               oprtr[y++]=op;
            else
            if(y==1)
              {
               oprtr[--y]=op;
               y++;
              }
           }

         if(x==0 && !chkOpExistance(oprnd[0]) && !chkOpExistance(oprnd[1])) // ('/'&&'=') || ('=')
           {
            if(oprtr[1].equals("=")) 
               result=calculate(Last_Result,Last_Result,oprtr[0]);
            else 
            if(oprtr[0].equals("=") && !chkOperand(op).equals("special_operator") && chkOpExistance(Last_Result))
               result=calculate(Last_Result,Last_Oprnd,Last_Oprtr);
            else
            if(oprtr[0].equals("="))
               result=calculate(Last_Oprnd,Last_Oprnd,Last_Oprtr);
           }
    
              
         if(x+y==4 || x+y==3)
            if(oprtr[1].equals("=") || chkOperand(oprtr[0]).equals("operator"))
              {
               Temp_op="";

               if(!oprtr[1].equals("=") && !oprtr[0].equals("=") && !oprtr[1].equals(oprtr[0]))  // Multiple Operator
                 {
                  Last_OprtrX=oprnd[0];
                  oprnd[0]=oprnd[1];
                  oprnd[1]=calculate(oprnd[0],oprnd[1],oprtr[1]);
                  oprnd[0]=Last_OprtrX;
                 }
              
               if(x+y==3 && y==2 && chkOpExistance(oprnd[0])) // (OPRND)(OPRTR)(=)
                  oprnd[1]=oprnd[0];
               else    
               if(x+y==3 && y==2 && !chkOpExistance(oprnd[0]))
                  oprnd[0]=Last_Result;
               else
               if(x+y==3 && y==2 && chkOpExistance(oprnd[1]))
                  oprnd[0]=Last_Result;     
                
               result=calculate(oprnd[0],oprnd[1],oprtr[0]);              
              }// If Ends
         try
 	     {
          if(op.equals("c"))                          // CLEAR
            {   
             result="";
             for(int i=0;i<Str.length;i++)
                {
           	     if(b[i].getText().equals("AC"))
                    b[i].setVisible(true);
                 
                 if(b[i].getText().equals(oprtr[0]))
                   {
                    b[i].setBorderPainted(false); 
                    off=b[i]; 
                   }

                 if(b[i].getText().equals("c"))
            	    b[i].setVisible(false);
        	     }
             if(chkOpExistance(oprnd[0]) && !chkOpExistance(oprnd[1]))
               {
                oprnd[0]="";
                x=0;
	           }
             else
             if(chkOpExistance(oprnd[1])) 
               {
                oprnd[1]="";
                x=1;
	           }
            }
          if(op.equals("+/-"))        // SIGN CHANGE
            {
             if(x==0 && chkOpExistance(Temp_op)) 
               {
                Temp_op=changeSign(Temp_op);
                result=Temp_op;
               } 
             else
             if(chkOpExistance(oprnd[0]) && !chkOpExistance(oprnd[1]))
               {
                result=changeSign(oprnd[0]);
                oprnd[0]=result;
               }  
             else
             if(chkOpExistance(oprnd[1]))
               {
                result=changeSign(oprnd[1]);
                oprnd[1]=result;
               }   
             else
             if((x%2==0 & y%2==0) || y==1)
                result=changeSign(Last_Result);
             else
                JOptionPane.showMessageDialog(JP,"Specify Proper Operand!!");
             }
         }// Inner Try{} Ends
         catch(NumberFormatException nfe)
              {
               JOptionPane.showMessageDialog(JP,nfe.getMessage()+", Enter a Number!"); 
               //nfe.printStackTrace();
              }  
         if(x+y==2 && !chkOpExistance(oprnd[1]) && !chkOpExistance(oprtr[1]) && oprtr[0].equals("="))
            throw new Exception();  
        }// Outer Try{} Ends
        catch(NullPointerException npe)
             {
              JOptionPane.showMessageDialog(JP,npe.getMessage());
              //npe.printStackTrace();
             }
        catch(ArrayIndexOutOfBoundsException aobe)
             {
              JOptionPane.showMessageDialog(JP,aobe.getMessage());
              if(x>2)
                 JOptionPane.showMessageDialog(JP,("<Only 2 Operands Required>").toUpperCase());
              //aobe.printStackTrace();
             }
        catch(NumberFormatException nfe)
             {
              JOptionPane.showMessageDialog(JP,nfe.getMessage()+", Proper Operand Required !");
              //nfe.printStackTrace();
             }
       catch(Exception ee)
            {
             JOptionPane.showMessageDialog(JP,"Proper Operator/Operand Required !");
             //ee.printStackTrace();
             //System.out.println(ee.getCause()); 
            }
        finally
        {
         metrics=labdisp.getFontMetrics(Res_Font);
         lab_width = metrics.stringWidth(result);
         if(labdisp.getWidth()<lab_width)
            Res_Font=new Font("Viner Hand ITC", Font.BOLD, 30);
         
         labdisp.setFont(Res_Font);
         labdisp.setText(result);
         labdisp.repaint();

         if(Last_Result.equals("Infinity") || Last_Result.equals("-Infinity"))
           {
            Temp_op="";
            Last_Result="";
            Last_Oprtr="";
            Last_Oprnd="";
            Last_OprtrX="";   
           }
            
         if(x+y==4 || x+y==2 || y==2 || (y==1 && oprtr[0].equals("=")) || op.equals("+/-"))
           { 
            if(x+y==4 || x+y==3 || (x==1 && y==1))
              {
               Last_Oprnd=oprnd[1];
               Last_Oprtr=oprtr[0];
               Last_OprtrX=oprtr[0];
              }

            if(!(x+y==2 && y==1 && chkOpExistance(oprnd[1])))  
               Last_Result=result;
           }

         if(x+y==3 && y==2) // For CALC. After Result
           {
            oprnd[0]="";
            oprnd[1]="";
            x=0;
           }

         //System.out.println("labdisp.getWidth()"+labdisp.getWidth()+"\nlab_width"+lab_width+"\nmetrics="+metrics);  
         System.out.println("<X="+x+"> <Y="+y+">");
         System.out.println("<"+oprnd[0]+">OPERAND<"+oprnd[1]+">");
         System.out.println("<"+oprtr[0]+">OPERATOR<"+oprtr[1]+">");
         System.out.println("("+oprnd[0]+") "+oprtr[0]+" ("+oprnd[1]+") = "+result);
         System.out.println("Last_Oprnd : "+Last_Oprnd+"\nLast_Oprtr : "+Last_Oprtr+"\nLast_Result : "+Last_Result+"\n**************\n");
        }
       }// While Ends
 }// Actionlistener Ends
}// Program Ends
