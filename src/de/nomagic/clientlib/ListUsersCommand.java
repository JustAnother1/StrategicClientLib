package de.nomagic.clientlib;

import de.nomagic.clientlib.ServerCommand;

public class ListUsersCommand extends ServerCommand
{
    private String gameType = null;

    public ListUsersCommand()
    {
    }

    public ListUsersCommand(String gameType)
    {
        this.gameType = gameType;
    }

    @Override
    public String getCommand()
    {
        if(null == gameType)
        {
            return "listUsers";
        }
        else
        {
            return "listUsers " + gameType;
        }

    }

}
