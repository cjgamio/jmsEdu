package edu.cibertec.jaad.jms;

import java.io.Serializable;

public class Profesor implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nombreM;
	private String dni;
	
	public Profesor(){
		
	}
	
	public Profesor(String nombreM, String dni) {
		super();
		this.nombreM = nombreM;
		this.dni = dni;
	}

	public String getNombreM() {
		return nombreM;
	}
	public void setNombreM(String nombreM) {
		this.nombreM = nombreM;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "Profesor [nombreM=" + nombreM + ", dni=" + dni + "]";
	}

}
