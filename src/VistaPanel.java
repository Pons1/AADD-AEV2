import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class VistaPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFNombre;
	private JPasswordField txtFContraseña;
	private JPasswordField txtFConfirmarContraseña;
	private JButton btnRegistrar;
	private JButton btnLogOut;
	private JTextField txtFConsulta;
	private JLabel lblConsulta;
	private JButton btnConsultar;
	private JTable tableConsulta;
	private JButton  btnExportar;
	private JButton btnLimpiar;
	private JButton btnImportar;
	private JTextPane txtImportar;
	
	public JTextPane getTxtImportar() {
		return txtImportar;
	}
	public JTextField getTxtFConsulta() {
		return txtFConsulta;
	}
	public JButton getBtnImportar() {
		return btnImportar;
	}
	public JButton getBtnConsultar() {
		return btnConsultar;
	}
	public JButton getBtnLimpiar() {
		return btnLimpiar;
	}

	public JTable getTableConsulta() {
		return tableConsulta;
	}

	public JButton getBtnExportar() {
		return btnExportar;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public JTextField getTxtFNombre() {
		return txtFNombre;
	}

	public JPasswordField getTxtFContraseña() {
		return txtFContraseña;
	}

	public JPasswordField getTxtFConfirmarContraseña() {
		return txtFConfirmarContraseña;
	}

	public JButton getBtnRegistrar() {
		return btnRegistrar;
	}

	public JButton getBtnLogOut() {
		return btnLogOut;
	}
	public VistaPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 915, 564);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		 btnRegistrar = new JButton("Registrar");
		btnRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnRegistrar.setBounds(199, 127, 119, 31);
		contentPane.add(btnRegistrar);
		
		txtFNombre = new JTextField();
		txtFNombre.setBounds(199, 36, 241, 19);
		contentPane.add(txtFNombre);
		txtFNombre.setColumns(10);
		
		txtFContraseña = new JPasswordField();
		txtFContraseña.setBounds(199, 65, 241, 21);
		contentPane.add(txtFContraseña);
		
		txtFConfirmarContraseña = new JPasswordField();
		txtFConfirmarContraseña.setBounds(199, 96, 241, 21);
		contentPane.add(txtFConfirmarContraseña);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNombre.setBounds(100, 32, 100, 21);
		contentPane.add(lblNombre);
		
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblContraseña.setBounds(75, 62, 100, 21);
		contentPane.add(lblContraseña);
		
		JLabel lblConfirmarContraseña = new JLabel("Confirmar \r\ncontraseña:");
		lblConfirmarContraseña.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblConfirmarContraseña.setBounds(10, 88, 190, 31);
		contentPane.add(lblConfirmarContraseña);
		
		btnLogOut = new JButton("LogOut");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLogOut.setBackground(new Color(255, 0, 0));
		btnLogOut.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogOut.setBounds(806, 492, 85, 21);
		contentPane.add(btnLogOut);
		
		txtFConsulta = new JTextField();
		txtFConsulta.setBounds(199, 184, 417, 31);
		contentPane.add(txtFConsulta);
		txtFConsulta.setColumns(10);
		
		lblConsulta = new JLabel("Consulta:");
		lblConsulta.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblConsulta.setBounds(100, 194, 100, 21);
		contentPane.add(lblConsulta);
		
		btnConsultar = new JButton("Ejecutar");
		btnConsultar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnConsultar.setBounds(651, 184, 119, 31);
		contentPane.add(btnConsultar);
		
		btnExportar = new JButton("Exportar a CSV");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExportar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnExportar.setBounds(75, 486, 162, 31);
		contentPane.add(btnExportar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(46, 248, 740, 227);
		contentPane.add(scrollPane);
		
		tableConsulta = new JTable();
		scrollPane.setViewportView(tableConsulta);
		tableConsulta.setBackground(new Color(192, 192, 192));
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLimpiar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLimpiar.setBounds(782, 184, 119, 31);
		contentPane.add(btnLimpiar);
		
		btnImportar = new JButton("Importar Population");
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnImportar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnImportar.setBounds(593, 10, 298, 31);
		contentPane.add(btnImportar);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(476, 52, 415, 122);
		contentPane.add(scrollPane_1);
		
		txtImportar = new JTextPane();
		scrollPane_1.setViewportView(txtImportar);
		
	}
}
