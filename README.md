# Bond Calculator Project

## Overview

This project includes three Java scripts designed to calculate and test the pricing values of South African (SA) bonds. The scripts are:

1. **BondCalculator.java**
2. **BondCalculatorTest.java**
3. **SABondAlgorithm.java**

## Scripts Description

### BondCalculator.java

This file contains the formulas necessary to calculate various pricing values of SA bonds. It computes the following bond values:

- **Accrued Interest (ACCRINT)**
- **All in Price (AIP)**
- **Clean Price (CP)**

Additionally, it calculates:

- The semi-annual discount factor (F) corresponding to the Yield (Y)
- The broken-period (BP) measured in half-years
- The broken-period discount factor (BPF)

### BondCalculatorTest.java

This script performs JUnit testing to verify the correctness of the values computed by the BondCalculator. It tests the following bond pricing values:

- **Accrued Interest (ACCRINT)**
- **All in Price (AIP)**
- **Clean Price (CP)**

This sript performs 5 different test cases:
- Example 1
- Example 2
- Test Case scenario 1 
- Test Case scenario 2
- Test Case scenario 3

### SABondAlgorithm.java

This script computes the bond pricing values based on given parameters. Users run this script to obtain the bond pricing values. 

#### Parameters Needed:
- `faceValue` (Redemption amount)
- `couponRate`
- `yieldRate`
- `instrumentName`
- `maturityDateStr`
- `settlementDateStr`
- `lastCouponDateStr`
- `nextCouponDateStr`
- `bookCloseDate1Str`

#### Running Default SABondAlgorithm.java

When running the default `SABondAlgorithm.java` script, users will get pricing values for:
- **Example 1:** Pricing Bond R186
- **Example 2:** Pricing Bond R2032

#### Three Test Case Scenarios:
- **Test Case 1:** R186 with a yield of 9% (changing only the yield)
- **Test Case 2:** R186 with a yield of 9.25% (changing only the yield)
- **Test Case 3:** R2032 with a settlement date of 16 July 2024 (changing only the settlement date)

## Solution Approach

An object-oriented programming approach was used to create a Bond Calculator class, which consists of the algorithms specified in the BOND PRICING FORMULA Specifications document. 

The `BondCalculator.java` script contains all the required algorithms, making it easier to reuse the code or call the algorithm in any Java script. By separating the algorithms from the computation script (`SABondAlgorithm.java`), it also simplifies performing JUnit testing for different scenarios.

## Data Structures

The following data structures were used:
- **Strings:** `maturityDateStr`, `settlementDateStr`, `lastCouponDateStr`, `nextCouponDateStr`, `bookCloseDate1Str`. These strings are converted into `DateTimeFormatter`.
- **Doubles:** `faceValue` (Redemption amount), `couponRate`, and `yieldRate` to handle decimal values.
# Bond_Calculator
