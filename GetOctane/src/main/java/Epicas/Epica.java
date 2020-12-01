package Epicas;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Epica {
	public String type;
	public String id;
	public String name;

	public Epica() {
	}
}