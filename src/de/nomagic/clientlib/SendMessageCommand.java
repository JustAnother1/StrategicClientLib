package de.nomagic.clientlib;

public class SendMessageCommand extends ServerCommand
{
    private final String receiver;
    private final MessageType typ;
    private final String msg;

    public SendMessageCommand(String receiver, MessageType type, String msg)
    {
        this.receiver = receiver;
        this.typ = type;
        this.msg = msg;
    }

    @Override
    public String getCommand()
    {
        return "sendMessage " + receiver + " " + typ.name() + " " + msg;
    }

}
