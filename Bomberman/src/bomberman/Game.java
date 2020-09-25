package bomberman;

import javax.swing.JOptionPane;

import bomberman.server.GameEngine;
import bomberman.server.Map;
import bomberman.server.Player;
import edu.uc3m.game.GameBoardGUI;

public class Game extends javax.swing.JFrame {
	public static Player player;
	static int size = 17; // Tama�o del tablero.
	public static GameBoardGUI gui = new GameBoardGUI(size, size);
	public static Map map;
	
	//este metodo se encarga de crear un cuadro de dialogo al inicio del juego que pide al usuario que introduzca un tema
	 private void tema() {
	    	Object result = JOptionPane.showInputDialog(this, "Elija un tema: clasico, verde o morado y pulsa aceptar","Tema",JOptionPane.QUESTION_MESSAGE);
	    	String respuesta = "" + result;
	    	if(result == null) //si no ha escrito ningun tema el metodo se llama a el mismo de nuevo
	    		tema();
	    	else if(respuesta.equals("clasico")) {
	    		for (int i = 0; i < size; i++){
			    	for (int j = 0; j < size; j++){
			    		if ((i+j) % 2 == 0){
			    			gui.gb_setSquareColor(i, j, 100, 100, 100);
			    		}
			    		else{
			    			gui.gb_setSquareColor(i, j, 230, 230, 230);
			    		}
			    			
			    	}
			    }
	    	}
	    	else if(respuesta.equals("morado")) {
	    		for (int i = 0; i < size; i++){
			    	for (int j = 0; j < size; j++){
			    		
			    			gui.gb_setSquareColor(i, j, 122, 42, 106);
			    	}
			    		}
	    	}
	    	else if(respuesta.equals("verde")) {
	    		for (int i = 0; i < size; i++){
			    	for (int j = 0; j < size; j++){
			    		
			    			gui.gb_setSquareColor(i, j, 42, 124, 108);
			    		
			    		}
			    			
			    }
	    	}
	    	else tema(); //si no se ha introducido un tema válido el metodo se vuelve a llamar a el mismo
	        }
	 
	public static void main(String[] args) throws InterruptedException {
		    
		    //imprime en consola el texto
			gui.gb_println("Juega al Boomberman"); 
			gui.setVisible(true); //hace visible el tablero
			
			//llamamos al metodo tema, para que el usuario personalice el tablero
			Game game=new Game();
		    game.tema();
			//creamos el atributo player, al llamar a su constructor se iniciará el movimiento del jugador e importantes funcionalidades del juego 
			player=new Player();
		    gui.gb_setPortraitPlayer("White_Bomberman_R.png");  
		    gui.gb_setTextPlayerName("Boomberman");
		    gui.gb_setTextPointsUp("Puntos");
		    gui.gb_setTextPointsDown("Bombas");
		    gui.gb_setTextAbility1("Alcance");
		    gui.gb_setTextAbility2("Velocidad");
		    gui.gb_setValueHealthMax(player.getHealthMax());
		    
		    
		    gui.gb_setValueHealthCurrent(player.getHealth());
		    player.alive=true;
		    map=new Map(1);  //creamos el mapa pasandole como argumento 1, que es nivel en el que va a empezar, se puede poner cualquier otro nivel para testear, como por ejemplo el 2 y saldrá la gota como enemigo
		    //creamos un objeto de la clase GameEngine con un el unico objetivo de llamar a su constructor e iniciar el Thread que contiene esta clase
		    GameEngine engine=new GameEngine();
		    

		   
		    
	}
}
