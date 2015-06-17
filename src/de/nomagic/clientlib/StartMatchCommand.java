package de.nomagic.clientlib;

public class StartMatchCommand extends ServerCommand
{
    private final String gameType;
    private final String matchName;

    public StartMatchCommand(String gameType, String matchName)
    {
        this.gameType = gameType;
        this.matchName = matchName;
    }

    @Override
    public String getCommand()
    {
        return "startGame " + gameType + " " + matchName;
    }

}
