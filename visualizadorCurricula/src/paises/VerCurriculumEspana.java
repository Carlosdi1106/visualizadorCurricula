package paises;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.xquery.*;

import com.saxonica.xqj.*;

public class VerCurriculumEspana {

	public static void main(String[] args) throws XQException
	  {
		String fileName = "output/curriculumVictorVisualizado.html";
		
		// create the data source and expression to process
		
	    XQDataSource xqs = new SaxonXQDataSource();
	    XQConnection conn = xqs.getConnection();
	    XQExpression xqe = conn.createExpression();
	    
	    String xqueryString = "declare namespace es = \"http://www.erasmus-espana.org\";\r\n"
	    		+ "declare namespace era = \"http://www.erasmus.org\";\r\n"
	    		+ "\r\n"
	    		+ "let $estudiante := doc(\"input/victorGarcia.xml\")//era:Estudiante\r\n"
	    		+ "return \r\n"
	    		+ "    <html>\r\n"
	    		+ "        <h1>Curriculum de España</h1>\r\n"
	    		+ "        \r\n"
	    		+ "        <h2>Datos personales</h2> \r\n"
	    		+ "        <h4>Nombre: {concat($estudiante/era:NombreCompleto/era:Nombre/text(), \" \", \r\n"
	    		+ "        $estudiante/era:NombreCompleto/era:PrimerApellido/text(), \" \",\r\n"
	    		+ "        $estudiante/era:NombreCompleto/era:SegundoApellido/text())}</h4>\r\n"
	    		+ "        <h4>Fecha de nacimiento: {$estudiante/era:FechaNacimiento/text()}</h4>\r\n"
	    		+ "        <h4>Telefono: +{$estudiante//era:DatosContacto/era:PrefijoTelefono/text()}  \r\n"
	    		+ "        {$estudiante//era:DatosContacto/era:Telefono/text()}</h4>\r\n"
	    		+ "        <h4>Email: {$estudiante//era:DatosContacto/era:CorreoElectronico/text() }</h4>\r\n"
	    		+ "        <h4>Direccion:</h4>\r\n"
	    		+ "        <p> {$estudiante//era:DatosContacto/era:Direccion/era:NombreCalle/text()}\r\n"
	    		+ "        {$estudiante//era:DatosContacto/era:Direccion/era:NumeroPortal/text()},\r\n"
	    		+ "        {$estudiante//era:DatosContacto/era:Direccion/era:Piso/text()}. {$estudiante//era:DatosContacto/era:Direccion/era:Puerta/text()}\r\n"
	    		+ "        , {$estudiante//era:DatosContacto/era:Direccion/era:CodigoPostal/text()}\r\n"
	    		+ "        , {$estudiante//era:DatosContacto/era:Direccion/era:Ciudad/text()}\r\n"
	    		+ "        , {$estudiante//era:DatosContacto/era:Direccion/era:Pais/text()}</p>\r\n"
	    		+ "        \r\n"
	    		+ "        <h2>Informacion de la Universidad de origen</h2>\r\n"
	    		+ "        <h3>Nombre de la universidad:\r\n"
	    		+ "        {$estudiante/era:Universidad/era:NombreUni/text()}</h3>\r\n"
	    		+ "        <p>La universidad esta localizada en la ciudad de \r\n"
	    		+ "        {$estudiante/era:Universidad/era:Procedencia/era:Ciudad/text()},\r\n"
	    		+ "        en la provincia de {$estudiante/era:Universidad/era:Procedencia/es:Provincia/text()}, \r\n"
	    		+ "        y en en la comunidad autónoma de {$estudiante/era:Universidad/era:Procedencia/es:ComunidadAutonoma/text()} \r\n"
	    		+ "        y en {$estudiante/era:Universidad/era:Procedencia/era:Pais/text()}. Su codigo postal es \r\n"
	    		+ "        {$estudiante/era:Universidad/era:Procedencia/era:CodigoPostal/text()}.</p>\r\n"
	    		+ "        <p>El coordinador de esta universidad es \r\n"
	    		+ "        {concat($estudiante/era:Universidad/es:Coordinador/es:Nombre/text(),\r\n"
	    		+ "        \" \", $estudiante/era:Universidad/es:Coordinador/es:Apellido/text())} \r\n"
	    		+ "        , su numero de telefono es {$estudiante/era:Universidad/es:Coordinador/es:Telefono/text()} \r\n"
	    		+ "        y su email {$estudiante/era:Universidad/es:Coordinador/es:Email/text()}</p>\r\n"
	    		+ "        \r\n"
	    		+ "        <h2>Informacion de sus estudios actuales</h2>\r\n"
	    		+ "        <p>Actualmente este alumno cursa \r\n"
	    		+ "        {string($estudiante/era:Universidad/era:Grados/@Curso)} del {if ($estudiante//era:Grado) then \"grado\" \r\n"
	    		+ "                  else (if ($estudiante//era:Master) then \"master\"\r\n"
	    		+ "                  else \"Doctorado\")\r\n"
	    		+ "                 } de\r\n"
	    		+ "        {$estudiante/era:Universidad/era:Grados/era:Grado/text()}\r\n"
	    		+ "        en la facultad de {$estudiante//era:Facultad/text()} del campus de\r\n"
	    		+ "        {$estudiante//era:Campus/text()}.       \r\n"
	    		+ "        </p>\r\n"
	    		+ "        <h3>Idiomas</h3>\r\n"
	    		+ "        {for $certificado in doc(\"input/victorGarcia.xml\")//era:Certificado\r\n"
	    		+ "        return\r\n"
	    		+ "            <p>Tiene un certificado de {string($certificado/@Idioma)}\r\n"
	    		+ "            con un nivel de {$certificado/text()}</p>\r\n"
	    		+ "        }\r\n"
	    		+ "        \r\n"
	    		+ "        <h2>Asignaturas que va a cursar en su estancia de erasmus</h2>\r\n"
	    		+ "        <p>El alumno va a ir de erasmus el \r\n"
	    		+ "        {if($estudiante//era:CuatrimestreDeEstancia/text()='Anual')\r\n"
	    		+ "        then \"el año completo\"\r\n"
	    		+ "        else concat($estudiante/era:CuatrimestreDeEstancia/text(),\" cuatrimestre\")}.</p>\r\n"
	    		+ "        <p>Sus asignaturas son:</p>\r\n"
	    		+ "        {for $asignatura in doc(\"input/victorGarcia.xml\")//era:Asignatura \r\n"
	    		+ "        return\r\n"
	    		+ "            <p>Va a cursar la asignatura {string($asignatura/@NombreAsignatura)} que\r\n"
	    		+ "            {if($asignatura/era:Cuatrimestre/text()='Anual')\r\n"
	    		+ "        then \"es anual\"\r\n"
	    		+ "        else concat(\"se imparte el cuatrimestre \", $asignatura/era:Cuatrimestre/text())}. \r\n"
	    		+ "        Vale {string($asignatura/era:Creditos)} creditos.</p>\r\n"
	    		+ "        }\r\n"
	    		+ "        <h2>Carta de presentacion</h2>\r\n"
	    		+ "        <p>{$estudiante//era:CartaPresentacion/text()}.</p>\r\n"
	    		+ "        \r\n"
	    		+ "        <h2>Becas otorgadas</h2>\r\n"
	    		+ "        {for $beca in doc(\"input/victorGarcia.xml\")//es:Beca\r\n"
	    		+ "        return\r\n"
	    		+ "            <p>{$beca/es:InstitucionEmisora/text()} le ha \r\n"
	    		+ "            dado la beca {$beca/es:NombreBeca/text()} por\r\n"
	    		+ "            un valor de {$beca/es:Importe/text()}€. Esta\r\n"
	    		+ "            beca es {string($beca/@TipoBeca)}.</p>\r\n"
	    		+ "        }\r\n"
	    		+ "        \r\n"
	    		+ "    </html>";
	    
	    XQResultSequence rs = xqe.executeQuery(xqueryString);
	 
	    try {
	        
	        BufferedWriter bufferedWriter =
	            new BufferedWriter(new FileWriter(fileName));
	        
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

