package es.peluqueriaMarta.tfc.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa un servicio ofrecido por la peluquería.
 */
@Document(collection = "services")
public class Service {
	
	@Id
	private ObjectId id;
	private String description;
	private int duration;

	public Service() {
	}
	
	public Service(String description, int duration) {
		this.description = description;
		this.duration = duration;
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
}
