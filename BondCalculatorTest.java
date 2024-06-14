import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class BondCalculatorTest {

    private static Stream<org.junit.jupiter.params.provider.Arguments> bondPricingData() {
        return Stream.of(
            // define params 

            // R186
            org.junit.jupiter.params.provider.Arguments.of(
                100, 10.5, 8.75, "R186", "21 December 2026", "7 February 2017", "21 December 2016", 
                "21 June 2017", "11 June 2017", 1.3808219178082193, 112.77263333578408, 111.39181141797586, 48
            ),

            // R2032
            org.junit.jupiter.params.provider.Arguments.of(
                100, 8.25, 9.5, "R2032", "31 March 2032", "16 May 2024", "31 March 2024", 
                "30 September 2024", "20 September 2024", 1.0397260273972602, 94.1966517753584, 93.15692574796114, 46
            ),

            // Test Case 1
            org.junit.jupiter.params.provider.Arguments.of(
                100, 10.5, 9, "R186 Test Case 1 yield of 9%", "21 December 2026", "7 February 2017", "21 December 2016", 
                "21 June 2017", "11 June 2017", 1.3808219178082193, 111.03751695778696, 109.65669503997874, 48
            ),

            // Test Case 2
            org.junit.jupiter.params.provider.Arguments.of(
                100, 10.5, 9.25, "R186 Test Case 1 yield of 9.25%", "21 December 2026", "7 February 2017", "21 December 2016", 
                "21 June 2017", "11 June 2017", 1.3808219178082193, 109.33863872781609, 107.95781681000787, 48
            ), 

            // Test Case 3
            org.junit.jupiter.params.provider.Arguments.of(
                100, 8.25, 9.5, "R2032 Test Case 3", "31 March 2032", "16 July 2024", "31 March 2024", 
                "30 September 2024", "20 September 2024", 2.4184931506849314, 95.66508827642178, 93.24659512573685, 107
            )

        );
    }

    @ParameterizedTest
    @MethodSource("bondPricingData")
    public void testBondPricing(double faceValue, double couponRate, double yieldRate, String instrumentName, 
                                String maturityDateStr, String settlementDateStr, String lastCouponDateStr, 
                                String nextCouponDateStr, String bookCloseDateStr, double expectedAccruedInterest, 
                                double expectedAIP, double expectedCleanPrice, long expectedDaysAccumulated) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        LocalDate maturityDate = LocalDate.parse(maturityDateStr, formatter);
        LocalDate settlementDate = LocalDate.parse(settlementDateStr, formatter);
        LocalDate lastCouponDate = LocalDate.parse(lastCouponDateStr, formatter);
        LocalDate nextCouponDate = LocalDate.parse(nextCouponDateStr, formatter);
        LocalDate bookCloseDate = LocalDate.parse(bookCloseDateStr, formatter);

        long daysAccumulated = BondCalculator.calculateDaysAccumulated(settlementDate, lastCouponDate, nextCouponDate, bookCloseDate);
        double accruedInterest = BondCalculator.calculateAccruedInterest(daysAccumulated, couponRate);
        double f = BondCalculator.calculateF(yieldRate);
        double[] bpValues = BondCalculator.calculateBP(nextCouponDate, lastCouponDate, maturityDate, settlementDate, yieldRate);
        double aip = BondCalculator.calculateAIP(bpValues[1], couponRate / 2, couponRate / 2, f, BondCalculator.calculateN(maturityDate, nextCouponDate), faceValue);
        double cleanPrice = aip - accruedInterest;

        assertEquals(expectedAccruedInterest, accruedInterest, 0.0001);
        assertEquals(expectedAIP, aip, 0.0001);
        assertEquals(expectedCleanPrice, cleanPrice, 0.0001);
        assertEquals(expectedDaysAccumulated, daysAccumulated);
    }
}
