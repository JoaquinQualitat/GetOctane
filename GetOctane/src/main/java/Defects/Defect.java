package Defects;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Defect {
	public String type;
	public boolean blocked;
	public Date closed_on;
	public String description;
	public String name;
	public String id;
	public Date creation_time;
	public Date fixed_on;
}