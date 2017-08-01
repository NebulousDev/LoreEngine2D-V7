package loreEngine.core;

import org.lwjgl.glfw.GLFW;

import loreEngine.Info;
import loreEngine.core.graphics.DisplayType;
import loreEngine.core.graphics.Window;
import loreEngine.utils.Log;
import loreEngine.utils.LogLevel;
import loreEngine.utils.Time;

public abstract class Game {
	
	public enum GameStatus {
		RUNNING,
		PAUSED,
		STOPPED
	}
	
	protected Window window;
	protected int tps = 16;
	protected int fps = 60;
	
	public int activeFPS;
	public double activeMS;
	
	protected GameStatus status;
	
	public Game() {
		this.window = Window.createWindow("LoreEngine2D - Test", 640, 480, DisplayType.WINDOWED, true);
		Info.printOpener(this);
	}
	
	public Game(Window window) {
		this.window = window;
		Info.printOpener(this);
	}

	public void start() {
		Log.logln(LogLevel.INFO, "Game is initializing...");
		init();
		Log.logln(LogLevel.INFO, "Game is starting...");
		onStart();
		Log.logln(LogLevel.INFO, "Game is running...");
		run();
	}
	
	public void stop() {
		Log.logln(LogLevel.INFO, "Game is stopping...");
		onStop();
		GLFW.glfwTerminate();
		Log.logln(LogLevel.INFO, "Game has stopped.");
		System.exit(0);
	}

	private void run() {
		
		status = GameStatus.RUNNING;
		
		double oldTime = 0.0;
		double newTime = 0.0;
		double delta   = 0.0;
		
		double startTime = Time.getTimeNanoSeconds();
		
		double frameTime  = 0.0;
		double tickTime = 0.0;
		
		double msStart = 0.0;
		double msEnd   = 0.0;
		
		int frames = 0;
		int tick = 0;
		
		double elapsedTick = 0.0;
		double elapsedFrame = 0.0;
		
		while(true) {
			
			window.clear();
			
			if(GLFW.glfwWindowShouldClose(window.getGLWindowID())) break;
			
			oldTime = newTime;
			newTime = Time.getTimeNanoSeconds();
			delta	= newTime - oldTime;
			
			elapsedFrame = ((newTime - startTime) / 1000000000) - frameTime;
			
			if(elapsedFrame > 1.0) {
				frameTime += 1.0;
				activeFPS = (int)frames;
				frames = 0;
			}
			
			elapsedTick = ((newTime - startTime) / 1000000000) - tickTime;
			
			if(elapsedTick > 1.0 / (double)tps) {
				tickTime += 1.0 / (double)tps;
				tick++;
				tick(tick, tps);
				if(tick >= tps) tick = 0;
			}
			
			update(delta);
			
			msStart = Time.getTimeMiliSeconds();
			
			render();
			
			msEnd = Time.getTimeMiliSeconds();
			activeMS = msEnd - msStart;
			
			frames++;
			
			window.update();
			
		}
		
		stop();
	}
	
	public abstract void init();
	
	public abstract void onStart();
	
	public abstract void update(double delta);
	
	public abstract void tick(int tick, int tock);
	
	public abstract void onStop();
	
	public abstract void render();
	
	public Window getWindow() {
		return window;
	}
	
	public int getActiveFPS() {
		return activeFPS;
	}
	
	public double getActiveMS() {
		return activeMS;
	}

}
