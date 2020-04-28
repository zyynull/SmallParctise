package BootPractise.Logs;
import java.util.*;
public class main {
        public static void main (String[] args) throws java.lang.Exception
        {
            Map a =new HashMap();
            a.put("a",null);
            try{
                a.get("a");
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
