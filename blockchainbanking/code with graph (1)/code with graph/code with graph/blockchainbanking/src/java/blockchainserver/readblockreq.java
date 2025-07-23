
package blockchainserver;

import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class readblockreq extends Thread
{
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;
    public static String previousHash="0";
    
    
    readblockreq()
    {
        super();
        start();
    }
    
    public void run()
    {
        try
        {
            ServerSocket ss=new ServerSocket(3000);
            
            while (true)
            {
                Socket soc=ss.accept();
                ObjectOutputStream oos=new ObjectOutputStream(soc.getOutputStream());
                ObjectInputStream oin=new ObjectInputStream(soc.getInputStream());
                
                String req=(String)oin.readObject();
                
                if (req.equals("ADDBLOCK"))
                {
                   String user1=(String)oin.readObject();
                   String user2=(String)oin.readObject();
                   String opr=(String)oin.readObject();
                   String amount=(String)oin.readObject();
                   String dtime=(String)new java.util.Date().toString();
                   
                   
                   blockcserver.jTextArea1.append("Operation "+opr+" from "+user1+"\n");
                  long start=System.currentTimeMillis(); 
                   Block b=new Block(user1,user2,opr,amount,dtime,previousHash);
                   blockchain.add(b);
                   blockcserver.jTextArea1.append("Block Successfully added!\n");
                   blockchain.get(blockchain.size()-1).mineBlock(difficulty);
                   long end=System.currentTimeMillis(); 
                   blockcserver.ttkn=end-start;
                   
                   oos.writeObject("SUCCESS");
                }
                else
                if (req.equals("VIEWLOG"))
                {
                    String user=(String)oin.readObject();
                    System.out.println(user);
                     blockcserver.jTextArea1.append("Operation "+req+" from "+user+"\n");
                     
                    Vector log=getlog(user);
                    
                    oos.writeObject(log);
                }
                
                
                
                oos.close();
                oin.close();
                soc.close();
                
            }
            
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }    
    
    
    Vector getlog(String user)
    {
        Vector v=new Vector();
       
        try
        {
            for (int i=0;i<blockchain.size();i++)
            {
                Block b=blockchain.get(i);
                
                if (b.user1.equals(user) || b.user2.equals(user))
                {
                	String data=b.user1+"$"+b.user2+"$"+b.operation+"$"+b.amount+"$"+b.dtime;
                    v.add(data);
                }
            }
            
            
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return v;
    }
    
    
    void writelogs()
    {
        try
        {
            if (blockchain.size()>0)
            {
                JSONArray blockList = new JSONArray();
                
                for (int i=0;i<blockchain.size();i++)
                {
                    Block b=(Block)blockchain.get(i);
                    JSONObject blockdetails = new JSONObject();
                    blockdetails.put("user1", b.user1);
                    blockdetails.put("user2", b.user2);
                    blockdetails.put("operation", b.operation);
                    blockdetails.put("amount", b.amount);
                    blockdetails.put("dtime", b.dtime);
                    blockdetails.put("previoushash", b.previousHash);
                   
                    blockdetails.put("hash", b.hash);
                    
                    JSONObject blockObject = new JSONObject(); 
                    blockObject.put("block", blockdetails);
                    
                    blockList.add(blockObject);
                }
                
                FileWriter file = new FileWriter("userlogs.json");
                 file.write(blockList.toJSONString()); 
                 file.flush();
                 file.close();
                
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
        
}
