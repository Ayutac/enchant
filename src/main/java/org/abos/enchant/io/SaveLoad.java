package org.abos.enchant.io;

import org.abos.enchant.core.Player;

import java.io.*;

public final class SaveLoad {

    private SaveLoad() {
        /* no instantiation */
    }

    public static void savePlayer(Player player, File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(player);
        }
    }

    public static Player loadPlayer(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Player)ois.readObject();
        }
    }

}
