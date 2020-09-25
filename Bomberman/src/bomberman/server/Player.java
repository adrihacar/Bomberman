package bomberman.server;


import bomberman.Game;

public class Player implements Runnable{
	private int health=100;
	private String name;
	private int X=10; //en divisones de cuadrados de 10
	private int Y=10;
	private int bombs; //indica el numero de bombas que tiene disponibles para poner en cada momento
	private int bombs_max=2;  //indica el numero maximo de bombas que puede poner a la vez
	private int speed=2;
	private int score=0;
	private int fire;
	private String portrait;
	private int healthMax=100;
	private String sprite;
	public boolean alive=true;
	

	private int lastTecla; //penultima tecla pulsada para ir cambiando las animaciones 
	private int contador_sprite=0;  //usado para saber que sprite usar
	private Bomba[]array_bombas=new Bomba[30]; //array con las bombas actuales en el tablero
	Thread mover_jugador;
	
	public Player(){
		bombs=bombs_max;
		Game.gui.gb_setValueAbility2(speed);
		Game.gui.gb_setValuePointsDown(bombs);
		Game.gui.gb_setValueAbility1(2);
		Game.gui.gb_addSprite(1, "bomberman111.png", true);
	    Game.gui.gb_setSpriteVisible(1, true);
	    Game.gui.gb_moveSprite(1, 1, 1);
	    
	    
	    mover_jugador=new Thread(this);
	    mover_jugador.start();
	}
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public String getName() {
		return name;
	}
	public void setName(String nombre) {
		this.name = nombre;
	}
	public int getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	public int getY() {
		return Y;
	}
	public void setY(int y) {
		Y = y;
	}
	public int getBombs() {
		return bombs;
	}
	public void setBombs(int bombs) {
		this.bombs = bombs;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getFire() {
		return fire;
	}
	public void setFire(int fire) {
		this.fire = fire;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public int getHealthMax() {
		return healthMax;
	}
	public void setHealthMax(int healthMax) {
		this.healthMax = healthMax;
	}
	public String getSprite() {
		return sprite;
	}
	public void setSprite(String sprite) {
		this.sprite = sprite;
	}
	
	public int getBombs_max() {
		return bombs_max;
	}
	public void setBombs_max(int bombs_max) {
		this.bombs_max = bombs_max;
	}
	
	
	//este metodo se encarga de mover al jugador y comprobar si se ha pulsado space o tab
	public void run()  {
		while(Game.player.alive) {
			String lastAction = Game.gui.gb_getLastAction().trim();//Recoge la �ltima acci�n
	    	if (lastAction.length() > 0){
	    	   Game.gui.gb_clearConsole();  //borramos lo que haya en la consola
	    	   Game.gui.gb_println(lastAction);  //imprimimos la ultima acción en consola
	    	   if (lastAction.equals("right")&&!Map.is_solid(this.X+this.speed-4, this.Y)){
	    	   Game.gui.gb_moveSpriteCoord(1, this.X=this.X+this.speed, this.Y);
	    	   
	    	   
	    	   if(!(this.lastTecla==1))   //conprueba si la ultima tecla pulsada
	    		   this.contador_sprite=0;
	    	   
	    	       this.lastTecla=1;
	    		   switch(contador_sprite) {
	    		     case 0:
	    	              Game.gui.gb_setSpriteImage(1, "bomberman131.png");
	    	              contador_sprite++;
	    	              break;
	    		     case 1:
	    	              Game.gui.gb_setSpriteImage(1, "bomberman132.png");
	    	              contador_sprite++;
	    	              break;
	    		     case 2:
	    		    	  Game.gui.gb_setSpriteImage(1, "bomberman133.png");
	    		    	  contador_sprite++;
	    		    	  break;
	    		     case 3:
	    		    	  Game.gui.gb_setSpriteImage(1, "bomberman134.png");
	    		    	  contador_sprite++;
	    		          break;
	    		     case 4:
	    	              Game.gui.gb_setSpriteImage(1, "bomberman135.png");
	    	              contador_sprite=0;
	    	              break;
	    		   
	    	   }
	    	   }
	    	   else if (lastAction.equals("down")&&!Map.is_solid(this.X-4, this.Y+this.speed)){
	    	   Game.gui.gb_moveSpriteCoord(1, this.X, this.Y=this.Y+this.speed);
	    	   if(!(this.lastTecla==2)) 
	    	   this.contador_sprite=0;
	    	   
    	       this.lastTecla=2;
    		   switch(contador_sprite) {
    		     case 0:
    		    	  Game.gui.gb_setSpriteImage(1, "bomberman111.png");
    	              contador_sprite++;
    	              break;
    		     case 1:
    		    	 Game.gui.gb_setSpriteImage(1, "bomberman112.png");
    	              contador_sprite++;
    	              break;
    		     case 2:
    		    	 Game.gui.gb_setSpriteImage(1, "bomberman113.png");
    		    	  contador_sprite++;
    		    	  break;
    		     case 3:
    		    	 Game.gui.gb_setSpriteImage(1, "bomberman114.png");
    		    	  contador_sprite++;
    		          break;
    		     case 4:
    		    	 Game.gui.gb_setSpriteImage(1, "bomberman115.png");
    	              contador_sprite=0;
    	              break;
    		   
	    	   
    		   }
	    	  
	    	   }
	    	   else if(lastAction.equals("left")&&!Map.is_solid(this.X-this.speed-4, this.Y)) {
	    	   Game.gui.gb_moveSpriteCoord(1, this.X=this.X-this.speed, this.Y);
	    	   if(!(this.lastTecla==3))   //conprueba si la ultima tecla pulsada
	    		   this.contador_sprite=0;
	    	   
	    	       this.lastTecla=3;
	    		   switch(contador_sprite) {
	    		     case 0:
	    		    	  Game.gui.gb_setSpriteImage(1, "bomberman121.png");
	    	              contador_sprite++;
	    	              break;
	    		     case 1:
	    		    	 Game.gui.gb_setSpriteImage(1, "bomberman122.png");
	    	              contador_sprite++;
	    	              break;
	    		     case 2:
	    		    	 Game.gui.gb_setSpriteImage(1, "bomberman123.png");
	    		    	  contador_sprite++;
	    		    	  break;
	    		     case 3:
	    		    	 Game.gui.gb_setSpriteImage(1, "bomberman124.png");
	    		    	  contador_sprite++;
	    		          break;
	    		     case 4:
	    		    	 Game.gui.gb_setSpriteImage(1, "bomberman125.png");
	    	              contador_sprite=0;
	    	              break;
	    		   
	    	   }
	    	   
	    	   }
	    	   else if(lastAction.equals("up")&&!Map.is_solid(this.X-4, this.Y-this.speed)) {
	           Game.gui.gb_moveSpriteCoord(1, this.X, this.Y=this.Y-this.speed);
	           
	           if(!(this.lastTecla==4))   //conprueba si la ultima tecla pulsada
	    		   this.contador_sprite=0;
	    	   
	    	       this.lastTecla=4;
	    		   switch(contador_sprite) {
	    		     case 0:
	    		    	  Game.gui.gb_setSpriteImage(1, "bomberman101.png");
	    	              contador_sprite++;
	    	              break;
	    		     case 1:
	    		    	 Game.gui.gb_setSpriteImage(1, "bomberman102.png");
	    	              contador_sprite++;
	    	              break;
	    		     case 2:
	    		    	 Game.gui.gb_setSpriteImage(1, "bomberman103.png");
	    		    	  contador_sprite++;
	    		    	  break;
	    		     case 3:
	    		    	  Game.gui.gb_setSpriteImage(1, "bomberman104.png");
	    		    	  contador_sprite++;
	    		          break;
	    		     case 4:
	    		    	  Game.gui.gb_setSpriteImage(1, "bomberman105.png");
	    	              contador_sprite=0;
	    	              break;
	           
	    		   }
	
	    	   }
	    	   //llama al metodo dejar bomba
	    	   if (lastAction.equals("space"))
				try {
					this.dejarBomba(this);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
	    	   //llama al metodo detonar_todas de Bomba que cambiará la variable detonar_todas a true
	    	   if (lastAction.equals("tab"))
	    		   Bomba.detonar_todas();
	    	   
	    	   
	    	   
	    	   
	    	
	    	}
	    	try {
				Thread.sleep(50L);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
	}
	
	public void dejarBomba(Player player)throws InterruptedException {
		
		if(player.bombs>0) {
			
			//numero_bomba será unico para cada bomba que haya en el tablero
			int numero_bomba=player.bombs_max-player.bombs;
			player.bombs--;
			Game.gui.gb_setValuePointsDown(bombs);
			
			//posicionar la bomba en el tablero, la posicion del jugador está a una escala 10 veces más grande que la bomba por lo que habrá que dividirla entre 10 para situarla correctamente
			int X2,Y2;
			if(player.X%10!=0)
				X2=player.X/10+1;
			else
				X2=player.X/10;
			
			if(player.Y%10!=0)
				Y2=player.Y/10+1;
			else
				Y2=player.Y/10;
			
			//se añade la bomba al array dependiendo de su numero_bomba
			player.array_bombas[numero_bomba]=new Bomba(X2,Y2,numero_bomba);
		    
		
			
		}
			
		
	}

}
