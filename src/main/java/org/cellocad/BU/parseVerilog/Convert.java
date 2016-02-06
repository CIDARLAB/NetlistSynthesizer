/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.parseVerilog;

/**
 *
 * @author prashantvaidyanathan
 */
public class Convert {
    
     public static String InttoHex(int dec)
     {
         
         if(dec == 0)
             return "0x0";
         String hex ="0x";
         String multh = "";
         int dig = 0;
         String temp = "";
         while(dec>0)
         {
             dig = dec%16;
            
             if(dig==0)
                 multh = "0";
             else if(dig==1)
                 multh = "1";
             else if(dig==2)
                 multh = "2";
             else if(dig==3)
                 multh = "3";
             else if(dig==4)
                 multh = "4";
             else if(dig==5)
                 multh = "5";
             else if(dig==6)
                 multh = "6";
             else if(dig==7)
                 multh = "7";
             else if(dig==8)
                 multh = "8";
             else if(dig==9)
                 multh = "9";
             else if(dig==10)
                 multh = "A";
             else if(dig==11)
                 multh = "B";
             else if(dig==12)
                 multh = "C";
             else if(dig==13)
                 multh = "D";
             else if(dig==14)
                 multh = "E";
             else if(dig==15)
                 multh = "F";
             else
                 multh ="0";
             
             temp += multh;
             dec = dec/16;
             
         }
         StringBuilder xhex = new StringBuilder(temp);
         hex +=xhex.reverse();
         return hex;
     }
     public static int HextoInt(String hex)
     {
         int dec=0,multh = 0,pow;
         if(hex.contains("0x"))
         { 
             hex = hex.substring(hex.indexOf("0x")+2);
         }
         int hlen = hex.length();
         int j=0;
         for(int i=(hlen-1);i>=0;i--)
         {
             if(hex.charAt(i)=='0')
                 multh = 0;
             else if(hex.charAt(i)=='1')
                 multh = 1;
             else if(hex.charAt(i)=='2')
                 multh = 2;
             else if(hex.charAt(i)=='3')
                 multh = 3;
             else if(hex.charAt(i)=='4')
                 multh = 4;
             else if(hex.charAt(i)=='5')
                 multh = 5;
             else if(hex.charAt(i)=='6')
                 multh = 6;
             else if(hex.charAt(i)=='7')
                 multh = 7;
             else if(hex.charAt(i)=='8')
                 multh = 8;
             else if(hex.charAt(i)=='9')
                 multh = 9;
             else if(hex.charAt(i)=='A' || hex.charAt(i)=='a')
                 multh = 10;
             else if(hex.charAt(i)=='B' || hex.charAt(i)=='b')
                 multh = 11;
             else if(hex.charAt(i)=='C' || hex.charAt(i)=='c')
                 multh = 12;
             else if(hex.charAt(i)=='D' || hex.charAt(i)=='d')
                 multh = 13;
             else if(hex.charAt(i)=='E' || hex.charAt(i)=='e')
                 multh = 14;
             else if(hex.charAt(i)=='F' || hex.charAt(i)=='f')
                 multh = 15;
             else
                 multh =0;
             pow = (int)Math.pow(16, j); 
             dec+= (multh*pow);
             
             j++;
         
         }
         
         
         
         return dec;
     }
     
     public static String invBin(String binnum)
     {
         String bininv ="";
         
         for(int i=0;i<binnum.length();i++)
         {
             if(binnum.charAt(i)== '1')
             {
                 bininv += "0";
             }
             else if(binnum.charAt(i)=='0')
             {
                 bininv += "1";
             }
             else 
             {
                 bininv += "-";
             }
         }
         
         return bininv;
     }
    
     public static int toDec(String num)
     {
        int number =0;
        if(num.contains("default"))
        {
            return -2;
        }
        else
        {
        if(num.contains("’"))
        {
            if(num.contains("b"))
            {
                num = num.substring(num.indexOf("b")+1);
                num = num.trim();
                number = bintoDec(num);
            }
            else if(num.contains("d"))
            {
                num = num.substring(num.indexOf("d")+1);
                num = num.trim();
                number = Integer.parseInt(num);
            }
        }
        else if(num.contains("'"))
        {
            if(num.contains("b"))
            {
                num = num.substring(num.indexOf("b")+1);
                num = num.trim();
                number = bintoDec(num);
            }
            else if(num.contains("d"))
            {
                num = num.substring(num.indexOf("d")+1);
                num = num.trim();
                number = Integer.parseInt(num);
            }
        }  
        else
        {
            //System.out.println(num);
            number = Integer.parseInt(num.trim());
        }
        }
        return number;
    }
    
     
    public static int bintoDec(String bin)
    {
        int dec =0;
        int exp =0;
        for(int i=bin.length()-1;i>=0;i--)
        {
            int pow2 = (int)Math.pow(2, exp);
            if(bin.charAt(i)== '1')
            {
                dec += pow2;
            }

            exp++;
        }
        return dec;
    }
    
    
    public static String dectoBin(int dec,int numofInputs)
    {
        String bin ="";
        
        for(int i=0;i<numofInputs;i++)
        {
            bin += "0";
        }
        if(dec ==0)
            return bin;
        
        StringBuilder xbin = new StringBuilder(bin);
        int indx =0;
        while(dec>0)
        {
            int x = dec%2;
            
            char bins =  Character.forDigit(x,10);
            xbin.setCharAt(indx, bins);
            dec = dec/2;
            indx++;
        }
        bin = xbin.reverse().toString();
        return bin;
    }
}
