package de.nomagic.clientlib;

public class FailedReply extends ServerReply
{
    private String[] parts =  {""};

    public FailedReply()
    {
    }

    public FailedReply(String[] parts)
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
        return "FailedReply" + msg;
    }
}
