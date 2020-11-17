package org.qtx.web.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.qtx.dominio.Articulo;
import org.qtx.persistencia.GestorDatos;
import org.springframework.beans.factory.annotation.Autowired;


@Path("articulos")
public class ServicioArticuloRest {
	
	@Autowired
	private GestorDatos gd;

	// GET ./webapi/articulos?clave=C-100P
    @GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getArticulo(
			@DefaultValue("INDEFINIDO") 
			@QueryParam("clave") String clave) {
    	Articulo artI = gd.leerArticulo(clave);
    	if(artI != null)
    		return artI.toString();
    	return "No existe un artículo con clave <" + clave + ">";
	}
    
    @Path("{cveArticulo}")
    @GET
    @Produces({MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Articulo getArticuloPath(
			@DefaultValue("INDEFINIDO")
			@PathParam("cveArticulo") String clave) {
     	if(clave.equals("INDEFINIDO"))
    		return null;
    	return gd.leerArticulo(clave);
	}
    
    //POST ./webapi/articulos
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String insertarArticulo(
			@DefaultValue("nulo")
			@FormParam("clave")
			String clave, 
			@FormParam("nombre")
			@DefaultValue("")
			String nombre, 
			@DefaultValue("")
			@FormParam("descripcion")
			String descripcion, 
			@DefaultValue("0.0")
			@FormParam("costo")
			BigDecimal costo) {
		if(clave.equals("nulo"))
			return "Clave no especificada";
		if(costo.floatValue() == 0f)
			return "Costo no especificado o igual a Cero";

		Articulo artI = new Articulo(clave, nombre, descripcion, costo);
		if(gd.insertarArticulo(artI) == true)
			return artI.toString();
		else
			return "Articulo duplicado";
	}

    //POST ./webapi/articulos
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String insertarArticulo(Articulo artI){
		if(gd.insertarArticulo(artI) == true)
			return artI.toString();
		else
			return "Articulo duplicado";
	}
    
    @DELETE
    @Path("{cveArt}")
    @Produces(MediaType.TEXT_PLAIN)
    public String eliminarArticulo(
    			@PathParam("cveArt") 
    			String cveArt) {
    	
    	Articulo artI = gd.eliminarArticulo(cveArt);
    	if (artI != null)
    		return artI.toString();
    	else
    		return "No hay una artículo con clave " + cveArt;
    }
       
    @Path("inventario")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int getTotalArticulos() {
    	return gd.getTotArticulos();
    }
    
	// GET ./webapi/articulos
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Articulo> getTodos(){
    	return this.gd.getTodos();
    }
	// GET ./webapi/articulos/todos_lento
    // Sirve para probar llamados asíncronos
    @Path("todos_lento")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Articulo> getTodosLento(){
    	try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return this.gd.getTodos();
    }
}
