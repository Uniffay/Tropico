package tropico.Model;

import tropico.Object.Data;

import java.io.IOException;
import java.util.List;

/**
 * save data
 * @author Quentin & Cléis
 */
public class DataManagement {

    /**
     * save data of the game
     */
    static private Data GAME_DATA = null;

    /**
     * get data of the game
     * @return data of the game
     */
    public static Data getData(){
        return GAME_DATA;
    }

    /**
     * initialize data of the game
     * @param names
     *      list of names of the players
     * @throws IOException if an I/O error occurs
     */
    public static void initializeData(List<String> names) throws IOException {
        GAME_DATA = new Data(
                names.size(),
                names.toArray(new String[0]),
                JSonPathManagement.getJsonSetting(),
                JSonPathManagement.getJsonFaction(),
                JSonPathManagement.getJsonEvent()
        );

    }

    public static void setData(Data data) {
        GAME_DATA = data;
    }
}
