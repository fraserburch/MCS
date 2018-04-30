import java.util.*;
import org.apache.commons.math3.distribution.GammaDistribution;


public class MCSim extends EquipObj{
    private static Scanner mcScan = new Scanner(System.in);

    private static double mean, SD, alpha, beta, ranNumber, failureRate, numberTimesRun, shutDown, activeRepairRate;
    private static int thisObj;
    static GammaDistribution gammaDis;
    static GammaDistribution repairDis;
    private static int failureCount;
    private static ArrayList<ArrayList<Integer>> daysOut = new ArrayList<ArrayList<Integer>>();
    private static ArrayList<ArrayList<Integer>> fails = new ArrayList<ArrayList<Integer>>();
    private static ArrayList<String> names = new ArrayList<String>();
    private static String name;
    private static int numDaysforRepair;


    public MCSim(int numYears, double mean, double sd, String objName, int objNum, double shutDownHours, double repairHrs){
        SDMean(mean, sd);
        names.add(objName);
        fails.add(new ArrayList<Integer>());
        daysOut.add(new ArrayList<Integer>());
        ranSampling(numYears, objNum, shutDownHours, repairHrs);
    }

    //Getters:

    public static String getName(int i){
        return names.get(i);
    }

    public static int sizeOfFailsArray(int objNum){
        return(fails.get(objNum).size());
    }

    public static int sizeOfRepairDaysArray(int objNum){
        return(daysOut.get(objNum).size());
    }

    public static int HoursOutofService(int objNum, int dayNum){
        return(daysOut.get(objNum).get(dayNum));
    }


    public static double FailorPass(int failItem, int year){
        return(fails.get(failItem).get(year));
        }

    public static double ranMCNum() {
        Random rand = new Random();
        double ranMC = rand.nextDouble();
        return (ranMC);
    }

    public static void SDMean(double thisMean, double sd){
        SD = sd;
        mean = thisMean;

        //Changing mean and SD to per day instead of per 10^6 hours
        mean = mean*Math.pow(10.0,-6.0)*24;
        SD = SD*Math.pow(10.0,-6.0)*24;

        //Setting alpha(shape parameter) and beta(scale parameter)
        setAlpha(SD, mean);
        setBeta(SD, mean);

        /*
        System.out.println("Enter the failure rate. This far right column in failure rate");
        double input = mcScan.nextDouble();

        failureRate = (input*Math.pow(10.0,-6.0)*24);
        */
    }

    //Setters:
    public static void setAlpha(double s, double m){
        double fraction = (m/s);
        alpha = (Math.pow(fraction, 2.0));
    }

    public static void setBeta(double s, double m){
        double numerator = Math.pow(s, 2.0);
        beta = (numerator/m);
    }

    public static GammaDistribution createDist(){
        return(new GammaDistribution(alpha, beta));
    }

    //Random Sampling from the Gamma Distribution
    public static void ranSampling(int years, int objNum, double downHours, double repairHours){
        double numYears = years;
        double numDays = numYears*365;
        thisObj = objNum;
        gammaDis = createDist();
        for (double i = 1; i <= numDays; i++) {
            numberTimesRun++;
            double sample = gammaDis.sample();
            ranNum();
            if(ranNumber < sample){
                fails.get(objNum).add(0);
                numDaysforRepair = repairTime(downHours, repairHours);
                daysOut.get(objNum).add(numDaysforRepair);
                i += numDaysforRepair;
            }
            else{
                fails.get(objNum).add(1);
                daysOut.get(objNum).add(0);
            }
        }
        /*
        System.out.println("Failed: " + failureCount + " in " + numberTimesRun + "days");
        double calculatedFailure = (failureCount/numberTimesRun);
        double expectedFailure = failureRate;
        System.out.println("The calculated failure rate per day is: " + calculatedFailure);
        System.out.println("The expected failure rate per day is: " + expectedFailure);
        System.out.println("The percent difference is: " + ((expectedFailure-calculatedFailure)/expectedFailure)*100);

    */
    }

    public static int repairTime(double downHours, double repairHours){
        int repairSample = (int)(downHours+repairHours);
        return(repairSample);
    }

    //Creating a random number between 1 and 0
    public static void ranNum(){
        ranNumber = Math.random();
    }


    //Create array of probabilities for an object

}
