package by.epam.inner.beans;

public class StrongTrial extends Trial {

    public StrongTrial() {
    }

    @Override
    public StrongTrial clone() {
        return (StrongTrial) super.clone();
    }

    @Override
    public boolean isResult() {
        return getMark1() >= (SCORE_THRESHOLD - getMark2()) >> 1;
    }
}
