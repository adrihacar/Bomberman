
package bomberman.server;


import bomberman.Game;
import bomberman.server.enemy.Enemigo;
 

public class GameEngine  implements Runnable{
     Thread thread;
	private static GameEngine engine;

	public GameEngine() {
		engine = this;
		thread=new Thread(this);
		thread.start();
	}
    
	//se encargar� de detectar si la posicion del jugador coincide con los items o enemigos para restarle la vida (las consecuencias de la bomba est�n programadas en la clase Bomba y no aqu�
	
	public void run() {
		while(true) {
			
			//si la vida es 0 sale del bucle
			if(Game.player.getHealth()<=0) {
				break;
			}
				
			//recorriendo el array de los enemigos comprueba si alguno de ellos est� en la misma casilla que el jugador, de ser as� resta en 1 la vida
			for(int i=0;Map.arrayEnemigos.length>i;i++) {
				
				if(Game.player.getX()/10==Map.arrayEnemigos[i].X/10 && Game.player.getY()/10==Map.arrayEnemigos[i].Y/10) { //comprueba si ambos elementos est�n en el mismo cuadrado
					Game.player.setHealth(Game.player.getHealth()-1);
					Game.gui.gb_setValueHealthCurrent(Game.player.getHealth());
					Game.gui.gb_println("tocado");
					Game.gui.gb_animateDamage();
					try {
						Thread.sleep(5L); //para que el da�o no sea tan seguido paramos el hilo durante un tiempo
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				
				}
			}
			//si las coordenadas del jugador y de la puerta coinciden y no quedan enemigos vivos se crea otro mapa diferente que se almacena en el atributo map de Game y automaticamente se llama a su constructor para asi crear otro mapa de nuevo pero pasando como argumento un nivel mas del actual
			if(Game.player.getX()/10==Map.door.x && Game.player.getY()/10==Map.door.y) {
				if(Enemigo.numeroEnemigos==0) { //si no quedan enemigos
					  try {
						  Game.map=new Map(Map.level+1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else {
					Game.gui.gb_clearCommandBar();
					Game.gui.gb_println("Todav�a quedan enemigos vivos");
				}
			 
			}
			
			//si esta en la mimsa casilla que la geta cambia la velocidad a 1, el minimo
			if(Game.player.getX()/10==Map.geta.x && Game.player.getY()/10==Map.geta.y) {
				Game.player.setSpeed(1);
				Game.gui.gb_setValueAbility2(Game.player.getSpeed());
				Game.gui.gb_setSpriteVisible(Map.geta.id, false); //la hacemos invisible
				Game.gui.gb_moveSprite(Map.geta.id, -1, -1); //lo movemos a una posici�n no accesible por el jugador para que solo pueda ser usado una vez
			}
			//si est� en la misma casilla que los patines aumenta la velocidad en 1 si es menor que 10
			if(Game.player.getX()/10==Map.patines.x && Game.player.getY()/10==Map.patines.y) {
				if(Game.player.getSpeed()<10)
				      Game.player.setSpeed(Game.player.getSpeed()+1);
				      Game.gui.gb_setSpriteVisible(Map.patines.id, false); //la hacemos invisible
				      //lo movemos a una posici�n no accesible por el jugador para que solo pueda ser usado una vez
				      Map.patines.x=-1;
				      Map.patines.y=-1;
				      Game.gui.gb_setValueAbility2(Game.player.getSpeed());
			}
			//si est� en la misma casilla que alguna de las BonusBomb aumenta en 1 el numero de Bombas
			for(int i=0;Map.bonusBomb.length>i;i++) {
				if(Game.player.getX()/10==Map.bonusBomb[i].x && Game.player.getY()/10==Map.bonusBomb[i].y) {
					  Game.player.setBombs(Game.player.getBombs()+1);
					  Game.player.setBombs_max(Game.player.getBombs_max()+1);
					  Game.gui.gb_setValuePointsDown(Game.player.getBombs());
				      Game.gui.gb_setSpriteVisible(Map.bonusBomb[i].id, false); //la hacemos invisible
				      //lo movemos a una posici�n no accesible por el jugador para que solo pueda ser usado una vez
				      Map.bonusBomb[i].x=-1;
				      Map.bonusBomb[i].y=-1;
			}
				}
			
			
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
		
		//se ejecuta este codigo cuando la vida es 0
		Game.player.alive=false;
		//creamos la animaci�n de desaparecer
		Game.gui.gb_setSpriteImage(1, "bomberman141.png");
		try {
			Thread.sleep(150L);
		} catch (InterruptedException e) {
			e.printStackTrace();}
		Game.gui.gb_setSpriteImage(1, "bomberman142.png");
		try {
			Thread.sleep(150L);
		} catch (InterruptedException e) {
			e.printStackTrace();}
		Game.gui.gb_setSpriteImage(1, "bomberman143.png");
		try {
			Thread.sleep(150L);
		} catch (InterruptedException e) {
			e.printStackTrace();}
		Game.gui.gb_setSpriteImage(1, "bomberman144.png");
		try {
			Thread.sleep(150L);
		} catch (InterruptedException e) {
			e.printStackTrace();}
		Game.gui.gb_setSpriteImage(1, "bomberman145.png");
		try {
			Thread.sleep(150L);
		} catch (InterruptedException e) {
			e.printStackTrace();}
		
		//muestra mensaje de "Game Over"
		Game.gui.gb_showMessageDialog("Game Over");
		//al pulsar aceptar en "Game Over" se cierra el juego
		System.exit(0);
		}

}	