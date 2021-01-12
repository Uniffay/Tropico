package tropico.Model;

import tropico.Object.Data;

import java.io.IOException;
import java.util.List;

public class DataManagement {

    static private Data GAME_DATA = null;

    static public Data getData(){
        return GAME_DATA;
    }

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
