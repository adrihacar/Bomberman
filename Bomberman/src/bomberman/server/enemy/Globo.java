package bomberman.server.enemy;

import bomberman.Game;
import bomberman.server.Map;
public class Globo extends Enemigo implements Runnable{
	 int numero_globo; //numero que se le asigna a cada globo del 1 al 10 para tener en cuenta su posicion en el tablero y el Thread que le corresponde
     int move=0;  //variable que indica a donde se a a mover el sprite de forma aleatoria, en la primera llamada 0(izquierda)
     static Thread[]threads=new Thread[10];
     int isprite=0; //para determinar el sprite que se est� usando en cada momento
	public Globo(int numero_globo) throws InterruptedException {
		this.numero_globo=numero_globo;
		int x,y;
		this.id=255+this.numero_globo;
		
		
		//lo colocamos en tablero de forma aleatoria
		do{
		     x=(int)(Math.random()*16);
			 y=(int)(Math.random()*16);
			 
		}while(Map.is_solid(x*10, y*10)||(x*10==10&&(y*10==10||y*10==20||y*10==30)));
		//pero si hay una pared o ladrillo busca otro de forma aleatoria
		
		Game.gui.gb_addSprite(this.id, "enemy100.png", true);
		Game.gui.gb_moveSprite(this.id, x, y);
		Game.gui.gb_setSpriteVisible(this.id, true);
		this.X=x*10;
		this.Y=y*10;
		
		//creamos e inciamos el Thread correspondiente al Globo
		threads[this.numero_globo]=new Thread(this);
		threads[this.numero_globo].start();
		
	}
	//tan solo llama al m�todo move
	public void run() {
		try {
			this.move();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	public void move() throws InterruptedException {
		while(this.alive&&Game.player.alive) {
			
		int imove=(int)(Math.random()*10);
		//hay un 70% de probabilidad para que cambie de direcci�n
		if (imove >7)//generamos de forma aleatoria a donde se va a mover en el caso de que tenga cambiar de direcci�n
			this.move=(int)(Math.random()*4);
		
		//con este switch animamos el sprite
		switch(this.isprite) {
		case 0:
	        Game.gui.gb_setSpriteImage(this.id, "enemy100.png");
	        this.isprite++;
	        break;
		case 1:
			 Game.gui.gb_setSpriteImage(this.id, "enemy121.png");
		        this.isprite++;
		        break;
		case 2:
		        Game.gui.gb_setSpriteImage(this.id, "enemy122.png");
		        this.isprite++;
		        break;
		case 3:
			    Game.gui.gb_setSpriteImage(this.id, "enemy123.png");
		        this.isprite=0;
		        break;
		
		}
		switch(this.move) {
		   case 0: //mover izquierda
			   if(!(Map.is_solid(this.X-2,this.Y))) {
				   Game.gui.gb_moveSpriteCoord(this.id, this.X-2, this.Y);
				   this.X=this.X-2;
				   }
			   else {
				   this.move=(int)(Math.random()*4);
				   this.move();
			   }
			   break;

		   case 1://mover derecha
			   if(!(Map.is_solid(this.X+2,this.Y))) {
				   Game.gui.gb_moveSpriteCoord(this.id, this.X+2, this.Y);
				   this.X=this.X+2;
				   }
			   else {
				   this.move=(int)(Math.random()*4);
				   this.move();
			   }
			   break;
			   
		   case 2://mover arriba
			   if(!(Map.is_solid(this.X,this.Y-2))) {
				   Game.gui.gb_moveSpriteCoord(this.id, this.X, this.Y-2);
				   this.Y=this.Y-2;
				   }
			   else {
				   this.move=(int)(Math.random()*4);
				   this.move();
			   }
			   break;
			   
		   case 3://mover abajo
			   if(!(Map.is_solid(this.X,this.Y+2))) {
				   Game.gui.gb_moveSpriteCoord(this.id, this.X, this.Y+2);
				   this.Y=this.Y+2;
				   }
			   else {
				   this.move=(int)(Math.random()*4);
				   this.move();
			   }
			   break;
			   
			
		}
		
		Thread.sleep(200L);
		}
	
	}
	
	
}
