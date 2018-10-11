package beans.facets;

import beans.facets.values.DemographicsValue;

import java.util.List;
import java.util.Map;

/**
* Define Demographics facet
*
* @author Roberto B. Stanziale
* @version 1.0
*/
public class Demographics {
    private String user;
    private Map<String, String> name;
    private List<DemographicsValue> location;
    private List<DemographicsValue> image;
    private List<DemographicsValue> email;
    private DemographicsValue gender;
    private List<DemographicsValue> language;
    private List<DemographicsValue> work;
    private List<DemographicsValue> industry;
    private List<DemographicsValue> weight;
    private List<DemographicsValue> height;
    private DemographicsValue dateOfBirth;
    private List<DemographicsValue> country;
    private List<DemographicsValue> device;
    private List<DemographicsValue> website;
    private List<DemographicsValue> bio;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Map<String, String> getName() {
        return name;
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }

    public List<DemographicsValue> getLocation() {
        return location;
    }

    public void setLocation(List<DemographicsValue> location) {
        this.location = location;
    }

    public List<DemographicsValue> getImage() {
        return image;
    }

    public void setImage(List<DemographicsValue> image) {
        this.image = image;
    }

    public List<DemographicsValue> getEmail() {
        return email;
    }

    public void setEmail(List<DemographicsValue> email) {
        this.email = email;
    }

    public DemographicsValue getGender() {
        return gender;
    }

    public void setGender(DemographicsValue gender) {
        this.gender = gender;
    }

    public List<DemographicsValue> getLanguage() {
        return language;
    }

    public void setLanguage(List<DemographicsValue> language) {
        this.language = language;
    }

    public List<DemographicsValue> getWork() {
        return work;
    }

    public void setWork(List<DemographicsValue> work) {
        this.work = work;
    }

    public List<DemographicsValue> getIndustry() {
        return industry;
    }

    public void setIndustry(List<DemographicsValue> industry) {
        this.industry = industry;
    }

    public List<DemographicsValue> getWeight() {
        return weight;
    }

    public void setWeight(List<DemographicsValue> weight) {
        this.weight = weight;
    }

    public List<DemographicsValue> getHeight() {
        return height;
    }

    public void setHeight(List<DemographicsValue> height) {
        this.height = height;
    }

    public DemographicsValue getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(DemographicsValue dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<DemographicsValue> getCountry() {
        return country;
    }

    public void setCountry(List<DemographicsValue> country) {
        this.country = country;
    }

    public List<DemographicsValue> getDevice() {
        return device;
    }

    public void setDevice(List<DemographicsValue> device) {
        this.device = device;
    }

    public List<DemographicsValue> getWebsite() {
        return website;
    }

    public void setWebsite(List<DemographicsValue> website) {
        this.website = website;
    }

    public List<DemographicsValue> getBio() {
        return bio;
    }

    public void setBio(List<DemographicsValue> bio) {
        this.bio = bio;
    }
}
