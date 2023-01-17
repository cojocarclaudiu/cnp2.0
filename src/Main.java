
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.text.*;

class Main {

    private static final CNP cnp = new CNP();

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        System.out.print("CNP:");

        try {
            Long cnpCode = scanner.nextLong();
            int cnpLength = cnpLengthFunction(cnpCode);
            if (cnpLength == 13) {
                int firstIndex = firstIndexFunction(cnpCode);
                if (firstIndex != -1) {
                    //verify date!
                    String date = String.valueOf(cnpCode).substring(1, 7);
                    if (createDate(firstIndex, date)) {
                        String country = String.valueOf(cnpCode).substring(7, 9);
                        if (verifyCountry(country)) {
                            if (verifyControlKye(cnpCode)) {
                                cnp.setRegisterNumber(String.valueOf(cnpCode).substring(9, 12));
                                System.out.print("VALID\n\n");
                                System.out.println("SEX: " + cnp.getSex());
                                System.out.println("DATE OF BIRTH:" + cnp.getBirthDate());
                                System.out.println("AGE: " + cnp.getAge());
                                System.out.println("COUNTRY: " + cnp.getCountry());
                                System.out.println("REGISTER NUMBER: " + cnp.getRegisterNumber());
                            } else System.err.print("Control kye is not ok...");
                        } else System.err.print("Country is not ok...");
                    } else System.err.print("Date is not ok");
                } else System.err.print("CNP need to start with 1-8 interval.");
            } else System.err.println("CNP need to have 13 decimals." +
                    "Your cnp code have " + cnpLength + " decimals.");
        } catch (Exception e) {
            System.err.print("CNP need to me numeric...");
        }
    }

    private static int cnpLengthFunction(Long cnpCode) {
        int decimals = 0;
        while (cnpCode > 0) {
            cnpCode /= 10;
            decimals++;
        }
        return decimals;
    }

    private static int firstIndexFunction(Long cnpCode) {
        while (cnpCode > 10) {
            cnpCode /= 10;
        }
        return verify(cnpCode.intValue());
    }

    private static int verify(int cnpCode) {
        for (int i = 1; i <= 8; i++) {
            if (i == cnpCode) {
                return i;
            }
        }
        return -1;
    }

    private static boolean verifyCountry(String country) {
        int intCountry = Integer.parseInt(country);
        if (countyMap.containsKey(intCountry)) {
            cnp.setCountry(countyMap.get(intCountry));
            return true;
        } else return false;
    }

    private static boolean verifyControlKye(long cnp) {
        long controlCNP = 279146358279L;
        long controlKye = cnp % 10;
        cnp /= 10;
        long sum = 0L;
        while (cnp > 0) {
            sum += (cnp % 10) * (controlCNP % 10);
            cnp /= 10;
            controlCNP /= 10;
        }
        sum %= 11;
        return sum == controlKye || sum / 10 == controlKye;
    }

    private static boolean createDate(int index, String date){

        date = date.substring(0, 2) + "-" + date.substring(2, 4) + "-" + date.substring(4);

        switch (index) {
            case 1:
                if (verifyDateFinal("19" + date)) {
                    cnp.setSex("Male");
                    return true;
                }
                break;
            case 2:
                if (verifyDateFinal("19" + date)) {
                    cnp.setSex("Female");
                    return true;
                }
                break;
            case 3:
                if (verifyDateFinal("18" + date)) {
                    cnp.setSex("Male");
                    return true;
                }
                break;
            case 4:
                if (verifyDateFinal("18" + date)) {
                    cnp.setSex("Female");
                    return true;
                }
                break;
            case 5:
                if (verifyDateFinal("20" + date)) {
                    cnp.setSex("Male");
                    return true;
                }
                break;
            case 6:
                if (verifyDateFinal("20" + date)) {
                    cnp.setSex("Female");
                    return true;
                }
                break;
            case 7:
                if (verifyDateFinal("19" + date)) {
                    cnp.setSex("Male-Resident");
                    return true;
                }
                break;
            case 8:
                if (verifyDateFinal("19" + date)) {
                    cnp.setSex("Female-Resident");
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }

    private static boolean verifyDateFinal(String date){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            return false;
        }
        cnp.setBirthDate(LocalDate.parse(date));
        cnp.setAge(Period.between(LocalDate.parse(date), LocalDate.now()).getYears());
        return true;
    }

    private static final Map<Integer, String> countyMap = Map.ofEntries(
            Map.entry(1, "Alba"),
            Map.entry(2, "Arad"),
            Map.entry(3, "Argeș"),
            Map.entry(4, "Bacău"),
            Map.entry(5, "Bihor"),
            Map.entry(6, "Bistrița-Năsăud"),
            Map.entry(7, "Botoșani"),
            Map.entry(8, "Brașov"),
            Map.entry(9, "Brăila"),
            Map.entry(10, "Buzău"),
            Map.entry(11, "Caraș-Severin"),
            Map.entry(12, "Cluj"),
            Map.entry(13, "Constanța"),
            Map.entry(14, "Covasna"),
            Map.entry(15, "Dâmbovița"),
            Map.entry(16, "Dolj"),
            Map.entry(17, "Galați"),
            Map.entry(18, "Gorj"),
            Map.entry(19, "Harghita"),
            Map.entry(20, "Hunedoara"),
            Map.entry(21, "Ialomița"),
            Map.entry(22, "Iași"),
            Map.entry(23, "Ilfov"),
            Map.entry(24, "Maramureș"),
            Map.entry(25, "Mehedinți"),
            Map.entry(26, "Mureș"),
            Map.entry(27, "Neamț"),
            Map.entry(28, "Olt"),
            Map.entry(29, "Prahova"),
            Map.entry(30, "Satu Mare"),
            Map.entry(31, "Sălaj"),
            Map.entry(32, "Sibiu"),
            Map.entry(33, "Suceava"),
            Map.entry(34, "Teleorman"),
            Map.entry(35, "Timiș"),
            Map.entry(36, "Tulcea"),
            Map.entry(37, "Vaslui"),
            Map.entry(38, "Vâlcea"),
            Map.entry(39, "Vrancea"),
            Map.entry(40, "București"),
            Map.entry(41, "București - Sector 1"),
            Map.entry(42, "București - Sector 2"),
            Map.entry(43, "București - Sector 3"),
            Map.entry(44, "București - Sector 4"),
            Map.entry(45, "București - Sector 5"),
            Map.entry(46, "București - Sector 6"),
            Map.entry(51, "Călărași"),
            Map.entry(52, "Giurgiu"),
            Map.entry(47, "Bucuresti - Sector 7 (desfiintat)"),
            Map.entry(48, "Bucuresti - Sector 8 (desfiintat)")
    );


}