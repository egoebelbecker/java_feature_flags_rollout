package io.rollout.example;

import io.rollout.configuration.RoxContainer;
import io.rollout.flags.RoxFlag;
import lombok.Data;

/**
 * COntainer for Rollout Feature Flags
 */
@Data
public class Flags implements RoxContainer {
    public RoxFlag holidaySeason = new RoxFlag();
}
