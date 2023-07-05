package org.abos.enchant.io;

import org.abos.enchant.cmd.CmdPlayer;
import org.abos.enchant.core.Player;

import java.io.File;
import java.io.IOException;

public class TestSaveLoad {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File tmp = new File("./src/test/tmp");
        System.out.println(tmp.isDirectory());
        Player player = new Player();
        player.setGold(100);
        SaveLoad.savePlayer(player, new File(tmp, "player"));
        player = SaveLoad.loadPlayer(new File(tmp, "player"));
        System.out.println(player.getGold() == 100);
        player = new CmdPlayer();
        SaveLoad.savePlayer(player, new File(tmp, "cmd_player"));
        player = SaveLoad.loadPlayer(new File(tmp, "cmd_player"));
        System.out.println(player instanceof CmdPlayer);
        SaveLoad.loadPlayer(new File(tmp, "cmd_player2"));
    }

}
