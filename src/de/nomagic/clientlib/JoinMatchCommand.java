package de.nomagic.clientlib;

public class JoinMatchCommand extends ServerCommand
{
    private final String gameType;
    private final String matchName;

    public JoinMatchCommand(String gameType, String matchName)
    {
        this.gameType = gameType;
        this.matchName = matchName;
    }

    @Override
    public String getCommand()
    {
        return "joinMatch " + gameType + " " + matchName;
    }

}
