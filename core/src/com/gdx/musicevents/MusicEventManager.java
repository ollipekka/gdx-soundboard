package com.gdx.musicevents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.musicevents.effects.Effect;

public class MusicEventManager {

    /**
     * Container for serialization purposes.
     */
    private static class Container {
        Array<MusicState> states;
        @SuppressWarnings("unused")
		public Container(){}
        private Container(Array<MusicState> states) {
            this.states = states;
        }
    }

    /**
     * The events that the manager is able to respond to.
     */
    private final ObjectMap<String, MusicState> states = new ObjectMap<String, MusicState>();

    /**
     * The current event.
     */
    private MusicState currentState;

    /**
     * Listeners in the event tool.
     */
    private final Array<MusicEventListener> listeners = new Array<MusicEventListener>();

    /**
     * The transitions in progress.
     */
    private final Array<Effect> transitions = new Array<Effect>();

    /**
     * Play the state using an enum.
     * 
     * @param stateName
     */
    public void play(Enum<?> stateName) {
        play(stateName.name());
    }

    public void play(String stateName) {
        MusicState nextState = states.get(stateName);
        play(nextState);
    }
    
    public void play(MusicState nextState){
        Gdx.app.log("MusicEventManager", "Play called next state " + nextState);
        
        if(currentState != null && !currentState.isPlaying()){
            currentState = null;
        }
        
        if(currentState != nextState){
            MusicState oldState = currentState;
            currentState = nextState;
            handleTransition(currentState, oldState);
        }
    }

    private void handleTransition(MusicState nextState, MusicState previousState){
        transitions.add(nextState.enter(previousState));
        if(previousState != null) {
            transitions.add(previousState.exit(nextState));
        }
    }

    /**
     * Update the music event manager.
     * @param dt The raw un-interpolated un-averaged delta time.
     */
    public void update(float dt){

        for(int i = transitions.size - 1; i >= 0 ; i--){
            Effect effect = transitions.get(i);
            effect.update(dt);

            if(effect.isDone()){
                transitions.removeIndex(i);
            }
        }

        if(currentState != null){
            currentState.update(dt);
        }
    }

    /**
     * Add a state.
     * @param state The state object.
     */
    public void add(MusicState state){
        state.init(this);
        this.states.put(state.getName(), state);
        for(int i = 0; i < listeners.size; i++){
            MusicEventListener observer = listeners.get(i);
            observer.eventAdded(state);
        }

    }

    /**
     * Remove an event.
     * @param state The state object.
     */
    public void remove(MusicState state){
        remove(state.getName());
    }
    /**
     * Remove an event.
     * @param stateName The name of the event.
     */
    public void remove(String stateName){
        MusicState event = this.states.remove(stateName);
        if(event != null) {

            for(ObjectMap.Entry<String, MusicState> entry : this.states){
                entry.value.removeEnterTransition(stateName);
                entry.value.removeExitTransition(stateName);
            }

            event.dispose();

            if(currentState == event){
                currentState = null;
            }
            for (int i = 0; i < listeners.size; i++) {
                MusicEventListener observer = listeners.get(i);
                observer.eventRemoved(event);
            }
        }
    }

    /**
     * Save to a file.
     * @param fileName The path to the file.
     */
    public void save(String fileName){
        FileHandle musicFile = new FileHandle(fileName);

        Json json = new Json(JsonWriter.OutputType.json);

        Container container = new Container(states.values().toArray());
        
        musicFile.writeString(json.prettyPrint(container), false);
    }

    /**
     * Load a save file.
     * @param fileName The path to the file.
     */
    public void load(String fileName){
        this.clear();
        Json json = new Json(JsonWriter.OutputType.json);

        FileHandle musicFile = Gdx.files.internal(fileName);
        Container container = json.fromJson(Container.class, musicFile.readString());
        for(int i = 0; i < container.states.size; i++){
            MusicState state = container.states.get(i);
            add(state);
        }
    }

    /**
     * Access the current state.
     * @return The state that is playing.
     */
    public MusicState getCurrentState() {
        return currentState;
    }

    /**
     * Set the current state.
     * @param musicState
     */
    public void setCurrentState(MusicState musicState) {
        MusicState oldState = currentState;
        this.currentState = musicState;
        this.handleTransition(this.currentState, oldState);
    }

    /**
     * Stop playing the current event.
     */
    public void stop() {
        if(this.currentState != null){
            currentState.stop();
        }
    }

    /**
     * Clear the manager. Removes all events.
     */
    public void clear() {
        Array<MusicState> stateArray = states.values().toArray();
        for(MusicState state : stateArray){
            state.dispose();
            for (int i = 0; i < listeners.size; i++) {
                MusicEventListener observer = listeners.get(i);
                observer.eventRemoved(state);
            }
        }
        states.clear();
    }

    /**
     * Add observer to the manager.
     * @param listener
     */
    public void addListener(MusicEventListener listener){
        this.listeners.add(listener);
    }

    /**
     * Remove observer from the manager.
     * @param listener
     */
    public void removeListener(MusicEventListener listener){
        this.listeners.removeValue(listener, true);
    }


    /**
     * Access all events in the system.
     * @return Copy of all events in the system.
     */
    public Array<MusicState> getEvents(){
        return states.values().toArray();
    }


}
