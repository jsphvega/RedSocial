package Administrador;

/**
 * Clase que contiene todos los parametros del proyecto
 * @author Joseph Vega Vargas
 * @author Lucía Solís Ceciliano
 * @author Miller Ruíz Urbina
 */
public class Parametros {
    
    //Variables que controlan datos hay en la BD
    static public int TamañoUsuarios = 0;
    static public int TamañoAristas = 0;
    
    //Variables que controlan los campos de la BD
    static public int CamposUsuarios = 11;
    static public int CamposAristas = 3;
    
    //Variables que contienen los insertar de los campos de la BD
    static public String InsertarBDUsuarios = "INSERT INTO usuarios (ID,Nombre,"
            + "Apellido1,Apellido2,Edad,Carrera,Año,Direccion,Telefono,Email,"
            + "Foto) VALUES(xyz)";
    static public String InsertarBDAristas = "INSERT INTO aristas (IDInicio,"
            + "IDFin,Etiqueta) VALUES(xyz)";
    
    //Variables que contienen los consultar de los campos de la BD
    static public String ConsultarBDUsuarios = "SELECT * FROM usuariosxyz";
    static public String ConsultarBDAristas = "SELECT * FROM aristasxyz";
    
    //Variables que contienen los modificar de los campos de la BD
    static public String ModificarBDUsuarios = "UPDATE usuarios SET "
            + "Nombre='xyz1',Apellido1='xyz2',Apellido2='xyz3',Edad=xyz4,"
            + "Carrera='xyz5',Ano=xyz6,Direccion='xyz7',Telefono='xyz8',"
            + "Email='xyz9',Foto=xyz WHERE ID = 'xyz0'";
}
