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
        Array<State> states;
        @SuppressWarnings("unused")
		public Container(){}
        private Container(Array<State> states) {
            this.states = states;
        }
    }

    /**
     * The events that the manager is able to respond to.
     */
    private final ObjectMap<String, State> states = new ObjectMap<String, State>();

    /**
     * The current event.
     */
    private State currentState;

    /**
     * Listeners in the event tool.
     */
    private final Array<MusicEventListener> listeners = new Array<MusicEventListener>();

    /**
     * The transitions in progress.
     */
    private final Array<Effect> transitions = new Array<Effect>();

    /**
     * Play the event using an enum.
     * 
     * @param eventName
     */
    public void play(Enum<?> eventName){
        play(eventName.name());
    }

    /**
     * Play an event.
     * @param eventName The name of the event.
     */
    public void play(String eventName){
        State nextState = states.get(eventName);
        if(nextState != null){

            if(currentState != nextState) {
                State oldState = currentState;
                currentState = nextState;

                handleTransition(nextState, oldState);
            } else {
                currentState.play();
            }
        }
    }


    private void handleTransition(State nextState, State previousState){
        transitions.add(nextState.enter(previousState));
        if(previousState != null) {
            transitions.add(previousState.exit(nextState));
        }
    }



    /**
     * 
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
    public void add(State state){
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
    public void remove(State state){
        remove(state.getName());
    }
    /**
     * Remove an event.
     * @param stateName The name of the event.
     */
    public void remove(String stateName){
        State event = this.states.remove(stateName);
        if(event != null) {

            for(ObjectMap.Entry<String, State> entry : this.states){
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
            State state = container.states.get(i);
            state.init();
            add(state);
        }
    }

    /**
     * Access the current event.
     * @return The event that is playing.
     */
    public State getCurrentEvent() {
        return currentState;
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
        Array<State> stateArray = states.values().toArray();
        for(State state : stateArray){
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
    public Array<State> getEvents(){
        return states.values().toArray();
    }

}
