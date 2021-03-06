package com.github.lalaland.simpletowerdefense.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.lalaland.simpletowerdefense.GameState;
import com.github.lalaland.simpletowerdefense.Resources;

public class Enemy {
    ResultNode lastPosition;
    float position;
	public int positionsGoneThrough;
	
	private float health = 100;
	
	
	boolean dead;
	public int poisons;
	public int slows;
	public int explosions;
	
	public boolean isDead()
	{
		return dead;
	}
    
    public Enemy(ResultNode startingPosition,float startingPos) {
        lastPosition = startingPosition;
        position = startingPos;
        positionsGoneThrough = 0;
    }
    
    void update(float time)
    {
    	
    	if (dead)
    		return;
    	
        position+= time* 3/(slows+1);
        
        damage(poisons*10*time);
        
        if (position > 1)
        {
            lastPosition = lastPosition.next;
            position-=1;
            positionsGoneThrough++;
        }
        
        if (isOffScreen())
        {
        	 GameState.getInstance().reduceHealth();
        	 dead = true;
        }
        
        
    }
    
   

	public float getX()
    {
        return lastPosition.x + (lastPosition.next.x - lastPosition.x) * position;
    }
    
    public float getY()
    {
        return lastPosition.y + (lastPosition.next.y - lastPosition.y) * position;
    }
    
    private boolean isOffScreen()
    {
        return lastPosition.next == null;
    }

	public float getTotalDistance() {
		// TODO Auto-generated method stub
		return positionsGoneThrough + position;
	}

	public void render(SpriteBatch batch, ShapeRenderer sRender) {
		
		if (dead)
			return;
		
		if (poisons>0 && slows>0)
			batch.draw(Resources.regularEnemyPoisonSlow,getX(),getY(),1,1,0,1,1,0);
		else if (poisons>0)
			batch.draw(Resources.regularEnemyPoison,getX(),getY(),1,1,0,1,1,0);
		else if (slows>0)
			batch.draw(Resources.regularEnemySlow,getX(),getY(),1,1,0,1,1,0);
		else
			batch.draw(Resources.regularEnemy,getX(),getY(),1,1,0,1,1,0);
		
		
		
		batch.end();
		
		
		
		float greenWidth = health/100.0f;
		
		sRender.begin(ShapeType.Filled);
		sRender.setColor(Color.BLACK);
		sRender.rect(getX(), getY()-.15f, 1, .1f);
		sRender.setColor(Color.RED);
		sRender.rect(getX()+.05f, getY()-.125f, .9f, .05f);
		sRender.setColor(Color.GREEN);
		sRender.rect(getX()+.05f, getY()-.125f, .9f * greenWidth, .05f);
		
		if (explosions>0)
		{
			sRender.setColor(1,0,0,.5f);
			sRender.circle(getX()+.5f, getY() +.5f, 2);
		
		}
		
		sRender.end();
		

		
		batch.begin();
	}

	public void damage(float f) {
		health -= f;
		if (health <= 0)
		{
			GameState.getInstance().money+=10;
			dead = true;
		}
			
		
	}
    
	
    
}
