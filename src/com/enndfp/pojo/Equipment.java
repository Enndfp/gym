package com.enndfp.pojo;

/**
 * 会员实体类
 *
 * @author Enndfp
 */
public class Equipment {
    /**
     * id
     */
    private Integer equipmentId;
    /**
     * 名称
     */
    private String equipmentName;
    /**
     * 位置
     */
    private String equipmentLocation;
    /**
     * 状态
     */
    private String equipmentStatus;
    /**
     * 备注
     */
    private String equipmentMessage;

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentLocation() {
        return equipmentLocation;
    }

    public void setEquipmentLocation(String equipmentLocation) {
        this.equipmentLocation = equipmentLocation;
    }

    public String getEquipmentStatus() {
        return equipmentStatus;
    }

    public void setEquipmentStatus(String equipmentStatus) {
        this.equipmentStatus = equipmentStatus;
    }

    public String getEquipmentMessage() {
        return equipmentMessage;
    }

    public void setEquipmentMessage(String equipmentMessage) {
        this.equipmentMessage = equipmentMessage;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "equipmentId=" + equipmentId +
                ", equipmentName='" + equipmentName + '\'' +
                ", equipmentLocation='" + equipmentLocation + '\'' +
                ", equipmentStatus='" + equipmentStatus + '\'' +
                ", equipmentMessage='" + equipmentMessage + '\'' +
                '}';
    }
}
