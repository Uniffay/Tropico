package tropico.Model;

public class Utils {

    public static int modifiedByDifficulty(int resourceValue){
        double difficulty = DataManagement.getData().getDifficulty();
        return (int)((resourceValue > 0)? resourceValue * (1/ difficulty): resourceValue * difficulty);
    }

    public static String getDateFromTurn(Integer turnLost) {
        int monthIntValue = ((turnLost % 4) * 3) + 3;
        String month = (monthIntValue < 10)? "0" + monthIntValue: String.valueOf(monthIntValue);
        return "24/" + month + "/" + (2020 + turnLost / 4);
    }

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
}
