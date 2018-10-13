package com.lanyu.miniprogram.bean;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author yansong
 * @Date 2018/10/13 12:20
 */
@Entity
@Table(name="data_item")
public class ReportData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "user_id")
    private String wechatId;

    @NotNull
    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "gender")
    private String gender;

    @Column(name = "province")
    private String province;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "working_months")
    private int workingMonths;

    @Column(name = "insured_months")
    private int insuredMonths;

    @Column(name = "continuous_work")
    private int continuousWork;

    @Column(name = "age")
    private int age;

    @Column(name = "legal_retirement_age")
    private int legalRetirementAge;

    @Column(name = "expected_retirement_age")
    private int expectedRetirementAge;

    @Column(name = "expected_life")
    private int expectedLife;

    @Column(name = "income_with_tax")
    private int incomeWithTax;

    @Column(name = "income_with_month")
    private int incomeWithMonth;

    @Column(name = "average_income_per_month")
    private int averageIncomePerMonth;

    @Column(name = "pension_balance")
    private int pensionBalance;

    @Column(name = "company_annuity")
    private boolean companyAnnuity;

    @Column(name = "pension_replacement_rate")
    private double pensionReplacementRate;

    @Column(name = "existing_pension")
    private int existingPension;

    @Column(name = "pension_benefit_rate")
    private double pensionBenefitRate;

    @Column(name = "name")
    private String name;

    @Column(name = "supplementary_pension")
    private int supplementaryPension;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public double getPensionReplacementRate() {
        return pensionReplacementRate;
    }

    public void setPensionReplacementRate(double pensionReplacementRate) {
        this.pensionReplacementRate = pensionReplacementRate;
    }

    public int getExistingPension() {
        return existingPension;
    }

    public void setExistingPension(int existingPension) {
        this.existingPension = existingPension;
    }

    public double getPensionBenefitRate() {
        return pensionBenefitRate;
    }

    public void setPensionBenefitRate(double pensionBenefitRate) {
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
