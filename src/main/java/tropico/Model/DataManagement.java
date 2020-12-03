package tropico.Model;

import tropico.Object.Data;

public class DataManagement {

    static private Data GAME_DATA = null;

    static public Data getData(){
        return GAME_DATA;
    }

    static public void setData(Data data){
        GAME_DATA = data;
    }
}
