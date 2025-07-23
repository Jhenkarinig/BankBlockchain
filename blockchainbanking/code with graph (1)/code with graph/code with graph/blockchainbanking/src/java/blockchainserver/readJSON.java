
package blockchainserver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
public class readJSON {

public readJSON()
{
    //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader("userlogs.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray blockList = (JSONArray) obj;
            System.out.println(blockList);
             
            //Iterate over block chain array
            blockList.forEach( blockobject -> parseLogObject( (JSONObject) blockobject ) );
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    
}
        
        

   
 
    private static void parseLogObject(JSONObject blockobject) 
    {
        //Get employee object within list
        JSONObject blockdetails = (JSONObject) blockobject.get("block");
         
        //Get user name
        String user1 = (String) blockdetails.get("user1");    
        System.out.println(user1);
        
        String user2 = (String) blockdetails.get("user2");    
        System.out.println(user2);
         
        //Get operation
        String opr = (String) blockdetails.get("operation");  
        System.out.println(opr);
        
        String amount = (String) blockdetails.get("amount");    
        System.out.println(amount);
        
        String dtime = (String) blockdetails.get("dtime");    
        System.out.println(dtime);
        
        String prev = (String) blockdetails.get("previoushash");  
        System.out.println(prev);
     
        
        String hash = (String) blockdetails.get("hash");  
        System.out.println(hash);
        
        Block b=new Block();
        b.user1=user1;
        b.user2=user2;
        b.operation=opr;
        b.amount=amount;
        b.dtime=dtime;
        b.previousHash=prev;
        b.hash=hash;
        
        readblockreq.blockchain.add(b);
         
        
    }
}
