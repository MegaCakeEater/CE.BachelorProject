package student.sdu.hearingscreening.OneUpTwoDownTest;

import java.util.ArrayList;
import student.sdu.hearingscreening.R;
import student.sdu.hearingscreening.application.HearingScreeningApplication;

/**
 * Created by Chris on 13-03-2017.
 */

public class OneUpTwoDownTest
{
    private int[] files;
    private int dbhl;
    private int startDBHL = 40;
    private int testFreqNo = 0;
    private float phoneMaxDBOutput = 65.8f;
    private int sequenceTracker = 0;
    private ArrayList<TestEntry> entries;
    private int ear; // 0 Left, 1 Right
    private TestDTO testDTO;

    public OneUpTwoDownTest()
    {
        entries = new ArrayList();
        setupTracks();
        testDTO = new TestDTO();
        dbhl = startDBHL;
        ear = 0;
    }

    /**
     * Takes a "yes/no" answer in the form of true or false.
     * Creates a TestEntry object with the response, and adds it to the entries list,
     * then calls the checkThreeHits() method.
     * @param response
     */
    public boolean answer(Boolean response)
    {
        sequenceTracker++;
        TestEntry entry = new TestEntry(dbhl, response, sequenceTracker);
        entries.add(entry);

        return checkThreeHits(response);
    }

    /**
     * Checks the entries for the current DB Hearing Level (dbhl) and counts the positive results.
     * If there are 3, then checks if all the frequencies have been tested.
     * If all frequencies have been tested, the test is ended.
     * Else the next frequency is tested.
     * If there is not 3 positive results, the test goes on at the same frequency at a new dbhl.
     * @param response
     */
    private boolean checkThreeHits(Boolean response)
    {
        boolean testOver = false;
        //Get entries for current DB Hearing Level (dbhl), count the amount of positive responses
        int positiveResponses = 0;
        for(TestEntry entry : entries)
        {
            if(entry.getDbhl() == this.dbhl) {
                if(entry.getAnswer()) {
                    positiveResponses++;
                }
            }
        }

        //If 3 positive responses at current level, check if all frequencies have been tested.
        //if all are tested, end the test. Else continue the test at the next frequency.
        if(positiveResponses>=3)
        {
            if(ear == 1) //After a right ear test the next frequency is used, and we go back to the left ear.
            {
                testDTO.addEntryToRightEar(testFreqNo, entries);
                testDTO.addResultRightEar(testFreqNo, dbhl);
                testFreqNo++;
                ear = 0;
            }
            else //after a left ear test, go to right ear.
            {
                testDTO.addEntryToLeftEar(testFreqNo, entries);
                testDTO.addResultLeftEar(testFreqNo, dbhl);
                ear = 1;
            }

            if(testFreqNo >=7 && ear == 1) //All testing done, save results
            {
                testOver = true;
                TestDAO dao = new TestDAO(HearingScreeningApplication.getContext());
                dao.saveTest(testDTO);
            }
            else //If not, continue testing
            {
                dbhl = startDBHL;
                entries = new ArrayList();
                sequenceTracker = 0;
            }
        }
        //If there is not 3 positive responses at the current DB Hearing Level (dbhl), the test
        // continues at the same frequency at a new dbhl.
        else
        {
            if(response)
            {
                dbhl-=10;
            }
            else
            {
                dbhl+=5;
            }
        }
        return testOver;
    }

    /**
     * Instantiates the files[] array with the sound resources.
     */
    private void setupTracks() {
        files = new int[8];
        files[0] = R.raw.twohundredfifty;
        files[1] = R.raw.fivehundred;
        files[2] = R.raw.onek;
        files[3] = R.raw.twok;
        files[4] = R.raw.threek;
        files[5] = R.raw.fourk;
        files[6] = R.raw.sixk;
        files[7] = R.raw.eightk;
    }

    /**
     * Calculates the amplitude needed to hit the wanted DB.
     * Uses a float dbTarget as the wanted DB Hearing Level.
     * Uses a float maxPhoneOutput, which should be the DB value produced by the phone at max volume.
     * @return the amplitude as a float
     */
    public float getAmplitude()
    {
        float amplitude = (float) Math.pow(10,(dbhl-phoneMaxDBOutput)/20);
        return amplitude;
    }

    public int getSoundFile()
    {
        return files[testFreqNo];
    }
    public int getEar() {
        return  ear;
    }

    /**
     * For quick database testing only
     */
    public void testTest() {
        answer(true);
        answer(false);
        answer(false);
        answer(true);
        answer(false);
        answer(false);
        answer(true);
        TestDAO dao = new TestDAO(HearingScreeningApplication.getContext());
        dao.saveTest(testDTO);

    }
}
