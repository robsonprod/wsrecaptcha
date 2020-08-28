package br.com.hapvida.dto;

import java.io.Serializable;
import java.util.Date;

public class RespostaDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private boolean success;
	
	private Date challenge_ts;
	
	private String hostname;
	
	private Double score;
	
	private String action;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Date getChallenge_ts() {
		return challenge_ts;
	}

	public void setChallenge_ts(Date challenge_ts) {
		this.challenge_ts = challenge_ts;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
