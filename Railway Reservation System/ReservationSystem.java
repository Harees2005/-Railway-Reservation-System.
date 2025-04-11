import java.util.*;

class ReservationSystem {
    private static final int MAX_CONFIRMED = 60;
    private static final int MAX_WAITING = 10;

    static class SeatType {
        String name;
        Queue<String> confirmed = new LinkedList<>();
        Queue<String> waiting = new LinkedList<>();  

        SeatType(String name) {
            this.name = name;
        }

        boolean bookTicket(String passengerName) {
            if (confirmed.size() < MAX_CONFIRMED) {
                confirmed.add(passengerName);
                System.out.println("Booking confirmed for " + passengerName + " in " + name);
                return true;
            } else if (waiting.size() < MAX_WAITING) {
                waiting.add(passengerName);
                System.out.println("Added to waiting list for " + passengerName + " in " + name);
                return true;
            } else {
                System.out.println("No seats or waiting available in " + name);
                return false;
             
        }

        void cancelTicket(String passengerName) {
            if (confirmed.remove(passengerName)) {
                System.out.println("Booking cancelled for " + passengerName + " in " + name);
                if (!waiting.isEmpty()) {
                    String promoted = waiting.poll();
                    confirmed.add(promoted);
                    System.out.println("Passenger " + promoted + " moved from waiting to confirmed in " + name);
                }
            } else if (waiting.remove(passengerName)) {
                System.out.println("Removed " + passengerName + " from waiting list in " + name);
            } else {
                System.out.println("No booking found for " + passengerName + " in " + name);
            }
        }

        void checkAvailability() {
            int confirmedLeft = MAX_CONFIRMED - confirmed.size();
            int waitingLeft = MAX_WAITING - waiting.size();
            System.out.println(name + ": Confirmed: " + confirmedLeft + " seats, Waiting: " + waitingLeft + " spots");
        }

        void prepareChart() {
            System.out.println("\n" + name + " - Confirmed Bookings:");
            if (confirmed.isEmpty()) {
                System.out.println("None");
            } else {
                confirmed.forEach(System.out::println);
            }

            System.out.println(name + " - Waiting List:");
            if (waiting.isEmpty()) {
                System.out.println("None");
            } else {
                waiting.forEach(System.out::println);
            }
        }
    }

    private static final Map<Integer, SeatType> seatTypes = new HashMap<>();

    public static void main(String[] args) {
        seatTypes.put(1, new SeatType("AC Coach"));
        seatTypes.put(2, new SeatType("Non-AC Coach"));
        seatTypes.put(3, new SeatType("Seater"));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Railway Reservation System ---");
            System.out.println("1. Book Ticket");
            System.out.println("2. Check Availability");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Prepare Chart");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    int type = getSeatType(scanner);
                    System.out.print("Enter passenger name: ");
                    String name = scanner.nextLine();
                    seatTypes.get(type).bookTicket(name);
                }
                case 2 -> {
                    int type = getSeatType(scanner);
                    seatTypes.get(type).checkAvailability();
                }
                case 3 -> {
                    int type = getSeatType(scanner);
                    System.out.print("Enter passenger name to cancel: ");
                    String name = scanner.nextLine();
                    seatTypes.get(type).cancelTicket(name);
                }
                case 4 -> {
                    seatTypes.values().forEach(SeatType::prepareChart);
                }
                case 5 -> {
                    System.out.println("Exiting... Thank you!");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static int getSeatType(Scanner scanner) {
        System.out.println("Select Seat Type:");
        System.out.println("1. AC Coach");
        System.out.println("2. Non-AC Coach");
        System.out.println("3. Seater");
        System.out.print("Enter choice: ");
        return scanner.nextInt();
    }
}
