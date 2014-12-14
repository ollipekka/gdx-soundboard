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
        Array<MusicEvent> events;
        @SuppressWarnings("unused")
		public Container(){}
        private Container(Array<MusicEvent> events) {
            this.events = events;
        }
    }

    /**
     * The events that the manager is able to respond to.
     */
    private final ObjectMap<String, MusicEvent> events = new ObjectMap<String, MusicEvent>();

    /**
     * The current event.
     */
    private MusicEvent currentEvent;

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
        MusicEvent nextEvent = events.get(eventName);
        if(nextEvent != null){

            if(currentEvent != nextEvent) {
                MusicEvent oldEvent = currentEvent;
                currentEvent = nextEvent;

                handleTransition(currentEvent, oldEvent);
            } else {
                currentEvent.getMusic().play();
            }
        }
    }


    private void handleTransition(MusicEvent currentEvent, MusicEvent oldEvent){
        transitions.add(currentEvent.startTransition(oldEvent));
        if(oldEvent != null) {
            transitions.add(oldEvent.endTransition(currentEvent));
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

        if(currentEvent != null){
            currentEvent.update(dt);
        }
    }

    /**
     * Add an event.
     * @param event The event object.
     */
    public void add(MusicEvent event){
        this.events.put(event.getName(), event);
        for(int i = 0; i < listeners.size; i++){
            MusicEventListener observer = listeners.get(i);
            observer.eventAdded(event);
        }

    }

    /**
     * Remove an event.
     * @param event The event object.
     */
    public void remove(MusicEvent event){
        remove(event.getName());
    }
    /**
     * Remove an event.
     * @param eventName The name of the event.
     */
    public void remove(String eventName){
        MusicEvent event = this.events.remove(eventName);
        if(event != null) {

            for(ObjectMap.Entry<String, MusicEvent> entry : this.events){
                entry.value.removeInTransition(eventName);
                entry.value.removeOutTransition(eventName);
            }

            event.dispose();

            if(currentEvent == event){
                currentEvent = null;
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

        Container container = new Container(getEvents());
        
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
        for(int i = 0; i < container.events.size; i++){
            MusicEvent event = container.events.get(i);
            event.init();
            add(event);
        }
    }

    /**
     * Access the current event.
     * @return The event that is playing.
     */
    public MusicEvent getCurrentEvent() {
        return currentEvent;
    }

    /**
     * Stop playing the current event.
     */
    public void stop() {
        if(this.currentEvent != null){
            currentEvent.getMusic().stop();
        }
    }

    /**
     * Clear the manager. Removes all events.
     */
    public void clear() {
        Array<MusicEvent> eventArray = events.values().toArray();
        for(MusicEvent event : eventArray){
            event.dispose();
            for (int i = 0; i < listeners.size; i++) {
                MusicEventListener observer = listeners.get(i);
                observer.eventRemoved(event);
            }

        }
        events.clear();
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
    public Array<MusicEvent> getEvents(){
        return events.values().toArray();
    }

}
