package com.eray.pbs.po;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "pbs_accessmh")
public class AccessMhPbs extends BaseEntity
{
    private String accessNo;
    private BigDecimal openMh;
    private BigDecimal closeMh;
    private String majorZone;
    private String aircraftType;
    private String fuelTankPanel;
    private String locationByZone;

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	": id"+id+
		"; accessNo:"+accessNo+
		"; openMh:"+openMh+
		"; closeMh:"+closeMh+
		"; majorZone:"+majorZone+
		"; aircraftType:"+aircraftType+
		"; fuelTankPanel:"+fuelTankPanel+
		"; locationByZone:"+locationByZone;
    }
    
    @Column(name = "accessno_")
    public String getAccessNo()
    {
        return accessNo;
    }

    public void setAccessNo(String accessNo)
    {
        this.accessNo = accessNo;
    }

    @Column(name = "openmh_")
    public BigDecimal getOpenMh()
    {
        return openMh;
    }

    public void setOpenMh(BigDecimal openMh)
    {
        this.openMh = openMh;
    }

    @Column(name = "closemh_")
    public BigDecimal getCloseMh()
    {
        return closeMh;
    }

    public void setCloseMh(BigDecimal closeMh)
    {
        this.closeMh = closeMh;
    }

    @Column(name = "majorzone_")
    public String getMajorZone()
    {
        return majorZone;
    }

    public void setMajorZone(String majorZone)
    {
        this.majorZone = majorZone;
    }

    @Column(name = "aircrafttype_")
    public String getAircraftType()
    {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType)
    {
        this.aircraftType = aircraftType;
    }

    /**
     * @return the fuelTankPanel
     */
    @Column(name = "fueltankpanel_")
    public String getFuelTankPanel()
    {
        return fuelTankPanel;
    }

    /**
     * @param fuelTankPanel the fuelTankPanel to set
     */
    public void setFuelTankPanel(String fuelTankPanel)
    {
        this.fuelTankPanel = fuelTankPanel;
    }

    /**
     * @return the locationByZone
     */
    @Column(name = "locationbyzone_")
    public String getLocationByZone()
    {
        return locationByZone;
    }

    /**
     * @param locationByZone the locationByZone to set
     */
    public void setLocationByZone(String locationByZone)
    {
        this.locationByZone = locationByZone;
    }

}
