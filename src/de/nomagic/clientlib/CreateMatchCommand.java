package de.nomagic.clientlib;

public class CreateMatchCommand extends ServerCommand
{
    private String gameType;
    private String matchName;

    public CreateMatchCommand(String gameType, String matchName)
    {
        this.gameType = gameType;
        this.matchName = matchName;
    }

    @Override
    public String getCommand()
    {
        return "createMatch " + gameType + " " + matchName;
    }

}
