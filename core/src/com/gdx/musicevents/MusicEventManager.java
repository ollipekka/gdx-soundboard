package com.gdx.musicevents;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.musicevents.effects.StartEffect;
import com.gdx.musicevents.effects.StopEffect;

public class MusicEventManager {

    /**
     * Container for serialization purposes.
     */
    private static class Container {
        
        transient String basePath = "./";
        Array<MusicState> states;

        public Container() {
        }

        private Container(Array<MusicState> states) {
            this.states = states;
        }
    }
    
    private Container container = new Container();

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
    private final Array<StartEffect> startTransitions = new Array<StartEffect>();
    
    private StartEffect currentStartEffect;
    

    private final Array<StopEffect> endTransitions = new Array<StopEffect>();
    private StopEffect currentEndEffect;

    /**
     * Play the state using an enum.
     * 
     * @param stateName
     */
    public void play(Enum<?> stateName) {
        play(stateName.name());
    }

    public void play(String stateName) {
        final MusicState nextState = states.get(stateName);
        play(nextState);
    }

    public void play(MusicState nextState) {
        if (currentState != null && !currentState.isPlaying()) {
            currentState = null;
        }

        if (currentState != nextState) {
            final MusicState oldState = currentState;
            currentState = nextState;
            handleTransition(currentState, oldState);
        }
    }

    private void handleTransition(MusicState nextState, MusicState previousState) {
        startTransitions.add(nextState.enter(previousState));
        if (previousState != null) {
            endTransitions.add(previousState.exit(nextState));
        }

        for (int i = 0; i < listeners.size; i++) {
            final MusicEventListener observer = listeners.get(i);
            observer.stateChanged(nextState, previousState);
        }
    }

    /**
     * Update the music event manager.
     * 
     * @param dt
     *            The raw un-interpolated un-averaged delta time.
     */
    public void update(float dt) {
        if(currentStartEffect != null){
            
            currentStartEffect.update(dt);
            if (currentStartEffect.isDone()) {
                currentStartEffect = null;
            }
        } else if(startTransitions.size > 0) {
            currentStartEffect = startTransitions.first();
            startTransitions.removeIndex(0);
            currentStartEffect.beginStart();
        }
        
        if(currentEndEffect != null){
            currentEndEffect.update(dt);
            if (currentEndEffect.isDone()) {
                currentEndEffect = null;
            }
        } else if(endTransitions.size > 0) {
            currentEndEffect = endTransitions.first();
            currentEndEffect.beginStop();
            endTransitions.removeIndex(0);
        }
        
        
        if (currentState != null) {
            currentState.update(dt);
        }
    }

    /**
     * Add a state.
     * 
     * @param state
     *            The state object.
     */
    public void add(MusicState state) {
        state.init(container.basePath);
        this.states.put(state.getName(), state);
        for (int i = 0; i < listeners.size; i++) {
            final MusicEventListener observer = listeners.get(i);
            observer.stateAdded(state);
        }
    }

    /**
     * Remove an event.
     * 
     * @param state
     *            The state object.
     */
    public void remove(MusicState state) {
        remove(state.getName());
    }

    /**
     * Remove an event.
     * 
     * @param stateName
     *            The name of the event.
     */
    public void remove(String stateName) {
        final MusicState event = this.states.remove(stateName);
        if (event != null) {

            for (final ObjectMap.Entry<String, MusicState> entry : this.states) {
                entry.value.removeEnterTransition(stateName);
                entry.value.removeExitTransition(stateName);
            }

            event.dispose();

            if (currentState == event) {
                currentState = null;
            }
            for (int i = 0; i < listeners.size; i++) {
                final MusicEventListener observer = listeners.get(i);
                observer.stateRemoved(event);
            }
        }
    }

    /**
     * Create a new project.
     * 
     * @param handle The base path.
     */
    public void create(FileHandle handle){
        this.clear();
        container = new Container();
        container.basePath = handle.path();
    }
    
    /**
     * Save to a file.
     * 
     * @param fileName
     *            The path to the file.
     */
    public void save(FileHandle file) {

        final Json json = new Json(JsonWriter.OutputType.json);

        final Container container = new Container(states.values().toArray());

        file.writeString(json.prettyPrint(container), false);
    }

    /**
     * Load a save file.
     * 
     * @param fileName
     *            The path to the file.
     */
    public void load(FileHandle file) {
        this.clear();
        final Json json = new Json(JsonWriter.OutputType.json);

        container = json.fromJson(Container.class,
                file.readString());

        String path = file.path();
        int lastSlash = path.lastIndexOf("/");
        container.basePath =  path.substring(0, lastSlash);
        
        for (int i = 0; i < container.states.size; i++) {
            final MusicState state = container.states.get(i);
            add(state);
        }
    }

    /**
     * Access the current state.
     * 
     * @return The state that is playing.
     */
    public MusicState getCurrentState() {
        return currentState;
    }

    /**
     * Set the current state.
     * 
     * @param musicState
     */
    public void setCurrentState(MusicState musicState) {
        final MusicState oldState = currentState;
        this.currentState = musicState;
        this.handleTransition(this.currentState, oldState);
    }

    /**
     * Stop playing the current event.
     */
    public void stop() {
        if (this.currentState != null) {
            currentState.stop();
        }
    }

    /**
     * Clear the manager. Removes all events.
     */
    public void clear() {
        final Array<MusicState> stateArray = states.values().toArray();
        for (final MusicState state : stateArray) {
            state.dispose();
            for (int i = 0; i < listeners.size; i++) {
                final MusicEventListener observer = listeners.get(i);
                observer.stateRemoved(state);
            }
        }
        states.clear();
    }

    /**
     * Add observer to the manager.
     * 
     * @param listener
     */
    public void addListener(MusicEventListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Remove observer from the manager.
     * 
     * @param listener
     */
    public void removeListener(MusicEventListener listener) {
        this.listeners.removeValue(listener, true);
    }

    /**
     * Access all events in the system.
     * 
     * @return Copy of all events in the system.
     */
    public Array<MusicState> getEvents() {
        return states.values().toArray();
    }

    public String getBasePath() {
        return container.basePath;
    }

}
