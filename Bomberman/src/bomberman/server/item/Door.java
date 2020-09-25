package bomberman.server.item;
import bomberman.Game;
public class Door extends Item {
	int id=1000;
	public Door(int x, int y) {
		this.x=x;
		this.y=y;
		//cambiando el tercer argumento a false, el elemento queda debajo del ladrillo, pero es conveniente tenerlo localizado para comprobar que el programa funciona
		Game.gui.gb_addSprite(this.id, "DoorClosed.png", true); 
		Game.gui.gb_moveSprite(this.id, x, y);
		Game.gui.gb_setSpriteVisible(this.id, false);
		
		//System.out.println(x+" "+y);
	}
}
