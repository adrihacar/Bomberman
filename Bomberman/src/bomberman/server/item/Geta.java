package bomberman.server.item;

import bomberman.Game;

public class Geta extends Item{
	public int id=1001;
	public Geta(int x, int y) {
		this.x=x;
		this.y=y;
		//cambiando el tercer argumento a false, el elemento queda debajo del ladrillo, pero es conveniente tenerlo localizado para comprobar que el programa funciona
		Game.gui.gb_addSprite(this.id, "Getasprite.png", true);
		Game.gui.gb_moveSprite(this.id, x, y);
		Game.gui.gb_setSpriteVisible(this.id, false);
		
		//System.out.println(x+" "+y);
	}
	public Geta() {
		
	}
}
