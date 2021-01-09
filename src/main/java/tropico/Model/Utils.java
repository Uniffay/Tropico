package tropico.Model;

public class Utils {

    public static int modifiedByDifficulty(int resourceValue){
        double difficulty = DataManagement.getData().getDifficulty();
        return (int)((resourceValue > 0)? resourceValue * (1/ difficulty): resourceValue * difficulty);
    }
}
