package tropico.Model;

public class ViewManagement {

    public static boolean FACTION_SHOW = true;
    public static boolean EVENT_SHOW = true;

    public static boolean isFactionShow(){
        return FACTION_SHOW;
    }

    public static boolean isEventShow() {
        return EVENT_SHOW;
    }

    public static void setFactionShow(boolean bool){
        FACTION_SHOW = bool;
    }

    public static void setEventShow(boolean bool){
        EVENT_SHOW = bool;
    }
}
