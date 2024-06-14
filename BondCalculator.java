import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BondCalculator {

    private static final double HALF_YEAR_DAYS = 365.25 / 2;

    public static long calculateN(LocalDate maturityDate, LocalDate nextCouponDate) {
        long daysBetween = ChronoUnit.DAYS.between(nextCouponDate, maturityDate);
        return Math.round(daysBetween / HALF_YEAR_DAYS);
    }

    public static int isCumEx(LocalDate settlementDate, LocalDate bookCloseDate) {
        return settlementDate.isBefore(bookCloseDate) ? 1 : 0;
    }

    public static long calculateDaysAccumulated(LocalDate settlementDate, LocalDate lastCouponDate, LocalDate nextCouponDate, LocalDate bookCloseDate1) {
        if (isCumEx(settlementDate, bookCloseDate1) == 1) {
            return ChronoUnit.DAYS.between(lastCouponDate, settlementDate);
        } else {
            return ChronoUnit.DAYS.between(nextCouponDate, settlementDate);
        }
    }

    public static double[] calculateCoupon(double couponRate, LocalDate settlementDate, LocalDate bookCloseDate1) {
        double semiAnnualCoupon = couponRate / 2;
        double couponNextCouponDate = semiAnnualCoupon * isCumEx(settlementDate, bookCloseDate1);
        return new double[]{couponNextCouponDate, semiAnnualCoupon};
    }

    public static double calculateF(double yieldRate) {
        return 1 / (1 + (yieldRate / 200));
    }

    public static double[] calculateBP(LocalDate nextCouponDate, LocalDate lastCouponDate, LocalDate maturityDate, LocalDate settlementDate, double yieldRate) {
        double bp;
        double bpFactor;
        if (!nextCouponDate.equals(maturityDate)) {
            bp = (double) ChronoUnit.DAYS.between(settlementDate, nextCouponDate) / ChronoUnit.DAYS.between(lastCouponDate, nextCouponDate);
            bpFactor = Math.pow(calculateF(yieldRate), bp);
        } else {
            bp = (double) ChronoUnit.DAYS.between(settlementDate, nextCouponDate) / HALF_YEAR_DAYS;
            bpFactor = calculateF(yieldRate) / (calculateF(yieldRate) + bp * (1 - calculateF(yieldRate)));
        }
        return new double[]{bp, bpFactor};
    }

    public static double calculateAccruedInterest(long daysAccumulated, double couponRate) {
        return (daysAccumulated * couponRate) / 365;
    }

    public static double calculateAIP(double bpFactor, double couponNextCouponDate, double semiAnnualCoupon, double f, long n, double faceValue) {
        if (f != 1) {
            return bpFactor * (couponNextCouponDate + semiAnnualCoupon * (f * (1 - Math.pow(f, n)) / (1 - f)) + faceValue * Math.pow(f, n));
        } else {
            return couponNextCouponDate + semiAnnualCoupon * n + faceValue;
        }
    }
}
