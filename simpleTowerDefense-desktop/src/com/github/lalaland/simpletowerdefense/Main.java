package com.github.lalaland.simpletowerdefense;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "simpleTowerDefense";
		cfg.useGL20 = false;
		cfg.width = 1600;
		cfg.height = 960;
		
		new LwjglApplication(new TowerGame(), cfg);
	}
}
