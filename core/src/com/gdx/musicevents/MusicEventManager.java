package com.gdx.musicevents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;

public class MusicEventManager {


    private static class Container {
        Array<MusicEvent> events;

        public Container(){}
        private Container(Array<MusicEvent> events) {
            this.events = events;
        }
    }

    private final ObjectMap<String, MusicEvent> events = new ObjectMap<String, MusicEvent>();

    private MusicEvent currentEvent;

    private final Array<MusicEventListener> listeners = new Array<MusicEventListener>();


    private final Array<Effect> transitions = new Array<Effect>();


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


    public void handleTransition(MusicEvent currentEvent, MusicEvent oldEvent){
        transitions.add(currentEvent.startTransition(oldEvent));
        if(oldEvent != null) {
            transitions.add(oldEvent.endTransition(currentEvent));
        }
    }

    public void play(Enum<?> eventName){
        play(eventName.name());
    }


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

    public void add(MusicEvent event){
        this.events.put(event.getName(), event);
        for(int i = 0; i < listeners.size; i++){
            MusicEventListener observer = listeners.get(i);
            observer.eventAdded(event);
        }

    }

    public void remove(MusicEvent event){
        remove(event.getName());
    }
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

    public void save(String fileName){
        FileHandle musicFile = Gdx.files.internal(fileName);
        Json json = new Json(JsonWriter.OutputType.json);

        Container container = new Container(getEvents());



        musicFile.writeString(json.prettyPrint(container), false);


    }

    public void load(String fileName){

        Json json = new Json(JsonWriter.OutputType.json);

        FileHandle musicFile = Gdx.files.internal(fileName);
        Container container = json.fromJson(Container.class, musicFile.readString());
        for(int i = 0; i < container.events.size; i++){
            MusicEvent event = container.events.get(i);
            event.init();
            add(event);
        }


    }

    public MusicEvent getCurrentEvent() {
        return currentEvent;
    }

    public void stop() {
        if(this.currentEvent != null){
            currentEvent.getMusic().stop();
        }
    }

    public void clear() {
        for(MusicEvent event : events.values()){
            event.dispose();
            for (int i = 0; i < listeners.size; i++) {
                MusicEventListener observer = listeners.get(i);
                observer.eventRemoved(event);
            }

        }
        events.clear();
    }

    public void addListener(MusicEventListener listener){
        this.listeners.add(listener);
    }

    public void removeListener(MusicEventListener listener){
        this.listeners.removeValue(listener, true);
    }


    public Array<MusicEvent> getEvents(){
        return events.values().toArray();
    }



/*

    public void createSerializer(){
        Json json = new Json();
        json.setSerializer(MusicEvent.class, new Json.Serializer<MusicEvent>() {
            public void write (Json json, MusicEvent event, Class knownType) {
                json.writeObjectStart();
                json.writeValue("name", event.getName());
                json.writeObjectEnd();
            }

            public MusicEvent read (Json json, JsonValue jsonData, Class type) {

                String name = jsonData.child.name();

                MusicEvent event = new MusicEvent(name, );
                event.setName(jsonData.child().name());
                event.setNumber(jsonData.child().asString());
                return number;
            }
        });
        json.setElementType(Person.class, "numbers", PhoneNumber.class);
        String text = json.prettyPrint(person);
        System.out.println(text);
        Person person2 = json.fromJson(Person.class, text);
    }
*/
}
