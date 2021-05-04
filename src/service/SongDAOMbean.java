package service;

import java.util.List;

import model.SongDTO;



public interface SongDAOMbean {
	public void add(SongDTO dto);
	public void delete(int id);
	public void update(SongDTO dto);
	public List<SongDTO> findAll();
	public SongDTO findOne(String id);
	
}
