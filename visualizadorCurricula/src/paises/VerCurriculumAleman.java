package paises;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQResultSequence;

import com.saxonica.xqj.SaxonXQDataSource;

public class VerCurriculumAleman {
	public static void main(String[] args) throws XQException
	  {
		String fileName = "output/curriculumAleman.html";
		
		// create the data source and expression to process
		
	    XQDataSource xqs = new SaxonXQDataSource();
	    XQConnection conn = xqs.getConnection();
	    XQExpression xqe = conn.createExpression();
	    
	    // the query to process
	    String xqueryString = "declare namespace al = \"http://www.erasmus-alemania.org\";"
	    		+"declare namespace ue = \"http://www.erasmus.org\";"
	    		+""
	    		+"let $curriculum := (doc('input/lukasKrause.xml')//ue:Estudiante)"
	    		+"return"
	    		+"    <html><h1>Curriculum de Estudiante Aleman</h1>"
	    		+"        <h2>Informacion de Estudiante</h2>"
	    		+"        <p>Nombre: {normalize-space(concat($curriculum/ue:NombreCompleto/ue:Nombre, ' ', $curriculum/ue:NombreCompleto/ue:PrimerApellido))}</p>"
	    		+"        <p>Fecha de Nacimiento: {data($curriculum/ue:FechaNacimiento)}</p>"
	    		+"        <h3>Datos de Contacto</h3>"
	    		+"        <p>Numero de Telefono: {normalize-space(concat('+', $curriculum/ue:DatosContacto/ue:PrefijoTelefono, $curriculum/ue:DatosContacto/ue:Telefono))}</p>"
	    		+"        <p>Correo Electronico: {$curriculum/ue:DatosContacto/ue:CorreoElectronico}</p>"
	    		+"        <br/>"
	    		+"        <table border='1'>"
	    		+"            <tr>"
	    		+"                <th>Direcci�n</th>"
	    		+"                <td>{normalize-space(concat($curriculum/ue:DatosContacto/ue:Direccion/ue:NombreCalle, ' ', $curriculum/ue:DatosContacto/ue:Direccion/ue:NumeroPortal,' ',$curriculum/ue:DatosContacto/ue:Direccion/ue:Piso,$curriculum/ue:DatosContacto/ue:Direccion/ue:Puerta))}</td>"
	    		+"            </tr>"
	    		+"            <tr>"
	    		+"                <th>Ciudad</th>"
	    		+"                <td>{$curriculum/ue:DatosContacto/ue:Direccion/ue:Ciudad}</td>"
	    		+"            </tr>"
	    		+"            <tr>"
	    		+"                <th>Pa�s</th>"
	    		+"                <td>{$curriculum/ue:DatosContacto/ue:Direccion/ue:Pais}</td>"
	    		+"            </tr>"
	    		+"            <tr>"
	    		+"                <th>C�digo Postal</th>"
	    		+"                <td>{$curriculum/ue:DatosContacto/ue:Direccion/ue:CodigoPostal}</td>"
	    		+"            </tr>"
	    		+"        </table>"
	    		+"        <h3>Mi Universidad</h3>"
	    		+"        <p>Nombre: {$curriculum/ue:Universidad/ue:NombreUni}</p>"
	    		+"        <br/>"
	    		+"        <table border='1'>"
	    		+"            <tr>"
	    		+"                <th>Ciudad</th>"
	    		+"                <td>{$curriculum/ue:Universidad/ue:Procedencia/ue:Ciudad}</td>"
	    		+"            </tr>"
	    		+"            <tr>"
	    		+"                <th>Pa�s</th>"
	    		+"                <td>{$curriculum/ue:Universidad/ue:Procedencia/ue:Pais}</td>"
	    		+"            </tr>"
	    		+"            <tr>"
	    		+"                <th>C�digo Postal</th>"
	    		+"                <td>{$curriculum/ue:Universidad/ue:Procedencia/ue:CodigoPostal}</td>"
	    		+"            </tr>"
	    		+"        </table>"
	    		+"        <h3>Mis Estudios</h3>"
	    		+"        {"
	    		+"            if ($curriculum//ue:Grado) then"
	    		+"                <p>Grado: {$curriculum//ue:Grado}</p>"
	    		+"            else"
	    		+"                (if ($curriculum//ue:Master) then"
	    		+"                    <p>Master: {$curriculum//ue:Master}</p>"
	    		+"                else"
	    		+"                    <p>Doctorado: {$curriculum//ue:Doctorado}</p>)"
	    		+"        }"
	    		+"        <p>Curso: {data($curriculum//ue:Grados/@Curso)}</p>"
	    		+"        <p>Facultad: {$curriculum//ue:Facultad}</p>"
	    		+"        <p>Campus: {$curriculum//ue:Campus}</p>"
	    		+"        <h3>Donde quiere ir de Erasmus</h3>"
	    		+"        <p>Universidad: {$curriculum//ue:UniversidadDestino}</p>"
	    		+"        <p>Pais: {$curriculum//ue:PaisDestino}</p>"
	    		+"        <p>Estancia: {if ($curriculum[ue:CuatrimestreDeEstancia='Anual']) then $curriculum//ue:CuatrimestreDeEstancia else normalize-space(concat($curriculum//ue:CuatrimestreDeEstancia,' Semestre'))}</p>"
	    		+"        <h2>Conocimiento y Nivel de diferentes Idiomas</h2>"
	    		+"        <table"
	    		+"            border='1'><tr><th>Nombre</th><th>Nivel</th></tr>"
	    		+"            {"
	    		+"                for $x in $curriculum/ue:NivelIdiomas/ue:Certificado"
	    		+"                return"
	    		+"                    <tr><td>{data($x/@Idioma)}</td><td>{data($x)}</td></tr>"
	    		+"            }"
	    		+"        </table>"
	    		+"        <h2>Experiencia Laboral</h2>"
	    		+"        <table"
	    		+"            border='1'><tr><th>Nombre Empresa</th><th>Puesto Ocupado</th><th>Horas Trabajadas</th></tr>"
	    		+"            {"
	    		+"                for $x in $curriculum/al:ExperienciasProfesionales/al:ExperienciaProfesional"
	    		+"                return"
	    		+"                    <tr><td>{data($x/al:NombreEmpresa)}</td><td>{data($x/al:Puesto)}</td><td>{data($x/al:HorasTrabajadas)}</td></tr>"
	    		+"            }"
	    		+"        </table>"
	    		+"        <h2>Asignaturas Cursadas</h2>"
	    		+"        <table"
	    		+"            border='1'><tr><th>Nombre Asignatura</th><th>Nota Media</th></tr>"
	    		+"            {"
	    		+"                for $x in $curriculum//ue:Grados/al:Asignaturas/al:Asignatura"
	    		+"                return"
	    		+"                    <tr><td>{data($x/al:Nombre)}</td><td>{data($x/al:NotaMedia)}</td></tr>"
	    		+"            }"
	    		+"        </table>"
	    		+"        <h2>Asignaturas que se esperen cursar en el Erasmus</h2>"
	    		+"        <table"
	    		+"            border='1'><tr><th>Nombre Asignatura</th><th>Cuatrimestre</th><th>Creditos</th></tr>"
	    		+"            {"
	    		+"                for $x in $curriculum/ue:Asignaturas/ue:Asignatura"
	    		+"                return"
	    		+"                    <tr><td>{data($x/@NombreAsignatura)}</td><td>{data($x/ue:Cuatrimestre)}</td><td>{data($x/ue:Creditos)}</td></tr>"
	    		+"            }"
	    		+"        </table>"
	    		+"    </html>  ";
	    
	    XQResultSequence rs = xqe.executeQuery(xqueryString);
	 
	    try {
	        
	        BufferedWriter bufferedWriter =
	            new BufferedWriter(new FileWriter(fileName));
	        
	        // write the result of the query in the file
	        while(rs.next()) {
	        	String aline = rs.getItemAsString(null);
	        	bufferedWriter.write(aline);
	        	bufferedWriter.newLine();
	        }
	        
	        bufferedWriter.close();
	    }
	    catch(IOException ex) {
	        System.out.println(
	            "Error writing to file '"
	            + fileName + "'");
	    }
	    
	    System.out.println("Finished!!");
	    conn.close();
	  }
}
