package pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserPojoFull {

	@JsonProperty("last_name")
	private String lastName;
	@JsonProperty("first_name")
	private String firstName;
	private int id;
	private String avatar;
	private String email;

}
