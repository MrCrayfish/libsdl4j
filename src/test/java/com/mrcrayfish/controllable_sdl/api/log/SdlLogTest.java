package com.mrcrayfish.controllable_sdl.api.log;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import com.mrcrayfish.controllable_sdl.api.video.SDL_Window;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import static com.mrcrayfish.controllable_sdl.api.Sdl.SDL_InitSubSystem;
import static com.mrcrayfish.controllable_sdl.api.Sdl.SDL_QuitSubSystem;
import static com.mrcrayfish.controllable_sdl.api.SdlSubSystemConst.SDL_INIT_EVERYTHING;
import static com.mrcrayfish.controllable_sdl.api.error.SdlError.SDL_GetError;
import static com.mrcrayfish.controllable_sdl.api.log.SDL_LogCategory.SDL_LOG_CATEGORY_ASSERT;
import static com.mrcrayfish.controllable_sdl.api.log.SDL_LogPriority.SDL_LOG_PRIORITY_INFO;
import static com.mrcrayfish.controllable_sdl.api.log.SDL_LogPriority.SDL_LOG_PRIORITY_VERBOSE;
import static com.mrcrayfish.controllable_sdl.api.log.SdlLog.SDL_LogGetOutputFunction;
import static com.mrcrayfish.controllable_sdl.api.log.SdlLog.SDL_LogMessage;
import static com.mrcrayfish.controllable_sdl.api.log.SdlLog.SDL_LogSetAllPriority;
import static com.mrcrayfish.controllable_sdl.api.log.SdlLog.SDL_LogSetOutputFunction;
import static com.mrcrayfish.controllable_sdl.api.video.SDL_WindowFlags.SDL_WINDOW_SHOWN;
import static com.mrcrayfish.controllable_sdl.api.video.SdlVideo.SDL_CreateWindow;
import static com.mrcrayfish.controllable_sdl.api.video.SdlVideo.SDL_DestroyWindow;

public final class SdlLogTest {

    @BeforeEach
    public void setUp() {
        SDL_InitSubSystem(SDL_INIT_EVERYTHING);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "true")
    public void registerLogCallback() {
        PointerByReference originalLogFunction = new PointerByReference();
        PointerByReference originalUserData = new PointerByReference();
        SDL_LogGetOutputFunction(originalLogFunction, originalUserData);
        SDL_LogSetOutputFunction(SdlLog::routeSdlLoggingToSlf4j, Pointer.NULL);
        SDL_LogSetAllPriority(SDL_LOG_PRIORITY_VERBOSE);

        SDL_Window window = SDL_CreateWindow("Test window", 200, 250, 400, 300, SDL_WINDOW_SHOWN);
        if (window == null) {
            throw new AssertionError("SDL Failure: " + SDL_GetError());
        }

        SDL_LogMessage(SDL_LOG_CATEGORY_ASSERT, SDL_LOG_PRIORITY_INFO, "Sample message %d, %d", 10, 20);

        SDL_DestroyWindow(window);

        SDL_LogSetOutputFunction(originalLogFunction.getValue(), originalUserData.getValue());
    }

    @AfterEach
    public void tearDown() {
        SDL_QuitSubSystem(SDL_INIT_EVERYTHING);
    }
}
