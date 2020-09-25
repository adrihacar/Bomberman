package bomberman.server.enemy;

import bomberman.Game;
import bomberman.server.Map;

public class Gota extends Enemigo implements Runnable{
    int numero_gota;
    static Thread[]threads=new Thread[10];
    int isprite=0;
    
   public Gota(int numero_gota)throws InterruptedException{
	   this.numero_gota=numero_gota;
	   this.id=735+this.numero_gota;
	   int x,y;
	   
	 //para que no aparezca encima de un ladrillo
	 		do{
	 		     x=(int)(Math.random()*16);
	 			 y=(int)(Math.random()*16);
	 			 
	 		}while(Map.is_solid(x*10, y*10)||(x*10==10&&(y*10==10||y*10==20||y*10==30)));
	 		
	 		
	 		Game.gui.gb_addSprite(this.id, "enemy200.png", false);
	 		Game.gui.gb_moveSprite(this.id, x, y);
	 		Game.gui.gb_setSpriteVisible(this.id, true);
	 		this.X=x*10;
	 		this.Y=y*10;
	 		
	 		threads[this.numero_gota]=new Thread(this);
	 		threads[this.numero_gota].start();
   }
   
   
   public void run() {
	   try {
	   this.move();
	   }
	   catch (InterruptedException e) {
			
			e.printStackTrace();
		}
   }
 
   
   public void move()throws InterruptedException{
	   
	   int move=0;
	   while(this.alive&&Game.player.alive) {
		   
	   //para decidir de forma aleatoria si se va a acercar al jugador disminuyendo la distancia en el eje de las x o en el de las y
	   int opcion_move=(int)(Math.random()*2);
	   switch(opcion_move) {
	       case 0:
	          if(this.X<Game.player.getX())
		           move=1;  //derecha
	          else
		           move=0;  //izquierda
	          break;
	          
	       case 1:
	    	   if(this.Y<Game.player.getY())
		           move=3;  //abajo
	          else
		           move=2;  //arriba
	          break; 
	   }
	 //con este switch animamos el sprite
	 		switch(this.isprite) {
	 		case 0:
	 	        Game.gui.gb_setSpriteImage(this.id, "enemy200.png");
	 	        this.isprite++;
	 	        break;
	 		case 1:
	 			 Game.gui.gb_setSpriteImage(this.id, "enemy211.png");
	 		        this.isprite++;
	 		        break;
	 		case 2:
	 		        Game.gui.gb_setSpriteImage(this.id, "enemy212.png");
	 		        this.isprite++;
	 		        break;
	 		case 3:
	 			    Game.gui.gb_setSpriteImage(this.id, "enemy213.png");
	 		        this.isprite=0;
	 		        break;
	 		
	 		}
		   
	   switch(move) {
	   case 0: //mover izquierda
		   if(!(Map.is_solid(this.X-2,this.Y))) {
			   Game.gui.gb_moveSpriteCoord(this.id, this.X-2, this.Y);
			   this.X=this.X-2;
			   }
		   break;

	   case 1://mover derecha
		   if(!(Map.is_solid(this.X+2,this.Y))) {
			   Game.gui.gb_moveSpriteCoord(this.id, this.X+2, this.Y);
			   this.X=this.X+2;
			   }
		   break;
		   
	   case 2://mover arriba
		   if(!(Map.is_solid(this.X,this.Y-2))) {
			   Game.gui.gb_moveSpriteCoord(this.id, this.X, this.Y-2);
			   this.Y=this.Y-2;
			   }
		   break;
		   
	   case 3://mover abajo
		   if(!(Map.is_solid(this.X,this.Y+2))) {
			   Game.gui.gb_moveSpriteCoord(this.id, this.X, this.Y+2);
			   this.Y=this.Y+2;
			   }
		   break;
	   
	   }
	   Thread.sleep(200L);
	   }
	   
	   
   }
}
