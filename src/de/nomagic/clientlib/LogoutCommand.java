package de.nomagic.clientlib;

public class LogoutCommand extends ServerCommand
{
    public LogoutCommand()
    {
    }

    @Override
    public String getCommand()
    {
        return "logout";
    }


}
