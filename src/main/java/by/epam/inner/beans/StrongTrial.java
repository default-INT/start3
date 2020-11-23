package by.epam.inner.beans;

public class StrongTrial extends Trial {

    public StrongTrial() {
    }

    public StrongTrial(StrongTrial trial) {
        super(trial);
    }

    @Override
    public StrongTrial copy() {
        return new StrongTrial(this);
    }

    @Override
    public boolean isResult() {
        return getMark1() >= (SCORE_THRESHOLD - getMark2()) >> 1;
    }
}
