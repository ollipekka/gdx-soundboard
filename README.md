gdx-soundboard v0.3
==============

![Screenshot](http://i.imgur.com/B28wFEg.png)

### The Problem

This tool aims to improve the workflow between game programmer and the audio engineer. gdx-soundboard addresses the problem where audio-engineers want to describe events and event transitions without touching code. gdx-soundboard solves with easy-to-use GUI and the ability to export json.

## The Workflow
    * Your audio guy creates music tracks for the game.
    * Your audio guy specifies transitions between these tracks.
    * Your audio guy saves the track by exporting the json.
    * You import the json in your game using the library.
    * You fire events and the music automatically transitions using the defined transitions.

### Supports:

Standard libgdx formats: ogg, wav and mp3.

Supported effects:
- Fade in / out
- Stop / Play

### Contribute

Need an effect? Want to fix a bug? Want to change the format? Pull requests and issues are welcome.

### ToDo v0.3
- Cleanup (done)
- Configure to remember song position when switching between states. (done)
- Configure loop songs vs iterating the lists. (Loops song if only item)
- Remove Match Position. (Done)
- Proper save and load dialogs.
- Fix bugs with resume position.


### Credits

The example audio tracks were created by @Sweeppah (https://twitter.com/Sweeppah)


http://forums.tigsource.com/index.php?topic=45088.0
