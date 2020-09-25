package bomberman.server.item;

import bomberman.Game;

public class Patines extends Item{
	public int id=1002;
	public Patines(int x, int y) {
		this.x=x;
		this.y=y;
		//cambiando el tercer argumento a false, el elemento queda debajo del ladrillo, pero es conveniente tenerlo localizado para comprobar que el programa funciona
		Game.gui.gb_addSprite(this.id, "Skatesprite.png", true);
		Game.gui.gb_moveSprite(this.id, x, y);
		Game.gui.gb_setSpriteVisible(this.id, false);
		
		//System.out.println(x+" "+y);
	}
	public Patines() {
		
	}
}
