import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Model {
	private Connection con;
	private String logIn;
	private String contraseña;
	private String type;
	private ResultSet rs;

	/**
	 * @return retorna el ResultSet
	 */
	public ResultSet getRs() {
		return rs;
	}

	/**
	 * Mètode que arreplega una cadena de text i la transforma a MD5
	 * @param contra cadena de text a convertir
	 * @return la cadena de text ja convertida a MD5
	 */
	private String ConvertirContraseña(String contra) {
		try {

			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] bytes = contra.getBytes();

			byte[] hashBytes = md.digest(bytes);

			StringBuilder hexString = new StringBuilder();
			for (byte b : hashBytes) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error al generar el hash MD5", e);
		}
	}

	/**
	 * Métode que llig tots els fitxers de una carpeta y els inserta en la base de dades
	 * @param ruta ruta de la carpeta aon estàn els fitxers XML
	 * @return true si se ha completat la acció o false si no
	 */
	public Boolean LeerXMLs(String ruta) {
	    try {
	        File carpeta = new File(ruta);
	        String[] ficheros = carpeta.list();

	        if (ficheros == null || ficheros.length == 0) {
	            return false;  
	        }

	        this.abrirConexion();  

	        String comando = "INSERT INTO population (country, population, density, area, fertility, age, urban, share) "
	                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        PreparedStatement ps = con.prepareStatement(comando);

	        for (String fichero : ficheros) {
	            File file = new File(ruta + "/" + fichero);

	            // Lee y parsea el archivo XML
	            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	            Document document = dBuilder.parse(file);
	            document.getDocumentElement().normalize();

	            NodeList nodeList = document.getElementsByTagName("pais");

	            for (int i = 0; i < nodeList.getLength(); i++) {
	                Node node = nodeList.item(i);
	                if (node.getNodeType() == Node.ELEMENT_NODE) {
	                    Element eElement = (Element) node;

	                    String country = eElement.getElementsByTagName("country").item(0).getTextContent();
	                    String population = eElement.getElementsByTagName("population").item(0).getTextContent();
	                    String density = eElement.getElementsByTagName("density").item(0).getTextContent();
	                    String area = eElement.getElementsByTagName("area").item(0).getTextContent();
	                    String fertility = eElement.getElementsByTagName("fertility").item(0).getTextContent();
	                    String age = eElement.getElementsByTagName("age").item(0).getTextContent();
	                    String urban = eElement.getElementsByTagName("urban").item(0).getTextContent();
	                    String share = eElement.getElementsByTagName("share").item(0).getTextContent();

	                    // Inserta los valores en la base de datos
	                    ps.setString(1, country);
	                    ps.setString(2, population);
	                    ps.setString(3, density);
	                    ps.setString(4, area);
	                    ps.setString(5, fertility);
	                    ps.setString(6, age);
	                    ps.setString(7, urban);
	                    ps.setString(8, share);

	                    // Ejecuta la inserción
	                    ps.executeUpdate();
	                }
	            }
	        }

	        // Cierra la conexión una vez procesados todos los archivos
	        con.close();
	        return true;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}


	/**
	 * @param fit ruta del archiu que es vol convertir a XML
	 * @param ruta ruta de la carpeta aon es volen crear els XML
	 * @return una Cadena de text amb la informació del fitcher o un mensatge de error
	 */
	public String CrearXMLs(String fit, String ruta) {
		File csv = new File(fit);
		File carpeta = new File(ruta);
		String[] cabezales = null;
		String[] datos = null;
		String resultado = "Datos Importados: ";

		try {

			if (!carpeta.exists()) {
				carpeta.mkdirs();
			}

			FileReader fr = new FileReader(csv);
			BufferedReader br = new BufferedReader(fr);
			String linea;

			cabezales = br.readLine().split(";");

			while ((linea = br.readLine()) != null) {
				datos = linea.split(";");

				File pais = new File(carpeta, datos[0] + ".xml");
				pais.createNewFile();

				FileWriter fw = new FileWriter(pais);
				BufferedWriter bw = new BufferedWriter(fw);

				try {

					DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
					DocumentBuilder build = dFact.newDocumentBuilder();
					Document doc = build.newDocument();
					Element raiz = doc.createElement("pais");
					doc.appendChild(raiz);

					Element country = doc.createElement("country");
					country.appendChild(doc.createTextNode(datos[0]));
					raiz.appendChild(country);

					Element population = doc.createElement("population");
					population.appendChild(doc.createTextNode(datos[1]));
					raiz.appendChild(population);

					Element density = doc.createElement("density");
					density.appendChild(doc.createTextNode(datos[2]));
					raiz.appendChild(density);

					Element area = doc.createElement("area");
					area.appendChild(doc.createTextNode(datos[3]));
					raiz.appendChild(area);

					Element fertility = doc.createElement("fertility");
					fertility.appendChild(doc.createTextNode(datos[4]));
					raiz.appendChild(fertility);

					Element age = doc.createElement("age");
					age.appendChild(doc.createTextNode(datos[5]));
					raiz.appendChild(age);

					Element urban = doc.createElement("urban");
					urban.appendChild(doc.createTextNode(datos[6]));
					raiz.appendChild(urban);

					Element share = doc.createElement("share");
					share.appendChild(doc.createTextNode(datos[7]));
					raiz.appendChild(share);

					TransformerFactory tranFactory = TransformerFactory.newInstance();
					Transformer aTransformer = tranFactory.newTransformer();
					aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
					aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
					aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(fw);
					aTransformer.transform(source, result);
					resultado += "\n" + datos[0] + " " + datos[1] + " " + datos[2] + " " + datos[3] + " " + datos[4]
							+ " " + datos[5] + " " + datos[6] + " " + datos[7];

				} catch (ParserConfigurationException | TransformerException e) {
					e.printStackTrace();
					return "Error al importar CSV";
				}

				bw.close();
				fw.close();
			}

			br.close();
			fr.close();

		} catch (IOException e) {
			e.printStackTrace();
			return "Error al importar CSV";
		}

		return resultado;
	}

	/**
	 * Mètode que crea una tabla en la base de dades amb els camps del fitcher
	 * @param ruta ruta aon está el fitcher csv que es vol insertar en la tabla
	 * @return true si se ha creat la tabla o false si no
	 */
	public Boolean CrearTablaPopulation(String ruta) {
		File csv = new File(ruta);
		String[] cabezales = null;
		try {
			FileReader fr = new FileReader(csv);
			BufferedReader br = new BufferedReader(fr);
			String cabezal = br.readLine();
			cabezales = cabezal.split(";");
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.abrirConexion();
		Statement statement = null;
		try {
			String borrado = "DROP TABLE IF EXISTS population;";
			String tabla = " CREATE TABLE population (";
			for (int i = 0; i < cabezales.length; i++) {
				tabla += cabezales[i] + " VARCHAR(50)";
				if (i < cabezales.length - 1) {
					tabla += ",";
				}
			}

			tabla += ");";
			statement = con.createStatement();
			statement.execute(borrado);
			statement.execute(tabla);
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Mètode per a realitzar consultes a la Base de Dades
	 * @param consulta Consulta a executar
	 * @return el resultat en un Objecte ResultSet
	 */
	public ResultSet Consultar(String consulta) {
		this.abrirConexion();
		ResultSet resultSet = null;
		Statement statement = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(consulta);
		} catch (SQLException e) {

			System.out.println(e.getErrorCode());
			if (e.getErrorCode() == 1044) {
				JOptionPane.showMessageDialog(null, "Error de permisos: No tienes acceso para realizar esta consulta.",
						"Error de SQL", JOptionPane.ERROR_MESSAGE);
			} else if (e.getErrorCode() == 1142) {
				JOptionPane.showMessageDialog(null, "Error de SQL - Comando denegado \n\nError: \n" + e.getMessage(),
						"Error SQL", JOptionPane.ERROR_MESSAGE);
			} else {

				JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + e.getMessage(), "Error de SQL",
						JOptionPane.ERROR_MESSAGE);
			}
			e.printStackTrace();

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage(), "Error General",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		rs = resultSet;
		return resultSet;
	}

	/**
	 * Mètode que exporta la informació de la consulta realitzada a un fitcher CSV
	 * @param resultSet resultat de la consulta
	 * @param filePath ruta del archiu aon es vol exportar el resultat de la consulta
	 * @return True si tot ha ixit be o false si no
	 */
	public boolean ExportarCSV(ResultSet resultSet, String filePath) {
		try (FileWriter writer = new FileWriter(filePath)) {
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			// Escribir encabezados
			for (int i = 1; i <= columnCount; i++) {
				writer.append(metaData.getColumnName(i));
				if (i < columnCount) {
					writer.append(";");
				}
			}
			writer.append("\n");

			// Escribir datos fila por fila
			while (resultSet.next()) {
				for (int i = 1; i <= columnCount; i++) {
					writer.append(resultSet.getString(i));
					if (i < columnCount) {
						writer.append(";");
					}
				}
				writer.append("\n");
			}

			writer.flush();
			return true;

		} catch (IOException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Mètode que inserta un usuari a la tabla users de la base de dades
	 * @param login nom de l'usuari
	 * @param password contraseña de l'usuari
	 * @return true si se ha completat la acció correctament o false si no
	 */
	public Boolean RegistrarUsuarioBBDD(String login, char[] password) {
		this.abrirConexion();
		String comando = "INSERT INTO users (login, password, type) VALUES (?, ?, ?)";
		String contraseña = new String(password);
		contraseña = ConvertirContraseña(contraseña);
		type = "client";
		try (PreparedStatement stmt = con.prepareStatement(comando)) {

			stmt.setString(1, login);
			stmt.setString(2, contraseña);
			stmt.setString(3, type);

			stmt.executeUpdate();
			con.close();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Mètode que crea un usuari en la plataforma de gestió de bases de dades
	 * @param login nom de l'usuari a crear
	 * @param password contraseña de l'usuari a crear
	 * @return True si se ha completat correctament o false si no
	 */
	public Boolean CrearUsuarioHeidi(String login, char[] password) {
		System.out.println("ola");
		this.abrirConexion();
		String contraseña = new String(password);
		contraseña = ConvertirContraseña(contraseña);
		String comandoCrear = "CREATE USER '" + login + "' IDENTIFIED BY '" + contraseña + "'";
		String comandoGrants = "GRANT SELECT ON population.population TO '" + login + "'";

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate(comandoCrear);
			System.out.println("paso1");
			stmt.executeUpdate(comandoGrants);
			System.out.println("paso2");
			con.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Compara si dos Strings son iguals
	 * @param password primera contrasenya
	 * @param password2 confirmació de la contrasenya
	 * @return True si son iguals o False si no
	 */
	public Boolean ComprobarContraseñas(char[] password, char[] password2) {
		return Arrays.equals(password, password2);
	}

	/** 
	 * Inicialitza el atribut con de la classe
	 * @param login usuari gastat per a conectarse a la base de dades
	 * @param password contraseña  gastada per a conectarse a la base de dades
	 * @return true si se a realitzat la conexió o false si no
	 */
	public Boolean AccederBBDD(String login, char[] password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String contraseña = new String(password);
			contraseña = ConvertirContraseña(contraseña);
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", login, contraseña);
			this.logIn = login;
			this.contraseña = contraseña;
			con.close();
			return true;

		} catch (ClassNotFoundException e) {
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Mètode que s'utilitza per a obrir la conexió
	 * @return un objecte de tipus Connection
	 */
	public Connection abrirConexion() {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/population", logIn, contraseña);
			return con;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
