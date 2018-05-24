package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;
@Entity
@Table(name = "pbs_partner")
public class PartnerPbs extends BaseEntity
{
    private String name;
    private String street;
    private String city;
    private String postCode;
    private String countryKey;
    private String firstTelephone;
    private String searchTerm;
    private String partnerFunction;
    private String partnerId;
    private String description;
    

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    	    	"; name:"+name+
    			"; street:"+street+
    			"; city:"+city+
    			"; postCode:"+postCode+
    			"; countryKey:"+countryKey+
    			"; firstTelephone:"+firstTelephone+
    			"; searchTerm:"+searchTerm+
    			"; partnerFunction:"+partnerFunction+
    			"; partnerId:"+partnerId+
    			"; description:"+description;
    }
    
    @Column(name = "name_")
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    @Column(name = "city_")
    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    @Column(name = "countrykey_")
    public String getCountryKey()
    {
        return countryKey;
    }
    public void setCountryKey(String countryKey)
    {
        this.countryKey = countryKey;
    }
    @Column(name = "firsttelephone_")
    public String getFirstTelephone()
    {
        return firstTelephone;
    }
    public void setFirstTelephone(String firstTelephone)
    {
        this.firstTelephone = firstTelephone;
    }
    @Column(name = "partnerfunction_")
    public String getPartnerFunction()
    {
        return partnerFunction;
    }
    public void setPartnerFunction(String partnerFunction)
    {
        this.partnerFunction = partnerFunction;
    }
    @Column(name = "partnerid_")
    public String getPartnerId()
    {
        return partnerId;
    }
    public void setPartnerId(String partnerId)
    {
        this.partnerId = partnerId;
    }
    @Column(name = "description_")
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    @Column(name = "street_")
    public String getStreet()
    {
        return street;
    }
    public void setStreet(String street)
    {
        this.street = street;
    }
    @Column(name = "postcode_")
    public String getPostCode()
    {
        return postCode;
    }
    public void setPostCode(String postCode)
    {
        this.postCode = postCode;
    }
    @Column(name = "searchterm_")
    public String getSearchTerm()
    {
        return searchTerm;
    }
    public void setSearchTerm(String searchTerm)
    {
        this.searchTerm = searchTerm;
    }
}
