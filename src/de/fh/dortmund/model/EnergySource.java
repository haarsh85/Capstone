package de.fh.dortmund.model;

public class EnergySource {

	private String id;
	private String sourceName;
	private String capacity;

	public EnergySource(String id, String sourceName, String capacity) {

		this.id = id;
		this.sourceName = sourceName;
		this.capacity = capacity;
	}

	public String getId() {
		return id;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getCapacity() {
		return capacity;
	}

}
