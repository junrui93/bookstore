package junrui.model;

public class Entity {
	private String publEntityId;
	private String publEntityAttribute;
	private String publAttributeValue;
	
	public String getPublEntityId() {
		return publEntityId;
	}
	public void setPublEntityId(int publEntityId) {
		this.publEntityId = "P"+publEntityId;
	}
	public String getPublEntityAttribute() {
		return publEntityAttribute;
	}
	public void setPublEntityAttribute(String publEntityAttribute) {
		this.publEntityAttribute = publEntityAttribute;
	}
	public String getPublAttributeValue() {
		return publAttributeValue;
	}
	public void setPublAttributeValue(String publAttributeValue) {
		this.publAttributeValue = publAttributeValue;
	}
	
	public String toString() {
		return this.getPublEntityId();
	}
	
	
	

}
