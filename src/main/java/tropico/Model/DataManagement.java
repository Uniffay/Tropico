package tropico.Model;

import tropico.Object.Data;

public class DataManagement {

    static private Data GAMEDATA = null;

    static public Data getData(){
        return GAMEDATA;
    }

    static public void setData(Data data){
        GAMEDATA = data;
    }
}
