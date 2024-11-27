import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFieldLogin;
	private JPasswordField passwordField;
	public JButton btnEntrar;
	

	/**
	 * Mètode que mostra un mensatje en la aplicació
	 * @param txt mensatje que es vol mostrar
	 */
	public void MostrarMensaje(String txt) {
		 JOptionPane.showMessageDialog(null, txt, "AVISO", JOptionPane.INFORMATION_MESSAGE);
	}
	public JPanel getContentPane() {
		return contentPane;
	}



	public JTextField getTxtFieldLogin() {
		return txtFieldLogin;
	}


	public JPasswordField getPasswordField() {
		return passwordField;
	}


	public JButton getBtnEntrar() {
		return btnEntrar;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 517);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Gestor de datos (Adrián Pons)");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblTitulo.setBounds(86, 10, 607, 91);
		contentPane.add(lblTitulo);
		
		JLabel lblLogin = new JLabel("LogIn:");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblLogin.setBounds(129, 134, 86, 54);
		contentPane.add(lblLogin);
		
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblContraseña.setBounds(61, 230, 154, 54);
		contentPane.add(lblContraseña);
		
		txtFieldLogin = new JTextField();
		txtFieldLogin.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtFieldLogin.setBounds(225, 147, 320, 29);
		contentPane.add(txtFieldLogin);
		txtFieldLogin.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		passwordField.setBounds(225, 243, 281, 29);
		contentPane.add(passwordField);
		
		 btnEntrar = new JButton("Entrar");
		btnEntrar.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnEntrar.setBounds(301, 380, 185, 44);
		contentPane.add(btnEntrar);
		setVisible(true);

	}
}
