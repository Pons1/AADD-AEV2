
public class Principal {

	public static void main(String[] args) {
		Vista vista = new Vista();
		VistaPanel vistaPanel = new VistaPanel();
		Model model = new Model();
		Controlador controlador = new Controlador(vista,vistaPanel,model);

	}


}
