package junrui.model;

public class Entity {
	private String entityId;
	private String entityAttribute;
	private String attributeValue;
	
	private String personEntityId;
	private String personAttrValueType;
	
	private String venueEntityId;
	private String edgeEntityId;
	
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(int entityId) {
		this.entityId = "P"+entityId;
	}
	public String getEntityAttribute() {
		return entityAttribute;
	}
	public void setEntityAttribute(String entityAttribute) {
		this.entityAttribute = entityAttribute;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}	
	public String getPersonEntityId() {
		return personEntityId;
	}
	public void setPersonEntityId(int personEntityId) {
		this.personEntityId = "A"+personEntityId;
	}
	public String getPersonAttrValueType() {
		return personAttrValueType;
	}
	public void setPersonAttrValueType(String personAttrValueType) {
		if(personAttrValueType.equals("0")){
			this.personAttrValueType = "Author";
		}
		else if(personAttrValueType.equals("1")){
			this.personAttrValueType = "Editor";
		}
		else{
			this.personAttrValueType = personAttrValueType;
		}
	}
	public String getVenueEntityId() {
		return venueEntityId;
	}
	public void setVenueEntityId(int venueEntityId) {
		this.venueEntityId = "V"+venueEntityId;
	}
	public String getEdgeEntityId() {
		return edgeEntityId;
	}
	public void setEdgeEntityId(int edgeEntityId) {
		this.edgeEntityId = "E"+edgeEntityId;
	}
	
	

}
