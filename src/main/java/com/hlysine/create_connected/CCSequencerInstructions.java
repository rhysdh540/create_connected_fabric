package com.hlysine.create_connected;

import com.simibubi.create.content.kinetics.transmission.sequencer.SequencerInstructions;
import org.jetbrains.annotations.NotNull;

public class CCSequencerInstructions {
    @NotNull
    public static SequencerInstructions TURN_AWAIT;
    @NotNull
    public static SequencerInstructions TURN_TIME;
    @NotNull
    public static SequencerInstructions LOOP;

    static {
        for (SequencerInstructions value : SequencerInstructions.values()) {
			switch(value.name()) {
				case "TURN_AWAIT" -> TURN_AWAIT = value;
				case "TURN_TIME" -> TURN_TIME = value;
				case "LOOP" -> LOOP = value;
			}
        }
    }
}
