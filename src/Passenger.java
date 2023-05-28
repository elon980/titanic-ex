import java.util.ArrayList;

public class Passenger {
    private int passengerId;
    private boolean survived;
    private int pClass;
    private String name;
    private boolean isMale;
    private Float age;
    private int sibSp;
    private int pArch;
    private String ticket;
    private double fare;
    private String cabin;
    private char embarked;

    public Passenger(String line) {
        String[] data = line.split(",");
        try {
            this.passengerId = Integer.parseInt(data[Constants.PASSENGER_ID]);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(data[Constants.PASSENGER_ID]);
        }
        this.survived = data[Constants.SURVIVED].equals("1");
        this.pClass = Integer.parseInt(data[Constants.P_CLASS]);
        this.name = this.getFormattedName(data[Constants.FIRST_NAME], data[Constants.LAST_NAME]);
        this.isMale = data[Constants.SEX].equals("male");
        this.age = (data[Constants.AGE].equals(""))?null : Float.parseFloat(data[Constants.AGE]);
        this.sibSp = Integer.parseInt(data[Constants.SIB_SP]);
        this.pArch = Integer.parseInt(data[Constants.PARCH]);
        this.ticket = data[Constants.TICKET];
        this.fare = Double.parseDouble(data[Constants.FARE]);
        this.cabin = data[Constants.CABIN];
        if(data.length > 12){
            this.embarked = data[Constants.EMBARKED].charAt(0);
        }else{
            this.embarked = ' ';
        }
    }

    public int getpClass() {
        return pClass;
    }

    private String getFormattedName(String lastName, String firstName) {
        firstName = firstName.substring(firstName.indexOf('.') + 1).trim();
        String fullName = firstName.substring(0, firstName.length() - 1) + " " + lastName.substring(1);
        fullName = fullName.replace("\"\"", "\"");
        return fullName;
    }

    public int getPassengerId() {
        return passengerId;
    }
    public boolean passesFilters(String sibSpPassenger, String parchPassenger, String minPassengerTicketFare, String maxPassengerTicketFare, String pClass, String passengerName,
                                 String sexOfPassenger, String passengerCabin, String passengerTicket, String passengerEmbarked) {
        boolean result = false;
        if (this.checkMatch(sibSpPassenger, this.sibSp + "") &&
                this.checkMatch(parchPassenger, this.pArch + "") &&
                this.checkMatch(pClass, this.pClass + "") &&
                this.checkName(passengerName) &&
                this.checkMatch(passengerCabin, this.cabin) &&
                this.checkMatch(passengerEmbarked, this.embarked + "") &&
                this.checkMatch(passengerTicket, this.ticket)) {
            if (this.priceCheck(minPassengerTicketFare, maxPassengerTicketFare)) {
                if (this.checkSex(sexOfPassenger)) {
                    result = true;
                }
            }
        }
        return result;
    }
    private boolean checkName(String name){
        return this.name.toLowerCase().contains(name.toLowerCase());
    }

    public boolean checkSurvived(){
        return this.survived;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "passengerId=" + passengerId +
                ", survived=" + survived +
                ", pClass=" + pClass +
                ", name='" + name + '\'' +
                ", isMale=" + isMale +
                ", age=" + age +
                ", sibSp=" + sibSp +
                ", pArch=" + pArch +
                ", ticket='" + ticket + '\'' +
                ", fare=" + fare +
                ", cabin='" + cabin + '\'' +
                ", embarked=" + embarked +
                '}';
    }

    public boolean checkFamilyMember(){
        return (this.sibSp + this.pArch) > 0;
    }

    public boolean fareInRange(float min, float max){
        return this.fare >= min && this.fare < max;
    }
    public boolean matchesEmbarked(char embarked){
        return this.embarked == embarked;
    }

    private boolean checkSex(String sexOfPassenger){
        boolean check = false;
        String sex;
        if (this.isMale)
            sex = "Men";
        else
            sex = "Women";
        if (sexOfPassenger.equals("") || sexOfPassenger.equals(sex))
            check = true;
        return check;
    }
    public boolean ageInRange(float min, float max) {
        boolean result = false;
        if (this.age != null) {
            result = this.age >= min && this.age < max;
        }
        return result;
    }

    public boolean isMale() {
        return isMale;
    }

    private boolean checkMatch(String check, String original){
        boolean result = check.equals("") || check.equals(original);
        return result;
    }

    private boolean priceCheck (String min, String max) {
        boolean result = false;
        if (min.equals("") && max.equals("")) {
            result = true;
        } else {
            try {
                if (!min.equals("")) {
                    result = this.fare>Double.parseDouble(min);
                } else {
                    result = true;
                }
                if (result && !max.equals("")) {
                    result = this.fare<Double.parseDouble(max);
                }
            } catch (Exception e) {
                System.out.println("The min cant be big than max");
            }
        }
        return result;
    }

        public static boolean lineValidation(String line) {
        String[] data = line.split(",");
        boolean result = false;
        try {
            if (isNumber(data[Constants.PASSENGER_ID]) &&
                    (data[Constants.SURVIVED].equals("0") || data[Constants.SURVIVED].equals("1")) &&
                    (data[Constants.P_CLASS].equals("1") || data[Constants.P_CLASS].equals("2") || data[Constants.P_CLASS].equals("3")) &&
                    (data[Constants.SEX].equals("female") || data[Constants.SEX].equals("male")) &&
                    (data[Constants.AGE].equals("") || isNumber(data[Constants.AGE])) &&
                    (isNumber(data[Constants.SIB_SP])) &&
                    (isNumber(data[Constants.PARCH])) &&
                    (isNumber(data[Constants.FARE]))) {

                if (data.length > Constants.EMBARKED) {
                    if (data[Constants.EMBARKED].length() < 2) {
                        result = true;
                    }
                } else {
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(data[Constants.LAST_NAME] + " " +
                    data[Constants.FIRST_NAME] + " " + data[Constants.PASSENGER_ID]);
        }
        return result;
    }

    private static boolean isNumber(String number) {
        boolean result = true;
        if (number.contains(".")) {
            number = number.replaceFirst("\\.", "");
        }
        if (number.length() > 0) {
            for (int i = 0; i < number.length(); i++) {
                char c = number.charAt(i);
                if (c < '0' || c > '9') {
                    result = false;
                    break;
                }
            }
        } else {
            result = false;
        }
        return result;
    }
}
