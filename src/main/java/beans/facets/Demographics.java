
package beans.facets;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Define Demographics values from Myrror facets
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "location",
    "image",
    "email",
    "gender",
    "language",
    "work",
    "industry",
    "height",
    "weight",
    "dateOfBirth",
    "country",
    "device",
    "website",
    "bio"
})
public class Demographics {

    @JsonProperty("name")
    private Name name;
    @JsonProperty("location")
    private List<Location> location = null;
    @JsonProperty("image")
    private List<Image> image = null;
    @JsonProperty("email")
    private List<Email> email = null;
    @JsonProperty("gender")
    private Gender gender;
    @JsonProperty("language")
    private List<Language> language = null;
    @JsonProperty("work")
    private List<Object> work = null;
    @JsonProperty("industry")
    private List<Industry> industry = null;
    @JsonProperty("height")
    private List<Object> height = null;
    @JsonProperty("weight")
    private List<Object> weight = null;
    @JsonProperty("dateOfBirth")
    private DateOfBirth dateOfBirth;
    @JsonProperty("country")
    private List<Object> country = null;
    @JsonProperty("device")
    private List<Object> device = null;
    @JsonProperty("website")
    private List<Object> website = null;
    @JsonProperty("bio")
    private List<Bio> bio = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public Name getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(Name name) {
        this.name = name;
    }

    @JsonProperty("location")
    public List<Location> getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(List<Location> location) {
        this.location = location;
    }

    @JsonProperty("image")
    public List<Image> getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(List<Image> image) {
        this.image = image;
    }

    @JsonProperty("email")
    public List<Email> getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(List<Email> email) {
        this.email = email;
    }

    @JsonProperty("gender")
    public Gender getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @JsonProperty("language")
    public List<Language> getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(List<Language> language) {
        this.language = language;
    }

    @JsonProperty("work")
    public List<Object> getWork() {
        return work;
    }

    @JsonProperty("work")
    public void setWork(List<Object> work) {
        this.work = work;
    }

    @JsonProperty("industry")
    public List<Industry> getIndustry() {
        return industry;
    }

    @JsonProperty("industry")
    public void setIndustry(List<Industry> industry) {
        this.industry = industry;
    }

    @JsonProperty("height")
    public List<Object> getHeight() {
        return height;
    }

    @JsonProperty("height")
    public void setHeight(List<Object> height) {
        this.height = height;
    }

    @JsonProperty("weight")
    public List<Object> getWeight() {
        return weight;
    }

    @JsonProperty("weight")
    public void setWeight(List<Object> weight) {
        this.weight = weight;
    }

    @JsonProperty("dateOfBirth")
    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    @JsonProperty("dateOfBirth")
    public void setDateOfBirth(DateOfBirth dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @JsonProperty("country")
    public List<Object> getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(List<Object> country) {
        this.country = country;
    }

    @JsonProperty("device")
    public List<Object> getDevice() {
        return device;
    }

    @JsonProperty("device")
    public void setDevice(List<Object> device) {
        this.device = device;
    }

    @JsonProperty("website")
    public List<Object> getWebsite() {
        return website;
    }

    @JsonProperty("website")
    public void setWebsite(List<Object> website) {
        this.website = website;
    }

    @JsonProperty("bio")
    public List<Bio> getBio() {
        return bio;
    }

    @JsonProperty("bio")
    public void setBio(List<Bio> bio) {
        this.bio = bio;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
