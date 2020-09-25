package bomberman.server;
import bomberman.Game;
import bomberman.server.enemy.*;
import bomberman.server.item.*;
public class Map {
	 static Square[][]square;  //es el tablero del juego
     public static int level=1;
     public static Enemigo[]arrayEnemigos;
     public static Door door;
     public static Geta geta;
     public static Patines patines;
     public static BonusBomb[]bonusBomb;
	
	public  Map(int level) throws InterruptedException  {
		Map.level=level;
		square=new Square[17][17];
		//crea la estructura de las paredes
		int f=10; //El id ya que es unico, para el caso de las paredes fijas empiza en 10 hasta 123
		for(int i=0;i<17;i++) {
			for(int c=0;c<17;c++) {
				
				//para crear las paredes que rodean el tablero
				if(i==0||i==16||c==0||c==16) {
				Game.gui.gb_addSprite(f, "wall.gif", true);
				Game.gui.gb_moveSprite(f, i, c);
				Game.gui.gb_setSpriteVisible(f, true);
		        square[i][c]=new Square();
		        square[i][c].nombre_elemento="wall";
				square[i][c].existe=true;
				square[i][c].id=f;
				f++;
				}
				else {
					//para crear las paredes que est�n dentro del tablero
					if(c%2==0&&i%2==0) {
						Game.gui.gb_addSprite(f, "wall.gif", false);
						Game.gui.gb_moveSprite(f, i, c);
						Game.gui.gb_setSpriteVisible(f, true);
						square[i][c]=new Square();
					    square[i][c].nombre_elemento="wall";
					    square[i][c].existe=true;
					    square[i][c].id=f;
						f++;
					}
				}
				
			}
		}
		//este bucle crea los 50 ladrillos de cada mapa, su id(id) empieza en 200 hasta 250
		
		for(int id=200;id<=250;id++) {
			int x,y;
			do{
				
		     x=(int)(Math.random()*16);
			 y=(int)(Math.random()*16);
			}while(Map.is_solid(x*10, y*10)||(x*10==10&&(y*10==10||y*10==20||y*10==30))); //para no crear un ladrillo en un solido(donde hay otro ladrillo o pared) o en las proximidades de donde aparece el jugador
			
			Game.gui.gb_addSprite(id, "bricks.gif", true);
			Game.gui.gb_moveSprite(id, x, y);
			Game.gui.gb_setSpriteVisible(id, true);
			square[x][y]=new Square();
		    square[x][y].nombre_elemento="brick";
		    square[x][y].existe=true;
		    square[x][y].id=id;
		}
		
		//GENERA EL NUMERO DE GLOBOS Y GOTAS POR CADA NIVEL
		int globos=(int)(Math.random()*9+1); //se crear�n entre 1 y 10 globos
		int num_gotas=0;
		
		if((level-2)%4==0) {  //las gotas empiezan a aparecer a partir del nivel 2 y cada 4 niveles hay una m�s
			num_gotas=((level-2)/4)+1;
		}
		
		//creamos un array con todos los enemigos
		Map.arrayEnemigos=new Enemigo[globos+num_gotas];
		Enemigo.numeroEnemigos=globos+num_gotas;
		for(int i=0;globos>i;i++) {
				Map.arrayEnemigos[i]=new Globo(i);
		}
		//i se inicializa con el valor de los globos para que recorra los enemigos tipo Gota que quedan y no los Globos otra vez
		for(int i=globos;Enemigo.numeroEnemigos>i;i++) {
			Map.arrayEnemigos[i]=new Gota(i);
	}
	    
		
		
		
		
		//el siguiente codigo se encarga de personalizar cada nivel, creando los enemigos e items correspondientes
		Game.gui.gb_setValueLevel(level);
		int x,y, prob;
		
		//puerta
		do {
		   do {
		    x=(int)(Math.random()*17);
		    y=(int)(Math.random()*17); 
		     }while(Map.square[x][y]==null);  //para que el programa no lance NullPointerException
		}while((Map.square[x][y].nombre_elemento.equals("wall"))||(Map.square[x][y].item)); //crear debajo de un ladrillo
		door=new Door(x,y);
		Map.square[x][y].item=true;
		
		//Geta
        prob=(int)(Math.random()*5);
		if(prob==0) {
			do {
			   do {
		      x=(int)(Math.random()*17);
			  y=(int)(Math.random()*17);
			   }while(Map.square[x][y]==null);
			}while(Map.square[x][y].nombre_elemento.equals("wall")||Map.square[x][y].item); 
			geta=new Geta(x,y);
			Map.square[x][y].item=true;
		}
		else
			geta=new Geta();
		
		//Patines
		if(level%2==0) {
			prob=(int)(Math.random()*2); //habra un 50% de posibilidades de que aparezca
			if(prob==0) {
				do {
					do {
				  x=(int)(Math.random()*17);
			      y=(int)(Math.random()*17);
					}while(Map.square[x][y]==null);
			   }while((Map.square[x][y].nombre_elemento.equals("wall"))||(Map.square[x][y].item)); 
			   patines=new Patines(x,y);
			   Map.square[x][y].item=true;
			}
			
		}
		else
			patines=new Patines();
		
		//BonusBomb
		prob=(int)(Math.random()*3); //puede haber entre 0 y 2 por cada nivel
		Map.bonusBomb=new BonusBomb[prob];
		for(int i=0;prob>i;i++) {
			do {
				do {
			  x=(int)(Math.random()*17);
		      y=(int)(Math.random()*17);
				}while(Map.square[x][y]==null);
		   }while((Map.square[x][y].nombre_elemento.equals("wall"))||(Map.square[x][y].item)); 
			Map.bonusBomb[i]=new BonusBomb(i,x,y);
	    }
			
		
	}
	//devuelve true si hay algun elemento solido (en escala peque�a)
	public static boolean is_solid(int x,int y) { 
		if(x%10>0)
			x=x+10;
		if(y%10>0)
			y=y+10;
			
		if(Map.square[x/10][y/10]==null) {
			return false;
		}
		else
			return true;
		
	}
	
}
