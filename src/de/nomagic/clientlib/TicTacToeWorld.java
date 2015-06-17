package de.nomagic.clientlib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TicTacToeWorld
{
    private final Logger log = (Logger) LoggerFactory.getLogger(this.getClass().getName());

    public final static char BOARD_FIELD_EMPTY = ' ';
    public final static char BOARD_FIELD_PLAYER_0 = 'X';
    public final static char BOARD_FIELD_PLAYER_1 = 'O';

    /* Example:
                DATA
                Match Name : bla
                created by : lars
                Player 1 (X): lars
                Player 2 (O): Uwe
                is ongoing : true
                number of moves executed : 0
                won by : none
                [Board]
                +--+---+--+
                |  |   |  |
                +--+---+--+
                |  |   |  |
                +--+---+--+
                |  |   |  |
                +--+---+--+
                [History]
                +--+---+--+
                |0 | 0 | 0|
                +--+---+--+
                |0 | 0 | 0|
                +--+---+--+
                |0 | 0 | 0|
                +--+---+--+
                End
     */

    private char[][] board = new char[3][3];
    private char enemy;
    private char myself;
    private String MatchName;

    public TicTacToeWorld(String MatchName, DataReply matchData, String PlayerName)
    {
        this.MatchName = MatchName;
        String[] general = matchData.getGeneralData();
        for(int i = 0; i < general.length; i++)
        {
            String line = general[i];
            if(line.contains("Player"))
            {
                if(line.contains(PlayerName))
                {
                    if(line.contains("(" + BOARD_FIELD_PLAYER_0 + ")"))
                    {
                        log.info("I play X!");
                        enemy = BOARD_FIELD_PLAYER_1;
                        myself = BOARD_FIELD_PLAYER_0;
                    }
                    else
                    {
                        log.info("I play O!");
                        enemy = BOARD_FIELD_PLAYER_0;
                        myself = BOARD_FIELD_PLAYER_1;
                    }
                }
            }
        }

        String[] brd = matchData.getSectionData("Board");
        if(null == brd)
        {
            log.error("Could not get The board !");
        }
        if(7 != brd.length)
        {
            log.error("Received invalid Board:");
            for(int i = 0; i < brd.length; i++)
            {
                log.error(brd[i]);
            }
        }
        else
        {
            log.trace("Received the Board:");
            for(int i = 0; i < brd.length; i++)
            {
                log.trace("_" + brd[i] + "_");
            }
        }
        board[0][0] = brd[1].charAt(1);
        board[1][0] = brd[1].charAt(5);
        board[2][0] = brd[1].charAt(9);

        board[0][1] = brd[3].charAt(1);
        board[1][1] = brd[3].charAt(5);
        board[2][1] = brd[3].charAt(9);

        board[0][2] = brd[5].charAt(1);
        board[1][2] = brd[5].charAt(5);
        board[2][2] = brd[5].charAt(9);

        log.trace("The Board is :");
        log.trace(" 012");
        log.trace("0" + board[0][0] + board[1][0]+ board[2][0]);
        log.trace("1" + board[0][1] + board[1][1]+ board[2][1]);
        log.trace("2" + board[0][2] + board[1][2]+ board[2][2]);
    }

    public boolean isempty(int x, int y)
    {
        if((null == board) || (x >2) || (y >2) || (0 > x) || (0 >y))
        {
            return false;
        }
        if(board[x][y] == BOARD_FIELD_EMPTY)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isTakenByOpponent(int x, int y)
    {
        if(enemy == board[x][y])
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isTakenByMe(int x, int y)
    {
        if(myself == board[x][y])
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public ServerCommand getMoveCommandFor(int x, int y)
    {
        return new MakeMoveCommand("Tic-Tac-Toe", MatchName, x + " " + y);
    }

}
