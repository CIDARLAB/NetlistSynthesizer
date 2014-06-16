/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BU.WebSockets;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.websocket.ClientEndpoint;
//import javax.websocket.DeploymentException;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//import org.json.simple.JSONObject;
        
/*
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.api.WebSocketServerListener;
import org.jwebsocket.factory.JWebSocketFactory;
import static org.jwebsocket.factory.JWebSocketFactory.getTokenServer;
import org.jwebsocket.kit.RawPacket;
import org.jwebsocket.kit.WebSocketServerEvent;
import org.jwebsocket.listener.WebSocketServerTokenEvent;
import org.jwebsocket.server.TokenServer;
import org.jwebsocket.token.Token;
*/

/**
 *
 * @author prashantvaidyanathan
 */

//@ClientEndpoint
public class ClothoSocket
{
  /*  
    
    
    public static void connect() throws DeploymentException, IOException
    {
        javax.websocket.WebSocketContainer container = javax.websocket.ContainerProvider.getWebSocketContainer();
        try {
            
            Session newsession = container.connectToServer(ClothoSocket.class, new URI("wss://localhost:8443/websocket"));
            //container.connectToServer(ClothoSocket.class, new URI("wss://localhost:8443/websocket"));
            System.out.println(newsession.getId());
        } catch (URISyntaxException ex) {
            Logger.getLogger(ClothoSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @OnOpen
    public void myOnOpen (Session session) {
        System.out.println ("WebSocket opened: "+session.getId());
    }
    
    
    @OnMessage
    public void myOnMessage (String txt) 
    {
        System.out.println ("WebSocket received message: "+txt);
    } 
    //USE JSR 356
    
    
    /*public static void main(String[] args) throws IOException {
        
        JSONObject json;
        json = new JSONObject();
        json.put("type", "CONNECT");
        Socket s = new Socket("wss://localhost:8443/websocket", 80);
        
        DataOutputStream dOut = new DataOutputStream(s.getOutputStream());

// Send first message
dOut.writeByte(1);
dOut.writeUTF("This is the first type of message.");
dOut.flush(); // Send off the data

// Send the second message
dOut.writeByte(2);
dOut.writeUTF("This is the second type of message.");
dOut.flush(); // Send off the data

// Send the third message
dOut.writeByte(3);
dOut.writeUTF("This is the third type of message (Part 1).");
dOut.writeUTF("This is the third type of message (Part 2).");
dOut.flush(); // Send off the data

// Send the exit message
dOut.writeByte(-1);
dOut.flush();

dOut.close();
        
        
        /*try
        {
            OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8); 
            out.write(json.toString());
        }
        catch(IOException ioException)
        {
                System.out.println("Nothing happened");
        }*/
            
    
    
    
    
    
    
   
    
    
    
    /*
    
    private TokenServer tokenServer;

    public TokenServer getTokenServer() {
        return tokenServer;
    }

    public void init() {
        try {
            
            //JWebSocketFactory.start();
            tokenServer =   (TokenServer) JWebSocketFactory.getServer("wss://localhost:8443/");
            if (tokenServer != null) {
                System.out.println("server was found");
                tokenServer.addListener((WebSocketServerListener) this);
            } else {
                System.out.println("server was NOT found");

            }
        } catch (Exception lEx) {
            lEx.printStackTrace();
        }
    }

    public void processToken(WebSocketServerTokenEvent serverTokenEvent, Token token) {
    }

    public void processClosed(WebSocketServerEvent arg0) {
    }

    public void processOpened(WebSocketServerEvent event) {
        System.out.println("***********Client '" + event.getSessionId()
                + "' connected.*********");
    }

    public  void sendPacket(int slideNumber) {
        Map lConnectorMap = getTokenServer().getAllConnectors();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        Collection<WebSocketConnector> lConnectors = lConnectorMap.values();
        for (WebSocketConnector wsc : lConnectors) {
            
            String json = "{\"channel\":\"connect\",\"data\":123,\"requestID\":" + dateFormat.format(date) + "}";
            WebSocketPacket wsPacket = new RawPacket(json);
            getTokenServer().sendPacket(wsc, wsPacket);            
        }
    }

    public void processPacket(WebSocketServerEvent arg0, WebSocketPacket arg1) {
        System.out.println("packet received " + arg1.getString());      
    }

    public static void main(String[] args) {

        ClothoSocket jc = new ClothoSocket();
        jc.init();
        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(3000);
                Object c = jc.getTokenServer().getAllConnectors();
                System.out.println(c);

                Map lConnectorMap = jc.getTokenServer().getAllConnectors();
                List<Map> lResultList = new ArrayList<Map>();
                Collection<WebSocketConnector> lConnectors = lConnectorMap.values();
                for (WebSocketConnector lConnector : lConnectors) {
                    Map lResultItem = new HashMap<String, Object>();
                    lResultItem.put("port", lConnector.getRemotePort());
                    lResultItem.put("unid", lConnector.getNodeId());
                    lResultItem.put("username", lConnector.getUsername());
                    lResultItem.put("isToken", lConnector.getBoolean(TokenServer.VAR_IS_TOKENSERVER));
                    lResultList.add(lResultItem);
                }
                for (Map m : lResultList) {
                    System.out.println(m);
                }

                jc.sendPacket(i % 5 + 1);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClothoSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    */
    
    
    
    
    
    
    
}
