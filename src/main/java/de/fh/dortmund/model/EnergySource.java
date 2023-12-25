package main.java.de.fh.dortmund.model;

public class EnergySource {

	private String id;
	private String sourceName;
	private String capacity;

	public String getId() {
		return id;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

}
