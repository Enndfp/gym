package com.enndfp.pojo;

/**
 * @author Enndfp
 * @date 2023/3/11
 */
public class Member {
    /**
     * 账号
     */
    private Integer memberAccount;
    /**
     * 密码
     */
    private String memberPassword;
    /**
     * 姓名
     */
    private String memberName;
    /**
     * 性别
     */
    private String memberGender;
    /**
     * 年龄
     */
    private Integer memberAge;
    /**
     * 身高
     */
    private Integer memberHeight;
    /**
     * 体重
     */
    private Integer memberWeight;
    /**
     * 手机号
     */
    private String memberPhone;
    /**
     * 办卡时间
     */
    private String cardTime;
    /**
     * 购买课时
     */
    private Integer cardClass;
    /**
     * 剩余课时
     */
    private Integer cardNextClass;

    public Integer getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(Integer memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberGender() {
        return memberGender;
    }

    public void setMemberGender(String memberGender) {
        this.memberGender = memberGender;
    }

    public Integer getMemberAge() {
        return memberAge;
    }

    public void setMemberAge(Integer memberAge) {
        this.memberAge = memberAge;
    }

    public Integer getMemberHeight() {
        return memberHeight;
    }

    public void setMemberHeight(Integer memberHeight) {
        this.memberHeight = memberHeight;
    }

    public Integer getMemberWeight() {
        return memberWeight;
    }

    public void setMemberWeight(Integer memberWeight) {
        this.memberWeight = memberWeight;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getCardTime() {
        return cardTime;
    }

    public void setCardTime(String cardTime) {
        this.cardTime = cardTime;
    }

    public Integer getCardClass() {
        return cardClass;
    }

    public void setCardClass(Integer cardClass) {
        this.cardClass = cardClass;
    }

    public Integer getCardNextClass() {
        return cardNextClass;
    }

    public void setCardNextClass(Integer cardNextClass) {
        this.cardNextClass = cardNextClass;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberAccount=" + memberAccount +
                ", memberPassword='" + memberPassword + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberGender='" + memberGender + '\'' +
                ", memberAge=" + memberAge +
                ", memberHeight=" + memberHeight +
                ", memberWeight=" + memberWeight +
                ", memberPhone='" + memberPhone + '\'' +
                ", cardTime='" + cardTime + '\'' +
                ", cardClass=" + cardClass +
                ", cardNextClass=" + cardNextClass +
                '}';
    }
}
