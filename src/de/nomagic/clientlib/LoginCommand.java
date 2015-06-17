package de.nomagic.clientlib;

public class LoginCommand extends ServerCommand
{
    private final String userName;
    private final String userPassword;

    public LoginCommand(String userName, String userPassword)
    {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @Override
    public String getCommand()
    {
        return "login " + userName + " " + userPassword;
    }

}
