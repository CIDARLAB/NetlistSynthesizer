/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseVerilog;

/**
 *
 * @author prashantvaidyanathan
 */
public class Convert {
    
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
