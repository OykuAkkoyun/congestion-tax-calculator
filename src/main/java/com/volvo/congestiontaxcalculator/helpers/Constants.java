package com.volvo.congestiontaxcalculator.helpers;

import java.text.SimpleDateFormat;

public class Constants
{
    public static String VALID_INPUT_MESSAGE = "Valid";
    public static String INPUT_CANNOT_BE_NULL = "Input cannot be null: ";

    public static String SERVICE_FAILED = "Calculation Service Failed.";

    public static String TAX_CALCULATED_MESSAGE = "Congestion Tax Amount Calculated for: ";
    public static String CONFIG_NOT_FOUND = "City Config is not found for: ";

    public static SimpleDateFormat ruleDateFormat = new SimpleDateFormat("HH:mm");

}
