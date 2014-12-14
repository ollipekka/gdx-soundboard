gdx-soundboard
==============

![Screenshot](http://i.imgur.com/TD3zVSR.png)

### The Problem

This tool aims to improve the workflow between game programmer and the audio engineer. gdx-soundboard addresses the problem where audio-engineers want to describe events and event transitions without touching code. gdx-soundboard solves with easy-to-use GUI and the ability to export json.

## The Workflow
    * Your audio guy creates music tracks.
    * Your audio guy specifies transitions between these tracks.
    * Your audio guy saves the tracks, and the json that defines the transitions.
    * You import the json in your game using the library.
    * You fire events and the music automatically transitions using the defined transitions.

### Supports:

Standard libgdx formats: ogg, wav and mp3.

Supported effects:
- Fade in / out
- Stop / Play

Match position to seamlessly transition between tracks.

### Contribute

Need an effect? Want to fix a bug? Want to change the format? Pull requests and issues are welcome.

### ToDo
- Refactor MusicEvent to MusicState which can hold multiple tracks.

### Credits

The example audio tracks were created by @Sweeppah (https://twitter.com/Sweeppah)


http://forums.tigsource.com/index.php?topic=45088.0
