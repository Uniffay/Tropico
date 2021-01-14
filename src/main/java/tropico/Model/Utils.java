package tropico.Model;

import java.util.List;
import java.util.Random;

public class Utils {

    /**
     *  modify a value by the difficulty ration of the game
     * @param resourceValue
     *  value that need modification
     * @return resource value modified
     */
    public static int modifiedByDifficulty(int resourceValue){
        double difficulty = DataManagement.getData().getDifficultyRatio();
        return (int)((resourceValue > 0)? resourceValue * (1/ difficulty): resourceValue * difficulty);
    }

    /**
     *  get the date from the turn
     * @param turnLost
     *      turn when the player lost
     * @return the date as a string of format JJ/MM/AAAA from turn
     *
     */
    public static String getDateFromTurn(Integer turnLost) {
        int monthIntValue = ((turnLost % 4) * 3) + 3;
        String month = (monthIntValue < 10)? "0" + monthIntValue: String.valueOf(monthIntValue);
        return "24/" + month + "/" + (2020 + turnLost / 4);
    }

    /**
     *  get the time in day of the reign
     * @param turnLost
     *      turn when the player lost
     * @return the time in day of the reign of the player
     */
    public static int getTimeReign(Integer turnLost) {
        //1 year = 4 turn
        int numberOfYear = turnLost / 4;
        //4year = 1 cycle (for year with 29 february)
        int cycleCount = numberOfYear / 4;
        //year that are in the current cycle
        int numberOfYearLeft = numberOfYear % 4;
        //month that are during the current year
        int numberOfMonthLeft = turnLost % 4;
        //there is 92 day between 24 mars/ 24 june and 24 june/ 24 september but only 91 for 24 september/ 24 december
        int dayFromMonthLeft=  (numberOfMonthLeft < 3)? 92 * numberOfMonthLeft: 92 * numberOfMonthLeft - 1;
        //4 years is equals to 365*3 - 366 days = 1461
        //2020 is a 366 days year but we start in Mars so we don't count the 29 february
        return 1461 * cycleCount + numberOfYearLeft * 365 + dayFromMonthLeft;
    }

    /**
     *  get a random element of a list
     * @param list
     *      list of element
     * @param <E>
     *     element
     * @return a random event of the list
     */
    public static <E> E getRandom(List<E> list){
        Random r = new Random();
        return list.get(r.nextInt(list.size()));
    }

    /**
     *  get a random integer between 0 and bounds - 1
     * @param bounds
     *     bounds of the random integer
     * @return a random integer
     */
    public static int getRandom(int bounds){
        Random r = new Random();
        return r.nextInt(bounds + 1);
    }
}
