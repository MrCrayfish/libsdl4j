package com.mrcrayfish.controllable_sdl.api.syswm;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.mrcrayfish.controllable_sdl.api.version.SDL_version;
import org.intellij.lang.annotations.MagicConstant;

import static com.mrcrayfish.controllable_sdl.api.syswm.SDL_SYSWM_TYPE.SDL_SYSWM_ANDROID;
import static com.mrcrayfish.controllable_sdl.api.syswm.SDL_SYSWM_TYPE.SDL_SYSWM_COCOA;
import static com.mrcrayfish.controllable_sdl.api.syswm.SDL_SYSWM_TYPE.SDL_SYSWM_DIRECTFB;
import static com.mrcrayfish.controllable_sdl.api.syswm.SDL_SYSWM_TYPE.SDL_SYSWM_KMSDRM;
import static com.mrcrayfish.controllable_sdl.api.syswm.SDL_SYSWM_TYPE.SDL_SYSWM_UIKIT;
import static com.mrcrayfish.controllable_sdl.api.syswm.SDL_SYSWM_TYPE.SDL_SYSWM_VIVANTE;
import static com.mrcrayfish.controllable_sdl.api.syswm.SDL_SYSWM_TYPE.SDL_SYSWM_WAYLAND;
import static com.mrcrayfish.controllable_sdl.api.syswm.SDL_SYSWM_TYPE.SDL_SYSWM_WINDOWS;
import static com.mrcrayfish.controllable_sdl.api.syswm.SDL_SYSWM_TYPE.SDL_SYSWM_WINRT;
import static com.mrcrayfish.controllable_sdl.api.syswm.SDL_SYSWM_TYPE.SDL_SYSWM_X11;

/**
 * The custom window manager information structure.
 *
 * <p>When this structure is returned, it holds information about which
 * low level system it is using, and will be one of SDL_SYSWM_TYPE.</p>
 *
 * <p>Your application has access to a special type of event {@code SDL_SYSWMEVENT},
 * which contains window-manager specific information and arrives whenever
 * an unhandled window event occurs.  This event is ignored by default, but
 * you can enable it with {@link com.mrcrayfish.controllable_sdl.api.event.SdlEvents#SDL_EventState(int, int)}.</p>
 */
@Structure.FieldOrder({
        "version",
        "subsystem",
        "info"
})
public final class SDL_SysWMInfo extends Structure {

    public SDL_version version;
    @MagicConstant(valuesFromClass = SDL_SYSWM_TYPE.class)
    public int subsystem;
    public SDL_SysWMInfoPlatformSpecific info;

    public SDL_SysWMInfo() {
    }

    public SDL_SysWMInfo(SDL_version version) {
        this.version = version;
    }

    public SDL_SysWMInfo(Pointer p, SDL_version version) {
        super(p);
        this.version = version;
    }

    @Override
    public void read() {
        readField("subsystem");
        switch (subsystem) {
            case SDL_SYSWM_WINDOWS:
                info.setType(SDL_SysWMInfoWindows.class);
                break;
            case SDL_SYSWM_WINRT:
                info.setType(SDL_SysWMInfoWinRT.class);
                break;
            case SDL_SYSWM_X11:
                info.setType(SDL_SysWMInfoX11.class);
                break;
            case SDL_SYSWM_DIRECTFB:
                info.setType(SDL_SysWMInfoDirectFB.class);
                break;
            case SDL_SYSWM_COCOA:
                info.setType(SDL_SysWMInfoCocoa.class);
                break;
            case SDL_SYSWM_UIKIT:
                info.setType(SDL_SysWMInfoUIKit.class);
                break;
            case SDL_SYSWM_WAYLAND:
                info.setType(SDL_SysWMInfoWayland.class);
                break;
            case SDL_SYSWM_ANDROID:
                info.setType(SDL_SysWMInfoAndroid.class);
                break;
            case SDL_SYSWM_VIVANTE:
                info.setType(SDL_SysWMInfoVivante.class);
                break;
            case SDL_SYSWM_KMSDRM:
                info.setType(SDL_SysWMInfoKmsDrm.class);
                break;
            default:
                info.setType(SDL_SysWMInfoDummy.class);
                break;
        }
        super.read();
    }
}
