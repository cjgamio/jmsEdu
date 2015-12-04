package edu.cibertec.jaad.jms;

import java.io.Serializable;

public class Oferta implements Serializable{
	private static final long serialVersionUID = 1L;
	private String description;
	private Double monto;
	private String producto;
	
	public Oferta() {
		super();
	}
	public Oferta(String description, Double monto, String producto) {
		super();
		this.description = description;
		this.monto = monto;
		this.producto = producto;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	@Override
	public String toString() {
		return "Oferta [description=" + description + ", monto=" + monto
				+ ", producto=" + producto + "]";
	}
	
	
}
