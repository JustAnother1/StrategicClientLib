package de.nomagic.clientlib;

public class MakeMoveCommand extends ServerCommand
{
    private final String gameType;
    private final String matchName;
    private final String move;

    public MakeMoveCommand(String gameType, String matchName, String move)
    {
        this.gameType = gameType;
        this.matchName = matchName;
        this.move = move;
    }

    @Override
    public String getCommand()
    {
        return "makeMove " + gameType + " " + matchName + " " + move;
    }

}
