import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SABondAlgorithm{

    private LocalDate settlementDate;
    private LocalDate bookCloseDate1;
    private LocalDate maturityDate;
    private LocalDate nextCouponDate;
    private LocalDate lastCouponDate;
    private double faceValue;
    private double couponRate;
    private double yieldRate;
    private String instrumentName;

    public SABondAlgorithm(String instrumentName, String maturityDateStr, String settlementDateStr,
                              String lastCouponDateStr, String nextCouponDateStr, String bookCloseDate1Str,
                              double faceValue, double couponRate, double yieldRate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");

        this.instrumentName = instrumentName;
        this.settlementDate = LocalDate.parse(settlementDateStr, formatter);
        this.bookCloseDate1 = LocalDate.parse(bookCloseDate1Str, formatter);
        this.maturityDate = LocalDate.parse(maturityDateStr, formatter);
        this.nextCouponDate = LocalDate.parse(nextCouponDateStr, formatter);
        this.lastCouponDate = LocalDate.parse(lastCouponDateStr, formatter);
        this.faceValue = faceValue;
        this.couponRate = couponRate;
        this.yieldRate = yieldRate;
    }

    public void calculate() {
        long n = BondCalculator.calculateN(maturityDate, nextCouponDate);
        long daysAccumulated = BondCalculator.calculateDaysAccumulated(settlementDate, lastCouponDate, nextCouponDate, bookCloseDate1);
        double[] couponValues = BondCalculator.calculateCoupon(couponRate, settlementDate, bookCloseDate1);
        double couponNextCouponDate = couponValues[0];
        double semiAnnualCoupon = couponValues[1];
        double f = BondCalculator.calculateF(yieldRate);
        double[] bpValues = BondCalculator.calculateBP(nextCouponDate, lastCouponDate, maturityDate, settlementDate, yieldRate);
        double bp = bpValues[0];
        double bpFactor = bpValues[1];

        double accruedInterest = BondCalculator.calculateAccruedInterest(daysAccumulated, couponRate);
        double allInPrice = BondCalculator.calculateAIP(bpFactor, couponNextCouponDate, semiAnnualCoupon, f, n, faceValue);
        double cleanPrice = allInPrice - accruedInterest;

        System.out.println("The pricing results for " + instrumentName + " settling on " + settlementDate + ".\n");
        System.out.println("Accrued Interest: " + accruedInterest);
        System.out.println("All-In Price (AIP): " + allInPrice);
        System.out.println("Clean Price: " + cleanPrice);
        System.out.println("----------------------------------");
        System.out.println("F: " + f);
        System.out.println("BP: " + bp);
        System.out.println("BP Factor: " + bpFactor);
        System.out.println("Days Accumulated: " + daysAccumulated);
        System.out.println("------------------------END-----------------------\n\n");
    }

    
    /*
    
    Change Parameters Here:
    
    */ 
    public static void main(String[] args) {
        // Run the bond pricing algorithm

        // Params for R186
        double faceValue1 = 100;
        double couponRate1 = 10.5;
        double yieldRate1 = 8.75;
        String instrumentName1 = "Example 1, R186";
        String maturityDateStr1 = "21 December 2026";
        String settlementDateStr1 = "7 February 2017";
        String lastCouponDateStr1 = "21 December 2016";
        String nextCouponDateStr1 = "21 June 2017";
        String bookCloseDate1Str1 = "11 June 2017";

        SABondAlgorithm bond1 = new SABondAlgorithm(instrumentName1, maturityDateStr1, settlementDateStr1,
                lastCouponDateStr1, nextCouponDateStr1, bookCloseDate1Str1, faceValue1, couponRate1, yieldRate1);
        bond1.calculate();


        // Params for R2032
        double faceValue2 = 100;
        double couponRate2 = 8.25;
        double yieldRate2 = 9.5;
        String instrumentName2 = "Example 2, R2032";
        String maturityDateStr2 = "31 March 2032";
        String settlementDateStr2 = "16 May 2024";
        String lastCouponDateStr2 = "31 March 2024";
        String nextCouponDateStr2 = "30 September 2024";
        String bookCloseDate1Str2 = "20 September 2024";

        SABondAlgorithm bond2 = new SABondAlgorithm(instrumentName2, maturityDateStr2, settlementDateStr2,
                lastCouponDateStr2, nextCouponDateStr2, bookCloseDate1Str2, faceValue2, couponRate2, yieldRate2);
        bond2.calculate();

        
        // Params for R186 Test Case 1
        double yieldRate11 = 9;
        String instrumentName11 = "R186 Test Case 1 yield of 9%";

        SABondAlgorithm bond11 = new SABondAlgorithm(instrumentName11, maturityDateStr1, settlementDateStr1,
                lastCouponDateStr1, nextCouponDateStr1, bookCloseDate1Str1, faceValue1, couponRate1, yieldRate11);
        bond11.calculate();

        // Params for R186 Test Case 2
        double yieldRate12 = 9.25;
        String instrumentName12 = "R186 Test Case 2 yield of 9.25%";

        SABondAlgorithm bond12 = new SABondAlgorithm(instrumentName12, maturityDateStr1, settlementDateStr1,
                lastCouponDateStr1, nextCouponDateStr1, bookCloseDate1Str1, faceValue1, couponRate1, yieldRate12);
        bond12.calculate();

        // Params for R2032 Test Case 1
        String settlementDateStr21 = "16 July 2024";
        String instrumentName21 = "R2032 Test Case 3";

        SABondAlgorithm bond21 = new SABondAlgorithm(instrumentName21, maturityDateStr2, settlementDateStr21,
                lastCouponDateStr2, nextCouponDateStr2, bookCloseDate1Str2, faceValue2, couponRate2, yieldRate2);
        bond21.calculate();


    }
}
