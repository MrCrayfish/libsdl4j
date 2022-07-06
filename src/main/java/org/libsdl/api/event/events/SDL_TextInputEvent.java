package org.libsdl.api.event.events;

import com.sun.jna.Structure;
import org.intellij.lang.annotations.MagicConstant;
import org.libsdl.jna.JnaStructure;

import static org.libsdl.api.event.SDL_EventType.SDL_TEXTINPUT;

@Structure.FieldOrder({
        "type",
        "timestamp",
        "windowID",
        "text"
})
public final class SDL_TextInputEvent extends JnaStructure {

    private static final int SDL_TEXTINPUTEVENT_TEXT_SIZE = 32;

    @MagicConstant(intValues = SDL_TEXTINPUT)
    public int type;
    public int timestamp;
    public int windowID;
    public byte[] text = new byte[SDL_TEXTINPUTEVENT_TEXT_SIZE];        // TODO: byte, char or String?
}