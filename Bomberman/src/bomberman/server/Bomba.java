package bomberman.server;
import bomberman.Game;
import bomberman.server.enemy.Enemigo;
import bomberman.server.enemy.Globo;
import bomberman.server.enemy.Gota;

public class Bomba implements Runnable {
	private int X,Y;
	private static final int id=300;  
	int numero_bomba;
	static Thread[]threads=new Thread[30]; //un array de Threads, ya que cada bomba tendr� el suyo propio
	static boolean detonar_todas=false;  //esta variable ser� true en el momento que se presione el tabulador
	
	public Bomba(int x, int y,int numero_bomba) {
		this.X=x;
		this.Y=y;
		
		this.numero_bomba=numero_bomba;
		
		Game.gui.gb_addSprite(id+numero_bomba, "bomb1.gif", false);
		Game.gui.gb_moveSprite(id+numero_bomba, x, y);
		Game.gui.gb_setSpriteVisible(id+numero_bomba, true);
		
		//creamos un nuevo thread y llamamos a su metodo run() con start()
		threads[this.numero_bomba]=new Thread(this);
		threads[this.numero_bomba].start();
	}
	
	
	// llama al metodo detonar
		public void run() {
			//creamos esta variable para poder medir el tiempo que ha pasado 
			 long time_start=System.currentTimeMillis();
			//si han pasado los 4 segundos o la variable detonar_todas es true sale del bucle y llama al metodo detonar
	        while(System.currentTimeMillis()-time_start<4000&&!detonar_todas) {
	        	
	        	  long time_start2=System.currentTimeMillis();
	        	  while(System.currentTimeMillis()-time_start2<100){
	        	  Game.gui.gb_setSpriteImage(id+numero_bomba, "bomb1.gif");
	        	  }
	        	  
	        	  while(System.currentTimeMillis()-time_start2<200){
	            	  Game.gui.gb_setSpriteImage(id+numero_bomba, "bomb2.gif");
	        	  }
			}
	        detonar_todas=false;   //volvemos a poner en false la variable detonar_todas
	        detonar(this); //pasamos como argumento la misma bomba, que es la queremos detonar
			
			
		}
		
		
	public static void detonar_todas() {
		detonar_todas=true;
	}
	
		
		
	static void detonar(Bomba bomba)  {
		long time_start=System.currentTimeMillis();
		
	    //CENTRAL
		Game.gui.gb_setSpriteImage(id+bomba.numero_bomba, "explosion_C1.gif");
		
		//comprobamos si esta rama de la bomba est� en contacto con alg�n enemigo y el jugador
		for(int i=0;Map.arrayEnemigos.length>i;i++) {
			if(bomba.X==Map.arrayEnemigos[i].X/10 && bomba.Y==Map.arrayEnemigos[i].Y/10) {
				Map.arrayEnemigos[i].alive=false;  
				Game.gui.gb_setSpriteVisible(Map.arrayEnemigos[i].id, false);
				Game.gui.gb_moveSprite(Map.arrayEnemigos[i].id,-1,-1);
				Enemigo.numeroEnemigos--; 
				
				//si es un globo sumamos 100 puntos
				if(Map.arrayEnemigos[i] instanceof Globo) {
					Game.player.setScore(Game.player.getScore()+100);
				}
				//si es una gota sumamos 250 puntos
				else if(Map.arrayEnemigos[i] instanceof Gota) {
					Game.player.setScore(Game.player.getScore()+250);
				}
				Game.gui.gb_setValuePointsUp(Game.player.getScore());
			}
	}
		//comprobamos si la rama est� en contacto con el jugador
		if(bomba.X==Game.player.getX()/10 && bomba.Y==Game.player.getY()/10) {
			Game.player.setHealth(0);
		}
		
		//IZQUIERDA
		if(!(Map.square[bomba.X-1][bomba.Y]==null)) {
			if(Map.square[bomba.X-1][bomba.Y].nombre_elemento.equals("brick")) {             //si es un ladrillo lo destruimos
				Game.gui.gb_setSpriteVisible(Map.square[bomba.X-1][bomba.Y].id, false); //le quitamos la visibilidad                                                                          
				Map.square[bomba.X-1][bomba.Y]=null;  //le asignamos null en la matriz square
				
				Game.gui.gb_addSprite((id+bomba.numero_bomba)*10, "explosion_W1.gif", false);
				Game.gui.gb_moveSprite((id+bomba.numero_bomba)*10, bomba.X-1, bomba.Y);
				Game.gui.gb_setSpriteVisible((id+bomba.numero_bomba)*10, true);
				                                       
			}	
			else //cramos de todas maneras el sprite para que despues no nos de error al ponerlo como false
				Game.gui.gb_addSprite((id+bomba.numero_bomba)*10, "explosion_W1.gif", false);
			
		}
		else {
			
			Game.gui.gb_addSprite((id+bomba.numero_bomba)*10, "explosion_W1.gif", false);
			Game.gui.gb_moveSprite((id+bomba.numero_bomba)*10, bomba.X-1, bomba.Y);
			Game.gui.gb_setSpriteVisible((id+bomba.numero_bomba)*10, true);
			//comprobamos si esta rama de la bomba est� en contacto con alg�n enemigo
			for(int i=0;Map.arrayEnemigos.length>i;i++) {
				if(bomba.X-1==Map.arrayEnemigos[i].X/10 && bomba.Y==Map.arrayEnemigos[i].Y/10) {
					Map.arrayEnemigos[i].alive=false;  
					Game.gui.gb_setSpriteVisible(Map.arrayEnemigos[i].id, false);
					Game.gui.gb_moveSprite(Map.arrayEnemigos[i].id,-1,-1);
					Enemigo.numeroEnemigos--; 
					
					//si es un globo sumamos 100 puntos
					if(Map.arrayEnemigos[i] instanceof Globo) {
						Game.player.setScore(Game.player.getScore()+100);
					}
					//si es una gota sumamos 250 puntos
					else if(Map.arrayEnemigos[i] instanceof Gota) {
						Game.player.setScore(Game.player.getScore()+250);
					}
					Game.gui.gb_setValuePointsUp(Game.player.getScore());
				}
		}
			//comprobamos si la rama est� en contacto con el jugador
			if(bomba.X-1==Game.player.getX()/10 && bomba.Y==Game.player.getY()/10) {
				Game.player.setHealth(0);
			}
		}
			
		
		
		//DERECHA
		if(!(Map.square[bomba.X+1][bomba.Y]==null)) {
			if(Map.square[bomba.X+1][bomba.Y].nombre_elemento.equals("brick")) {
				Game.gui.gb_setSpriteVisible(Map.square[bomba.X+1][bomba.Y].id, false);                                                                      
				Map.square[bomba.X+1][bomba.Y]=null;
			
			Game.gui.gb_addSprite((id+bomba.numero_bomba)*20, "explosion_E1.gif", false);
			Game.gui.gb_moveSprite((id+bomba.numero_bomba)*20, bomba.X+1, bomba.Y);
			Game.gui.gb_setSpriteVisible((id+bomba.numero_bomba)*20, true);
			}
			else
				Game.gui.gb_addSprite((id+bomba.numero_bomba)*20, "explosion_W1.gif", false);
		}
		else {
			Game.gui.gb_addSprite((id+bomba.numero_bomba)*20, "explosion_E1.gif", false);
			Game.gui.gb_moveSprite((id+bomba.numero_bomba)*20, bomba.X+1, bomba.Y);
			Game.gui.gb_setSpriteVisible((id+bomba.numero_bomba)*20, true);
			
			//comprobamos si esta rama de la bomba est� en contacto con alg�n enemigo
			for(int i=0;Map.arrayEnemigos.length>i;i++) {
				if(bomba.X+1==Map.arrayEnemigos[i].X/10 && bomba.Y==Map.arrayEnemigos[i].Y/10) {
					Map.arrayEnemigos[i].alive=false;  
					Game.gui.gb_setSpriteVisible(Map.arrayEnemigos[i].id, false);
					Game.gui.gb_moveSprite(Map.arrayEnemigos[i].id,-1,-1);
					Enemigo.numeroEnemigos--; 
					
					//si es un globo sumamos 100 puntos
					if(Map.arrayEnemigos[i] instanceof Globo) {
						Game.player.setScore(Game.player.getScore()+100);
					}
					//si es una gota sumamos 250 puntos
					else if(Map.arrayEnemigos[i] instanceof Gota) {
						Game.player.setScore(Game.player.getScore()+250);
					}
					Game.gui.gb_setValuePointsUp(Game.player.getScore());
				}
		}
			//comprobamos si la rama est� en contacto con el jugador
			if(bomba.X+1==Game.player.getX()/10 && bomba.Y==Game.player.getY()/10) {
				Game.player.setHealth(0);
			}
		}
		
		
		
		//ARRIBA
		if(!(Map.square[bomba.X][bomba.Y-1]==null)) {
			if(Map.square[bomba.X][bomba.Y-1].nombre_elemento.equals("brick")) {
				Game.gui.gb_setSpriteVisible(Map.square[bomba.X][bomba.Y-1].id, false);                                                                      
				Map.square[bomba.X][bomba.Y-1]=null;
				
				
			Game.gui.gb_addSprite((id+bomba.numero_bomba)*30, "explosion_N1.gif", false);
			Game.gui.gb_moveSprite((id+bomba.numero_bomba)*30, bomba.X, bomba.Y-1);
			Game.gui.gb_setSpriteVisible((id+bomba.numero_bomba)*30, true);
			}
			else
				Game.gui.gb_addSprite((id+bomba.numero_bomba)*30, "explosion_W1.gif", false);
		}
		else {
			Game.gui.gb_addSprite((id+bomba.numero_bomba)*30, "explosion_N1.gif", false);
			Game.gui.gb_moveSprite((id+bomba.numero_bomba)*30, bomba.X, bomba.Y-1);
			Game.gui.gb_setSpriteVisible((id+bomba.numero_bomba)*30, true);
			
			//comprobamos si esta rama de la bomba est� en contacto con alg�n enemigo
			for(int i=0;Map.arrayEnemigos.length>i;i++) {
				if(bomba.X==Map.arrayEnemigos[i].X/10 && bomba.Y-1==Map.arrayEnemigos[i].Y/10) {
					Map.arrayEnemigos[i].alive=false;  
					Game.gui.gb_setSpriteVisible(Map.arrayEnemigos[i].id, false);
					Game.gui.gb_moveSprite(Map.arrayEnemigos[i].id,-1,-1);
					Enemigo.numeroEnemigos--; 
					
					//si es un globo sumamos 100 puntos
					if(Map.arrayEnemigos[i] instanceof Globo) {
						Game.player.setScore(Game.player.getScore()+100);
					}
					//si es una gota sumamos 250 puntos
					else if(Map.arrayEnemigos[i] instanceof Gota) {
						Game.player.setScore(Game.player.getScore()+250);
					}
					Game.gui.gb_setValuePointsUp(Game.player.getScore());
				}
		}
			//comprobamos si la rama est� en contacto con el jugador
			if(bomba.X==Game.player.getX()/10 && bomba.Y-1==Game.player.getY()/10) {
				Game.player.setHealth(0);
			}
		}
		
		//ABAJO
		if(!(Map.square[bomba.X][bomba.Y+1]==null)) {
			if(Map.square[bomba.X][bomba.Y+1].nombre_elemento.equals("brick")) {
				Game.gui.gb_setSpriteVisible(Map.square[bomba.X][bomba.Y+1].id, false);                                                                      
				Map.square[bomba.X][bomba.Y+1]=null;
				
				
			Game.gui.gb_addSprite((id+bomba.numero_bomba)*40, "explosion_S1.gif", false);
			Game.gui.gb_moveSprite((id+bomba.numero_bomba)*40, bomba.X, bomba.Y+1);
			Game.gui.gb_setSpriteVisible((id+bomba.numero_bomba)*40, true);
		}
			else
				Game.gui.gb_addSprite((id+bomba.numero_bomba)*40, "explosion_W1.gif", false);
		}
		else {
			Game.gui.gb_addSprite((id+bomba.numero_bomba)*40, "explosion_S1.gif", false);
			Game.gui.gb_moveSprite((id+bomba.numero_bomba)*40, bomba.X, bomba.Y+1);
			Game.gui.gb_setSpriteVisible((id+bomba.numero_bomba)*40, true);
			
			//comprobamos si esta rama de la bomba est� en contacto con alg�n enemigo
			for(int i=0;Map.arrayEnemigos.length>i;i++) {
				if(bomba.X==Map.arrayEnemigos[i].X/10 && bomba.Y+1==Map.arrayEnemigos[i].Y/10) {
					Map.arrayEnemigos[i].alive=false;  
					Game.gui.gb_setSpriteVisible(Map.arrayEnemigos[i].id, false);
					Game.gui.gb_moveSprite(Map.arrayEnemigos[i].id,-1,-1);
					Enemigo.numeroEnemigos--; 
					
					//si es un globo sumamos 100 puntos
					if(Map.arrayEnemigos[i] instanceof Globo) {
						Game.player.setScore(Game.player.getScore()+100);
					}
					//si es una gota sumamos 250 puntos
					else if(Map.arrayEnemigos[i] instanceof Gota) {
						Game.player.setScore(Game.player.getScore()+250);
					}
					Game.gui.gb_setValuePointsUp(Game.player.getScore());
				}
		}
			//comprobamos si la rama est� en contacto con el jugador
			if(bomba.X==Game.player.getX()/10 && bomba.Y+1==Game.player.getY()/10) {
				Game.player.setHealth(0);
			}
		}
				
		
		
		
        while(System.currentTimeMillis()-time_start<2000) {
        //durante estos dos segundos ir�n cambiando los sprites de fuego de la bomba 
        	try {Thread.sleep(20L);
    		} catch (InterruptedException e) {
    			e.printStackTrace();}
        	Game.gui.gb_setSpriteImage((id+bomba.numero_bomba),"explosion_C2.gif" );
            Game.gui.gb_setSpriteImage((id+bomba.numero_bomba)*10, "explosion_W2.gif");
     		Game.gui.gb_setSpriteImage((id+bomba.numero_bomba)*20, "explosion_E2.gif");
     		Game.gui.gb_setSpriteImage((id+bomba.numero_bomba)*30, "explosion_N2.gif");
     		Game.gui.gb_setSpriteImage((id+bomba.numero_bomba)*40, "explosion_S2.gif");
     		try {Thread.sleep(20L);
    		} catch (InterruptedException e) {
    			e.printStackTrace();}
        	Game.gui.gb_setSpriteImage((id+bomba.numero_bomba),"explosion_C3.gif" );
            Game.gui.gb_setSpriteImage((id+bomba.numero_bomba)*10, "explosion_W3.gif");
     		Game.gui.gb_setSpriteImage((id+bomba.numero_bomba)*20, "explosion_E3.gif");
     		Game.gui.gb_setSpriteImage((id+bomba.numero_bomba)*30, "explosion_N3.gif");
     		Game.gui.gb_setSpriteImage((id+bomba.numero_bomba)*40, "explosion_S3.gif");
     		try {Thread.sleep(20L);
    		} catch (InterruptedException e) {
    			e.printStackTrace();}
        	Game.gui.gb_setSpriteImage((id+bomba.numero_bomba),"explosion_C4.gif" );
            Game.gui.gb_setSpriteImage((id+bomba.numero_bomba)*10, "explosion_W4.gif");
     		Game.gui.gb_setSpriteImage((id+bomba.numero_bomba)*20, "explosion_E4.gif");
     		Game.gui.gb_setSpriteImage((id+bomba.numero_bomba)*30, "explosion_N4.gif");
     		Game.gui.gb_setSpriteImage((id+bomba.numero_bomba)*40, "explosion_S4.gif");
     		try {Thread.sleep(20L);
    		} catch (InterruptedException e) {
    			e.printStackTrace();}
        	Game.gui.gb_setSpriteImage((id+bomba.numero_bomba),"explosion_C1.gif" );
            Game.gui.gb_setSpriteImage((id+bomba.numero_bomba)*10, "explosion_W1.gif");
     		Game.gui.gb_setSpriteImage((id+bomba.numero_bomba)*20, "explosion_E1.gif");
     		Game.gui.gb_setSpriteImage((id+bomba.numero_bomba)*30, "explosion_N1.gif");
     		Game.gui.gb_setSpriteImage((id+bomba.numero_bomba)*40, "explosion_S1.gif");
		
		
        }
        
        //cuando hayan pasado los dos segundos los sprites se volveran invisibles
        Game.gui.gb_setSpriteVisible((id+bomba.numero_bomba), false);
        Game.gui.gb_setSpriteVisible((id+bomba.numero_bomba)*10, false);
		Game.gui.gb_setSpriteVisible((id+bomba.numero_bomba)*20, false);
		Game.gui.gb_setSpriteVisible((id+bomba.numero_bomba)*30, false);
		Game.gui.gb_setSpriteVisible((id+bomba.numero_bomba)*40, false);
        
		//aumentamos en un 1 las bombas del jugador, ya que al haber explotado ya la tiene disponible y actualizamos las bombas que se muestran disponibles de forma gr�fica
		Game.player.setBombs(Game.player.getBombs()+1);
		Game.gui.gb_setValuePointsDown(Game.player.getBombs());
	}
	
	
	
	

}
