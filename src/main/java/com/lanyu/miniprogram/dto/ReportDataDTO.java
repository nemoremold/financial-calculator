package com.lanyu.miniprogram.dto;

import java.math.BigDecimal;

/**
 * @Author yansong
 * @Date 2018/10/13 12:59
 */
public class ReportDataDTO {
    private String wechatId;

    private String timestamp;

    private String gender;

    private String province;

    private String jobType;

    private int workingMonths;

    private int insuredMonths;

    private int continuousWork;

    private int age;

    private int legalRetirementAge;

    private int expectedRetirementAge;

    private int expectedLife;

    private int incomeWithTax;

    private int incomeWithMonth;

    private int averageIncomePerMonth;

    private int pensionBalance;

    private boolean companyAnnuity;

    private BigDecimal pensionReplacementRate;

    private int existingPension;

    private BigDecimal pensionBenefitRate;

    private String name;

    private int supplementaryPension;

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public int getWorkingMonths() {
        return workingMonths;
    }

    public void setWorkingMonths(int workingMonths) {
        this.workingMonths = workingMonths;
    }

    public int getInsuredMonths() {
        return insuredMonths;
    }

    public void setInsuredMonths(int insuredMonths) {
        this.insuredMonths = insuredMonths;
    }

    public int getContinuousWork() {
        return continuousWork;
    }

    public void setContinuousWork(int continuousWork) {
        this.continuousWork = continuousWork;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getLegalRetirementAge() {
        return legalRetirementAge;
    }

    public void setLegalRetirementAge(int legalRetirementAge) {
        this.legalRetirementAge = legalRetirementAge;
    }

    public int getExpectedRetirementAge() {
        return expectedRetirementAge;
    }

    public void setExpectedRetirementAge(int expectedRetirementAge) {
        this.expectedRetirementAge = expectedRetirementAge;
    }

    public int getExpectedLife() {
        return expectedLife;
    }

    public void setExpectedLife(int expectedLife) {
        this.expectedLife = expectedLife;
    }

    public int getIncomeWithTax() {
        return incomeWithTax;
    }

    public void setIncomeWithTax(int incomeWithTax) {
        this.incomeWithTax = incomeWithTax;
    }

    public int getIncomeWithMonth() {
        return incomeWithMonth;
    }

    public void setIncomeWithMonth(int incomeWithMonth) {
        this.incomeWithMonth = incomeWithMonth;
    }

    public int getAverageIncomePerMonth() {
        return averageIncomePerMonth;
    }

    public void setAverageIncomePerMonth(int averageIncomePerMonth) {
        this.averageIncomePerMonth = averageIncomePerMonth;
    }

    public int getPensionBalance() {
        return pensionBalance;
    }

    public void setPensionBalance(int pensionBalance) {
        this.pensionBalance = pensionBalance;
    }

    public boolean isCompanyAnnuity() {
        return companyAnnuity;
    }

    public void setCompanyAnnuity(boolean companyAnnuity) {
        this.companyAnnuity = companyAnnuity;
    }

    public BigDecimal getPensionReplacementRate() {
        return pensionReplacementRate;
    }

    public void setPensionReplacementRate(BigDecimal pensionReplacementRate) {
        this.pensionReplacementRate = pensionReplacementRate;
    }

    public int getExistingPension() {
        return existingPension;
    }

    public void setExistingPension(int existingPension) {
        this.existingPension = existingPension;
    }

    public BigDecimal getPensionBenefitRate() {
        return pensionBenefitRate;
    }

    public void setPensionBenefitRate(BigDecimal pensionBenefitRate) {
        this.pensionBenefitRate = pensionBenefitRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSupplementaryPension() {
        return supplementaryPension;
    }

    public void setSupplementaryPension(int supplementaryPension) {
        this.supplementaryPension = supplementaryPension;
    }
}
