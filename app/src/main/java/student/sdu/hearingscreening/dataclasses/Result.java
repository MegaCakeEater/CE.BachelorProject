package student.sdu.hearingscreening.dataclasses;

/**
 * Created by Bogs on 20-03-2017.
 */

public class Result {

    private int threshold;
    private int ear;
    private String frequency;
    private int frequencyId;

    public Result(int threshold, int ear, String frequency, int frequencyId) {
        this.threshold = threshold;
        this.ear = ear;
        this.frequency = frequency;
        this.frequencyId = frequencyId;
    }

    public int getThreshold() {
        return threshold;
    }

    public int getEar() {
        return ear;
    }

    public String getFrequency() {
        return frequency;
    }

    public int getFrequencyId() {
        return frequencyId;
    }
}