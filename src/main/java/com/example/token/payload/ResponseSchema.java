package com.example.token.payload;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseSchema<T> {
	@JsonProperty("error_schema")
	private ErrorSchema errorSchema;
	@JsonProperty("output_schema")
	@JsonInclude(Include.NON_NULL)
	private T outputSchema;
	public ResponseSchema() {
		super();
	}
	public ResponseSchema(ErrorSchema errorSchema) {
		super();
		this.errorSchema = errorSchema;
	}
	public ResponseSchema(ErrorSchema errorSchema, T outputSchema) {
		super();
		this.errorSchema = errorSchema;
		this.outputSchema = outputSchema;
	}
	public ErrorSchema getErrorSchema() {
		return errorSchema;
	}
	public void setErrorSchema(ErrorSchema errorSchema) {
		this.errorSchema = errorSchema;
	}
	public T getOutputSchema() {
		return outputSchema;
	}
	public void setOutputSchema(T outputSchema) {
		this.outputSchema = outputSchema;
	}
	@Override
	public String toString() {
		return "ResponseSchema [errorSchema=" + errorSchema + ", outputSchema=" + outputSchema + "]";
	}
	

}
