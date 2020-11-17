package org.qtx.persistencia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.qtx.dominio.Articulo;
import org.springframework.stereotype.Repository;

@Repository
public class GestorDatos {
	private Map<String,Articulo> repositorioArticulos = cargarArticulos();

	private static  Map<String,Articulo>  cargarArticulos() {
		Map<String,Articulo>  articulosTest = new TreeMap<>();
		articulosTest.put("C-100P",new Articulo("C-100P","Cuaderno Profesional", "100 hojas, carta", 
				new BigDecimal("35.20"), new BigDecimal("18.90"), 20));
		articulosTest.put("C-200P",new Articulo("C-200P","Cuaderno Profesional", "200 hojas, carta", 
				new BigDecimal("69.10"), new BigDecimal("38.90"), 16));
		articulosTest.put("L-HB",new Articulo("L-HB","Lápiz", "Mediano calidad estándar", 
				new BigDecimal("8.95"), new BigDecimal("4.90"), 130));
		articulosTest.put("G-BI",new Articulo("G-BI","Goma de Borrar", "Bicolor para lápiz y tinta", 
				new BigDecimal("7.75"), new BigDecimal("3.50"), 57));
		
		return articulosTest;
	}
	public Articulo leerArticulo(String clave) {
		return this.repositorioArticulos.get(clave);
	}
	public int getTotArticulos() {
		return this.repositorioArticulos.size();
	}
	public boolean insertarArticulo(Articulo artI) {
		if (this.repositorioArticulos.get(artI.getClave()) == null ) { // No existe
			this.repositorioArticulos.put(artI.getClave(), artI);
			return true;
		}
		return false;
	}
	public Articulo eliminarArticulo(String cveArt) {
		return this.repositorioArticulos.remove(cveArt);
	}
	
	public List<Articulo> getTodos(){
		return new ArrayList<Articulo>(this.repositorioArticulos.values());
	}
}
