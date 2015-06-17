package de.nomagic.clientlib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceivedMessageReply extends ServerReply
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private String[] parts =  {"", "",};

    public ReceivedMessageReply(String[] parts)
    {
        if(null == parts)
        {
            return;
        }
        if(1 >parts.length)
        {
            log.error("Received invalid Message !");
        }
        else if(2 >parts.length)
        {
            log.error("Received invalid Message {}", parts[0]);
        }
        else if(3 >parts.length)
        {
            log.error("Received invalid Message {} - {}", parts[0], parts[1]);
        }
        else
        {
            this.parts = parts;
        }
    }

    public MessageType getMessageType()
    {
        if(3>parts.length)
        {
            return MessageType.EMPTY; // chat with no text
        }
        for (MessageType typ : MessageType.values())
        {
            if(parts[2].startsWith(typ.name()))
            {
                return typ;
            }
        }
        return MessageType.UNKNOWN; // unlabeled chat
    }

    public String getSender()
    {
        return parts[1];
    }

    @Override
    public String toString()
    {
        String msg = "";
        for(int i = 1/* at 0 is "RECEIVED_MESSAGE" */; i < parts.length; i++)
        {
            msg = msg + " ";
            msg = msg + parts[i];
        }
        return "ReceivedMessageReply" + msg;
    }

    public String getGameType()
    {
        if(3 < parts.length)
        {
            return parts[3];
        }
        else
        {
            return "";
        }
    }

    public String getMatchName()
    {
        if(4 < parts.length)
        {
            return parts[4];
        }
        else
        {
            return "";
        }
    }

}
