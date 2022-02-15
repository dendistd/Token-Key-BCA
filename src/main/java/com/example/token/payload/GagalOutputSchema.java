package com.example.token.payload;

public class GagalOutputSchema {
	private String reason;

	public GagalOutputSchema() {
		super();
	}

	public GagalOutputSchema(String reason) {
		super();
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "GagalOutputSchema [reason=" + reason + "]";
	}

}
