import java.util.*;

public class SystemRunner {
    //Data
    private static Scanner ObjScanner = new Scanner(System.in);
    private static int numEquip;
    private static ArrayList<EquipObj> equipment = new ArrayList<EquipObj>();
    private static int numberOfYears;
    private static double numDaysOutofService;
    private static int eqNum, day, dayToYear, tempDay;
    private static int numberOfFails;
    private static double sum;
    //Data



    //Main
    public static void main(String[]args){
        creatingObj();
        printFail();
        //printName();

    }

    public static void creatingObj() {
        System.out.println("How many items of equipment do you want for the simulation?");
        numEquip = ObjScanner.nextInt();
        //To clear a bug:
        ObjScanner.nextLine();

        System.out.println("How many years do you want to run the simulation for?");
        numberOfYears = ObjScanner.nextInt();

            for(int i = 0; i < numEquip; i++){
                int equipmentNumber = (i+1);
                //To clear a bug:
                ObjScanner.nextLine();
                System.out.println("Enter the name of equipment item #" + equipmentNumber + ".");
                String equipName = ObjScanner.nextLine();
                System.out.println("How many barrels of oil in a day does the " + equipName + " produce?");
                int numBarrels = ObjScanner.nextInt();
                System.out.println("Enter the mean failure rate.");
                double meanFail = ObjScanner.nextDouble();
                System.out.println("Enter the standard deviation failure rate.");
                double sdFail = ObjScanner.nextDouble();
                System.out.println("Enter the active repair hours.");
                double activeRepairHrs = ObjScanner.nextDouble();
                System.out.println("Enter the time, in hours, taken to shut down the piece of equipment.");
                double shutDwnHrs = ObjScanner.nextDouble();
                equipment.add(new EquipObj(equipName, numBarrels, sdFail, meanFail, numberOfYears, i, shutDwnHrs, activeRepairHrs));
                System.out.println("");
            }
    }

    public static void printFail() {
        for (int i = 0; i < equipment.size(); i++) {
            for (int j = 0; j < equipment.get(i).getNumDays(i); j++) {
                eqNum = i;
                day = (j + 1);
                if (equipment.get(eqNum).getFailRate(i, j) == 0) {
                    numberOfFails++;
                    if (day > 365) {
                        dayToYear = (day / 365);
                        tempDay = day + 1 - (dayToYear * 365);
                       // System.out.println("The " + equipment.get(eqNum).getName(eqNum) + " Failed in year: " + dayToYear + " \ton Day: " + tempDay);
                        daysOutofService(eqNum, j);

                    } else {
                        //System.out.println("The " + equipment.get(eqNum).getName(eqNum) + " Failed in year: 1 \ton Day: " + day);
                        daysOutofService(eqNum, j);
                    }
                }
            }
            System.out.println("");
            System.out.println("The " + equipment.get(eqNum).getName(eqNum) +  " had " + numberOfFails + " total fails.");
            System.out.println("In total the " + equipment.get(eqNum).getName(eqNum) + " was out of service for " + sum + " days. Losing " + (sum * equipment.get(eqNum).getBarrels()) + " barrels of oil.");
            System.out.println("");
        }
    }

    public static void daysOutofService(int i, int j) {
        numDaysOutofService = (double) ((double) equipment.get(i).getNumRepairDays(i, j) / 24.0);
        sum += numDaysOutofService;
        /*Might use if we can figure out how to vary the repair time, but right now it is constant and this just prints the same thing
        if (numDaysOutofService >= 1.0) {
            System.out.println("The " + equipment.get(eqNum).getName(eqNum) + " was out of service for " + numDaysOutofService + " days.");
        } else {
            System.out.println("The " + equipment.get(eqNum).getName(eqNum) + " was out of service for " + numDaysOutofService + " of a day.");
        }
        */
    }


    public static void printName(){
        for(int i = 0; i < equipment.size(); i++) {
            System.out.println(equipment.get(i).getName(i));
        }
    }

}
