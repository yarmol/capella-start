package me.jarad.capella.persistance;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.data.annotation.Id;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Model {
	
	@Id
	private long id;
	
	private String value;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
