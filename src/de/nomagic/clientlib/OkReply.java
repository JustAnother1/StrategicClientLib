package de.nomagic.clientlib;

public class OkReply extends ServerReply
{
    private String[] parts =  {""};

    public OkReply()
    {
    }

    public OkReply(String[] parts)
    {
        this.parts = parts;
    }

    @Override
    public String toString()
    {
        String msg = "";
        for(int i = 1/* at 0 is the OK */; i < parts.length; i++)
        {
            msg = msg + " ";
            msg = msg + parts[i];
        }
        return "OkReply" + msg;
    }
}
