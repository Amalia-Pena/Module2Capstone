package com.techelevator.capstone;

import com.techelevator.capstone.dao.JdbcCampgroundDao;
import com.techelevator.capstone.dao.JdbcParkDao;
import com.techelevator.capstone.dao.JdbcReservationDao;
import com.techelevator.capstone.dao.JdbcSiteDao;
import com.techelevator.capstone.view.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class CampgroundCLI implements CommandLineRunner {

    @Autowired
    private DataSource dataSource; // Don't need to manual create the datasource it is provided by Spring

    private JdbcParkDao jdbcParkDao;
    private JdbcCampgroundDao jdbcCampgroundDao;
    private JdbcSiteDao jdbcSiteDao;
    private JdbcReservationDao jdbcReservationDao;
    private Menu menu;
    private String parkName;
    private Long campgroundSelection;
    private Long parkSelection;
    private double dailyFee;
    private final String VIEW_CAMPGROUNDS = "View Campgrounds";
    private final String SEARCH_RESERVATION = "Search for Reservation";
    private final String RETURN = "Return to Previous Screen";
    private final String SEARCH_AVAILABLE_RESERVATION = "Search for Available Reservation";
    private String[] SELECT_CAMPGROUND_MENU = {VIEW_CAMPGROUNDS, SEARCH_RESERVATION, RETURN};
    private String[] SEARCH_RESERVATION_MENU = {SEARCH_AVAILABLE_RESERVATION, RETURN};


    public CampgroundCLI(DataSource dataSource) {
        this.dataSource = dataSource;
        this.menu = new Menu(System.in, System.out);
        // create your DAOs here
        jdbcParkDao = new JdbcParkDao(dataSource);
        jdbcCampgroundDao = new JdbcCampgroundDao(dataSource);
        jdbcSiteDao = new JdbcSiteDao(dataSource);
        jdbcReservationDao = new JdbcReservationDao(dataSource);

    }

    public static void main(String[] args) {
        SpringApplication.run(CampgroundCLI.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Create park option array
            String[] parkOptions = createParkOptionsArray();
            System.out.println("\nView Parks Interface \nSelect a Park for Further Details");

            // Get user choice for park
            String choice = (String) menu.getChoiceFromOptions(parkOptions);

            // Get park id from user choice
            parkSelection = getParkSelection(parkOptions, choice);
            System.out.println("\nSelect a Command");
            // Get user choice for campground select screen
            choice = (String) menu.getChoiceFromOptionsAlternative(SELECT_CAMPGROUND_MENU);
            if (choice.equals(VIEW_CAMPGROUNDS)) {
                createCampgroundView(parkSelection);
                choice = (String) menu.getChoiceFromOptionsAlternative(SEARCH_RESERVATION_MENU);

                if (choice.equals(SEARCH_AVAILABLE_RESERVATION)) {
                    choice = SEARCH_RESERVATION;

                } else {
                    run();
                }
            }
            if (choice.equals(SEARCH_RESERVATION)) {
                // Get campground selection
                createCampgroundView(parkSelection);
                campgroundSelection = getUserCampgroundSelection(scanner);

                if (campgroundSelection != null) {
                    // Get reservation dates (Need to test user input)
                    LocalDate[] reservationDates = getUserReservationDates(scanner);
                    createSiteView(campgroundSelection, reservationDates[0], reservationDates[1]);
                    Long reservationId = getReservationSelection(scanner, reservationDates[0], reservationDates[1]);
                    if(reservationId != null){
                        System.out.println("The reservation has been made and the confirmation id is " + reservationId);
                        System.out.println("Thank you, goodbye!");
                        System.exit(0);
                    }
                } else {
                    // Return to main menu
                    run();
                }
            } else {
                // Return to main menu
                run();
            }
        }
    }

    // Create list of parks
    public String[] createParkOptionsArray() {
        jdbcParkDao.getParks();
        String[] viewParkOptionsArray = new String[jdbcParkDao.getParkList().size() + 1];

        for (int i = 0; i < viewParkOptionsArray.length; i++) {
            if (i < viewParkOptionsArray.length - 1) {
                viewParkOptionsArray[i] = jdbcParkDao.getParkList().get(i).getName();
            } else {
                viewParkOptionsArray[i] = "quit";
            }
        }
        return viewParkOptionsArray;
    }

    public Long getParkSelection(String[] parkOptions, String choice) {
        // Convert park menu selection into park id
        for (int i = 0; i < parkOptions.length - 1; i++) {
            if (choice.equals(parkOptions[i])) {
                jdbcParkDao.getParkList().get(i).displayPark();
                parkName = jdbcParkDao.getParkList().get(i).getName();
                return jdbcParkDao.getParkList().get(i).getParkId();
            }
        }
        return null;
    }

    public void createCampgroundView(Long parkSelection) {
        jdbcCampgroundDao.createCampgroundsList(parkSelection);
        System.out.println("\nPark Campgrounds \n" + parkName + " National Park Campgrounds\n" +
                "\n     Name         Open    Close      Daily Fee");
        for (int i = 0; i < jdbcCampgroundDao.getCampGroundsList().size(); i++) {
            System.out.println("#" + (i + 1) + " " + jdbcCampgroundDao.getCampGroundsList().get(i).toString());
        }
    }

    public Long getUserCampgroundSelection(Scanner scanner) {
        Long selectedCampground = null;

        while (selectedCampground == null) {
            System.out.println("Which campground (enter 0 to cancel)?");
            String userInput = scanner.nextLine();
            try {
                int selectedOption = Integer.valueOf(userInput);
                if (selectedOption > 0 && selectedOption <= jdbcCampgroundDao.getCampGroundsList().size()) {
                    selectedCampground = jdbcCampgroundDao.getCampGroundsList().get(selectedOption - 1).getCampgroundId();
                    dailyFee = Double.valueOf(jdbcCampgroundDao.getCampGroundsList().get(selectedOption - 1).getDailyFee().substring(1));
                } else if (selectedOption == 0) {
                    break;
                }
            } catch (NumberFormatException e) {
                // eat the exception, an error message will be displayed below since choice will be null
            }
            if (selectedCampground == null) {
                System.out.println((System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator()));
            }
        }
        return selectedCampground;
    }

    public LocalDate[] getUserReservationDates(Scanner scanner) {
        String userInput;
        LocalDate[] reservationDates = new LocalDate[2];

        System.out.println("What is the arrival date? yyyy/mm/dd");
        userInput = scanner.nextLine();
        reservationDates[0] = LocalDate.of(Integer.valueOf(userInput.substring(0, 4)), Integer.valueOf(userInput.substring(5, 7)), Integer.valueOf(userInput.substring(8)));

        System.out.println("What is the departure date? yyyy/mm/dd");
        userInput = scanner.nextLine();
        reservationDates[1] = LocalDate.of(Integer.valueOf(userInput.substring(0, 4)), Integer.valueOf(userInput.substring(5, 7)), Integer.valueOf(userInput.substring(8)));

        System.out.println("Date from: " + reservationDates[0]);
        System.out.println("Date to: " + reservationDates[1]);
        return reservationDates;
    }

    public void createSiteView(Long campgroundSelection, LocalDate from_Date, LocalDate to_date) {
        jdbcSiteDao.getAvailableSites(campgroundSelection, from_Date, to_date);
        Date date1 = Date.from(from_Date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date date2 = Date.from(to_date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
        Long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        System.out.println("Results Matching Your Search Criteria\n" +
                "Site No.    Max Occup.   Accessible?   Max RV Length    Utility    Cost");
        for (int i = 0; i < jdbcSiteDao.getSiteList().size(); i++) {
            System.out.println(jdbcSiteDao.getSiteList().get(i).toString() + " $" + dailyFee * diff);
        }

    }

    public Long getReservationSelection(Scanner scanner, LocalDate from_Date, LocalDate to_Date) {
        Long reservationId = null;
        List<Long> listOfSiteNumbers = new ArrayList<>();

        for (int  i= 0;  i < jdbcSiteDao.getSiteList().size(); i++) {
            listOfSiteNumbers.add(jdbcSiteDao.getSiteList().get(i).getSiteNumber());
        }

        while (reservationId == null) {
            System.out.println("Which site should be reserved (enter 0 to cancel)?");
            String userInput = scanner.nextLine();
            try {
                Long selectedOptionLong = Long.valueOf(userInput);
                int selectedOption = Integer.valueOf(userInput);
                if (listOfSiteNumbers.contains(selectedOptionLong)) {
                    reservationId = createReservation(jdbcSiteDao.getSiteList().get(selectedOption - 1).getSiteId(), scanner, from_Date, to_Date);
                } else if (selectedOption == 0) {
                    break;
                }
            } catch (NumberFormatException e) {
                // eat the exception, an error message will be displayed below since choice will be null
            }
            if (reservationId == null) {
                System.out.println((System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator()));
            }
        }
        return reservationId;
    }

    public Long createReservation(Long siteSelection, Scanner scanner, LocalDate from_date, LocalDate to_date){
        Long reservationId = null;

        while (reservationId == null) {
            System.out.println("What name should the reservation be made under?");
            String userInput = scanner.nextLine();
            if (userInput.length() >= 0){
                reservationId = jdbcReservationDao.makeReservation(siteSelection, userInput, from_date, to_date );
            }
        }
        return reservationId;
    }

}
