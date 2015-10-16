package com.currconv.rest.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyLayerResponse {


	private boolean success;
	
	private String terms;
	
	private String privacy;

	private String timestamp;
	
	private String source;
	
	private Map<String, String> quotes;
	

	public boolean getSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getTerms() {
		return terms;
	}


	public void setTerms(String terms) {
		this.terms = terms;
	}


	public String getPrivacy() {
		return privacy;
	}


	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}


	public String getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public Map<String, String> getQuotes() {
		return quotes;
	}


	public void setQuotes(Map<String, String> quotes) {
		this.quotes = quotes;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(CurrencyLayerResponse.class.getSimpleName())
		        .append(", success = ").append(success)
				.append(", terms = ").append(terms)
				.append(", privacy = ").append(privacy)
				.append(", timestamp = ").append(timestamp)
				.append(", source = ").append(source)
				.append(", quotes = ").append(quotes)
				.append("]");

		return builder.toString();
	}

}
