package tropico.Model;

import tropico.Object.Data;

public class RefundManagement {

    private static int LOAN_SAVE = 0;
    private static int LOAN_REFUND = 0;

    public static void initialize(Data gameData){
        LOAN_SAVE = gameData.getPlayerPlaying().getDebt();
        LOAN_REFUND = 0;
    }

    public static int getLoanSave() {
        return LOAN_SAVE;
    }

    public static int getLoanRefund() {
        return LOAN_REFUND;
    }

    public static int updateRefund(int number){
        int loanRefundBefore = LOAN_REFUND;
        LOAN_REFUND += number;
        LOAN_REFUND = Math.max(Math.min(LOAN_REFUND, LOAN_SAVE), 0);
        return LOAN_REFUND - loanRefundBefore;
    }


    public static void update(Data gameData) {
        LOAN_SAVE = gameData.getPlayerPlaying().getDebt();
    }
}
