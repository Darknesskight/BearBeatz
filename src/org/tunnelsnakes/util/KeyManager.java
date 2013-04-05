package org.tunnelsnakes.util;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.state.StateBasedGame;
import org.tunnelsnakes.states.BearState;

public class KeyManager implements KeyListener{

	private StateBasedGame game;
	public KeyManager(GameContainer gc, StateBasedGame game){
		this.game = game;
		gc.getInput().addKeyListener(this);
	}
	
	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int key, char c) {
		System.out.println("key Pressed");
		BearState temp = (BearState) game.getCurrentState();
		temp.handleKeyPress(key);
	}

	@Override
	public void keyReleased(int key, char c) {
		System.out.println("key Released");
		BearState temp = (BearState) game.getCurrentState();
		temp.handleKeyRelease(key);
		
	}

}
