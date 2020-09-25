package bomberman.server.item;

import bomberman.Game;

public class BonusBomb extends Item{
	public int id;
	public BonusBomb(int numero_bomba, int x, int y) {
		this.x=x;
		this.y=y;
		this.id=1004+numero_bomba;
		Game.gui.gb_addSprite(this.id, "Bombupsprite.png", true);
		Game.gui.gb_moveSprite(this.id, x, y);
		Game.gui.gb_setSpriteVisible(this.id, false);
	}
}
