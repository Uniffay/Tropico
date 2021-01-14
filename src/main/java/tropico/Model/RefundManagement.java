package tropico.Model;

import tropico.Object.Data;

/**
 * manage the refund of the player debt during the end of year
 * @author Cléis & Quentin
 */
public class RefundManagement {

    /**
     * loan that the player have left to refund
     */
    private static int LOAN_SAVE = 0;
    /**
     * loan that the player will refund
     */
    private static int LOAN_REFUND = 0;

    /**
     * initialize field of class
     * @param gameData
     *      data of the game
     */
    public static void initialize(Data gameData){
        LOAN_SAVE = gameData.getPlayerPlaying().getDebt();
        LOAN_REFUND = 0;
    }

    /**
     * get the value of the loan left the player have to refund
     * @return value of the loan left
     */
    public static int getLoanSave() {
        return LOAN_SAVE;
    }

    /**
     * get the value of the loan the the layer will refund
     * @return the value of the refund
     */
    public static int getLoanRefund() {
        return LOAN_REFUND;
    }

    /**
     * update the value the player will refund by number
     * @param number
     *      number the player will modify to the refund value
     * @return number modified to the refund value (can be different than number if max or min is reached)
     */
    public static int updateRefund(int number){
        int loanRefundBefore = LOAN_REFUND;
        LOAN_REFUND += number;
        LOAN_REFUND = Math.max(Math.min(LOAN_REFUND, LOAN_SAVE), 0);
        return LOAN_REFUND - loanRefundBefore;
    }

    /**
     * update loan value left
     * @param gameData
     *      data of the game
     */
    public static void update(Data gameData) {
        LOAN_SAVE = gameData.getPlayerPlaying().getDebt();
    }
}
