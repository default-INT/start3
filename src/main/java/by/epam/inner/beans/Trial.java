package by.epam.inner.beans;

import java.util.Objects;

public class Trial {

    protected final static int SCORE_THRESHOLD = 100;

    private String account;
    private int mark1;
    private int mark2;

    public Trial() {

    }

    public Trial(Trial trial) {
        account = trial.account;
        mark1 = trial.mark1;
        mark2 = trial.mark2;
    }

    public void clearMarks() {
        mark1 = 0;
        mark2 = 0;
    }

    public boolean isResult() {
        return (getMark1() + getMark2()) >= SCORE_THRESHOLD;
    }

    public Trial copy() {
        return new Trial(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trial trial = (Trial) o;
        return mark1 == trial.mark1 &&
                mark2 == trial.mark2 &&
                Objects.equals(account, trial.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, mark1, mark2);
    }

    public int getLink() {return super.hashCode();}

    @Override
    public String toString() {
        return "{" +
                "class: \"" + this.getClass().getSimpleName() + "\", " +
                argsToString() +
                "}";
    }

    protected String argsToString() {
        return "args: {" +
                "account: \"" + account + "\", " +
                "mark1: " + mark1 + ", " +
                "mark2: " + mark2 +
                "}";
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setMark1(int mark1) {
        this.mark1 = mark1;
    }

    public void setMark2(int mark2) {
        this.mark2 = mark2;
    }

    public String getAccount() {
        return account;
    }

    public int getMark1() {
        return mark1;
    }

    public int getMark2() {
        return mark2;
    }

    public String toCsv() {
        return this.getClass().getSimpleName() + ";" + getAccount() + ";" + getMark1() + ";" + getMark2();
    }
}
