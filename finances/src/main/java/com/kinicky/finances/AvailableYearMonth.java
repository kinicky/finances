package com.kinicky.finances;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class AvailableYearMonth {

    @Id
    private Long id;
    private String yearMonth;
    @Index
    private String year;
    private String month;

    public AvailableYearMonth() {
    }

    public AvailableYearMonth(String year, String month) {
        this.year = year;
        this.month = month;
        this.yearMonth = year + "-" + month;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
