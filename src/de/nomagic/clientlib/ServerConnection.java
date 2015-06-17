package de.nomagic.clientlib;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServerConnection
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public final static String END_OF_RESPONSE = "End";
    private boolean isConnected = false;
    private Socket clientSocket;
    private BufferedReader inFromServer;
    private DataOutputStream outToServer;
    private Vector<ServerReply> bufferedMessages = new Vector<ServerReply>();

    public ServerConnection()
    {
    }

    public void connectTo(String serverURL, int serverPort)
    {
        try
        {
            clientSocket = new Socket(serverURL, serverPort);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String hello = inFromServer.readLine();
            log.trace("Server Hello: {}", hello);

            isConnected = true;
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void disconnect()
    {
        try
        {
            clientSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        isConnected = false;
    }

    public boolean isConnected()
    {
        return isConnected;
    }

    public ServerReply waitForMessage()
    {
        if(false == bufferedMessages.isEmpty())
        {
            return bufferedMessages.remove(0);
        }
        else
        {
            return getReplyOrMessage();
        }
    }

    private ServerReply getReplyOrMessage()
    {
        try
        {
            boolean firstLine = true;
            Vector<String> receivedLines = new Vector<String>();
            while(true)
            {
                String replyLine = inFromServer.readLine();
                log.trace("received : _{}_", replyLine);
                if(true == firstLine)
                {
                    // remove prompt
                     if(true == replyLine.contains(" $ "))
                     {
                         replyLine = replyLine.substring(replyLine.indexOf('$') + 2);
                     }
                    log.trace("changed to  : _{}_", replyLine);
                }
                if(true == replyLine.endsWith(END_OF_RESPONSE))
                {
                    // end of reply found -> parse what we received
                    switch(receivedLines.size())
                    {
                    case 0: return new EmptyReply();

                    case 1:
                    {
                        String line =  receivedLines.get(0);
                        String[] parts = line.split(" ");
                        if(true == "OK".equals(parts[0]))
                        {
                            return new OkReply(parts);
                        }
                        if(true == "FAILED".equals(parts[0]))
                        {
                            return new FailedReply(parts);
                        }
                        // probably not a one line answer
                        if(true == "RECEIVED_MESSAGE".equals(parts[0]))
                        {
                            return new ReceivedMessageReply(receivedLines.toArray(new String[0]));
                        }
                        if(true == "DATA".equals(parts[0]))
                        {
                            return new DataReply(receivedLines.toArray(new String[0]), 1);
                        }
                        return new ProtocolViolationReply(receivedLines.toArray(new String[0]));
                    }

                    default:
                    {
                        String line =  receivedLines.get(0);
                        String[] parts = line.split(" ");
                        if(true == "RECEIVED_MESSAGE".equals(parts[0]))
                        {
                            return new ReceivedMessageReply(receivedLines.toArray(new String[0]));
                        }
                        if(true == "DATA".equals(parts[0]))
                        {
                            return new DataReply(receivedLines.toArray(new String[0]), 1);
                        }
                        return new ProtocolViolationReply(receivedLines.toArray(new String[0]));
                    }
                    }
                }
                else
                {
                    receivedLines.add(replyLine);
                }
                firstLine = false;
            }
        }
        catch (IOException e)
        {
            isConnected = false;
            e.printStackTrace();
        }
        return new ServerDisconnectedReply();
    }

    public ServerReply sendCommand(ServerCommand serverCommand)
    {
        String cmd = serverCommand.getCommand();
        try
        {
            outToServer.writeBytes(cmd + '\n');
            log.trace("sending : _{}_", cmd);
            boolean done = false;
            ServerReply rep;
            do{
                rep =  getReplyOrMessage();
                if(rep instanceof ReceivedMessageReply)
                {
                    bufferedMessages.add(rep);
                }
                else
                {
                    done = true;
                }
            } while(false == done);
            return rep;
        }
        catch (IOException e)
        {
            isConnected = false;
            e.printStackTrace();
        }
        return new ServerDisconnectedReply();
    }

}
