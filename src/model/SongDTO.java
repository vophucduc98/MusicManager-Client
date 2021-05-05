package model;

import java.io.Serializable;

// Song Data Tranfer Object
public class SongDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String artist;
	private int duration;
	public SongDTO() {
		super();
	}
	
	public SongDTO(int id, String name, String artist, int duration) {
		super();
		this.id = id;
		this.name = name;
		this.artist = artist;
		this.duration = duration;
	}

	public SongDTO(String name, String artist, int duration) {
		super();
		this.name = name;
		this.artist = artist;
		this.duration = duration;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
}
