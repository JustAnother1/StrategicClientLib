package de.nomagic.clientlib;

import java.util.Arrays;

public class ProtocolViolationReply extends ServerReply
{
    private final String[] strings;

    public ProtocolViolationReply(String[] strings)
    {
        this.strings = strings;
    }

    @Override
    public String toString()
    {
        return "ProtocolViolationReply [strings=" + Arrays.toString(strings) + "]";
    }
}
