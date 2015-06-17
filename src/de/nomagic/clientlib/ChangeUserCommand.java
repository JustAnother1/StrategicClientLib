package de.nomagic.clientlib;

public class ChangeUserCommand extends ServerCommand
{
    private final String userName;
    private final String Option;
    private final boolean value;

    public ChangeUserCommand(String userName, String Option, boolean value)
    {
        this.userName = userName;
        this.Option = Option;
        this.value = value;
    }

    @Override
    public String getCommand()
    {
        String valueString;
        if(true == value)
        {
            valueString = "yes";
        }
        else
        {
            valueString = "no";
        }
        return "changeUser " + userName + " " + Option + " " + valueString;
    }

}
