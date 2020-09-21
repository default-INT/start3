package by.epam.inner.beans;

import java.util.Objects;

public class ExtraTrial extends Trial {
    private final static int EXTRA_SCORE_THRESHOLD = 80;
    private int mark3;

    public ExtraTrial() {
    }

    public ExtraTrial(ExtraTrial extraTrial) {
        super(extraTrial);
        mark3 = extraTrial.mark3;
    }

    @Override
    public ExtraTrial copy() {
        return new ExtraTrial(this);
    }

    @Override
    public void clearMarks() {
        super.clearMarks();
        mark3 = 0;
    }

    @Override
    public boolean isResult() {
        return super.isResult() && getMark3() >= EXTRA_SCORE_THRESHOLD;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ExtraTrial that = (ExtraTrial) o;
        return mark3 == that.mark3;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mark3);
    }

    public void setMark3(int mark3) {
        this.mark3 = mark3;
    }

    public int getMark3() {
        return mark3;
    }

    @Override
    protected String argsToString() {
        return"args: {" +
                "account: \"" + getAccount() + "\", " +
                "mark1: " + getMark1() + ", " +
                "mark2: " + getMark2() + ", " +
                "mark3: " + getMark3() +
                "}";
    }
}
