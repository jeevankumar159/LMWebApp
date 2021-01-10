package Model;
import java.time.Instant;

public class Image {
	private String id;
	private String owner;
	
	public Image(String ownername){
		this.owner = ownername;
		long now = Instant.now().toEpochMilli();
		this.id = ownername + String.valueOf(now);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	
}
