package by.epam.inner.beans;

public class LightTrial extends Trial {
    private final static int MARK1_THRESHOLD = 60;
    private final static int MARK2_THRESHOLD = 70;

    public LightTrial() {
    }

    public LightTrial(LightTrial trial) {
        super(trial);
    }

    @Override
    public LightTrial copy() {
        return new LightTrial(this);
    }

    @Override
    public boolean isResult() {
        return getMark1() >= MARK1_THRESHOLD && getMark2() >= MARK2_THRESHOLD;
    }
}
