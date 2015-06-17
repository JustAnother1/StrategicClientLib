package de.nomagic.clientlib;

public class RegisterCommand extends ServerCommand
{
    private final String password;
    private final String userName;

    public RegisterCommand(String userName, String userPassword)
    {
        this.password = userPassword;
        this.userName = userName;
    }

    @Override
    public String getCommand()
    {
        return "register " + userName + " " + password;
    }

}
