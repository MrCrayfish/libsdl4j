package org.libsdl.api.rwops;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import org.intellij.lang.annotations.MagicConstant;
import org.libsdl.jna.JnaStructure;

import static org.libsdl.api.rwops.SDL_RWopsType.SDL_RWOPS_JNIFILE;
import static org.libsdl.api.rwops.SDL_RWopsType.SDL_RWOPS_MEMORY;
import static org.libsdl.api.rwops.SDL_RWopsType.SDL_RWOPS_MEMORY_RO;
import static org.libsdl.api.rwops.SDL_RWopsType.SDL_RWOPS_STDFILE;
import static org.libsdl.api.rwops.SDL_RWopsType.SDL_RWOPS_UNKNOWN;
import static org.libsdl.api.rwops.SDL_RWopsType.SDL_RWOPS_VITAFILE;
import static org.libsdl.api.rwops.SDL_RWopsType.SDL_RWOPS_WINFILE;

@Structure.FieldOrder({
        "size",
        "seek",
        "read",
        "write",
        "close",
        "type",
        "hidden"
})
public final class SDL_RWops extends JnaStructure {

    public SDL_RWSizeFunction size;
    public SDL_RWSeekFunction seek;
    public SDL_RWReadFunction read;
    public SDL_RWWriteFunction write;
    public SDL_RWCloseFunction close;
    @MagicConstant(valuesFromClass = SDL_RWopsType.class)
    public int type;
    public SDL_RWopsPlatformSpecific hidden;

    @Override
    public void read() {
        readField("type");
        switch (type) {
            case SDL_RWOPS_UNKNOWN:
            default:
                hidden.setType(SDL_RWopsUnknownIO.class);
                break;
            case SDL_RWOPS_WINFILE:
                hidden.setType(SDL_RWopsWindowsIO.class);
                break;
            case SDL_RWOPS_STDFILE:
                hidden.setType(SDL_RWopsStdIO.class);
                break;
            case SDL_RWOPS_JNIFILE:
                hidden.setType(SDL_RWopsAndroidIO.class);
                break;
            case SDL_RWOPS_MEMORY:
            case SDL_RWOPS_MEMORY_RO:
                hidden.setType(SDL_RWopsMemoryIO.class);
                break;
            case SDL_RWOPS_VITAFILE:
                hidden.setType(SDL_RWopsVitaIO.class);
                break;
        }
        super.read();
    }
}