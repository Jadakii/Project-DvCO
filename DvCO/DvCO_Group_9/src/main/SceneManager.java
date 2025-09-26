package main;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Item;
import model.User;

public class SceneManager {
	Stage stage;
	public HashMap<String, Scene> scenes;
	private HashMap<String, Object> scenesObject;
	private User loggedInUser;

	public SceneManager(Stage stage) {
	   this.stage = stage;
	   this.scenes = new HashMap<>();
	   this.scenesObject = new HashMap<>();
	   this.loggedInUser = new User();
	}
	
	public void setLoggedInUser(User loginUser) {
		this.loggedInUser = loginUser;
	}
	public User getLoggedInUser() {
		return this.loggedInUser;
	}
	
	public void addScene(String name, Scene scene, Object sceneObject) {
		if(scene != null && sceneObject != null) {
			scenes.put(name, scene);			
			scenesObject.put(name, sceneObject);			
		}
	}
	
	public void changeScene(String name) {
		stage.setScene(scenes.get(name));
	}
	
	public Object getScenesObject(String name) {
		 Object sceneObject = scenesObject.get(name);
	        if (sceneObject == null) {
	            System.err.println("SceneObject for " + name + " is null.");
	        }
	        return sceneObject;
	}
	
	

}
