import java.util.*;

public class EquipObj extends SystemRunner{

    private static ArrayList<MCSim> MCList = new ArrayList<MCSim>();
    private static int NumberofBarrels;

    public EquipObj(){

    }


    public EquipObj(String name, int barrels, double sd, double mean, int numYears, int objNum, double shutDwnHrs, double repairHours){
        String EquipmentName = name;
        NumberofBarrels = barrels;
        double equipSD = sd;
        double equipMean = mean;
        MCList.add(new MCSim(numYears, mean, sd, name, objNum, shutDwnHrs, repairHours));
    }

    public static double getFailRate(int item, int year){
        return(MCList.get(item).FailorPass(item, year));

    }

    public static String getName(int item){
        return(MCList.get(item).getName(item));
    }

    public static int getNumDays(int item){
        return(MCList.get(item).sizeOfFailsArray(item));
    }

    public static int getNumRepairDays(int item, int day){
            return (MCList.get(item).HoursOutofService(item, day));

    }

    public static int repairArraySize(int item){
        return (MCList.get(item).sizeOfRepairDaysArray(item));
    }

    public static int getBarrels(){
        return(NumberofBarrels);
    }








}
