package com.codesignet.pmp.notes.data_access_layer.pojos;

import com.google.gson.annotations.SerializedName;

public class Note {

	@SerializedName("note")
	private String note;

	@SerializedName("question")
	private Question question;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("id")
	private int id;

	private Integer databaseId;

	@SerializedName("type")
	private String type;

	private int isdeleted;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}

	public Integer getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(Integer databaseId) {
		this.databaseId = databaseId;
	}
}