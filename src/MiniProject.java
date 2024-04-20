import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class MiniProject {
    public static String bookingHistory = "";
    public static void press() {
        Scanner input = new Scanner(System.in);
        System.out.print("Press enter to continue!");
        input.nextLine();
    }
    public static void menu() {
        System.out.println("=========[ Theater ]=========");
        System.out.println("1. Hall Booking");
        System.out.println("2. Hall Checking");
        System.out.println("3. Show Time Checking");
        System.out.println("4. Booking History");
        System.out.println("5. Rebooting Hall");
        System.out.println("6. Exit");
    }
    public static void showtime() {
        System.out.println("======================================");
        System.out.println("#Daily Showtime of Theater Hall :");
        System.out.println("# 1. Morning (10:00AM - 12:30PM)");
        System.out.println("# 2. Afternoon (3:00PM - 5:30PM");
        System.out.println("# 3. Evening (7:00PM - 9:30PM)");
        System.out.println("======================================");
    }
    public static void checkhall(String[][] morning, String[][] afternoon, String[][] evening) {
        System.out.println("======================================");
        System.out.println("\t\t\t#Hall Information");
        System.out.println("======================================");
        System.out.println("============Hall - Morning============");
        check(morning);
        System.out.println("============Hall - Afternoon==========");
        check(afternoon);
        System.out.println("============Hall - Evening============");
        check(evening);
    }

    public static void check(String[][] checkhall2) {
        char seat = 'A';
        for (int i = 0; i < checkhall2.length; i++) {
            for (int j = 0; j < checkhall2[i].length; j++) {
                System.out.print("|" + seat + "-" + (j + 1) + "::" + checkhall2[i][j] + "|");
            }
            seat++;
            System.out.println();
        }
    }

    public static void bookSeat(String[][] hall, String showtime) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter seat numbers to book (e.g., A-1, B-2): ");
        String seatNumbersInput = input.nextLine().toUpperCase();
        String[] seatNumbers = seatNumbersInput.split(",");
        StringBuilder bookedSeats = new StringBuilder();
        StringBuilder alreadyBookedSeats = new StringBuilder();

        System.out.print("Enter your name: ");
        String name = input.nextLine();
        System.out.print("Enter your ID: ");
        String id = input.nextLine();

        System.out.print("Are you sure you want to book these seats? (y/n): ");
        String confirmation = input.nextLine();

        if (confirmation.equalsIgnoreCase("y")) {
            for (String seatNumber : seatNumbers) {
                int row = seatNumber.charAt(0) - 'A';
                int col = Integer.parseInt(seatNumber.substring(2)) - 1;
                if (row < 0 || row >= hall.length || col < 0 || col >= hall[0].length) {
                    System.out.println("Invalid seat selection: " + seatNumber);
                    continue;
                }
                if (hall[row][col].equals("AV")) {
                    hall[row][col] = "BO";
                    if (bookedSeats.length() > 0) {
                        bookedSeats.append(",");
                    }
                    bookedSeats.append(seatNumber);
                    bookingHistory += showtime + ": " + name + " (ID: " + id + ") booked seat " + seatNumber + "\n";
                } else {
                    // Add the already booked seat to the list
                    if (alreadyBookedSeats.length() > 0) {
                        alreadyBookedSeats.append(",");
                    }
                    alreadyBookedSeats.append(seatNumber);
                }
            }
            if (alreadyBookedSeats.length() > 0) {
                System.out.println("Seats " + alreadyBookedSeats.toString() + " are already booked.");
            }
            if (bookedSeats.length() > 0) {
                System.out.println("Seats " + bookedSeats.toString() + " booked successfully.");
            }
        } else {
            System.out.println("Booking cancelled.");
        }
    }

    public static void showBookingHistory() {
        System.out.println("=========[ Booking History ]=========");
        if (bookingHistory.isEmpty()) {
            System.out.println("No booking history available.");
        } else {
            String[] bookings = bookingHistory.split("\n");
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(formatter);

            for (String booking : bookings) {
                System.out.println(booking + " - " +  formattedTime );
            }
        }
        System.out.println("=====================================");
    }
    public static void rebootHall(String[][]... halls) {
        System.out.println("Rebooting halls!");
        for (String[][] hall : halls) {
            for (String[] row : hall) {
                Arrays.fill(row, "AV");
            }
        }
        bookingHistory = "";
        System.out.println("Halls rebooted successfully.");
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("=========[ Set Hall]=========");
        System.out.print("Input number of rows: ");
        int row = input.nextInt();
        System.out.print("Input number of columns: ");
        int col = input.nextInt();
        String[][] morning = new String[row][col];
        String[][] afternoon = new String[row][col];
        String[][] evening = new String[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                morning[i][j] = "AV";
                afternoon[i][j] = "AV";
                evening[i][j] = "AV";
            }
        }
        while (true) {
            menu();
            System.out.print("Please choose your option: ");
            int op = input.nextInt();
            switch (op) {
                case 1 -> {
                    System.out.println("Which showtime do you want to book?");
                    System.out.println("1. Morning");
                    System.out.println("2. Afternoon");
                    System.out.println("3. Evening");
                    System.out.print("Choose showtime: ");
                    int showtimeOption = input.nextInt();
                    switch (showtimeOption) {
                        case 1 -> {
                            System.out.println("Morning Showtime Hall:");
                            check(morning);
                            bookSeat(morning, "Morning");
                            press();
                        }
                        case 2 -> {
                            System.out.println("Afternoon Showtime Hall:");
                            check(afternoon);
                            bookSeat(afternoon, "Afternoon");
                            press();
                        }
                        case 3 -> {
                            System.out.println("Evening Showtime Hall:");
                            check(evening);
                            bookSeat(evening, "Evening");
                            press();
                        }
                        default -> System.out.println("Invalid showtime option.");
                    }
                }
                case 2 -> {
                    checkhall(morning, afternoon, evening);
                    press();
                }
                case 3 -> showtime();
                case 4 -> {
                    showBookingHistory();
                    press();
                }
                case 5 -> rebootHall(morning,afternoon,evening);
                case 6 -> System.exit(0);
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}