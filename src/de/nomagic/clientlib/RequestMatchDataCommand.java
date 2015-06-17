package de.nomagic.clientlib;

public class RequestMatchDataCommand extends ServerCommand
{
    private final String gameType;
    private final String matchName;

    public RequestMatchDataCommand(String gameType, String matchName)
    {
        this.gameType = gameType;
        this.matchName =  matchName;
    }

    @Override
    public String getCommand()
    {
        return "requestMatchData " + gameType + " " + matchName;
    }

}
