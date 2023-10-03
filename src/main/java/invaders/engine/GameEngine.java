package invaders.engine;

import java.util.Random;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import invaders.GameObject;
import invaders.builder.*;
import invaders.entities.Player;
import invaders.factory.Bullet;
import invaders.factory.PlayerBullet;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.strategy.EnemyBullet;
import invaders.strategy.FastBullet;
import invaders.strategy.SlowBullet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class manages the main loop and logic of the game
 * TODO make EnemyBullet hit condition
 * TODO reload enemy bullets
 */
public class GameEngine {
	private List<EnemyBullet> enemyBullet;
	private List<Alien> aliens;
	private List<GameObject> gameobjects;
	private List<Renderable> renderables;

	private int total_alien_bullets;
	private Player player;
	private double Xplayer;
	private double Yplayer;
	private long width;
	private long height;
	private boolean left;
	private boolean right;

	public GameEngine(String config){
		gameobjects = new ArrayList<GameObject>();
		renderables = new ArrayList<Renderable>();
		aliens = new ArrayList<Alien>();

		total_alien_bullets =2;
		// read the config here
		JSONParser parser = new JSONParser();
		AlienBuilder alienBuild = new AlienBuilder();
		Director director = new Director();
		BunkerBuilder bunkerBuild = new BunkerBuilder();

		try {
			FileReader reader = new FileReader(config);
			Object object = parser.parse(reader);

			// convert Object to JSONObject
			JSONObject jsonObject = (JSONObject) object;
			JSONObject player = (JSONObject) jsonObject.get("Player");
			Long player_X = (Long) ((JSONObject) player.get("position")).get("x");
			Long player_Y = (Long) ((JSONObject) player.get("position")).get("y");
			Xplayer = player_X.doubleValue();
			Yplayer = player_Y.doubleValue();
			// reading the Game section:
			JSONObject jsonGame = (JSONObject) jsonObject.get("Game");

			// reading a coordinate from the nested section within the game
			// note that the game x and y are of type Long (i.e. they are integers)
			Long gameX = (Long) ((JSONObject) jsonGame.get("size")).get("x");
			Long gameY = (Long) ((JSONObject) jsonGame.get("size")).get("y");
			setGameWidth(gameX);
			setGameHeight(gameY);
			// reading the "Enemies" array:
			JSONArray jsonEnemies = (JSONArray) jsonObject.get("Enemies");
			JSONArray jsonBunkers = (JSONArray) jsonObject.get("Bunkers");
			// reading from the array:
			for (Object obj : jsonEnemies) {
				JSONObject jsonEnemy = (JSONObject) obj;

				Long positionX = (Long) ((JSONObject) jsonEnemy.get("position")).get("x");
				Long positionY = (Long) ((JSONObject) jsonEnemy.get("position")).get("y");
				Double x = positionX.doubleValue();
				Double y = positionY.doubleValue();

				director.setPosition(new Vector2D(x,y));

				String projectileStrategy = (String) jsonEnemy.get("projectile");
				if (projectileStrategy.equals("slow_straight")){
					EnemyBullet slow = new SlowBullet();
					director.setBullet(slow);

				}
				else if (projectileStrategy.equals("fast_straight")){
					EnemyBullet fast = new FastBullet();
					director.setBullet(fast);
				}
				director.constructAlienBuilder(alienBuild);
				Alien alien = alienBuild.getAlien();
				renderables.add(alien);
				gameobjects.add(alien);
				aliens.add(alien);
			}

			for (Object obj: jsonBunkers){
				JSONObject jsonBunker = (JSONObject) obj;

				Long positionX = (Long) ((JSONObject) jsonBunker.get("position")).get("x");
				Long positionY = (Long) ((JSONObject) jsonBunker.get("position")).get("y");
				Double x = positionX.doubleValue();
				Double y = positionY.doubleValue();
				director.setPosition(new Vector2D(x,y));
//				bunkerBuild.setPosition(new Vector2D(x,y));

				Long width =  (Long) ((JSONObject) jsonBunker.get("size")).get("x");
				Long height =  (Long) ((JSONObject) jsonBunker.get("size")).get("y");
				Double width_X = width.doubleValue();
				Double height_Y = height.doubleValue();
				director.setWidth(width_X);
				director.setHeight(height_Y);
//				bunkerBuild.setWidth(width_X);
//				bunkerBuild.setHeight(height_Y);
				director.constructBunkerBuilder(bunkerBuild);
				Bunker bunker = bunkerBuild.getBunker();
				renderables.add(bunker);
				gameobjects.add(bunker);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		player = new Player(new Vector2D(Xplayer, Yplayer));

		renderables.add(player);

	}

	/**
	 * Updates the game/simulation
	 */
	public void update(){
		movePlayer();
		if (!player.isAlive()){
			System.exit(0);
		}
		alienShoots();
//		for(GameObject go: gameobjects){
//			go.update();
//			if (go instanceof EnemyBullet){
//				if (go.getPosition().getY()==getGameHeight()){
//					go.takeDamage(-1);
//				}
//			}
//			for (GameObject col: gameobjects){
//				gameOver(go);
//				bulletHitsPlayer(go);
//				if (go.equals(col) == false&& go.isAlive()&&col.isAlive()){
//					if (go.isColliding(col)){
//						collisionOccurs(go,col);
//					}
//				}
//			}
//		}
//		for (GameObject go: gameobjects){
//			go.update();
//			if (go instanceof EnemyBullet){
//				if (go.getPosition().getY()>=getGameHeight()){
//					go.takeDamage(-1);
//				}
//			}
//			for (GameObject col: gameobjects){
//				gameOver(go);
//				bulletHitsPlayer(go);
//				if (go.equals(col)==false && go.isAlive()&&col.isAlive()){
//					if (go.isColliding(col)&& !isBunker(go)&&alienHitsAlienBullet(go,col)){
//						collisionOccurs(go,col);
//					}
//				}
//			}
//		}

		for (GameObject go: gameobjects){
			go.update();
			for (GameObject col: gameobjects){
				gameOver(go);
				bulletHitsPlayer(go);
				//EnemyBullet hits another EnemyBullet
				if (go.isColliding(col)){
					if (isEnemyBullet(go)&& !isEnemyBullet(col)){
						//when it hits the bottom screen
//						enemyBulletHitsFloor(go);

						//EnemyBullet hits bunker
						enemyBulletHitsBunker(go,col);

						alienHitsAlienBullet(go,col);

						bulletHitsPlayer(go);
					}

					if (isAlien(go)){
						alienHitsBunker(go,col);
					}

					playerBulletHitsBunker(go,col);
					playerBulletHitsAlien(go,col);
					playerBulletHitsEnemyBullet(go,col);
				}
			}
		}

		moveAliens();
		findAlien();
		// ensure that renderable foreground objects don't go off-screen
		for(Renderable ro: renderables){

			if(!ro.getLayer().equals(Renderable.Layer.FOREGROUND)){
				continue;
			}
			if(ro.getPosition().getX() + ro.getWidth() >= getGameWidth()) {
				ro.getPosition().setX(getGameWidth()-ro.getWidth());
			}

			if(ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(0);
			}

			if(ro.getPosition().getY() + ro.getHeight() >= getGameHeight()) {
				if (ro instanceof EnemyBullet){
					ro.takeDamage(-2);
				}else{
					ro.getPosition().setY(getGameHeight()-ro.getHeight());
				}

			}

			if(ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(1);
			}
		}
	}
	public void setGameWidth(long i){
		width = i;
	}

	public long getGameWidth(){
		return width;
	}

	public void setGameHeight(long i){
		height = i;
	}

	public long getGameHeight(){
		return height;
	}

	public List<Renderable> getRenderables(){
		return renderables;
	}

	public void leftReleased() {
		this.left = false;
	}

	public void rightReleased(){
		this.right = false;
	}

	public void leftPressed() {
		this.left = true;
	}
	public void rightPressed(){
		this.right = true;
	}

	public boolean shootPressed(){
		player.shoot();
		if (player.getShoot()==false){
			Bullet bullet = player.createBullet();
			gameobjects.add(bullet);
			renderables.add(bullet);
			player.setShoot(true);
		}
		return true;
	}

	public void moveAliens(){
		for (Alien ro : aliens){
			if(ro.getPosition().getX() + ro.getWidth() > getGameWidth()-2) {
				for (Alien a : aliens){
					a.down();
					a.onRightScreen();
				}
			}
			if(ro.getPosition().getX()-2 <= 0) {
				for (Alien a : aliens){
					a.down();
					a.onLeftScreen();
				}
			}
		}
	}

	public void spawnPlayer(){
		player.takeDamage(-1);
		System.out.println(player.getHealth());
		if (player.isAlive()){
			player.getPosition().setX(Xplayer);
			player.getPosition().setY(Yplayer);
		}
		else{
			System.exit(0);
		}
	}

	private void movePlayer(){
		if(left){
			player.left();
		}
		if(right){
			player.right();
		}
	}

	//how to end game
	private void gameOver(GameObject go){
		if (isAlien(go)){
			if (go.getPosition().getY() + go.getHeight() >= getGameHeight()){
				System.exit(0);
			}
			if (go.isColliding(player)){
				System.exit(0);
			}
		}
	}

	//when bullet hits player
	private void bulletHitsPlayer(GameObject go){
//		if (go instanceof EnemyBullet){
//			if (go.isColliding(player)){
//				spawnPlayer();
//				go.takeDamage(-1);
//			}
//		}
		if (go.isColliding(player)){
			spawnPlayer();
			go.takeDamage(-1);

		}
	}

	private boolean alienOrBunker(GameObject go, GameObject col){
		return (isAlien(go) && isBunker(col)||isBunker(go) && col instanceof Alien );
	}

	private void alienHitsBunker(GameObject go, GameObject col){
		if (isBunker(col)){
			col.takeDamage(-4);
		}
		else if (isBunker(go)){
			go.takeDamage(-4);
		}
	}

	private void collisionOccurs(GameObject go, GameObject col){
		//Alien hits bunker, destroy bunker
		if (alienOrBunker(go,col)){
			alienHitsBunker(go,col);
		}
		else if(isEnemyBullet(go)&& isBunker(col)){
			enemyBulletHitsBunker(go,col);
		}
		else{
			go.takeDamage(-1);
			col.takeDamage(-1);
		}
	}

	public void findAlien(){
		int counter = 0;
		for (int i = 0; i<gameobjects.size();i++){
			if (!gameobjects.get(i).isAlive()){
				if (isAlien(gameobjects.get(i))){
					counter++;
				}

				if (gameobjects.get(i) instanceof PlayerBullet){
					player.setShoot(false);
				}

				if (isEnemyBullet(gameobjects.get(i))){
					total_alien_bullets++;
				}
				gameobjects.remove(gameobjects.get(i));
			}
		}
		alienDie(counter);
	}

	public void alienDie(int counter){
		if (counter>0){
			for (Alien a: aliens){
				a.setSpeed();
			}
		}
	}


	public void alienShoots(){
		Random random = new Random();
		for (Alien a: aliens){
			int randomInt = random.nextInt(100);
			if (randomInt==90&& total_alien_bullets>0){
				EnemyBullet bullet = a.firesBullet();
				gameobjects.add(bullet);
				renderables.add(bullet);
				total_alien_bullets--;
			}
		}
	}

	public boolean alienHitsAlienBullet(GameObject go, GameObject col){
//		if(isEnemyBullet(go) && isEnemyBullet(col)){
//			return false;
//		}

		if( isAlien(go)&& isEnemyBullet(col) || isEnemyBullet(go)  && isAlien(col)){
			return false;
		}
		return true;
	}

	public void enemyBulletHitsBunker(GameObject go, GameObject col){
		if (isEnemyBullet(go)&& isBunker(col)&& go.isAlive()){
			col.takeDamage(-1);
			go.takeDamage(-1);
		}
	}

	public void enemyBulletHitsFloor(GameObject go){
		System.out.println(go.getPosition().getY());
		System.out.println(getGameHeight()-60- go.getHeight()-go.getPosition().getY());
//		if (getGameHeight()-60- go.getHeight()-go.getPosition().getY()<=110){
//			go.takeDamage(-1);
//			System.out.println("floor");
//		}
	}

	public void playerBulletHitsBunker(GameObject go, GameObject col){
		if (isPlayerBullet(go)&& isBunker(col)){
			go.takeDamage(-1);
			col.takeDamage(-1);
		}
	}

	public void playerBulletHitsAlien(GameObject go, GameObject col){
		if (isPlayerBullet(go)&& isAlien(col)){
			go.takeDamage(-1);
			col.takeDamage(-1);
		}
	}

	public void playerBulletHitsEnemyBullet(GameObject go, GameObject col){
		if (isPlayerBullet(go)&& isEnemyBullet(col)|| isEnemyBullet(go)&& isPlayerBullet(col)){
			go.takeDamage(-1);
			col.takeDamage(-1);
		}
	}

	public boolean isEnemyBullet(GameObject go){
		return (go instanceof EnemyBullet);
	}

	public boolean isBunker(GameObject go){
		return (go instanceof Bunker);
	}

	public boolean isAlien(GameObject go){
		return (go instanceof Alien);
	}

	public boolean isPlayerBullet(GameObject go){
		return (go instanceof PlayerBullet);
	}
}