package br.com.projetofinal.DTO;

import java.util.Date;

public class NormalTaskDTO implements ITaskDTO {

	private int id;
    private String title;
    private String status;
    private String type;
    private Date created_At;

    public NormalTaskDTO(String title, String status, Date created_At) {
        this.title = title;
        this.status = status;
        this.type = "normal";
        this.created_At = created_At;
    }
    
    public NormalTaskDTO(String title, String status) {
    	this.title = title;
    	this.status = status;
    	this.type = "normal";
    }

    public NormalTaskDTO(int id, String title, String status, String type, Date created_At) {
		this.id = id;
		this.title = title;
		this.status = status;
		this.type = type;
		this.created_At = created_At;
	}

	@Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Date getCreatedAt() {
        return created_At;
    }

	@Override
	public int getId() {
		return id;
	}
}
