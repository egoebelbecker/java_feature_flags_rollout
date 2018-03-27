package io.rollout;

import io.rollout.configuration.RoxContainer;
import io.rollout.flags.RoxFlag;
import lombok.Data;

@Data
public class Flags implements RoxContainer {
    public RoxFlag holidaySeason = new RoxFlag();
}
