import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

public class Controlador {
	
	private Vista vistaLogin;
	private VistaPanel vistaPanel;
	private Model model;
	public Controlador(Vista vista, VistaPanel vistaPanel, Model model) {
		this.vistaLogin = vista;
		this.vistaPanel=vistaPanel;
		this.model = model;
		initEventHandlers();
	}
	
	public  void initEventHandlers() {
		
		vistaLogin.btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (model.AccederBBDD(vistaLogin.getTxtFieldLogin().getText(), vistaLogin.getPasswordField().getPassword())) {
					vistaLogin.MostrarMensaje("Conexión realizada con éxito");
					vistaLogin.dispose();
					vistaPanel.setVisible(true);
					
				} else {
					vistaLogin.MostrarMensaje("Error en la conexión. Vuelva a intentarlo");
				}
				
		      }
			
        });
		vistaPanel.getBtnRegistrar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (model.ComprobarContraseñas(vistaPanel.getTxtFContraseña().getPassword(), vistaPanel.getTxtFConfirmarContraseña().getPassword())&&(!vistaPanel.getTxtFNombre().getText().equals(""))) {
					if (model.CrearUsuarioHeidi(vistaPanel.getTxtFNombre().getText(),vistaPanel.getTxtFContraseña().getPassword())) {
						vistaLogin.MostrarMensaje("Usuario creado con éxito en el SGBD");
						if (model.RegistrarUsuarioBBDD(vistaPanel.getTxtFNombre().getText(),vistaPanel.getTxtFContraseña().getPassword())) {
							vistaLogin.MostrarMensaje("Usuario añadido con éxito en la tabla usuarios");
						} else {
							vistaLogin.MostrarMensaje("Fallo al crear usuario en el SGBD");
						}
					} else {
						vistaLogin.MostrarMensaje("Error en la conexión. Vuelva a intentarlo");
					}
				} else {
					vistaLogin.MostrarMensaje("La contraseña no coincide o el nombre está vacio");
				}
				
				
		      }
			
        });
		
		vistaPanel.getBtnLogOut().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vistaLogin.setVisible(true);
				vistaLogin.getTxtFieldLogin().setText(null);
				vistaLogin.getPasswordField().setText(null);
				vistaPanel.dispose();
				vistaPanel.getTxtFConsulta().setText("");
				vistaPanel.getTxtImportar().setText("");
				vistaPanel.getTxtFNombre().setText("");
				vistaPanel.getTxtFContraseña().setText("");
				vistaPanel.getTxtFConfirmarContraseña().setText("");
				DefaultTableModel tableModel = (DefaultTableModel) vistaPanel.getTableConsulta().getModel();
		        tableModel.setRowCount(0);
		        tableModel.setColumnCount(0);
				
				
		      }
			
        });
		vistaPanel.getBtnLimpiar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vistaPanel.getTxtFConsulta().setText("");
				vistaPanel.getTxtImportar().setText("");
				vistaPanel.getTxtFNombre().setText("");
				vistaPanel.getTxtFContraseña().setText("");
				vistaPanel.getTxtFConfirmarContraseña().setText("");
				DefaultTableModel tableModel = (DefaultTableModel) vistaPanel.getTableConsulta().getModel();
		        tableModel.setRowCount(0);
		        tableModel.setColumnCount(0);
			
				
		      }
			
        });
		vistaPanel.getBtnExportar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			if (model.ExportarCSV( model.Consultar(vistaPanel.getTxtFConsulta().getText()), "ResultadoConsulta.csv")) {
				vistaLogin.MostrarMensaje("Exportado a CSV correctamente");
			} else {
				vistaLogin.MostrarMensaje("Fallo al exportar CSV");
			}
				
		      }
			
        });
		vistaPanel.getBtnImportar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (model.CrearTablaPopulation("AE02_population.csv")) {
					vistaLogin.MostrarMensaje("Creación de la tabla en la Base de Datos realizada con éxito");
					vistaPanel.getTxtImportar().setText(model.CrearXMLs("AE02_population.csv","xml")); 
					if (model.LeerXMLs("xml")) {
						vistaLogin.MostrarMensaje("Importación del fichero CSV a la Base de Datos realizada con éxito");
					} else {
						vistaLogin.MostrarMensaje("Fallo en la importación del fichero CSV a la Base de Datos");
					}
					
				} else {
					vistaLogin.MostrarMensaje("Fallo en la creación de la tabla");
				}
				
		      }
			
        });
		vistaPanel.getBtnConsultar().addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		        DefaultTableModel tableModel = (DefaultTableModel) vistaPanel.getTableConsulta().getModel();

		        // Limpiar contenido previo de la tabla
		        tableModel.setRowCount(0);
		        tableModel.setColumnCount(0);

		        // Realizar la consulta
		        try (ResultSet resultSet = model.Consultar(vistaPanel.getTxtFConsulta().getText())) {
		            if (resultSet == null) {
		                vistaLogin.MostrarMensaje("No se obtuvieron resultados de la consulta.");
		                return;
		            }

		            // Obtener metadatos del ResultSet
		            ResultSetMetaData metaData = resultSet.getMetaData();
		            int columnCount = metaData.getColumnCount();

		            // Establecer nombres de columnas en la tabla
		            String[] columnNames = new String[columnCount];
		            for (int i = 1; i <= columnCount; i++) {
		                columnNames[i - 1] = metaData.getColumnName(i);
		            }
		            tableModel.setColumnIdentifiers(columnNames); // Configura los encabezados

		            // Agregar filas de datos al modelo de la tabla
		            while (resultSet.next()) {
		                Object[] row = new Object[columnCount];
		                for (int i = 1; i <= columnCount; i++) {
		                    row[i - 1] = resultSet.getObject(i);
		                }
		                tableModel.addRow(row);
		            }

		            // Forzar actualización de la tabla
		            vistaPanel.getTableConsulta().revalidate();
		            vistaPanel.getTableConsulta().repaint();

		        } catch (SQLException ex) {
		            // Mostrar mensaje en caso de error
		            vistaLogin.MostrarMensaje("Error al realizar la consulta: " + ex.getMessage());
		            ex.printStackTrace();
		        } catch (Exception ex) {
		            vistaLogin.MostrarMensaje("Ocurrió un error inesperado: " + ex.getMessage());
		            ex.printStackTrace();
		        }
		    }
		});



	}
}
