package com.lanyu.miniprogram.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author i343746
 */
public class RenderData {
    private String wechatId;
    @SerializedName(value ="generate_time")
    private String generate_time;
    @SerializedName(value ="avatar_url")
    private String avatar_url;
    @SerializedName(value ="target-name")
    private String name;
    @SerializedName(value ="gender")
    private String gender;
    @SerializedName(value ="age")
    private int age;
    @SerializedName(value ="start-date")
    private String join_time;
    @SerializedName(value ="mandatory-age-for-retirement")
    private int legal_retire_age;
    @SerializedName(value ="expected-retirement-age")
    private int expected_retire_age;
    @SerializedName(value ="time-for-participation")
    private int years_insure;
    @SerializedName(value ="social-security-location")
    private String location;
    @SerializedName(value ="company-type")
    private String type_of_insure;
    @SerializedName(value ="personal-salary-before-tax")
    private int salary_with_tax;
    @SerializedName(value ="local-average-salary-last-year")
    private int local_salary;
    @SerializedName(value ="social-security-pension-account-balance")
    private int remaining_insure;
    @SerializedName(value ="ratioOfBasicReceivePension")
    private double ratioOfBasicReceivePension;
    @SerializedName(value ="pointAverage")
    private double pointAverage;
    @SerializedName(value ="planMonths")
    private int planMonths;
    @SerializedName(value ="retirementSalaryPerMonth")
    private double retirementSalaryPerMonth;
    @SerializedName(value ="pensionBasicSocialInsurance")
    private double pensionBasicSocialInsurance;
    @SerializedName(value ="pensionPersonalAccount")
    private double pensionPersonalAccount;
    @SerializedName(value ="pensionTransition")
    private double pensionTransition;
    @SerializedName(value ="companyAnnuity")
    private double companyAnnuity;
    @SerializedName(value ="pensionInFirstRetirementMonth")
    private double pensionInFirstRetirementMonth;
    @SerializedName(value ="pensionReplacementRate")
    private double pensionReplacementRate;
    @SerializedName(value ="rateOfSocialInsurancePlusAnnuity")
    private double rateOfSocialInsurancePlusAnnuity;
    @SerializedName(value ="gapOfPensionReplacementRateValue")
    private double gapOfPensionReplacementRateValue;
    @SerializedName(value ="pensionGapPerMonth")
    private double pensionGapPerMonth;
    @SerializedName(value ="salaries")
    private String salaries;
    @SerializedName(value ="pensions")
    private String pensions;
    @SerializedName(value ="gaps")
    private String gaps;

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getGenerate_time() {
        return generate_time;
    }

    public void setGenerate_time(String generate_time) {
        this.generate_time = generate_time;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJoin_time() {
        return join_time;
    }

    public void setJoin_time(String join_time) {
        this.join_time = join_time;
    }

    public int getLegal_retire_age() {
        return legal_retire_age;
    }

    public void setLegal_retire_age(int legal_retire_age) {
        this.legal_retire_age = legal_retire_age;
    }

    public int getExpected_retire_age() {
        return expected_retire_age;
    }

    public void setExpected_retire_age(int expected_retire_age) {
        this.expected_retire_age = expected_retire_age;
    }

    public int getYears_insure() {
        return years_insure;
    }

    public void setYears_insure(int years_insure) {
        this.years_insure = years_insure;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType_of_insure() {
        return type_of_insure;
    }

    public void setType_of_insure(String type_of_insure) {
        this.type_of_insure = type_of_insure;
    }

    public int getSalary_with_tax() {
        return salary_with_tax;
    }

    public void setSalary_with_tax(int salary_with_tax) {
        this.salary_with_tax = salary_with_tax;
    }

    public int getLocal_salary() {
        return local_salary;
    }

    public void setLocal_salary(int local_salary) {
        this.local_salary = local_salary;
    }

    public int getRemaining_insure() {
        return remaining_insure;
    }

    public void setRemaining_insure(int remaining_insure) {
        this.remaining_insure = remaining_insure;
    }

    public double getRatioOfBasicReceivePension() {
        return ratioOfBasicReceivePension;
    }

    public void setRatioOfBasicReceivePension(double ratioOfBasicReceivePension) {
        this.ratioOfBasicReceivePension = ratioOfBasicReceivePension;
    }

    public double getPointAverage() {
        return pointAverage;
    }

    public void setPointAverage(double pointAverage) {
        this.pointAverage = pointAverage;
    }

    public int getPlanMonths() {
        return planMonths;
    }

    public void setPlanMonths(int planMonths) {
        this.planMonths = planMonths;
    }

    public double getRetirementSalaryPerMonth() {
        return retirementSalaryPerMonth;
    }

    public void setRetirementSalaryPerMonth(double retirementSalaryPerMonth) {
        this.retirementSalaryPerMonth = retirementSalaryPerMonth;
    }

    public double getPensionBasicSocialInsurance() {
        return pensionBasicSocialInsurance;
    }

    public void setPensionBasicSocialInsurance(double pensionBasicSocialInsurance) {
        this.pensionBasicSocialInsurance = pensionBasicSocialInsurance;
    }

    public double getPensionPersonalAccount() {
        return pensionPersonalAccount;
    }

    public void setPensionPersonalAccount(double pensionPersonalAccount) {
        this.pensionPersonalAccount = pensionPersonalAccount;
    }

    public double getPensionTransition() {
        return pensionTransition;
    }

    public void setPensionTransition(double pensionTransition) {
        this.pensionTransition = pensionTransition;
    }

    public double getCompanyAnnuity() {
        return companyAnnuity;
    }

    public void setCompanyAnnuity(double companyAnnuity) {
        this.companyAnnuity = companyAnnuity;
    }

    public double getPensionInFirstRetirementMonth() {
        return pensionInFirstRetirementMonth;
    }

    public void setPensionInFirstRetirementMonth(double pensionInFirstRetirementMonth) {
        this.pensionInFirstRetirementMonth = pensionInFirstRetirementMonth;
    }

    public double getPensionReplacementRate() {
        return pensionReplacementRate;
    }

    public void setPensionReplacementRate(double pensionReplacementRate) {
        this.pensionReplacementRate = pensionReplacementRate;
    }

    public double getRateOfSocialInsurancePlusAnnuity() {
        return rateOfSocialInsurancePlusAnnuity;
    }

    public void setRateOfSocialInsurancePlusAnnuity(double rateOfSocialInsurancePlusAnnuity) {
        this.rateOfSocialInsurancePlusAnnuity = rateOfSocialInsurancePlusAnnuity;
    }

    public double getGapOfPensionReplacementRateValue() {
        return gapOfPensionReplacementRateValue;
    }

    public void setGapOfPensionReplacementRateValue(double gapOfPensionReplacementRateValue) {
        this.gapOfPensionReplacementRateValue = gapOfPensionReplacementRateValue;
    }

    public double getPensionGapPerMonth() {
        return pensionGapPerMonth;
    }

    public void setPensionGapPerMonth(double pensionGapPerMonth) {
        this.pensionGapPerMonth = pensionGapPerMonth;
    }

    public String getSalaries() {
        return salaries;
    }

    public void setSalaries(String salaries) {
        this.salaries = salaries;
    }

    public String getPensions() {
        return pensions;
    }

    public void setPensions(String pensions) {
        this.pensions = pensions;
    }

    public String getGaps() {
        return gaps;
    }

    public void setGaps(String gaps) {
        this.gaps = gaps;
    }
}
