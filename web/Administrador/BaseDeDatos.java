package Administrador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase que se encargará de hacer las conexiones necesarias entre el programa y
 * mySQL.
 * @author Joseph Vega Vargas
 * @author Lucía Solís Ceciliano
 * @author Miller Ruíz Urbina
 */
public class BaseDeDatos {
    //Variables Globales
    private String bd = "redsocial";    //Contiene el nombre de la BD
    private String login = "root";      //Contiene el usuario de la BD
    private String password = "root";   //Contienen la contraseña de la BD
    private String url = "jdbc:mysql://localhost/"+bd; //Crea la dirección
  
    private Connection Conexion = null; //Contiene los datos de conexión
    private Statement Estado; //Mantiene el estado de la conexión
    
    /**
     * Método constructor de la Base de Datos.
     */
    public void ConectarBase() {
        //Prueba que se realice bien la conexión
        try {
            //Inicia y asigna los datos de la conexión
            Class.forName("com.mysql.jdbc.Driver");
            Conexion = DriverManager.getConnection(url,login,password);
            Estado = Conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            
            //Revisa que la conexión funcione
            if(Conexion != null) 
                System.out.println("Conexion a base de datos "+url+" ... "
                        + "Correcta ");
        } catch(SQLException | ClassNotFoundException ex){
            System.out.println(ex);
        }
    }
    
    /**
     * Método que permite ingresar datos en la Base de Datos.
     * @param Instruccion = Nombres de todos los campos de la tabla
     * @param Datos = Datos que se van a a almacenar en la tabla
     * (Formato: 'CAPMO1,CAMPO2')
     * @return Boolean si se asignaron los datos o no.
     */
    public boolean IngresarDatos(String Instruccion, String Datos) {
        //Prueba que se pueden almacenar los datos
        try{
            ConectarBase(); //Llama al método de conectar base
            
            //Asigna y envia los datos a la BD
            Instruccion = Instruccion.replace("xyz",Datos);
            Estado.executeUpdate(Instruccion);
        } catch(Exception e) {
            // Si hay error retorna false
            System.out.println(e);
            return false;
        } finally {
            //Prueba para cerrar la conexión
            try {
                this.Conexion.close();
            } catch(Exception e) {}
        }
        return true;
    }
    
    /**
     * Método que permite consultar datos en la Base de Datos en la tabla arista.
     * @param Instruccion = Nombres de todos los campos de la tabla
     * @param Filtro = Contiene la instruccion si se desea filtrar datos 
     * (where CAMPOTABLA = 'DATO')
     * @param Tamaño = Contiene los datos que va a guardar.
     * @param Contador = Contiene el contador de los datos en la BD.
     * @return devuelve la lista de los datos consultados
     */
    public Object[] ConsultarDatos(String Instruccion, String Filtro, 
            int Tamaño, int Contador){
        
        //Variables Locales
        Object[] Arreglo;
        
        //Prueba que se pueden consultar los datos
        try {
            ConectarBase();  //Llama al método de conectar base
            
            //Asigna y envia los datos a la BD
            Instruccion = Instruccion.replace("xyz",Filtro);
            ResultSet rs = Estado.executeQuery(Instruccion); 
            rs.first(); //Coloca en la primera posición de los datos encontrados
            
            Arreglo = new Object[Contador]; //Inicializa el arreglo
            
            //Valida que la busqueda no este vacía
            if(rs != null){
                //Ciclo que va a ir asignando los datos en el arreglo
                for(int i=0; i<Contador; i++){
                    //Llama al proceso que asigna datos.
                    Arreglo[i] = AsignaEnLista(Tamaño,rs);
                    rs.next(); //Continua la siguiente linea
                }
            }        
        }
        catch (Exception e)
        {   System.out.println(e);  
            return null;
        } finally {
            try {   
                Conexion.close(); 
            } catch (Exception e) {   }
        }
        return Arreglo;
    }
    
    /**
     * Método que asigna los datos de la BD  a un arreglo.
     * @param Tamaño = Contiene los datos que va a guardar.
     * @param rs  Contiene los datos encontrados
     * @return el arreglo.
     */
    public Object[] AsignaEnLista(int Tamaño, ResultSet rs){
        //Prueba que se asignen los datos en el arreglo
        try{
            Object[] Arreglo = new Object[Tamaño];
            
            //Valida si es la BD de Aristas o de Usuarios
            if(Tamaño == 3){
                Arreglo[0] = rs.getString("IDInicio");
                Arreglo[1] = rs.getString("IDFin");
                Arreglo[2] = rs.getString("IDEtiqueta");
            } else{
                //Campos de tipo String
                Arreglo[0] = rs.getString("ID");
                Arreglo[1] = rs.getString("Nombre");
                Arreglo[2] = rs.getString("Apellido1");
                Arreglo[3] = rs.getString("Apellido2");
                Arreglo[5] = rs.getString("Carrera");
                Arreglo[7] = rs.getString("Direccion");
                Arreglo[8] = rs.getString("Telefono");
                Arreglo[9] = rs.getString("Email");
                //Campos de tipo int
                Arreglo[4] = "" + rs.getInt("Edad");
                Arreglo[6] = "" + rs.getInt("Ano");
                //Campos de tipo blob o imagen
                //Arreglo[10] += rs.getBlob("Foto");
            }
            return Arreglo;
        } catch (SQLException e){ 
            return null;
        }
    }
    
    /**
     * Método que permite ingresar datos en la Base de Datos.
     * @param Instruccion = Nombres de todos los campos de la tabla
     * @param Datos = Datos que se van a a almacenar en la tabla
     * @param Tamaño = Contiene los datos que va a guardar.
     * @return Boolean si se editaron los datos o no.
     */
    public boolean modificacliente(String Instruccion,Object[] Datos,int Tamaño){
        //Prueba que se pueden consultar los datos
        try {
            ConectarBase(); //Llama al método de conectar base
            
            //Ciclo que va remplazando los datos de la istrucción
            for (int i=0; i<Tamaño-1; i++)
                Instruccion = Instruccion.replace("xyz"+i, 
                        String.valueOf(Datos[i]));
            Instruccion = Instruccion.replace("xyz",String.valueOf(Datos[10]));
            
             //Asigna y envia los datos a la BD
            Estado.executeUpdate(Instruccion);
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        } finally{
            try{
                Conexion.close(); 
            } catch(Exception e){ }
        }
        return true;
    }
    
    /**
     * Método que permite eliminar datos en la Base de Datos.
     * @param Instruccion = Nombres de todos los campos de la tabla
     * @param Filtro = Contiene la instruccion si se desea filtrar datos 
     * (where CAMPOTABLA = 'DATO')
     * @return Boolean si se eliminaron los datos o no.
     */
    public boolean EliminarDatos(String Instruccion, String Filtro) {      
        //Prueba que se pueden eliminar los datos
        try{
            ConectarBase(); //Llama al método de conectar base
            
            //Asigna y envia los datos a la BD
            Instruccion = Instruccion.replace("xyz", Filtro);
            Estado.execute(Instruccion);
            
        } catch (Exception e){
            System.out.println(e);
            return false;
        } finally {
            try
            {   Conexion.close(); }
            catch (Exception e)
            {   }
        }
        return true;
    }     
}
