package com.kinicky.finances.common;

import org.apache.commons.lang.StringUtils;

public class FinancesHelper {

    public static double parseDouble(String number) {
        if (StringUtils.isNotBlank(number)) {
            return Double.parseDouble(number);
        }
        return 0;
    }
}
