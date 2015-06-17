package de.nomagic.clientlib;

public class DeleteMatchCommand extends ServerCommand
{
    private final String gameType;
    private final String matchName;

    public DeleteMatchCommand(String gameType, String matchName)
    {
        this.gameType = gameType;
        this.matchName = matchName;
    }

    @Override
    public String getCommand()
    {
        return "deleteMatch " + gameType + " " + matchName;
    }

}
